CREATE TYPE answer_type AS ENUM ('SINGLE_CHOICE', 'MULTIPLE_CHOICE', 'TEXT', 'TRUE_FALSE');

ALTER TABLE quiz_questions
    ADD COLUMN media_url   VARCHAR(255),
    ADD COLUMN time_limit  INT,
    ADD COLUMN sequence    INT,
    ADD COLUMN hint        VARCHAR(100),
    ADD COLUMN answer_type answer_type,
    ADD CONSTRAINT question_time_limit_max CHECK (time_limit <= 1800);

ALTER TABLE quizzes
    ADD COLUMN created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Sets created_at to current timestamp on insert
    ADD COLUMN updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Initial value, but we’ll use a trigger to auto-update it
    ADD COLUMN public_id            VARCHAR(32), -- String with max length of 35
    ADD COLUMN description     TEXT, -- Optional text field, no length limit specified
    ADD COLUMN life_count      INT       DEFAULT 3, -- Integer with default value of 3
    ADD COLUMN skip_life_count BOOLEAN   DEFAULT FALSE, -- Boolean with default value of FALSE
    ADD COLUMN time_limit      INT, -- Optional, with no specific max constraint,
    ADD CONSTRAINT unique_public_id UNIQUE (public_id), -- Constraint to make table contain only unique hash values
    ADD CONSTRAINT time_limit_max CHECK (time_limit <= 3600); -- Constraint to ensure time_limit does not exceed 3600

-- Create a function to auto-update the updated_at column on modification
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create a trigger to call the function before any update on quizzes table
CREATE TRIGGER update_quizzes_updated_at
    BEFORE UPDATE
    ON quizzes
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();
