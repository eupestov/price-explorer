package com.example.priceexplorer.core;

import com.example.priceexplorer.spi.Channel;
import com.example.priceexplorer.spi.PriceQuote;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Currency;
import java.util.Map;

import static java.time.Duration.ofHours;
import static java.time.Duration.ofSeconds;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by eugene on 18/05/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class PriceCalculatorTest {

    @Mock
    private ChannelRegistry channelRegistry;

    @InjectMocks
    private PriceCalculator priceCalculator;

    @Test
    public void getPriceQuotes() {

        Instant start = Instant.now();
        Instant finish = start.plus(ofHours(2));
        Duration duration = ofSeconds(15);
        int times = 2;

        PriceQuote testQuote = new PriceQuote(new BigDecimal("10.50"), Currency.getInstance("USD"));
        TestChannel testChannel = new TestChannel("Test", testQuote);
        when(channelRegistry.getRegisteredChannels()).thenReturn(singletonList(testChannel));

        Map<String, PriceQuote> quotes = priceCalculator.getPriceQuotes(start, finish, duration, times);

        assertNotNull("Null result", quotes);
        assertEquals("The result set size is wrong", 1, quotes.size());
        assertTrue("No quote for Test channel", quotes.containsKey(testChannel.getName()));
        assertEquals("Wrong quote returend", testQuote, quotes.get(testChannel.getName()));
    }

    @RequiredArgsConstructor
    private static class TestChannel implements Channel {

        private final String name;
        private final PriceQuote priceQuote;

        @Override
        public String getName() {
            return name;
        }

        @Override
        public PriceQuote getPriceQuote(final Instant start, final Instant finish, final Duration duration, final int times) {
            return priceQuote;
        }
    }

}
