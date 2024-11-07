CREATE TABLE quiz_questions_join
(
    quiz_id     BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    PRIMARY KEY (quiz_id, question_id),
    FOREIGN KEY (quiz_id) REFERENCES quizzes (id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES quiz_questions (id) ON DELETE CASCADE
);

CREATE TABLE question_options_join
(
    question_id BIGINT NOT NULL,
    option_id   BIGINT NOT NULL,
    PRIMARY KEY (question_id, option_id),
    FOREIGN KEY (question_id) REFERENCES quiz_questions (id) ON DELETE CASCADE,
    FOREIGN KEY (option_id) REFERENCES quiz_answer_options (id) ON DELETE CASCADE
);