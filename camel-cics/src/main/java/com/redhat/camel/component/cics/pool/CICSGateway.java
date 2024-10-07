package com.redhat.camel.component.cics.pool;

import com.ibm.ctg.client.GatewayRequest;
import com.ibm.ctg.client.JavaGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;


public class CICSGateway implements Closeable {
    private String id;
    private JavaGateway gateway;
    private CICSGatewayPool memberOf;
    private boolean broken;

    private static final Logger LOG = LoggerFactory.getLogger(CICSGateway.class);

    /**
     * Constructs a default CICSGateway object.
     */
    public CICSGateway() {
        this.gateway = new JavaGateway();
        this.id = generateId();
    }

    /**
     * Constructs a CICSGateway object and connects it via the defined protocol
     * to a remote Gateway daemon or local client. This constructor calls the relevant
     * setURL(), setPort() and open() methods. This has the same result as constructing
     * a default CICSGateway and then setting these properties prior to opening it.
     *
     * @param strSetURL URL specifying the remote/local Gateway to connect to
     * @param iSetPort TCP/IP port to connect to if connecting to a remote Gateway daemon.
     *                 This parameter will be ignored if connecting via local mode.
     * @throws IOException If an error occurs when opening the JavaGateway
     */
    public CICSGateway(String strSetURL, int iSetPort) throws IOException {
        this.gateway = new JavaGateway(strSetURL, iSetPort);
        this.id = generateId();
    }

    /**
     * Constructs a CICSGateway object and connects it via the defined protocol
     * to a remote Gateway daemon or local client. This constructor calls the relevant
     * setURL(), setPort(), setProtocolProperties() and open() methods. This has the
     * same result as constructing a default CICSGateway and then setting these properties prior to opening it.
     *
     * @param strSetURL URL specifying the remote/local Gateway to connect to
     * @param iSetPort TCP/IP port to connect to if connecting to a remote Gateway
     *                 daemon. This parameter will be ignored if connecting via local mode.
     * @param protocolProps any protocol specific properties
     * @throws IOException  If an error occurs when opening the JavaGateway
     */
    public CICSGateway(String strSetURL, int iSetPort, Properties protocolProps) throws IOException {
        this.gateway = new JavaGateway(strSetURL, iSetPort, protocolProps);
        this.id = generateId();
    }

    /**
     * Constructs a CICSGateway object and connects it via the defined protocol
     * to a remote Gateway daemon or local client. This constructor calls the
     * relevant setURL(), setPort(), setSecurity() and open() methods. This has
     * the same result as constructing a default CICSGateway and then setting these
     * properties prior to opening it.
     *
     * @param strSetURL URL specifying the remote/local Gateway to connect to
     * @param iSetPort TCP/IP port to connect to if connecting to a remote Gateway
     *                 daemon. This parameter will be ignored if connecting via local mode.
     * @param strSetClientSecurity Name of class implementing ClientSecurity to use on this connection
     * @param strSetServerSecurity Name of class implementing ServerSecurity to use on this connection
     * @param protocolProps  any protocol specific properties
     * @throws IOException If an error occurs when opening the CICSGateway
     */
    public CICSGateway(String strSetURL, int iSetPort, String strSetClientSecurity, String strSetServerSecurity, Properties protocolProps) throws IOException {
        this.gateway = new JavaGateway(strSetURL,  iSetPort,  strSetClientSecurity, strSetServerSecurity, protocolProps);
        this.id = generateId();
    }

    /**
     * Constructs a CICSGateway object and connects it via the defined protocol
     * to a remote Gateway daemon or local client. This constructor calls the relevant
     * setURL(), setPort(), setSecurity() and open() methods. This has the same result as
     * constructing a default CICSGateway and then setting these properties prior to opening it.
     *
     * @param strSetURL URL specifying the remote/local Gateway to connect to
     * @param iSetPort TCP/IP port to connect to if connecting to a remote Gateway daemon.
     *                 This parameter will be ignored if connecting via local mode.
     * @param strSetClientSecurity Name of class implementing ClientSecurity to use on this connection
     * @param strSetServerSecurity Name of class implementing ServerSecurity to use on this connection
     * @throws IOException If an error occurs when opening the CICSGateway
     */
    public CICSGateway(String strSetURL, int iSetPort, String strSetClientSecurity, String strSetServerSecurity) throws IOException {
        this.gateway = new JavaGateway(strSetURL, iSetPort, strSetClientSecurity, strSetServerSecurity);
        this.id = generateId();
    }

    /**
     * Constructs a CICSGateway object and connects it via the defined protocol to a
     * Gateway daemon or local client. This constructor calls the relevant setURL(),
     * setPort(), setSocketConnectTimeout(), setProtocolProperties() and open() methods.
     * This has the same result as constructing a default CICSGateway and then setting
     * these properties prior to opening it.
     *
     * @param strSetURL URL specifying the remote/local Gateway to connect to
     * @param iSetPort TCP/IP port to connect to if connecting to a remote Gateway
     *                 daemon. This parameter will be ignored if connecting via local mode.
     * @param setSocketConnectTimeout The timeout value (in milliseconds) to allow a socket to connect to a remote Gateway daemon
     * @param protocolProps any protocol specific properties
     * @throws IOException If an error occurs when opening the JavaGateway
     */
    public CICSGateway(String strSetURL, int iSetPort, int setSocketConnectTimeout, Properties protocolProps) throws IOException {
        this.gateway = new JavaGateway(strSetURL, iSetPort, setSocketConnectTimeout, protocolProps);
        this.id = generateId();
    }

    /**
     * Constructs a CICSGateway object and connects it via the defined protocol to
     * a Gateway daemon or local client. This constructor calls the relevant setURL(),
     * setPort(), setSocketConnectTimeout(), setSecurity(), setProtocolProperties() and
     * open() methods. This has the same result as constructing a default JavaGateway
     * and then setting these properties prior to opening it.
     *
     * @param strSetURL URL specifying the remote/local Gateway to connect to
     * @param iSetPort TCP/IP port to connect to if connecting to a remote Gateway daemon. This parameter will be ignored if connecting via local mode.
     * @param setSocketConnectTimeout The timeout value (in milliseconds) to allow a socket to connect to a remote Gateway daemon
     * @param strSetClientSecurity Name of class implementing ClientSecurity to use on this connection
     * @param strSetServerSecurity Name of class implementing ServerSecurity to use on this connection
     * @param protocolProps any protocol specific properties
     * @throws IOException  If an error occurs when opening the CICSGateway
     */
    public CICSGateway(String strSetURL, int iSetPort, int setSocketConnectTimeout, String strSetClientSecurity, String strSetServerSecurity, Properties protocolProps) throws IOException {
        this.gateway = new JavaGateway(strSetURL, iSetPort, setSocketConnectTimeout, strSetClientSecurity, strSetServerSecurity, protocolProps);
        this.id = generateId();
    }

    private String generateId(){
        return UUID.randomUUID().toString();
    }

    /**
     * Tests if a specified protocol string represents a local JavaGateway.
     *
     * @param protocol The protocol string to test
     * @return true if the protocol is local; otherwise false
     */
    public static boolean isLocal(String protocol) {
        return JavaGateway.isLocal(protocol);
    }

    /**
     * Get a string containing the comma-delimited package-qualified classnames
     * of the RequestExit monitors that were or will be enabled when the open()
     * method is called on this object.
     * @return the requestExits
     */
    public String getRequestExits() {
        return this.gateway.getRequestExits();
    }

    /**
     * Set the RequestExit monitors that will be enabled when the open() method
     * is called on this object. The string contains comma-delimited package-qualified
     * classnames of classes that implement the com.ibm.ctg.monitoring.RequestExit interface.
     * These classes must be available on the class path.
     * @param requestExits  String containing the requestExits to set
     */
    public void setRequestExits(String requestExits) {
        this.gateway.setRequestExits(requestExits);
    }

    /**
     * Opens this CICSGateway type object. The connection parameters currently set
     * are used to determine what type of underlying connection to create.
     * @throws IOException If an error occurs when opening the CICSGateway
     */
    public void open() throws IOException {
        if (this.gateway != null && this.gateway.isOpen()){
            LOG.debug("Connection [ID:{}] already opened",this.id);
            return;
        }
        try {
            LOG.debug("Opening connection [ID:{}]",this.id);
            this.gateway.open();
        }catch (IOException e){
            LOG.debug("Error opening connection [ID:{}]: {}", this.id, e.getMessage());
            broken = true;
            throw e;
        }
    }

    /**
     * Closes the connection to the CICS Transaction Gateway.
     * @throws IOException If a network I/O error occurs during the operation
     */
    public void close() throws IOException {
        if(this.memberOf == null) {
            LOG.debug("Closing connection [ID:{}]", this.id);
            gateway.close();
            return;
        }
        if(broken){
            LOG.debug("Evicting broken connection from pool [ID:{}]", this.id);
            memberOf.returnBrokenGateway(this);
            return;
        }
        LOG.trace("Returning connection to the pool [ID:{}]", this.id);
        memberOf.returnObject(this);
    }

    //to be used only by CICSGatewayPool
    void closeQuietly() {
        if (gateway != null) {
            try {
                gateway.close();
                LOG.trace("Closing connection quietly [ID:{}]", this.id);
            } catch (IOException e) {
                LOG.trace("Error closing connection quietly [ID:{}] {}", this.id, e.getMessage());
                // ignored
            }
        }
    }

    /**
     * Flows the specified GatewayRequest to the CICS Transaction Gateway and then
     * waits for the reply. The reply is returned in the original request object.
     * If the thread is interrupted during the flow, an IOException is thrown but
     * the request might continue to execute in the Gateway daemon. In this case
     * the state of the request object is undefined.
     *
     * @param gatRequest GatewayRequest object containing the request
     * @return Return code from this flow operation
     * @throws IOException If a network I/O error occurs or the thread is interrupted during the operation
     * @throws IllegalArgumentException - If a null object is passed to this method
     */
    public int flow(GatewayRequest gatRequest) throws IOException {
        try {
            LOG.debug("Sending request with connection [ID:{}]", this.id);
            return this.gateway.flow(gatRequest);
        }catch (IOException e){
            LOG.debug("Exception sending request with connection [ID:{}]: {}", this.id, e.getMessage());
            broken = true;
            throw e;
        }
    }

    /**
     * Set the address of the CICS Transaction Gateway that this JavaGateway instance connects to.
     * This method is only allowed when the CICSGateway is in a closed state.
     * @param strSetAddress  Address (normally TCP/IP) to connect to
     * @throws IOException If the CICSGateway is in an open state
     */
    public void setAddress(String strSetAddress) throws IOException {
        this.gateway.setAddress(strSetAddress);
    }

    /**
     * Returns the address that this CICSGateway is currently or will be connected to.
     * @return String address (normally TCP/IP) to connect to
     */
    public String getAddress() {
        return this.gateway.getAddress();
    }

    /**
     * Sets the port of the CICS Transaction Gateway that this JavaGateway instance connects to.
     * This parameter will be ignored if connecting via local mode.
     * This method is only allowed when the CICSGateway is in a closed state.
     * @param iSetPort  Port to connect to
     * @throws IOException  If the CICSGateway is in an open state
     */
    public void setPort(int iSetPort) throws IOException {
        this.gateway.setPort(iSetPort);
    }

    /**
     * Returns the port of the CICS Transaction Gateway that this CICSGateway
     * instance is currently or will be connected to. This value will be ignored
     * if connecting via local mode.
     * @return port value
     */
    public int getPort() {
        return this.gateway.getPort();
    }

    /**
     * Sets the socket connection timeout value for this CICSGateway instance.
     * This timeout represents the maximum amount of time (in milliseconds)
     * a CICSGateway instance will attempt to open a socket connection successfully
     * to a remote Gateway daemon. This parameter will be ignored if connecting via local mode.
     * This method is only allowed when the CICSGateway is in a closed state.
     * @param socketConnectTimeout The timeout value (in milliseconds). A value of 0 represents no timeout.
     * @throws IOException  If the CICSGateway is in an open state
     * @throws IllegalArgumentException  If a negative value is passed
     */
    public void setSocketConnectTimeout(int socketConnectTimeout) throws IOException {
        this.gateway.setSocketConnectTimeout(socketConnectTimeout);
    }

    /**
     * Returns the socket connect timeout value for this CICSGateway instance.
     * The timeout represents the maximum amount of time (in milliseconds)
     * a CICSGateway instance will attempt to open a socket connection successfully
     * to a remote Gateway daemon. This timeout will be ignored if connecting via local mode.
     *
     * @return socketConnectTimeout The timeout value (in milliseconds). A value of 0 represents no timeout.
     */
    public int getSocketConnectTimeout() {
        return this.gateway.getSocketConnectTimeout();
    }

    /**
     * Set the protocol that this CICSGateway will use to connect to the CICS Transaction
     * Gateway. By default TCP/IP is selected protocol.
     * This method is only allowed when the CICSGateway is in a closed state.
     * @param strSetProtocol Protocol used to connect via
     * @throws IOException If the CICSGateway is in an open state
     */
    public void setProtocol(String strSetProtocol) throws IOException {
        this.gateway.setProtocol(strSetProtocol);
    }

    /**
     * Returns the protocol that this CICSGateway instance is currently using or
     * will use to connect to the CICS Transaction Gateway.
     * @return the protocol
     */
    public String getProtocol() {
        return this.gateway.getProtocol();
    }

    /**
     * Sets the protocol, address and port of this CICSGateway by means of a single
     * URL string. The URL takes the expected form of : protocol://address:port/
     * This method is only allowed when the CICSGateway is in a closed state.
     * @param strSetURL  URL that the CICSGateway will connect to
     * @throws IOException If the CICSGateway is in an open state
     */
    public void setURL(String strSetURL) throws IOException {
        this.gateway.setURL(strSetURL);
    }

    /**
     * Returns the URL of the CICS Transaction Gateway that this CICSGateway is
     * currently or will be connected to.
     * @return String URL
     */
    public String getURL() {
        return this.gateway.getURL();
    }

    /**
     * Sets the protocol specific properties for this CICSGateway.
     * This method is only allowed when the CICSGateway is in a closed state.
     * @param newProps The protocol specific properties
     * @throws IOException  If the CICSGateway is in an open state
     */
    public void setProtocolProperties(Properties newProps) throws IOException {
        this.gateway.setProtocolProperties(newProps);
    }

    /**
     * Returns a Properties object containing any settings specific to the protocol
     * used to connect to the CICS Transaction Gateway, or null if there are none.
     * @return a Properties object containing protocol specific properties
     */
    public Properties getProtocolProperties() {
        return this.gateway.getProtocolProperties();
    }

    /**
     * Checks both our expected state and the real state of any wrappered object.
     * @return true if this CICSGateway instance is connected to a Gateway as expected
     */
    public boolean isOpen() {
       return this.gateway.isOpen();
    }

    /**
     * Sets the security classes to be used on the client and server sides of this
     * connection. The parameters specify the names of the classes to use. The client-side
     * class must implement the ClientSecurity interface, and the server-side class
     * the ServerSecurity interface.
     * This method is only allowed when the CICSGateway is in a closed state.
     *
     * @param strSetClientSecurity Name of class implementing ClientSecurity to use on this connection
     * @param strSetServerSecurity Name of class implementing ServerSecurity to use on this connection
     * @throws IOException If the CICSGateway is in an open state
     */
    public void setSecurity(String strSetClientSecurity, String strSetServerSecurity) throws IOException {
        this.gateway.setSecurity(strSetClientSecurity, strSetServerSecurity);
    }

    /**
     * When a JavaGateway instance connects to a remote Gateway, an initial flow
     * takes place. This flow includes information about security objects being used,
     * along with other information such as the Locale that the Gateway is using.
     * If you are not using security objects and are not interested in any Locale
     * information, you can choose to not incur the additional network traffic produced
     * by the initial flow. Call this method with false to turn off the initial flow.
     *
     * This method is only allowed when the CICSGateway is in a closed state.
     *
     * @param bSetInitialFlow Whether to produce initial flows or not.
     * @throws IOException If the CICSGateway is in an open state
     */
    public void setInitialFlow(boolean bSetInitialFlow) throws IOException {
        this.gateway.setInitialFlow(bSetInitialFlow);
    }

    /**
     * Checks whether an initial flow will be sent when this CICSGateway is opened.
     * @return true if an initial flow will be sent when this CICSGateway is opened.
     */
    public boolean isInitialFlow() {
        return this.gateway.isInitialFlow();
    }

    /**
     * Returns the Locale of the machine running the remote Gateway daemon if it is known.
     * This method will return null if you have yet to open the connection to the remote
     * Gateway, you disabled the initial flow which communicates this information, or you
     * are connected to a local Gateway.
     * @return Remote Gateway daemon Locale
     */
    public Locale getGatewayLocale() {
        return this.gateway.getGatewayLocale();
    }

    /**
     * Returns a string containing the operating system, architecture and version
     * of the machine running the remote Gateway daemon. This method will return null
     * if you have yet to open the connection to the remote Gateway, you disabled the
     * initial flow which communicates this information, or you are connected to a local Gateway.
     * @return String containing the operating system, architecture and version
     *         of the machine running the remote Gateway daemon.
     */
    public String getGatewayOs() {
        return this.gateway.getGatewayOs();
    }

    /**
     * Set the handling pool that this CICSGateway is part of
     * @param memberOf the handling pool
     */
    public void setHandlingPool(final CICSGatewayPool memberOf) {
        this.memberOf = memberOf;
    }

    /**
     * Check if this CICSGateway is still valid or not
     * @return true if this CICSGateway is valid
     */
    public boolean isBroken() {
        return broken;
    }

    /**
     * Return the CICSGateway id
     * @return the CICSGateway id
     */
    public String getId(){
        return this.id;
    }
}
