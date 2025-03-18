CREATE TABLE completion_pages
(
    id    BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    body  TEXT
);

CREATE TABLE quiz_completion_page_join
(
    quiz_id            BIGINT NOT NULL,
    completion_page_id BIGINT NOT NULL,
    PRIMARY KEY (quiz_id, completion_page_id),
    FOREIGN KEY (quiz_id) REFERENCES quizzes (id) ON DELETE CASCADE,
    FOREIGN KEY (completion_page_id) REFERENCES completion_pages (id) ON DELETE CASCADE
);