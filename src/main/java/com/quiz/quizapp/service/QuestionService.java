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

  public boolean areQuestionsCached(String publicId) {
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
    if (!areQuestionsCached(publicId)) {
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

}
