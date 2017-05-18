package com.example.priceexplorer.rest;

import com.example.priceexplorer.WebSecurityConfiguration;
import com.example.priceexplorer.core.PriceCalculator;
import com.example.priceexplorer.spi.PriceQuote;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.web.header.Header;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Collections;
import java.util.Currency;

import static java.time.Duration.ofHours;
import static java.time.Duration.ofSeconds;
import static java.time.Instant.now;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.Base64Utils.encodeToString;

/**
 * Created by eugene.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PriceCalculatorController.class)
@Import(WebSecurityConfiguration.class)
public class PriceCalculatorControllerSecurityTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PriceCalculator priceCalculator;

    @Test
    public void calculatePriceUnauthorized() throws Exception {

        Instant start = now();
        Instant finish = now().plus(ofHours(1));
        Duration duration = ofSeconds(15);
        int times = 2;

        mvc.perform(
                get("/api/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("start", start.toString())
                        .param("finish", finish.toString())
                        .param("duration", duration.toString())
                        .param("times", String.valueOf(times)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void calculatePriceAuthorized() throws Exception {

        Instant start = now();
        Instant finish = now().plus(ofHours(1));
        Duration duration = ofSeconds(15);
        int times = 2;

        mvc.perform(
                get("/api/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + encodeToString("user:user".getBytes()))
                        .param("start", start.toString())
                        .param("finish", finish.toString())
                        .param("duration", duration.toString())
                        .param("times", String.valueOf(times)))
                .andExpect(status().isOk());
    }

}
