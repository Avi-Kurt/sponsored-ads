create table if not exists products
(
    id            bigint        not null auto_increment primary key,
    campaign_id   bigint        not null,
    title         varchar(255)  not null,
    category      varchar(255)  not null,
    price         decimal(7, 2) not null,
    serial_number varchar(255)  not null unique,

    constraint fk_campaign_id_campaigns foreign key (campaign_id) references campaigns (id),
    INDEX category_campaign_id_id_index (category desc, campaign_id desc, id desc)
);
