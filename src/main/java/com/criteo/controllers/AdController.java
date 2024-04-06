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

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AdController {

    private final ProductService productService;

    @GetMapping(path = "/ad/serve", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductOutModel serveAd(@RequestParam(name = "category")
                                   @NotBlank(message = "Ad category is missing.") String category) {

        Optional<Product> product = productService.getHighestBidActivePromotedProductByCategory(category);
        if (product.isPresent()) {
            return ProductOutModel.of(product.get());
        }

        return ProductOutModel.of(productService.getHighestBidActivePromotedProduct());
    }

}
