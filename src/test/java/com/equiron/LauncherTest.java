package com.equiron;

import org.apache.catalina.LifecycleException;
import org.junit.Test;

import javax.servlet.ServletException;
import java.net.URISyntaxException;

public class LauncherTest {

    @Test
    public void startStop() throws ServletException,
            LifecycleException, URISyntaxException {
        Launcher.start("/equiron-test", 8189).stop();
    }
}
