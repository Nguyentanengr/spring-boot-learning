CREATE DATABASE shopapp_db;
USE shopapp_db;

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(10) NOT NULL, 
    address VARCHAR(200) DEFAULT '', 
    password VARCHAR(100) NOT NULL DEFAULT '',
    created_at DATETIME,
    updated_at DATETIME,
    is_active TINYINT(1) DEFAULT 1,
    date_of_birth DATE,
    facebook_account_id BIGINT DEFAULT 0,
    google_account_id BIGINT DEFAULT 0
);

ALTER TABLE users ADD COLUMN role_id BIGINT;

CREATE TABLE roles(
	id BIGINT PRIMARY KEY, 
    name VARCHAR(20) NOT NULL
);

ALTER TABLE users ADD FOREIGN KEY (role_id) REFERENCES roles(id);

-- Ho tro dang nhap tu facebook va google 
CREATE TABLE social_accounts(
	id BIGINT PRIMARY KEY AUTO_INCREMENT, 
    provider VARCHAR(20) NOT NULL,
    provider_id VARCHAR(50) NOT NULL,
    email VARCHAR(150) NOT NULL,
    name VARCHAR(100) NOT NULL, 
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Bang danh muc san pham 

CREATE TABLE categories(
	id BIGINT PRIMARY KEY AUTO_INCREMENT, 
    name VARCHAR(100) NOT NULL DEFAULT ''
);

-- Ban san pham

CREATE TABLE products(
	id BIGINT PRIMARY KEY AUTO_INCREMENT, 
	`name` VARCHAR(100) NOT NULL DEFAULT '', 
    price FLOAT NOT NULL CHECK(price >= 0), 
    thumbnail VARCHAR(300) DEFAULT '', 
    `description` LONGTEXT, 
    created_at DATETIME,
    updated_at DATETIME,
    category_id BIGINT, 
    FOREIGN KEY (category_id) REFERENCES categories(id)
);


CREATE TABLE orders(
	id BIGINT PRIMARY KEY AUTO_INCREMENT, 
    user_id BIGINT, 
    fullname VARCHAR(100) DEFAULT '', 
    email VARCHAR(100) DEFAULT '', 
    phone_number VARCHAR(20) NOT NULL,
	address VARCHAR(200) NOT NULL, 
    note VARCHAR(200) DEFAULT '',
    oder_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20),
    total_money FLOAT CHECK(total_money >= 0),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

ALTER TABLE orders ADD COLUMN shipping_method VARCHAR(100);
ALTER TABLE orders ADD COLUMN shipping_address VARCHAR(200);
ALTER TABLE orders ADD COLUMN shipping_date DATE;
ALTER TABLE orders ADD COLUMN tracking_number VARCHAR(100);
ALTER TABLE orders ADD COLUMN payment_method VARCHAR(100);
ALTER TABLE orders ADD COLUMN active TINYINT(1);
ALTER TABLE orders MODIFY COLUMN status ENUM('pending', 'processing', 'shipped', 'delivered', 'cancelled');

CREATE TABLE order_details(
	id BIGINT PRIMARY KEY AUTO_INCREMENT, 
    order_id BIGINT, 
    FOREIGN KEY (order_id) REFERENCES orders(id),
    product_id BIGINT, 
    FOREIGN KEY (product_id) REFERENCES products(id),
    price FLOAT CHECK(price >= 0),
    number_of_products INT CHECK(number_of_products > 0),
    total_money FLOAT CHECK(total_money >= 0),
	color VARCHAR(20) DEFAULT ''
);
