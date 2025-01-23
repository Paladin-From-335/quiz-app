package com.quiz.quizapp.model.httpmodel.request;

import java.util.List;

/**
 * <p>
 * Represents participant answer request.
 * P.S. might be removed in case of validation on front-end side.
 * </p>
 *
 * @param questionId         - quiz question ID
 * @param participantAnswers - answer options' IDs chosen by participant (user) as answers
 */
@SuppressWarnings("Might be removed")
public record AnswerRequest(Long questionId, List<Long> participantAnswers) {
}
