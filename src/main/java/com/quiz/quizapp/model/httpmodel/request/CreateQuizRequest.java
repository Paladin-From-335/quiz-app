package com.quiz.quizapp.model.httpmodel.request;

import java.util.List;

/**
 * <p>Represents a data of a quiz creation.</p>
 *
 * @param quizName              - name of the quiz user may see on the "Start quiz page" (e.g. "Quiz for friends")
 * @param questions             - quiz questions
 */
public record CreateQuizRequest(String quizName, List<CreateQuestionRequest> questions) {
}
