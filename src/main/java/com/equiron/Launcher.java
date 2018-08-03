package com.equiron;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
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
     */
    public static void main(final String[] args) throws ServletException,
            LifecycleException, URISyntaxException {
        String contextPath = "/equiron";
        URL codeLocation = Launcher.class.getProtectionDomain()
                .getCodeSource().getLocation();
        Path selfPath = Paths.get(codeLocation.toURI());
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.valueOf(Optional.ofNullable(
                System.getProperty("http.port")).orElse("8080")
        ));
        tomcat.getHost().setAppBase(".");
        tomcat.addWebapp(contextPath, selfPath.toFile().getAbsolutePath());
        tomcat.start();
        tomcat.getServer().await();
    }
}
