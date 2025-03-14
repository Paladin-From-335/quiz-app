package com.quiz.quizapp.model.httpmodel.request;

/**
 * <p>Represents a data of an answer option creation.</p>
 */
public record CreateOptionRequest(String optionName, Boolean isCorrect) {
}
