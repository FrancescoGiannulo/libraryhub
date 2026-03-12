CREATE TABLE books (
                       id             BIGINT AUTO_INCREMENT PRIMARY KEY,
                       isbn           VARCHAR(20)  UNIQUE,
                       title          VARCHAR(255) NOT NULL,
                       author         VARCHAR(255) NOT NULL,
                       cover_url      VARCHAR(500),
                       genre          VARCHAR(100),
                       published_year INT,
                       description    TEXT
);