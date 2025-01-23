package com.quiz.quizapp.model.httpmodel.response;

import java.util.List;

/**
 * <p>Represents quiz question data.</p>
 *
 * @param id           - question id
 * @param options      - answer options
 * @param questionName - question declaration text
 */
public record QuestionResponse(Long id, String questionName, List<OptionResponse> options) {
}
