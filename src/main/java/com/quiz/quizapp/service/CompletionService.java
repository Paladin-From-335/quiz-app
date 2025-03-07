package com.quiz.quizapp.service;

import com.quiz.quizapp.exception.QuizNotCompletedException;
import com.quiz.quizapp.exception.QuizNotFoundException;
import com.quiz.quizapp.model.entity.Completion;
import com.quiz.quizapp.model.entity.Quiz;
import com.quiz.quizapp.model.httpmodel.request.CreateCompletionPageRequest;
import com.quiz.quizapp.model.httpmodel.response.CompletionPageResponse;
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

  public CompletionPageResponse getCompletionData(String publicId, Boolean isCompleted) {
    if (!isCompleted) {
      throw new QuizNotCompletedException();
    }
    return completionMapper.map(
        completionRepository.findCompletionByQuizPublicId(publicId)
            .orElseThrow(QuizNotCompletedException::new));
  }

  public void createCompletionPage(String publicId, CreateCompletionPageRequest request) {
    Completion completion = completionMapper.map(request);
    Quiz quiz = quizRepository.findByPublicId(publicId)
        .orElseThrow(() -> new QuizNotFoundException(publicId));

    completion.setQuiz(quiz);
    completionRepository.save(completion);
  }
}
