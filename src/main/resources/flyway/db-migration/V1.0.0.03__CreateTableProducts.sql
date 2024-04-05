-- -------------------------------------- --
-- Create Table `Products`  if not exists --
-- -------------------------------------- --
create table if not exists products
(
    id            bigint unsigned not null auto_increment primary key,
    campaign_id   bigint unsigned not null,
    title         varchar(255)    not null,
    category      varchar(255)    not null,
    price         decimal(7, 2)   not null,
    serial_number varchar(255)    not null,

    constraint fk_campaign_id_campaigns foreign key (campaign_id) references campaigns (id)
);
