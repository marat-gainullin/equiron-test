package com.equiron;

import com.equiron.endpoints.DealsEndpoint;
import org.hamcrest.Matcher;
import org.hamcrest.core.StringContains;
import org.hamcrest.core.StringStartsWith;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author mg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class DealsEndpointTest {

    /**
     * {@link Matcher} used to match error descriptions.
     */
    private static final Matcher<String> VALIDATION_PROBLEM_MATCHER
            = new StringStartsWith(
            "{\"code\":400,\"description\":\"Validation failed");

    /**
     * {@link Matcher} used to match error descriptions.
     */
    private static final Matcher<String> SAME_COUNTERPARTY_PROBLEM_MATCHER
            = new StringContains("Same seller / customer detected");

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
        restMock = MockMvcBuilders.standaloneSetup(dealsEndpoint)
                .build();
    }

    @Test
    public void absentDealTest() throws Exception {
        restMock.perform(get("/deals/{deal-id}", 101010101010L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void dealCreationTest() throws Exception {
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
    public void badDealBodyTest() throws Exception {
        restMock.perform(post("/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content("" +
                        "{\n"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(new StringStartsWith("{\"code\":400,\"description\":\"JSON parse error: Unexpected end-of-input: expected close marker for Object")));
    }

    @Test
    public void absentDealFieldTest() throws Exception {
        restMock.perform(post("/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content("" +
                        "{\n" +
                        "}\n"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(SAME_COUNTERPARTY_PROBLEM_MATCHER));
    }

    @Test
    public void emptyDealFieldTest() throws Exception {
        restMock.perform(post("/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content("" +
                        "{\n" +
                        "  \"seller\":\"\",\n" +
                        "  \"customer\":\"\",\n" +
                        "  \"products\":[]\n" +
                        "}\n"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(SAME_COUNTERPARTY_PROBLEM_MATCHER));
    }

    @Test
    public void nullDealFieldTest() throws Exception {
        restMock.perform(post("/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content("" +
                        "{\n" +
                        "  \"seller\":null,\n" +
                        "  \"customer\":null,\n" +
                        "  \"products\":null\n" +
                        "}\n"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(SAME_COUNTERPARTY_PROBLEM_MATCHER));
    }

    @Test
    public void badCounterpartyKeyTest() throws Exception {
        restMock.perform(post("/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content("" +
                        "{\n" +
                        "  \"seller\":\"123\",\n" +
                        "  \"customer\":\"648\",\n" +
                        "  \"products\":[{\"name\":\"milk\",\"code\":\"2364758363546\"},{\"name\":\"water\",\"code\":\"3656352437590\"}]\n" +
                        "}\n"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(VALIDATION_PROBLEM_MATCHER));
        restMock.perform(post("/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content("" +
                        "{\n" +
                        "  \"seller\":\"1234567891234\",\n" +
                        "  \"customer\":\"648\",\n" +
                        "  \"products\":[{\"name\":\"milk\",\"code\":\"2364758363546\"},{\"name\":\"water\",\"code\":\"3656352437590\"}]\n" +
                        "}\n"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(VALIDATION_PROBLEM_MATCHER));
    }

    @Test
    public void emptyProductsCollectionTest() throws Exception {
        restMock.perform(post("/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content("" +
                        "{\n" +
                        "  \"seller\":\"123534251\",\n" +
                        "  \"customer\":\"648563524\",\n" +
                        "  \"products\":[]\n" +
                        "}\n"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(VALIDATION_PROBLEM_MATCHER));
    }

    @Test
    public void absentProductFieldTest() throws Exception {
        restMock.perform(post("/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content("" +
                        "{\n" +
                        "  \"seller\":\"123534251\",\n" +
                        "  \"customer\":\"648563524\",\n" +
                        "  \"products\":[{\"name\":null}]\n" +
                        "}\n"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(VALIDATION_PROBLEM_MATCHER));
    }

    @Test
    public void emptyProductFieldTest() throws Exception {
        restMock.perform(post("/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content("" +
                        "{\n" +
                        "  \"seller\":\"123534251\",\n" +
                        "  \"customer\":\"648563524\",\n" +
                        "  \"products\":[{\"name\":\"\",\"code\":\"222\"},{\"name\":\"water\",\"code\":\"\"}]\n" +
                        "}\n"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(VALIDATION_PROBLEM_MATCHER));
    }

    @Test
    public void nullProductFieldTest() throws Exception {
        restMock.perform(post("/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content("" +
                        "{\n" +
                        "  \"seller\":\"123534251\",\n" +
                        "  \"customer\":\"648563524\",\n" +
                        "  \"products\":[{\"name\":null,\"code\":\"222\"},{\"name\":\"water\",\"code\":null}]\n" +
                        "}\n"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(VALIDATION_PROBLEM_MATCHER));
    }

    @Test
    public void badProductKeyTest() throws Exception {
        restMock.perform(post("/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content("" +
                        "{\n" +
                        "  \"seller\":\"123534251\",\n" +
                        "  \"customer\":\"648563524\",\n" +
                        "  \"products\":[{\"name\":\"milk\",\"code\":\"222\"},{\"name\":\"water\",\"code\":\"333\"}]\n" +
                        "}\n"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(VALIDATION_PROBLEM_MATCHER));
        restMock.perform(post("/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content("" +
                        "{\n" +
                        "  \"seller\":\"123534251\",\n" +
                        "  \"customer\":\"648563524\",\n" +
                        "  \"products\":[{\"name\":\"milk\",\"code\":\"236475836354600000000\"},{\"name\":\"water\",\"code\":\"365635243759000000\"}]\n" +
                        "}\n"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(VALIDATION_PROBLEM_MATCHER));
    }

}
