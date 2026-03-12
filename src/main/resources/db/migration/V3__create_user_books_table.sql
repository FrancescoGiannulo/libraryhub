CREATE TABLE user_books (
                            id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                            user_id     BIGINT      NOT NULL,
                            book_id     BIGINT      NOT NULL,
                            status      VARCHAR(20) NOT NULL,
                            added_at    DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            finished_at DATETIME,
                            CONSTRAINT fk_user_books_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                            CONSTRAINT fk_user_books_book FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE,
                            CONSTRAINT uq_user_book UNIQUE (user_id, book_id)
);