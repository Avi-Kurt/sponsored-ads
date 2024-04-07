create table if not exists campaigns
(
    id         bigint        not null auto_increment primary key,
    name       varchar(255)  not null,
    start_date datetime      not null,
    end_date   datetime      not null,
    bid        decimal(7, 2) not null,

    INDEX end_date_bid_id_index (end_date desc, bid desc, id desc)
);
