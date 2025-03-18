package com.quiz.quizapp.service;

import com.quiz.quizapp.exception.QuizNotCompletedException;
import com.quiz.quizapp.exception.QuizNotFoundException;
import com.quiz.quizapp.model.entity.CompletionPage;
import com.quiz.quizapp.model.entity.Quiz;
import com.quiz.quizapp.model.httpmodel.request.CreateCompletionPageRequest;
import com.quiz.quizapp.model.httpmodel.response.CompletionPageResponse;
import com.quiz.quizapp.repository.CompletionPageRepository;
import com.quiz.quizapp.repository.QuizRepository;
import com.quiz.quizapp.utils.mapper.CompletionPageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompletionService {

  private final QuizRepository quizRepository;
  private final CompletionPageRepository completionPageRepository;
  private final CompletionPageMapper completionPageMapper;

  public CompletionPageResponse getCompletionData(String publicId, Boolean isCompleted) {
    if (!isCompleted) {
      throw new QuizNotCompletedException();
    }
    return completionPageMapper.map(
        completionPageRepository.findCompletionPageByQuizPublicId(publicId)
            .orElseThrow(QuizNotCompletedException::new));
  }

  public void createCompletionPage(String publicId, CreateCompletionPageRequest request) {
    CompletionPage completion = completionPageMapper.map(request);
    Quiz quiz = quizRepository.findByPublicId(publicId)
        .orElseThrow(() -> new QuizNotFoundException(publicId));
    completion.setQuiz(quiz);
    completionPageRepository.save(completion);
  }
}
