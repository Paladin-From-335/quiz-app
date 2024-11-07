package com.quiz.quizapp.controller;

import com.quiz.quizapp.model.httpmodel.HttpQuiz;
import com.quiz.quizapp.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("quiz")
public class QuizUserController {

  private final QuizService quizService;

  @GetMapping("/{id}")
  public HttpQuiz getQuiz(@PathVariable Long id) {
    return quizService.getQuiz(id);
  }
}
