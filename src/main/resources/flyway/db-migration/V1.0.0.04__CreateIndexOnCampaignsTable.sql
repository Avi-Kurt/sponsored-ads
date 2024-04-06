-- --------------------------------------------------------------------- --
-- Create `end_date_bid_id_index` Index on Campaigns Table if not exists --
-- --------------------------------------------------------------------- --
SET @preparedStatement = (SELECT IF(
                                         (SELECT COUNT(*)
                                          FROM INFORMATION_SCHEMA.STATISTICS
                                          WHERE (table_name = 'campaigns')
                                            AND (INDEX_NAME = 'end_date_bid_id_index')) > 0,
                                         'SELECT 1;',
                                         'ALTER TABLE campaigns
                                            ADD INDEX end_date_bid_id_index(end_date desc, bid desc, id desc);'
                                 ));

PREPARE alterIfNotExists FROM @preparedStatement;
EXECUTE alterIfNotExists;
DEALLOCATE PREPARE alterIfNotExists;
