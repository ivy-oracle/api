CREATE TABLE "eth_user" (
    "address" VARCHAR(50) PRIMARY KEY,
    nonce INTEGER NOT NULL,
    nonce_updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    last_logged_in_at TIMESTAMP NOT NULL DEFAULT NOW(),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_eth_address FOREIGN KEY("address") REFERENCES eth_address(eth_address)
)
