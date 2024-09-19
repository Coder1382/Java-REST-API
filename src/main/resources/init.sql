CREATE TABLE sellers(id BIGSERIAL PRIMARY KEY, name VARCHAR(80) NOT NULL,
rating INT CHECK (rating>0 AND rating<11), supplier_id BIGINT NOT NULL,
CONSTRAINT fk FOREIGN KEY(supplier_id) REFERENCES suppliers(id) ON DELETE CASCADE);
INSERT INTO sellers(name, rating, supplier_id) VALUES('ivan', 5, 2);
INSERT INTO sellers(name, rating, supplier_id) VALUES('ibragim', 5, 1);
INSERT INTO sellers(name, rating, supplier_id) VALUES('moisey', 5, 1);
INSERT INTO sellers(name, rating, supplier_id) VALUES('john', 5, 3);

CREATE TABLE fruit(id BIGSERIAL PRIMARY KEY, name VARCHAR(80) NOT NULL, color VARCHAR(20),
price NUMERIC CHECK (price>=0));
INSERT INTO fruit(name, color, price) VALUES('green apple', 'green', 10);
INSERT INTO fruit(name, color, price) VALUES('mango', 'yellow', 40);
INSERT INTO fruit(name, color, price) VALUES('red apple', 'red', 15);

CREATE TABLE suppliers(id BIGSERIAL PRIMARY KEY, company VARCHAR(80) NOT NULL);
INSERT INTO suppliers(company) VALUES('big ideas');
INSERT INTO suppliers(company) VALUES('x-vendor');
INSERT INTO suppliers(company) VALUES('solid partner');

CREATE TABLE seller_fruit(seller_id BIGINT, fruit_id BIGINT, PRIMARY KEY(seller_id, fruit_id),
CONSTRAINT fk_seller FOREIGN KEY(seller_id) REFERENCES sellers(id) ON DELETE CASCADE,
CONSTRAINT fk_fruit FOREIGN KEY(fruit_id) REFERENCES fruit(id) ON DELETE CASCADE);
INSERT INTO seller_fruit(seller_id, fruit_id) VALUES(1,3);
INSERT INTO seller_fruit(seller_id, fruit_id) VALUES(1,2);
INSERT INTO seller_fruit(seller_id, fruit_id) VALUES(3,2);
INSERT INTO seller_fruit(seller_id, fruit_id) VALUES(2,3);
INSERT INTO seller_fruit(seller_id, fruit_id) VALUES(3,1);
