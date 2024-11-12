package com.quiz.quizapp.service;

import com.quiz.quizapp.model.httpmodel.HttpCompletion;
import com.quiz.quizapp.model.httpmodel.request.CompletionRequest;
import com.quiz.quizapp.repository.CompletionDataRepository;
import com.quiz.quizapp.utils.mapper.CompletionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompletionService {

  private final CompletionDataRepository completionRepository;
  private final CompletionMapper completionMapper;

  //Todo refactor with custom error handling
  public HttpCompletion getCompletionData(String publicId, CompletionRequest request) {
    if (!request.isCompleted()) {
      throw new IllegalArgumentException("Quiz is not completed");
    }
    return completionMapper.map(completionRepository.findCompletionByQuizPublicId(publicId).orElseThrow());
  }
}
