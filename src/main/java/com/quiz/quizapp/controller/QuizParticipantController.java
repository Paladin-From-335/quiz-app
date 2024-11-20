package com.quiz.quizapp.controller;

import com.quiz.quizapp.model.httpmodel.HttpCompletion;
import com.quiz.quizapp.model.httpmodel.HttpQuiz;
import com.quiz.quizapp.model.httpmodel.request.AnswerRequest;
import com.quiz.quizapp.model.httpmodel.request.CompletionRequest;
import com.quiz.quizapp.service.CompletionService;
import com.quiz.quizapp.service.QuestionService;
import com.quiz.quizapp.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("quiz")
public class QuizParticipantController {

  private final QuizService quizService;
  private final CompletionService completionService;
  private final QuestionService questionService;

  @GetMapping("/{uuid}")
  public HttpQuiz getQuiz(@PathVariable("uuid") String publicId) {
    return quizService.getQuiz(publicId);
  }

  @GetMapping("/{uuid}/complete")
  public HttpCompletion getCompletionPage(@PathVariable("uuid") String publicId, CompletionRequest request) {
    return completionService.getCompletionData(publicId, request);
  }

  @PostMapping("/{uuid}")
  public Boolean submitAnswer(@PathVariable("uuid") String publicId, AnswerRequest request) {
    return questionService.validateQuestionAnswers(publicId, request);
  }
}
