package com.example.test;

import com.example.priceexplorer.spi.Channel;
import com.example.priceexplorer.spi.PriceQuote;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Currency;

/**
 * Created by eugene on 16/05/17.
 */
public class BBC implements Channel {

    @Override
    public String getName() {
        return "BBC";
    }

    @Override
    public PriceQuote getPriceQuote(final Instant start,
                                    final Instant finish,
                                    final Duration duration,
                                    final int times) {

        return new PriceQuote(new BigDecimal("149.99"), Currency.getInstance("GBP"));
    }

}
