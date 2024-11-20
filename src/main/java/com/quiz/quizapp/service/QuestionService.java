package com.quiz.quizapp.service;

import com.quiz.quizapp.model.entity.Option;
import com.quiz.quizapp.model.entity.Question;
import com.quiz.quizapp.model.httpmodel.request.AnswerRequest;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

  private final RedisTemplate<String, Object> redisTemplate;

  private boolean isQuizCached(String publicId) {
    return redisTemplate.hasKey(publicId);
  }

  /**
   * Method is being triggered in case when user (quiz participant) gets a quiz
   * Caches quiz questions and correct answers to Redis to decrease main DB load
   *
   * @param questions - quiz questions, stored to SQL database
   * @param publicId  - quiz public ID (UUID)
   */
  public void cacheQuestions(List<Question> questions, String publicId) {
    if (!isQuizCached(publicId)) {
      // redis hash operations, needed to operate triplets <T,K,V>
      HashOperations<String, Long, List<Long>> hashOps = redisTemplate.opsForHash();
      questions.forEach(q -> {
        List<Long> correctOptionIds = q.getOptions().stream()
            .filter(Option::getIsCorrect)
            .map(Option::getId)
            .toList();
        //Stores to redis HashOperations quiz publicID, questionID and list of correct answers
        hashOps.put(publicId, q.getId(), correctOptionIds);
      });
    }
    redisTemplate.expire(publicId, Duration.ofMinutes(30)); //TODO make duration customizable
  }

  public Boolean validateQuestionAnswers(String publicId, AnswerRequest request) {
    if (isQuizCached(publicId)) {
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
