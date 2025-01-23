package com.quiz.quizapp.model.httpmodel.response;

import java.time.Duration;

/**
 * <p>Represents a data of completion(final) quiz page.</p>
 *
 * @param header   - header of the page (e.g. "Congratulations, you passed the quiz")
 * @param body     - additional text (e.g. "Thank you for passing the quiz I made for you.")
 * @param timeSpent - time spent to complete a quiz
 */
public record CompletionPageResponse(String header, String body, Duration timeSpent) {
}
