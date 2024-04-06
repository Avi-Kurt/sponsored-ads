package com.criteo.controllers;

import com.criteo.constants.ResponseCode;
import com.criteo.models.internal.Product;
import com.criteo.models.out.ProductOutModel;
import com.criteo.models.out.ValidResponse;
import com.criteo.services.ProductService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    public ValidResponse serveAd(@RequestParam(name = "category")
                                 @NotBlank(message = "Product category is missing.")
                                 @Size(max = 255, message = "Product category cannot be longer than 255 characters.")
                                 String category) {

        Optional<Product> product = productService.getHighestBidActivePromotedProductByCategory(category);
        if (product.isPresent()) {
            return ValidResponse.of(ProductOutModel.of(product.get()));
        }

        product = productService.getHighestBidActivePromotedProduct();
        if (product.isPresent()) {
            return ValidResponse.of(ProductOutModel.of(product.get()));
        }

        return ValidResponse.of("No Data.", ResponseCode.NOT_FOUND);
    }

}
