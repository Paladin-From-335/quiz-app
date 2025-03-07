ALTER TABLE quiz_questions
    ADD COLUMN media_url   VARCHAR(255),
    ADD COLUMN time_limit  INT,
    ADD COLUMN sequence    INT,
    ADD COLUMN hint        VARCHAR(100),
    ADD COLUMN answer_type VARCHAR(50) DEFAULT 'single';

ALTER TABLE quizzes
    ADD COLUMN created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Sets created_at to current timestamp on insert
    ADD COLUMN updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Initial value, but we’ll use a trigger to auto-update it
    ADD COLUMN public_id       VARCHAR(32), -- String with max length of 35
    ADD COLUMN description     TEXT, -- Optional text field, no length limit specified
    ADD COLUMN life_count      INT       DEFAULT 3, -- Integer with default value of 3
    ADD COLUMN skip_life_count BOOLEAN   DEFAULT FALSE, -- Boolean with default value of FALSE
    ADD COLUMN time_limit      INT, -- Optional, with no specific max constraint,
    ADD CONSTRAINT unique_public_id UNIQUE (public_id); -- Constraint to make table contain only unique hash values

