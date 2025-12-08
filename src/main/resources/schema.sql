CREATE TABLE Product (
    id int primary key,
    name varchar(100),
    price float,
    creation_datetime timestamp
);

CREATE TABLE Product_category (
    id int,
    name varchar(100),
    product_id int,
    CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES Product(id)
);