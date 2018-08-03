package com.equiron.functional;

import com.equiron.EquironTestConfig;
import com.equiron.endpoints.DealsEndpoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author mg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EquironFunctionTest.Config.class},
        loader = AnnotationConfigContextLoader.class)
public class EquironFunctionTest extends AbstractJUnit4SpringContextTests {

    /**
     * Configuration stub for spring <code>SpringJUnit4ClassRunner</code>
     *
     * @see SpringJUnit4ClassRunner
     */
    @Configuration
    public static class Config extends EquironTestConfig {
    }

    /**
     * An <code>DealsEndpoint</code> autowired instance.
     *
     * @see DealsEndpoint
     */
    @Autowired
    private DealsEndpoint dealsEndpoint;

    /**
     * Spring's test mockery.
     *
     * @see MockMvc
     */
    private MockMvc restMock;

    /**
     * Setup of Spring's mockery.
     */
    @Before
    public void setup() {
        restMock = MockMvcBuilders.standaloneSetup(dealsEndpoint).build();
    }

    @Test
    public void implementMe() {
    }
}
