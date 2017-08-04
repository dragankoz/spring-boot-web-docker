package org.demostuff;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoServiceIT {

    private static final Logger LOG = LoggerFactory.getLogger(DemoServiceIT.class);

    private String appUrl;

    @Before
    public void setup() {
        appUrl = System.getProperty("app.url");
        if (StringUtils.isEmpty(appUrl)) {
            throw new IllegalArgumentException("System property  app.url is not defined!");
        }
    }

    @Test
    public void testLog() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(appUrl, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertTrue(response.getBody().contains("Hello There!"));
    }

}
