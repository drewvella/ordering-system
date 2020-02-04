
CREATE TABLE orders.product (
    id uuid NOT NULL PRIMARY KEY ,
    product_name character varying(255) NOT NULL,
    price numeric(19,2) NOT NULL DEFAULT 0,
    created_date_time timestamp without time zone not null
);

CREATE TABLE orders.order (
    id uuid NOT NULL PRIMARY KEY,
    buyer_email character varying(255) NOT NULL,
    created_date_time timestamp without time zone not null,
    total numeric(19,2) NOT NULL DEFAULT 0
);

CREATE TABLE orders.order_product (
    id uuid NOT NULL PRIMARY KEY,
    order_id uuid NOT NULL references orders.order(id),
    product_id uuid NOT NULL references orders.product(id),
    product_name character varying(255) NOT NULL,
    product_price numeric(19,2) NOT NULL DEFAULT 0
);
