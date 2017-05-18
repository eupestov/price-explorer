package com.example.priceexplorer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by eugene on 14/05/17.
 */
@SpringBootApplication
public class PriceExplorerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PriceExplorerApplication.class, args);
    }
}
