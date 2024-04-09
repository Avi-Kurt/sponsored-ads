create table if not exists campaigns
(
    id         bigint        not null auto_increment primary key,
    name       varchar(255)  not null,
    start_date datetime      not null,
    end_date   datetime      not null,
    bid        decimal(7, 2) not null,

    INDEX bid_id_index (bid desc, id),
    INDEX start_date_end_date_id_index (start_date desc, end_date desc, id desc)
);
