CREATE TABLE address
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    address_line1 VARCHAR(255) NULL,
    address_line2 VARCHAR(255) NULL,
    landmark      VARCHAR(255) NULL,
    pin_code      VARCHAR(255) NULL,
    city          VARCHAR(255) NULL,
    state         VARCHAR(255) NULL,
    country       VARCHAR(255) NULL,
    CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE `admin`
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    name         VARCHAR(255) NULL,
    email        VARCHAR(255) NULL,
    password     VARCHAR(255) NULL,
    country      VARCHAR(255) NULL,
    phone_number VARCHAR(255) NULL,
    CONSTRAINT pk_admin PRIMARY KEY (id)
);

CREATE TABLE cart
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    session_id BIGINT NULL,
    status     VARCHAR(255) NULL,
    CONSTRAINT pk_cart PRIMARY KEY (id)
);

CREATE TABLE cart_item
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    product_id BIGINT NULL,
    quantity   INT NULL,
    cart_id    BIGINT NULL,
    CONSTRAINT pk_cartitem PRIMARY KEY (id)
);

CREATE TABLE customer
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    name         VARCHAR(255) NULL,
    email        VARCHAR(255) NULL,
    password     VARCHAR(255) NULL,
    country      VARCHAR(255) NULL,
    phone_number VARCHAR(255) NULL,
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

CREATE TABLE customer_address
(
    address_id  BIGINT NOT NULL,
    customer_id BIGINT NOT NULL
);

CREATE TABLE customer_session
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    customer_id BIGINT NULL,
    status      VARCHAR(255) NULL,
    CONSTRAINT pk_customersession PRIMARY KEY (id)
);

CREATE TABLE invoice
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    order_id   BIGINT NULL,
    payment_id BIGINT NULL,
    seller_id  BIGINT NULL,
    CONSTRAINT pk_invoice PRIMARY KEY (id)
);

CREATE TABLE `order`
(
    id                  BIGINT AUTO_INCREMENT NOT NULL,
    customer_id         BIGINT NULL,
    status              VARCHAR(255) NULL,
    payment_id          BIGINT NULL,
    delivery_address_id BIGINT NULL,
    CONSTRAINT pk_order PRIMARY KEY (id)
);

CREATE TABLE order_item
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    product_id BIGINT NULL,
    quantity   INT NULL,
    order_id   BIGINT NULL,
    invoice_id BIGINT NULL,
    CONSTRAINT pk_orderitem PRIMARY KEY (id)
);

CREATE TABLE payment
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    amount         DECIMAL NULL,
    status         VARCHAR(255) NULL,
    transaction_id VARCHAR(255) NULL,
    CONSTRAINT pk_payment PRIMARY KEY (id)
);

CREATE TABLE product
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255) NULL,
    category_id   BIGINT NULL,
    `description` VARCHAR(255) NULL,
    price         DECIMAL NULL,
    image_id      BIGINT NULL,
    seller_id     BIGINT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE product_category
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    CONSTRAINT pk_productcategory PRIMARY KEY (id)
);

CREATE TABLE product_image
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    CONSTRAINT pk_productimage PRIMARY KEY (id)
);

CREATE TABLE product_images
(
    product_image_id BIGINT NOT NULL,
    image_url        VARCHAR(255) NULL
);

CREATE TABLE product_inventory
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    product_id BIGINT NULL,
    quantity   INT NULL,
    CONSTRAINT pk_productinventory PRIMARY KEY (id)
);

CREATE TABLE seller
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    name           VARCHAR(255) NULL,
    email          VARCHAR(255) NULL,
    password       VARCHAR(255) NULL,
    pan_number     VARCHAR(255) NULL,
    gst_reg_number VARCHAR(255) NULL,
    address_id     BIGINT NULL,
    country        VARCHAR(255) NULL,
    phone_number   VARCHAR(255) NULL,
    CONSTRAINT pk_seller PRIMARY KEY (id)
);

ALTER TABLE invoice
    ADD CONSTRAINT uc_invoice_order UNIQUE (order_id);

ALTER TABLE product
    ADD CONSTRAINT uc_product_image UNIQUE (image_id);

ALTER TABLE product_inventory
    ADD CONSTRAINT uc_productinventory_product UNIQUE (product_id);

ALTER TABLE seller
    ADD CONSTRAINT uc_seller_address UNIQUE (address_id);

ALTER TABLE cart_item
    ADD CONSTRAINT FK_CARTITEM_ON_CART FOREIGN KEY (cart_id) REFERENCES cart (id);

ALTER TABLE cart_item
    ADD CONSTRAINT FK_CARTITEM_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE cart
    ADD CONSTRAINT FK_CART_ON_SESSION FOREIGN KEY (session_id) REFERENCES customer_session (id);

ALTER TABLE customer_session
    ADD CONSTRAINT FK_CUSTOMERSESSION_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE invoice
    ADD CONSTRAINT FK_INVOICE_ON_ORDER FOREIGN KEY (order_id) REFERENCES `order` (id);

ALTER TABLE invoice
    ADD CONSTRAINT FK_INVOICE_ON_PAYMENT FOREIGN KEY (payment_id) REFERENCES payment (id);

ALTER TABLE invoice
    ADD CONSTRAINT FK_INVOICE_ON_SELLER FOREIGN KEY (seller_id) REFERENCES seller (id);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDERITEM_ON_INVOICE FOREIGN KEY (invoice_id) REFERENCES invoice (id);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDERITEM_ON_ORDER FOREIGN KEY (order_id) REFERENCES `order` (id);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDERITEM_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE `order`
    ADD CONSTRAINT FK_ORDER_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE `order`
    ADD CONSTRAINT FK_ORDER_ON_DELIVERY_ADDRESS FOREIGN KEY (delivery_address_id) REFERENCES address (id);

ALTER TABLE `order`
    ADD CONSTRAINT FK_ORDER_ON_PAYMENT FOREIGN KEY (payment_id) REFERENCES payment (id);

ALTER TABLE product_inventory
    ADD CONSTRAINT FK_PRODUCTINVENTORY_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES product_category (id);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_IMAGE FOREIGN KEY (image_id) REFERENCES product_image (id);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_SELLER FOREIGN KEY (seller_id) REFERENCES seller (id);

ALTER TABLE seller
    ADD CONSTRAINT FK_SELLER_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES address (id);

ALTER TABLE customer_address
    ADD CONSTRAINT fk_cusadd_on_address FOREIGN KEY (address_id) REFERENCES address (id);

ALTER TABLE customer_address
    ADD CONSTRAINT fk_cusadd_on_customer FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE product_images
    ADD CONSTRAINT fk_product_images_on_product_image FOREIGN KEY (product_image_id) REFERENCES product_image (id);