package com.quiz.quizapp.model.httpmodel.request;

import java.util.List;

public record UpdateQuestionRequest(Long id, String questionName, List<UpdateOptionRequest> options) {
}
