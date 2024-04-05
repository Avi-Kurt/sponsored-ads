package com.criteo.services;

import com.criteo.daos.ProductDao;
import com.criteo.models.in.ProductInModel;
import com.criteo.models.internal.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    @Transactional
    public Optional<Integer> createProductsForCampaign(Long campaignId, List<ProductInModel> products) {
        return productDao.createProductsForCampaign(campaignId, products);
    }

    public List<Product> getProductsByCampaignId(Long campaignId) {
        return productDao.findProductsByCampaignId(campaignId);
    }

    public Optional<Product> getHighestBidActivePromotedProductByCategory(String category) {
        return productDao.findHighestBidActivePromotedProductByCategory(category);
    }

    public Product getHighestBidActivePromotedProduct() {
        return productDao.findHighestBidActivePromotedProduct();
    }

}
