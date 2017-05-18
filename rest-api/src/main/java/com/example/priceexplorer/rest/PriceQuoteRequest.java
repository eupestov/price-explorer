package com.example.priceexplorer.rest;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.Instant;

/**
 * Created by eugene on 18/05/17.
 */
@Data
public class PriceQuoteRequest {
    @NotNull
    private Instant start;
    @NotNull
    private Instant finish;
    @NotNull
    private Duration duration;
    @Min(1)
    private int times = 1;
}
