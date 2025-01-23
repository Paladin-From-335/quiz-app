package com.quiz.quizapp.model.httpmodel.request;

import java.util.List;

/**
 * <p>Represents a data of a quiz question creation.</p>
 */
public record CreateQuestionRequest(String questionName, List<CreateOptionRequest> options) {
}
