package com.example.priceexplorer.core;

import com.example.priceexplorer.spi.PriceQuote;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by eugene.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PriceCalculator {

    private final ChannelRegistry channelRegistry;

    public Map<String, PriceQuote> getPriceQuotes(final Instant start,
                                                  final Instant finish,
                                                  final Duration duration,
                                                  final int times) {

        final Map<String, PriceQuote> priceQuotes = new HashMap<>();
        channelRegistry.getRegisteredChannels().forEach(channel ->
                priceQuotes.put(channel.getName(), channel.getPriceQuote(start, finish, duration, times)));

        return priceQuotes;
    }
}
