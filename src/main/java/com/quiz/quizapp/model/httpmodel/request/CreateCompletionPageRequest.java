package com.quiz.quizapp.model.httpmodel.request;

/**
 * <p>Represents a data of a completion(final) quiz page creation.</p>
 *
 * @param title - title of the page (e.g. "Congratulations, you passed the quiz")
 * @param body   - additional text (e.g. "Thank you for passing the quiz I made for you.")
 */
public record CreateCompletionPageRequest(String title, String body) {
}
