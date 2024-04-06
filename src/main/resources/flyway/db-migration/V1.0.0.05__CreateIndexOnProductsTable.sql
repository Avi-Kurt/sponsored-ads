-- ---------------------------------------------------------------------------- --
-- Create `category_campaign_id_id_index` Index on Products Table if not exists --
-- ---------------------------------------------------------------------------- --
SET @preparedStatement = (SELECT IF(
                                         (SELECT COUNT(*)
                                          FROM INFORMATION_SCHEMA.STATISTICS
                                          WHERE (table_name = 'products')
                                            AND (INDEX_NAME = 'category_campaign_id_id_index')) > 0,
                                         'SELECT 1;',
                                         'ALTER TABLE products
                                            ADD INDEX category_campaign_id_id_index(category desc, campaign_id desc, id desc);'
                                 ));

PREPARE alterIfNotExists FROM @preparedStatement;
EXECUTE alterIfNotExists;
DEALLOCATE PREPARE alterIfNotExists;
