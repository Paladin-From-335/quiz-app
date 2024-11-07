package com.quiz.quizapp.controller;

import com.quiz.quizapp.model.httpmodel.HttpQuiz;
import com.quiz.quizapp.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("quiz-creator")
@RequiredArgsConstructor
public class QuizCreatorController {

  private final QuizService service;

  @PostMapping
  public String createQuiz(@RequestBody HttpQuiz quiz) {
    //TODO refactor to prettify the response
    return service.saveQuiz(quiz);
  }

  @GetMapping("/{id}")
  public HttpQuiz getQuiz(@PathVariable Long id) {
    return service.getQuiz(id);
  }

  @PutMapping("/{id}")
  public HttpQuiz updateQuiz(@PathVariable Long id, @RequestBody HttpQuiz quiz) {
    return service.updateQuiz(id, quiz);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteQuiz(@PathVariable Long id) {
    service.deleteQuiz(id);
    return ResponseEntity.ok("Your quiz no longer exist");
  }
}
