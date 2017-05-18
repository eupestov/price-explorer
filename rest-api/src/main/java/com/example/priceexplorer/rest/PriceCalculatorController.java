package com.example.priceexplorer.rest;

import com.example.priceexplorer.core.PriceCalculator;
import com.example.priceexplorer.spi.PriceQuote;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * User facing controller for price calculations.
 *
 * Created by eugene on 14/05/17.
 */
@Slf4j
@RestController
@RequestMapping("/api/price")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PriceCalculatorController {

    private final PriceCalculator priceCalculator;

    @GetMapping
    public Map<String, PriceQuote> calculatePrice(@Valid PriceQuoteRequest request) {
        log.debug(request.toString());
        return priceCalculator.getPriceQuotes(request.getStart(), request.getFinish(),
                request.getDuration(), request.getTimes());
    }
}
