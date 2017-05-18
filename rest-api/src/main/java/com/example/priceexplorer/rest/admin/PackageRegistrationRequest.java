package com.example.priceexplorer.rest.admin;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by eugene on 18/05/17.
 */
@Data
public class PackageRegistrationRequest {
    @NotNull
    @Size(min = 1, max = 64)
    private String name;
    @NotNull
    @Size(min = 5)
    private String file;
}
