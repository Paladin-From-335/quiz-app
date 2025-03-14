package com.quiz.quizapp.exception.handler;

import com.quiz.quizapp.exception.QuizNotCompletedException;
import com.quiz.quizapp.exception.QuizNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler {

  @ExceptionHandler(QuizNotCompletedException.class)
  public ResponseEntity<?> handleQuizNotCompletedException(QuizNotCompletedException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Do not cheat. Quiz is not completed!");
  }

  @ExceptionHandler(QuizNotFoundException.class)
  public ResponseEntity<?> handleQuizNotFoundException(QuizNotFoundException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }

  @ExceptionHandler
  public ResponseEntity<?> handleGlobalException(Exception e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
