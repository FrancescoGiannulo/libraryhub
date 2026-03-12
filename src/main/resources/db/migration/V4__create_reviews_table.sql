CREATE TABLE reviews (
                         id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                         user_book_id BIGINT   NOT NULL UNIQUE,
                         rating       INT  NOT NULL CHECK (rating BETWEEN 1 AND 5),
                         comment      TEXT,
                         created_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         CONSTRAINT fk_reviews_user_book FOREIGN KEY (user_book_id) REFERENCES user_books (id) ON DELETE CASCADE
);