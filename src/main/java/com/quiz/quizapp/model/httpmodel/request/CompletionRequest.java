package com.quiz.quizapp.model.httpmodel.request;

import java.util.Optional;

/**
 * @param isCompleted - in case if all the questions were completed should be set true on a front-end side
 */
public record CompletionRequest (Boolean isCompleted){
}
