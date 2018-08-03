package com.equiron.integration;

import com.equiron.EquironTestConfig;
import com.equiron.endpoints.DealsEndpoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author mg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EquironIntegrationTest.Config.class},
        loader = AnnotationConfigContextLoader.class)
public class EquironIntegrationTest extends AbstractJUnit4SpringContextTests {

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
    public void createDealTest() throws Exception {
        restMock.perform(post("/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content("" +
                        "{\n" +
                        "  \"seller\":\"123534251\",\n" +
                        "  \"customer\":\"648563524\",\n" +
                        "  \"products\":[{\"name\":\"milk\",\"code\":\"2364758363546\"},{\"name\":\"water\",\"code\":\"3656352437590\"}]\n" +
                        "}\n"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/deals/1"))
                .andExpect(content().string(""));

        restMock.perform(get("/deals/{deal-key}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("" +
                        "{\n" +
                        "  \"seller\":{\"key\":\"123534251\", \"name\":\"Equiron\"},\n" +
                        "  \"customer\":{\"key\":\"648563524\", \"name\":\"PTDC\"},\n" +
                        "  \"products\":[{\"name\":\"milk\",\"code\":\"2364758363546\"},{\"name\":\"water\",\"code\":\"3656352437590\"}]\n" +
                        "}\n"));
    }

    @Test
    public void absentDealTest() throws Exception {
        restMock.perform(get("/deals/{deal-id}", 101010101010L))
                .andExpect(status().isNotFound());
    }
}
