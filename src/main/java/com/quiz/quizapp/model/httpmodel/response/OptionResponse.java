package com.quiz.quizapp.model.httpmodel.response;

/**
 * <p>
 * Represents answer option. Param "isCorrect" is not listed, since all the validation on back-end side
 * Validation might be moved to front-end side (or be duplicated on front-end side)
 * </p>
 *
 * @param id   - answer option id
 * @param name - answer option declaration (name)
 */
public record OptionResponse(Long id, String name) {
}
