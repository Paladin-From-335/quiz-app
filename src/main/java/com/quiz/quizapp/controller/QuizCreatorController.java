package com.quiz.quizapp.controller;

import com.quiz.quizapp.model.httpmodel.request.CreateCompletionPageRequest;
import com.quiz.quizapp.model.httpmodel.request.CreateQuizRequest;
import com.quiz.quizapp.model.httpmodel.request.UpdateQuizRequest;
import com.quiz.quizapp.model.httpmodel.response.QuizResponse;
import com.quiz.quizapp.model.httpmodel.response.QuizUrlResponse;
import com.quiz.quizapp.service.CompletionService;
import com.quiz.quizapp.service.QuizService;
import com.quiz.quizapp.utils.URIGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("quiz-creator")
@RequiredArgsConstructor
@CrossOrigin
public class QuizCreatorController {

  private final QuizService service;
  private final CompletionService completionService;

  @PostMapping
  public ResponseEntity<?> createQuiz(@RequestBody CreateQuizRequest request) {
    String publicId = service.saveQuiz(request);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .header("Quiz-UUID", publicId)
        .build();
  }

  @PostMapping("/complete")
  public ResponseEntity<QuizUrlResponse> createCompletionPage(@RequestHeader("Quiz-UUID") String publicId,
                                                              @RequestBody CreateCompletionPageRequest request) {
    completionService.createCompletionPage(publicId, request);
    QuizUrlResponse response = new QuizUrlResponse(URIGenerator.generateURI(publicId));
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .header("Quiz-UUID", publicId)
        .body(response);
  }

  @GetMapping("/{uuid}")
  public QuizResponse getQuiz(@PathVariable("uuid") String publicId) {
    return service.getQuiz(publicId);
  }

  // Tmp; TODO implement dynamic query building in serivce layer
  @SuppressWarnings("Update logic is not complete")
  @PutMapping("/{uuid}")
  public QuizResponse updateQuiz(@PathVariable("uuid") String publicId, @RequestBody UpdateQuizRequest request) {
    return service.updateQuiz(publicId, request);
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<?> deleteQuiz(@PathVariable("uuid") String publicId) {
    service.deleteQuiz(publicId);
    return ResponseEntity.ok("Your quiz no longer exist");
  }
}
