package com.quiz.quizapp.model.httpmodel.request;

import java.util.List;

/**
 * @param questionId         - quiz question ID
 * @param participantAnswers - Answer Options' IDs chosen by participant (user) as answers
 */
public record AnswerRequest(Long questionId, List<Long> participantAnswers) {
}
