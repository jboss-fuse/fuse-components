package com.redhat.camel.component.cics;

import org.apache.camel.test.junit5.CamelTestSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.MountableFile;

import java.time.Duration;

public class AbstractCICSContainerizedTest  extends CamelTestSupport {

    public static final Logger LOG = LoggerFactory.getLogger(AbstractCICSContainerizedTest.class);

    protected final static Network network = Network.newNetwork();
    /** CTG Server */

    protected static final GenericContainer<?> ctgContainer =
            new GenericContainer<>("images.paas.redhat.com/fuseqe/ibm-cicstg-container-linux-x86-trial:9.3")
                    .withEnv("LICENSE","accept")
                    .withNetwork(network)
                    .withNetworkAliases("ctg")
                    .withExposedPorts(2006, 2810)
                    .withCopyFileToContainer(MountableFile.forClasspathResource("ctg.ini"), "/var/cicscli/ctg.ini")
                    .waitingFor(Wait.forLogMessage(".*CTG6512I CICS Transaction Gateway initialization complete.*",1))
                    .withStartupTimeout(Duration.ofSeconds(60L));

    @Override
    protected void doPreSetup() throws Exception {
        ctgContainer.start();
    }

}
