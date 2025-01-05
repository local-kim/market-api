CREATE TABLE `member` (
    `id`	INT NOT NULL    AUTO_INCREMENT,
    `email` VARCHAR(255)	NULL    UNIQUE,
    `password`	VARCHAR(255)	NULL,
    `created_at`	TIMESTAMP	NOT NULL	DEFAULT NOW(),
    `updated_at`	TIMESTAMP	NOT NULL	DEFAULT NOW(),

    PRIMARY KEY (id)
);

CREATE TABLE `product` (
    `id`	INT NOT NULL    AUTO_INCREMENT,
    `seller_id`	INT NOT NULL,
    `name`	VARCHAR(255)	NULL,
    `price` BIGINT	NULL,
    `status`	VARCHAR(255)	NULL	COMMENT '판매중, 예약중, 완료',
    `quantity`	INT	NULL,
    `stock`	INT NULL,
    `created_at`	TIMESTAMP	NOT NULL,
    `updated_at`	TIMESTAMP	NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE `transaction` (
    `id`    INT	NOT NULL    AUTO_INCREMENT,
    `buyer_id`	INT NOT NULL,
    `product_id`	INT	NOT NULL,
    `status`	VARCHAR(255)	NULL	COMMENT '거래중, 판매승인, 구매확정',
    `price` BIGINT	NULL,
    `created_at`	TIMESTAMP	NOT NULL	DEFAULT NOW(),
    `updated_at`	TIMESTAMP	NOT NULL	DEFAULT NOW(),

    PRIMARY KEY (id)
);

-- Foreign Key
ALTER TABLE `product` ADD CONSTRAINT `FK_member_TO_product_1` FOREIGN KEY (
    `seller_id`
)
REFERENCES `member` (
    `id`
);

ALTER TABLE `transaction` ADD CONSTRAINT `FK_member_TO_transaction_1` FOREIGN KEY (
    `buyer_id`
)
REFERENCES `member` (
    `id`
);

ALTER TABLE `transaction` ADD CONSTRAINT `FK_product_TO_transaction_1` FOREIGN KEY (
    `product_id`
)
REFERENCES `product` (
    `id`
);
