package com.quiz.quizapp.controller;

import com.quiz.quizapp.model.httpmodel.request.AnswerRequest;
import com.quiz.quizapp.model.httpmodel.response.CompletionPageResponse;
import com.quiz.quizapp.model.httpmodel.response.QuizResponse;
import com.quiz.quizapp.service.AnswerValidationService;
import com.quiz.quizapp.service.CompletionService;
import com.quiz.quizapp.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("quiz")
public class QuizParticipantController {

  private final QuizService quizService;
  private final CompletionService completionService;
  private final AnswerValidationService validationService;

  //Todo return as pageable
  @GetMapping("/{uuid}")
  public QuizResponse getQuiz(@PathVariable("uuid") String publicId) {
    return quizService.getQuiz(publicId);
  }

  @GetMapping("/{uuid}/complete")
  public CompletionPageResponse getCompletionPage(@PathVariable("uuid") String publicId, @RequestParam Boolean isCompleted) {
    return completionService.getCompletionData(publicId, isCompleted);
  }

  @PostMapping("/{uuid}")
  public Boolean submitAnswer(@PathVariable("uuid") String publicId, @RequestBody AnswerRequest request) {
    return validationService.validateQuestionAnswers(publicId, request);
  }
}
