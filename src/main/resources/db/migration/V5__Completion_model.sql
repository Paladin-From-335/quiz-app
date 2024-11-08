CREATE TABLE completion_data
(
    id         BIGSERIAL PRIMARY KEY,
    header     VARCHAR(255) NOT NULL,
    body       TEXT,
    time_spent INT
);

CREATE TABLE quiz_completion_data_join
(
    quiz_id            BIGINT NOT NULL,
    completion_data_id BIGINT NOT NULL,
    PRIMARY KEY (quiz_id, completion_data_id),
    FOREIGN KEY (quiz_id) REFERENCES quizzes (id) ON DELETE CASCADE,
    FOREIGN KEY (completion_data_id) REFERENCES completion_data (id) ON DELETE CASCADE
);