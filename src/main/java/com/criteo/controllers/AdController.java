package com.criteo.controllers;

import com.criteo.models.internal.Product;
import com.criteo.models.out.ProductOutModel;
import com.criteo.services.ProductService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdController {

    private final ProductService productService;

    @GetMapping(path = "/ad/serve",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductOutModel serveAd(@RequestParam @NotBlank(message = "Ad category is missing.") String category) {

        Product product = productService.getHighestBidActivePromotedProductByCategory(category);
        if (product != null) {
            return ProductOutModel.of(product);
        }

        return ProductOutModel.of(productService.getHighestBidActivePromotedProduct());
    }

}
