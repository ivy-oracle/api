CREATE TABLE delegation_event (
    delegation_event_id bigserial NOT NULL,
    block_number numeric NULL,
    from_address varchar(255) NULL,
    log_index numeric NULL,
    new_vote_power numeric NULL,
    prior_vote_power numeric NULL,
    to_address varchar(255) NULL,
    transaction_hash varchar(255) NULL,
    transaction_index numeric NULL,
    CONSTRAINT delegation_event_pkey PRIMARY KEY (delegation_event_id),
    CONSTRAINT unique_logs UNIQUE (block_number, transaction_index, log_index)
);

CREATE TABLE ftso_data_provider (
    ftso_data_provider_id bigserial NOT NULL,
    address varchar(255) NULL,
    CONSTRAINT ftso_data_provider_pkey PRIMARY KEY (ftso_data_provider_id)
);

CREATE TABLE price_finalized_event (
    price_finalized_event_id bigserial NOT NULL,
    epoch_id numeric NULL,
    finalization_type numeric(19, 2) NULL,
    high_reward_price numeric NULL,
    low_reward_price numeric NULL,
    price numeric NULL,
    rewarded_ftso bool NULL,
    symbol varchar(255) NULL,
    "timestamp" numeric(19, 2) NULL,
    CONSTRAINT price_finalized_event_pkey PRIMARY KEY (price_finalized_event_id),
    CONSTRAINT unique_epoch_id_symbol UNIQUE (epoch_id, symbol)
);

CREATE TABLE price_revealed_event (
    price_revealed_event_id bigserial NOT NULL,
    epoch_id numeric NULL,
    price numeric NULL,
    random numeric NULL,
    symbol varchar(255) NULL,
    "timestamp" numeric(19, 2) NULL,
    vote_power_asset numeric NULL,
    vote_power_nat numeric NULL,
    voter varchar(255) NULL,
    CONSTRAINT price_revealed_event_pkey PRIMARY KEY (price_revealed_event_id),
    CONSTRAINT unique_epoch_id_symbol_voter UNIQUE (epoch_id, symbol, voter)
);

create materialized view delegation as
select
    concat(from_address, to_address) as delegation_id,
    from_address,
    to_address,
    sum(new_vote_power - prior_vote_power) / power(10, 18) :: numeric as amount,
    max(block_number) as updated_at_block,
    min(block_number) as created_at_block
from
    delegation_event
group by
    from_address,
    to_address with no data;

refresh materialized view delegation;

create unique index from_to on delegation (delegation_id);
