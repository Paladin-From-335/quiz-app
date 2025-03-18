package com.quiz.quizapp.model.httpmodel.response;

import java.util.List;

/**
 * <p>Represents a data of a quiz.</p>
 *
 * @param quizName              - name of the quiz user may see on the "Start quiz page" (e.g. "Quiz for friends")
 * @param questions             - quiz questions
 */
public record QuizResponse(String quizName, List<QuestionResponse> questions) {
}
