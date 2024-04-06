package com.criteo.daos;

import com.criteo.exceptions.FailedToCreateException;
import com.criteo.models.in.ProductInModel;
import com.criteo.models.internal.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<Integer> createProductsForCampaign(Long campaignId, List<ProductInModel> products) {
        Integer effectedRows = 0;
        if (products == null || products.isEmpty()) {
            return Optional.of(effectedRows);
        }

        List<SqlParameterSource> bach = new ArrayList<>();
        products.forEach(product -> {
            SqlParameterSource record = new MapSqlParameterSource()
                    .addValue("campaignId", campaignId)
                    .addValue("title", product.getTitle())
                    .addValue("category", product.getCategory())
                    .addValue("price", product.getPrice());
            bach.add(record);
        });

        int[] insertResults = namedParameterJdbcTemplate.batchUpdate("""
                        insert into products (campaign_id, title, category, price, serial_number)
                        values (:campaignId, :title, :category, :price, UUID())""",
                bach.toArray(new SqlParameterSource[bach.size()]));
        bach.clear();

        for (int result : insertResults) {
            if (result == 1) {
                effectedRows++;
            } else {
                throw new FailedToCreateException("Unable to save Products Batch.");
            }
        }

        return Optional.of(effectedRows);
    }

    public List<Product> findProductsByCampaignId(Long campaignId) {

        return namedParameterJdbcTemplate.query("""
                        select * from products where campaign_id = :campaignId;""",
                Map.of("campaignId", campaignId),
                DataClassRowMapper.newInstance(Product.class));
    }

    public Optional<Product> findHighestBidActivePromotedProductByCategory(String category) {

        return namedParameterJdbcTemplate.query("""
                        select products.* from products
                            inner join campaigns
                                on products.campaign_id = campaigns.id
                        where end_date >= now()
                            and category = :category
                        order by bid desc
                        limit 1;""",
                Map.of("category", category), // Not Case sensitive!
                DataClassRowMapper.newInstance(Product.class)).stream().findAny();
    }

    public Optional<Product> findHighestBidActivePromotedProduct() {

        return namedParameterJdbcTemplate.query("""
                        select * from products
                            where campaign_id in (SELECT c.id FROM campaigns c
                                                    WHERE c.end_date >= now()
                                                    and c.bid = (select max(subc.bid) from campaigns subc))
                        limit 1;""",
                DataClassRowMapper.newInstance(Product.class)).stream().findAny();
    }

}
