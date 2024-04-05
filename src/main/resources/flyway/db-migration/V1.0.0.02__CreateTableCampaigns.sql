-- --------------------------------------- --
-- Create Table `Campaigns`  if not exists --
-- --------------------------------------- --
create table if not exists campaigns
(
    id         bigint unsigned not null auto_increment primary key,
    name       varchar(255)    not null,
    start_date datetime        not null,
    end_date   datetime        not null,
    bid        decimal(7, 2)   not null
);
