package org.demostuff;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

@RunWith(SpringRunner.class)
public class DemoServiceIT {

    private static final Logger LOG = LoggerFactory.getLogger(DemoServiceIT.class);

    private String springAppUrl;

    @Before
    public void setup() {
        springAppUrl = System.getProperty("spring.app.url");
        LOG.info("spring.app.url={}", springAppUrl);
        if (StringUtils.isEmpty(springAppUrl)) {
            throw new IllegalArgumentException("System property spring.app.url is not defined!");
        }
    }

    @Test
    public void testLog() throws Exception {
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(springAppUrl, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertTrue(response.getBody().contains("Hello There!"));
    }

}
