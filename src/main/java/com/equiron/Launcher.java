package com.equiron;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.Server;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Standalone mode main class.
 */
public final class Launcher {

    /**
     * Utility class hidden constructor.
     */
    private Launcher() {
    }

    /**
     * Entry point method.
     *
     * @param args Array of command line arguments
     * @throws ServletException   if problems occur while web application
     *                            configuration.
     * @throws LifecycleException if web application configured incorrectly.
     * @throws URISyntaxException if problems with {@link Launcher} occur.
     * @throws IOException        if problems with IO occur.
     */
    public static void main(final String[] args) throws ServletException,
            LifecycleException, URISyntaxException, IOException {
        start("/equiron", Integer.valueOf(Optional.ofNullable(
                System.getProperty("http.port")).orElse("8080")
        ));
        System.in.read();
    }

    /**
     * Configures and starts an embedded tomcat server.
     *
     * @param contextPath Context path, the micro service application should
     *                    be deployed at.
     * @param port        Port, starting server will listen ro
     * @return {@link Server} instance started.
     * @throws ServletException   if problems occur while web application
     *                            configuration.
     * @throws LifecycleException if web application configured incorrectly.
     * @throws URISyntaxException if problems with {@link Launcher} occur.
     */
    static Server start(final String contextPath, final int port)
            throws ServletException,
            LifecycleException, URISyntaxException {
        URL codeLocation = Launcher.class.getProtectionDomain()
                .getCodeSource().getLocation();
        Path selfPath = Paths.get(codeLocation.toURI());
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.getHost().setAppBase(".");
        tomcat.addWebapp(contextPath, selfPath.toFile().getAbsolutePath());
        tomcat.start();
        System.out.println("Equiron service started at http://localhost:" + port + contextPath);
        System.out.print("To terminate press enter...");
        return tomcat.getServer();
    }
}
