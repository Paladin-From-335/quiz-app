package com.quiz.quizapp.service;

import com.quiz.quizapp.model.httpmodel.request.AnswerRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerValidationService {

  private final QuestionService questionService;
  private final RedisTemplate<String, Object> redisTemplate;

  public Boolean validateQuestionAnswers(String publicId, AnswerRequest request) {
    if (questionService.areQuestionsCached(publicId)) {
      HashOperations<String, Long, List<Long>> hashOps = redisTemplate.opsForHash();
      List<Long> correctAnswers = hashOps.get(publicId, request.questionId());
      return compareAnswers(correctAnswers, request.participantAnswers());
    }
    return false;
  }

  /**
   * @param cAnswers - correct answers gotten from Redis
   * @param pAnswers - participant answers from request
   * @return true or false after comparison of correct and participant answers
   */
  private Boolean compareAnswers(List<Long> cAnswers, List<Long> pAnswers) {
    if (cAnswers == null || pAnswers == null) {
      return false;
    }
    if (cAnswers.size() != pAnswers.size()) {
      return false;
    }
    List<Long> cAnswersSorted = new ArrayList<>(cAnswers);
    List<Long> pAnswersSorted = new ArrayList<>(pAnswers);
    Collections.sort(cAnswersSorted);
    Collections.sort(pAnswersSorted);

    return cAnswersSorted.equals(pAnswersSorted);
  }
}
