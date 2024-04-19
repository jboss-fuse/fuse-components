package com.redhat.camel.component.cics.pool;

import com.redhat.camel.component.cics.exceptions.CICSException;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public class CICSPooledGatewayFactory implements CICSGatewayFactory {
    private static final Logger LOG = LoggerFactory.getLogger(CICSPooledGatewayFactory.class);

    private static final int DEFAULT_INITIAL_POOL_SIZE = 5;

    private CICSGatewayFactory factory;
    private GenericObjectPoolConfig<CICSGateway> config;
    private CICSGatewayPool gatewayPool;
    private  AtomicBoolean stopped = new AtomicBoolean(true);
    /*
        The protocol that this JavaGateway instance is currently using or will
        use to connect to the CICS Transaction Gateway.
     */
    private String protocol;
    /*
        The address that this JavaGateway is currently or will be connected to.
     */
    private String host;
    /*
        The port of the CICS Transaction Gateway that this JavaGateway instance is currently or will be connected to.
     */
    private int port;
    /*
         if true, an initial flow takes place when a JavaGateway instance connects to a remote Gateway
     */
    private boolean initialFlow;

    private String sslKeyring;
    private String sslPassword;
    private int socketConnectionTimeout;
    private Properties protocolProperties;

    private int initialPoolSize = DEFAULT_INITIAL_POOL_SIZE;

    @Override
    public CICSGateway createGateway() throws Exception {
        if (stopped.get()) {
            LOG.debug("CICSPooledGatewayFactory is stopped, cannot create a new connection.");
            throw new CICSException("CICSPooledGatewayFactory is stopped, cannot create a new connection");
        }
        if (factory == null) {
            throw new IllegalStateException("No CICSGatewayFactory instance has been configured");
        }
        CICSGateway cpg = gatewayPool.borrowObject();
        return cpg;
    }

    /**
     * Starts the Connection pool.
     * <p>
     * If configured to do so this method will attempt to create an initial Gateway to place
     * into the pool using the default {@link CICSSingleGatewayFactory#createGateway()} from the configured
     * provider {@link CICSSingleGatewayFactory}.
     */
    public void start() throws Exception {
        LOG.debug("Starting the JmsPoolConnectionFactory.");
        initGatewayPool();
        stopped.set(false);
    }

    /**
     * Stops the pool from providing any new connections and closes all pooled Connections.
     * <p>
     * This method stops services from the Java Gateway Pool closing down any Gateway in
     * the pool regardless of them being loaned out at the time.  The pool cannot be restarted
     * after a call to stop.
     */
    public void stop() {
        if (stopped.compareAndSet(false, true)) {
            LOG.debug("Stopping the JmsPoolConnectionFactory, number of connections in cache: {}",
                    gatewayPool != null ? gatewayPool.getNumActive() : 0);
            try {
                if (gatewayPool != null) {
                    gatewayPool.close();
                    gatewayPool = null;
                }
            } catch (Exception ignored) {
                LOG.trace("Caught exception on close of the Gateway pool: ", ignored);
            }
        }
    }

    private GenericObjectPoolConfig<CICSGateway> getConfig() {
        if (this.config == null) {
            this.config = new GenericObjectPoolConfig<>();
        }
        return this.config;
    }

    public CICSPooledGatewayFactory(CICSSingleGatewayFactory factory) {
        this.factory = factory;
    }

    private void initGatewayPool() throws Exception {

        if(gatewayPool == null) {
            PooledObjectFactory<CICSGateway> pooledObjectFactory = new PooledObjectFactory<CICSGateway>() {
                    @Override
                    public void activateObject(PooledObject<CICSGateway> pooledGateway) throws Exception {
                        LOG.trace("Activating Gateway [ID:{}]",pooledGateway.getObject().getId());
                        final CICSGateway gw = pooledGateway.getObject();
                        gw.setHandlingPool(gatewayPool);
                        if (!gw.getAddress().equals(factory.getHost()) || gw.getPort() != factory.getPort()) {
                            gw.setAddress(factory.getHost());
                            gw.setPort(factory.getPort());
                        }
                    }
                    @Override
                    public void destroyObject(PooledObject<CICSGateway> pooledGateway) throws Exception {
                        LOG.trace("Destroying Gateway [ID:{}]",pooledGateway.getObject().getId());
                        final CICSGateway gw = pooledGateway.getObject();
                        gw.setHandlingPool(null);
                        if (gw.isOpen()) {
                            gw.closeQuietly();
                        }
                    }
                    @Override
                    public PooledObject<CICSGateway> makeObject() throws Exception {
                        CICSGateway gw = factory.createGateway();
                        LOG.trace("Creating Gateway [ID:{}]", gw.getId());
                        return new DefaultPooledObject<>(gw);
                    }
                    @Override
                    public void passivateObject(PooledObject<CICSGateway> pooledGateway) throws Exception {
                        //TODO: waiting for an answer from IBM guys about if it's possible and
                        // how to passivate (reset state, if there is any) a JavaGateway object
                        LOG.trace("passivate Gateway {}", pooledGateway.getObject().getId() );
                    }
                    @Override
                    public boolean validateObject(PooledObject<CICSGateway> pooledGateway) {
                        // disable validation and manage the broken connection objects when an
                        // exception occurs.
                        return true;
                    }
            };
            gatewayPool = new CICSGatewayPool(pooledObjectFactory, getConfig());
            gatewayPool.addObjects(initialPoolSize);
        }
    }

    // Gateway config

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean isInitialFlow() {
        return initialFlow;
    }

    @Override
    public void setInitialFlow(boolean initialFlow) {
        this.initialFlow = initialFlow;
    }

    @Override
    public String getSslKeyring() {
        return sslKeyring;
    }

    @Override
    public void setSslKeyring(String sslKeyring) {
        this.sslKeyring = sslKeyring;
    }

    @Override
    public String getSslPassword() {
        return sslPassword;
    }

    @Override
    public void setSslPassword(String sslPassword) {
        this.sslPassword = sslPassword;
    }

    @Override
    public int getSocketConnectionTimeout() {
        return socketConnectionTimeout;
    }

    @Override
    public void setSocketConnectionTimeout(int socketConnectionTimeout) {
        this.socketConnectionTimeout = socketConnectionTimeout;
    }

    @Override
    public Properties getProtocolProperties() {
        return protocolProperties;
    }

    @Override
    public void setProtocolProperties(Properties protocolProperties) {
        this.protocolProperties = protocolProperties;
    }

    // Getters and Setters
    public CICSGatewayFactory getFactory() {
        return factory;
    }

    public void getFactory(CICSGatewayFactory factory) {
         this.factory = factory;
    }


    // pool config

    public boolean getLifo() {
        return this.getConfig().getLifo();
    }

    public void setLifo(boolean lifo) {
        this.getConfig().setLifo(lifo);
    }

    public boolean getFairness() {
        return this.getConfig().getFairness();
    }

    public void setFairness(boolean fairness) {
        this.getConfig().setFairness(fairness);
    }

    public Duration getMaxWaitDuration() {
        return this.getConfig().getMaxWaitDuration();
    }

    public void setMaxWait(Duration maxWaitDuration) {
        this.getConfig().setMaxWait(maxWaitDuration);
    }

    public boolean isBlockWhenExhausted() {
        return this.getConfig().getBlockWhenExhausted();
    }

    public void setBlockWhenExhausted(boolean blockWhenExhausted) {
         this.getConfig().setBlockWhenExhausted(blockWhenExhausted);
    }

    public boolean isJmxEnabled() {
        return this.getConfig().getJmxEnabled();
    }

    public void setJmxEnabled(boolean jmxEnabled) {
         this.getConfig().setJmxEnabled(jmxEnabled);
    }

    public String getJmxNamePrefix() {
        return this.getConfig().getJmxNamePrefix();
    }

    public void setJmxNamePrefix(String jmxNamePrefix) {
         this.getConfig().setJmxNamePrefix(jmxNamePrefix);
    }

    public String getJmxNameBase() {
        return this.getConfig().getJmxNameBase();
    }

    public void setJmxNameBase(String jmxNameBase) {
         this.getConfig().setJmxNameBase(jmxNameBase);
    }

    public int getMaxTotal() {
        return this.getConfig().getMaxTotal();
    }

    public void setMaxTotal(int maxTotal) {
         this.getConfig().setMaxTotal(maxTotal);
    }

    public int getMaxIdle() {
        return this.getConfig().getMaxIdle();
    }

    public void setMaxIdle(int maxIdle) {
         this.getConfig().setMaxIdle(maxIdle);
    }

    public int getMinIdle() {
        return this.getConfig().getMinIdle();
    }

    public void setMinIdle(int minIdle) {
         this.getConfig().setMinIdle(minIdle);
    }

    public int getInitialPoolSize() {
        return initialPoolSize;
    }

    public void setInitialPoolSize(int initialPoolSize) {
        this.initialPoolSize = initialPoolSize;
    }
}
