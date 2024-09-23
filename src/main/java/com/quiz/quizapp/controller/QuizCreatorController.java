package com.quiz.quizapp.controller;

import com.quiz.quizapp.model.httpmodel.HttpQuiz;
import com.quiz.quizapp.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("quiz-creator")
@RequiredArgsConstructor
public class QuizCreatorController {

  private final QuizService service;

  @PostMapping
  public ResponseEntity<?> createQuiz(@RequestBody HttpQuiz quiz) {
    service.saveQuiz(quiz);
    return ResponseEntity.ok("ok");//plug
  }
}
