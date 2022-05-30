package com.learning.urlshortener;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ServletWebServerFactoryAutoConfiguration.class})
class UrlShortenerApplicationTests extends TestContainerSupplier {

    @Test
    void contextLoads() {

    }
}
