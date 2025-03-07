INSERT INTO quiz_answer_options (id, name, is_correct)
VALUES (101, 'Option A', TRUE),
       (102, 'Option B', FALSE),
       (103, 'Option C', TRUE),
       (104, 'Option D', FALSE),
       (105, 'Option E', FALSE),
       (106, 'Option F', TRUE);

INSERT INTO quiz_questions(id, question_name, media_url, time_limit, sequence, hint, answer_type)
VALUES (101, 'What is the capital of France?', NULL, 30, 1, '5 letters', 'single'),
       (102, 'What is 2 + 2?', NULL, 15, 2, 'Not 2', 'single'),
       (103, 'Is a burger vegetable', NULL, 25, 3, NULL, 'single'),
       (104, 'What?', NULL, 0, 4, NULL, 'single'),
       (105, 'Opposite to left', NULL, 20, 5, 'R...', 'multiple');

INSERT INTO quizzes(id, quiz_name, created_at, updated_at, public_id, description, life_count, time_limit)
VALUES (101, 'Math Quiz', '2023-10-01 12:00:00', '2023-10-01 12:00:00', 'public-uuid-1',
        'Basic math quiz', 3, 300),
       (102, 'Geography Quiz', '2023-10-02 14:00:00', '2023-10-02 14:00:00', 'public-uuid-2',
        'Quiz about countries and capitals.', 0, NULL);

INSERT INTO question_options_join(question_id, option_id)
VALUES (101, 101),
       (101, 102),
       (102, 103),
       (102, 104),
       (102, 105),
       (105, 106);

INSERT INTO quiz_questions_join(quiz_id, question_id)
VALUES (101, 101),
       (101, 102),
       (101, 103),
       (102, 104),
       (102, 105);