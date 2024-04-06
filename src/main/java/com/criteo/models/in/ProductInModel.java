package com.criteo.models.in;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class ProductInModel {

    @NotBlank(message = "Product title is missing.")
    @Size(max = 255, message = "Product title cannot be longer than 255 characters.")
    private String title;

    @NotBlank(message = "Product category is missing.")
    @Size(max = 255, message = "Product category cannot be longer than 255 characters.")
    private String category;

    @NotNull(message = "Product price is missing.")
    @DecimalMin(value = "0.1", message = "Product price cannot be less than 0.1.")
    @DecimalMax(value = "99999.99", message = "Product price cannot be higher than 99999.99.")
    private BigDecimal price;

}
