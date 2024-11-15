package com.quiz.quizapp.service;

import com.quiz.quizapp.exception.QuizNotCompletedException;
import com.quiz.quizapp.exception.QuizNotFoundException;
import com.quiz.quizapp.model.entity.Completion;
import com.quiz.quizapp.model.entity.Quiz;
import com.quiz.quizapp.model.httpmodel.HttpCompletion;
import com.quiz.quizapp.model.httpmodel.request.CompletionRequest;
import com.quiz.quizapp.repository.CompletionDataRepository;
import com.quiz.quizapp.repository.QuizRepository;
import com.quiz.quizapp.utils.mapper.CompletionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompletionService {

  private final QuizRepository quizRepository;
  private final CompletionDataRepository completionRepository;
  private final CompletionMapper completionMapper;

  public HttpCompletion getCompletionData(String publicId, CompletionRequest request) {
    if (!request.isCompleted()) {
      throw new QuizNotCompletedException();
    }
    return completionMapper.map(
        completionRepository.findCompletionByQuizPublicId(publicId)
            .orElseThrow(QuizNotCompletedException::new));
  }

  public void createCompletionPage(String publicId, HttpCompletion httpCompletion) {
    Completion completion = completionMapper.map(httpCompletion);
    Quiz quiz = quizRepository.findByPublicId(publicId)
        .orElseThrow(() -> new QuizNotFoundException(publicId));

    completion.setQuiz(quiz);
    completionRepository.save(completion);
  }
}
