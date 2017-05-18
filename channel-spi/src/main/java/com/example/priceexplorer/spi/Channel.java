package com.example.priceexplorer.spi;

import java.time.Duration;
import java.time.Instant;

/**
 * Service Provider Interface for calculating prices of commercials
 * for a specific TV channel.
 *
 * Created by eugene on 14/05/17.
 */
public interface Channel {

    String getName();

    PriceQuote getPriceQuote(Instant start,
                             Instant finish,
                             Duration duration,
                             int times);
}
