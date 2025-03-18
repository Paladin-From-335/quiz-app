package com.quiz.quizapp.model.httpmodel.request;

public record UpdateOptionRequest(Long id, String optionName, Boolean isCorrect) {
}
