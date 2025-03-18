package com.quiz.quizapp.model.httpmodel.request;

import java.util.List;

public record UpdateQuizRequest(String quizName, List<UpdateQuestionRequest> questions) {
}
