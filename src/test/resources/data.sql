INSERT INTO quiz_answer_options (id, name, is_correct)
VALUES (1, 'Option A', true),
       (2, 'Option B', false),
       (3, 'Option C', true),
       (4, 'Option D', false),
       (5, 'Option E', false),
       (6, 'Option F', true);

INSERT INTO question_options_join(question_id, option_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (2, 5),
       (5, 1),
       (5, 2),
       (5, 4),
       (5, 6);

INSERT INTO quiz_questions(id, question_name, media_url, time_limit, sequence, hint, answer_type)
VALUES (1, 'What is the capital of France?', NULL, 30, 1, '5 letters', 'single'),
       (2, 'What is 2 + 2?', NULL, 15, 2, 'Not 2', 'single'),
       (3, 'Is a burger vegetable', NULL, 25, 3, NULL, 'single'),
       (4, 'What?', NULL, 0, 4, NULL, 'single'),
       (5, 'Opposite to left', NULL, 20, 5, 'R...', 'multiple');
);

INSERT INTO quiz_questions_join(quiz_id, questiond_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5);

INSERT INTO quizzes(id, quiz_name, created_at, updated_at, public_id, description, life_counter, time_limit)
VALUES (1, 'Math Quiz', '2023-10-01 12:00:00', '2023-10-01 12:00:00', '550e8400-e29b-41d4-a716-446655440000',
        'Basic math quiz about numbers.', 3, 300),
       (2, 'Geography Quiz', '2023-10-02 14:00:00', '2023-10-02 14:00:00', '550e8400-e29b-41d4-a716-446655440001',
        'Quiz about countries and capitals.', 0, NULL);