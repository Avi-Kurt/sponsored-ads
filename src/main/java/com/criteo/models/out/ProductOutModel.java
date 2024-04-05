package com.criteo.models.out;

import com.criteo.models.internal.Product;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Builder
public class ProductOutModel {

    private String title;
    private String category;
    private BigDecimal price;
    private String serialNumber;

    public static ProductOutModel of(Product product) {

        if (product == null) {
            return null;
        }

        return ProductOutModel.builder()
                .title(product.getTitle())
                .category(product.getCategory())
                .price(product.getPrice())
                .serialNumber(product.getSerialNumber())
                .build();
    }

    public static List<ProductOutModel> of(List<Product> products) {

        if (products == null || products.isEmpty()) {
            return Collections.emptyList();
        }

        return products.stream()
                .filter(Objects::nonNull)
                .map(ProductOutModel::of)
                .collect(Collectors.toList());
    }

}
