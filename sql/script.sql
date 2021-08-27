SELECT * FROM mysql.user;
SHOW databases;

CREATE USER testuser identified by 'testpasswd';
DROP USER 'iago'@'%';

GRANT SELECT, INSERT, UPDATE, DELETE ON morebooks.* to 'testuser'@'%';
flush privileges;

show tables;

DROP TABLE books;
DROP TABLE users;
DROP TABLE user_readbook;
DROP TABLE user_wishbook;

CREATE TABLE books (
	id VARCHAR(16) PRIMARY KEY,
    title VARCHAR(128),
    subtitle VARCHAR(128),
    authors VARCHAR(256),
    publisher VARCHAR(64),
    published_date VARCHAR(64),
    description VARCHAR(1024),
    page_count INT,
    thumbnail VARCHAR(1024)
);

ALTER TABLE books MODIFY thumbnail varchar(1024);

CREATE TABLE users (
	id VARCHAR(40) PRIMARY KEY,
    username VARCHAR(64) UNIQUE,
    email VARCHAR(64) UNIQUE,
    passwd VARCHAR(100),
    firstname VARCHAR(32),
    lastname VARCHAR(32)
);

CREATE TABLE user_readbook (
	user_id VARCHAR(40),
    book_id VARCHAR(16),
    
    CONSTRAINT fk_user_read_id
    FOREIGN KEY (user_id) REFERENCES users(id),
    
    CONSTRAINT fk_book_read_id
    FOREIGN KEY (book_id) REFERENCES books(id)
);

CREATE TABLE user_wishbook (
	user_id VARCHAR(40),
    book_id VARCHAR(16),
    
    CONSTRAINT fk_user_wish_id
    FOREIGN KEY (user_id) REFERENCES users(id),
    
    CONSTRAINT fk_book_wish_id
    FOREIGN KEY (book_id) REFERENCES books(id)
);

SELECT * FROM books;
SELECT * FROM users;
SELECT * FROM user_readbook;
SELECT * FROM user_wishbook;

SELECT * FROM users WHERE id = :id LIMIT 1;
SELECT * FROM users WHERE username = :username OR email = :email LIMIT 1;
INSERT INTO users (id, username, email, passwd, firstname, lastname) VALUES(:id, :username, :email, :passwd, :firstname, :lastname);

SELECT * FROM books WHERE id = :bookid;
SELECT * FROM books JOIN user_readbook AS urb WHERE urb.user_id = :userid;
INSERT INTO books (id, title, subtitle, authors, publisher, published_date, description, page_count, thumbnail) VALUES(:id, :title, :subtitle, :authors, :publisher, :published_date, :description, :page_count, :thumbnail);
INSERT INTO user_readbook (user_id, book_id) VALUES(:userid, :bookid)
INSERT INTO user_wishbook (user_id, book_id) VALUES(:userid, :bookid)
