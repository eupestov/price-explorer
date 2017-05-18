package com.example.priceexplorer.rest.admin;

import com.example.priceexplorer.WebSecurityConfiguration;
import com.example.priceexplorer.core.ChannelRegistry;
import com.example.priceexplorer.core.PriceCalculator;
import com.example.priceexplorer.rest.PriceCalculatorController;
import com.example.priceexplorer.service.TmpFileService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

import static java.time.Duration.ofHours;
import static java.time.Duration.ofSeconds;
import static java.time.Instant.now;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.Base64Utils.encodeToString;

/**
 * Created by eugene.
 */
//@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ChannelAdminController.class)
@Import(WebSecurityConfiguration.class)
public class ChannelAdminControllerSecurityTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ChannelRegistry channelRegistry;

    @MockBean
    private TmpFileService tmpFileService;

    @Before
    public void setup() {
        given(channelRegistry.listChannelPackages()).willReturn(Stream.empty());
    }

    @Test
    public void listPackagesUnauthorized() throws Exception {

        mvc.perform(
                get("/api/admin/packages")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void listPackagesAuthorized() throws Exception {

        mvc.perform(
                get("/api/admin/packages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + encodeToString("admin:admin".getBytes())))
                .andExpect(status().isOk());
    }

}
