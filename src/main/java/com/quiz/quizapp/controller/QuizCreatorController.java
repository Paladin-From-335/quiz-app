package com.quiz.quizapp.controller;

import com.quiz.quizapp.model.httpmodel.HttpQuiz;
import com.quiz.quizapp.model.httpmodel.response.QuizResponse;
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
  public QuizResponse createQuiz(@RequestBody HttpQuiz quiz) {
    return new QuizResponse(service.saveQuiz(quiz));
  }

  @GetMapping("/{uuid}")
  public HttpQuiz getQuiz(@PathVariable("uuid") String publicId) {
    return service.getQuiz(publicId);
  }

  @PutMapping("/{uuid}")
  public HttpQuiz updateQuiz(@PathVariable("uuid") String publicId, @RequestBody HttpQuiz quiz) {
    return service.updateQuiz(publicId, quiz);
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<?> deleteQuiz(@PathVariable("uuid") String publicId) {
    service.deleteQuiz(publicId);
    return ResponseEntity.ok("Your quiz no longer exist");
  }
}
