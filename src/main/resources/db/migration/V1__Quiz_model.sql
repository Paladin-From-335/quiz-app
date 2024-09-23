CREATE TABLE quizzes
(
    id        BIGSERIAL PRIMARY KEY,
    quiz_name VARCHAR(255) NOT NULL
);

CREATE TABLE quiz_questions
(
    id            BIGSERIAL PRIMARY KEY,
    question_name VARCHAR(255) NOT NULL
);

CREATE TABLE quiz_answer_options
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    is_correct  BOOLEAN NOT NULL
);