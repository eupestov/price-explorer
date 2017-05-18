package com.example.priceexplorer.spi;

import lombok.Value;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Created by eugene on 18/05/17.
 */
@Value
public class PriceQuote {
    private BigDecimal price;
    private Currency currency;
}
