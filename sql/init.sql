CREATE TABLE `member` (
    `id`	INT NOT NULL    AUTO_INCREMENT  PRIMARY KEY,
    `email` VARCHAR(255)	NOT NULL    UNIQUE,
    `password`	VARCHAR(255)	NOT NULL,
    `created_at`	TIMESTAMP	NOT NULL	DEFAULT NOW(),
    `updated_at`	TIMESTAMP	NOT NULL	DEFAULT NOW()   ON UPDATE NOW()
);

CREATE TABLE `product` (
    `id`	INT NOT NULL    AUTO_INCREMENT  PRIMARY KEY,
    `seller_id`	INT NOT NULL,
    `name`	VARCHAR(255)	NOT NULL,
    `description`   VARCHAR(255)    NULL,
    `price` BIGINT	NOT NULL,
    `status`	VARCHAR(255)	NOT NULL	COMMENT '판매중, 예약중, 완료',
    `quantity`	INT	NOT NULL,
    `stock`	INT NOT NULL,
    `image_urls`    JSON    NULL,
    `created_at`	TIMESTAMP	NOT NULL    DEFAULT NOW(),
    `updated_at`	TIMESTAMP	NOT NULL    DEFAULT NOW()    ON UPDATE NOW()
);

CREATE TABLE `transaction` (
    `id`    INT	NOT NULL    AUTO_INCREMENT  PRIMARY KEY,
    `buyer_id`	INT NOT NULL,
    `product_id`	INT	NOT NULL,
    `status`	VARCHAR(255)	NOT NULL	COMMENT '확인중, 판매승인, 구매확정',
    `price` BIGINT	NOT NULL,
    `created_at`	TIMESTAMP	NOT NULL	DEFAULT NOW(),
    `updated_at`	TIMESTAMP	NOT NULL	DEFAULT NOW()   ON UPDATE NOW()
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
