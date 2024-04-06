package com.criteo.daos;

import com.criteo.models.internal.Campaign;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CampaignDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private Optional<Campaign> findById(Long id) {

        return namedParameterJdbcTemplate.query("select * from campaigns where id = :id",
                        Map.of("id", id),
                        DataClassRowMapper.newInstance(Campaign.class))
                .stream().findAny();
    }

    public Optional<Campaign> createCampaign(String name, Date startDate, Date endDate, BigDecimal bid) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update("""
                        insert into campaigns (name, start_date, end_date, bid)
                        values (:name, :startDate, :endDate, :bid)""",
                new MapSqlParameterSource()
                        .addValue("name", name)
                        .addValue("startDate", startDate)
                        .addValue("endDate", endDate)
                        .addValue("bid", bid),
                keyHolder, new String[]{"id"});

        return findById(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

}
