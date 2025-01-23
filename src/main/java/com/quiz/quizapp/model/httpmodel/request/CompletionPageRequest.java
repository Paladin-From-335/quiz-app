package com.quiz.quizapp.model.httpmodel.request;

/**
 * <p>Request to get a completion (final) page data.</p>
 *
 * @param isCompleted - in case if all the questions were completed should be set true on a front-end side
 * @see com.quiz.quizapp.model.httpmodel.response.CompletionPageResponse
 */
public record CompletionPageRequest(Boolean isCompleted) {
}
