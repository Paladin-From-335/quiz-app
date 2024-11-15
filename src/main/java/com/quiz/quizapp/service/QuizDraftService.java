package com.quiz.quizapp.service;

import com.quiz.quizapp.model.httpmodel.HttpQuiz;
import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizDraftService {

  private final RedisTemplate<String, Object> redisTemplate;

  public String saveQuizDraft(HttpQuiz httpQuiz) {
    String draftId = UUID.randomUUID().toString();
    redisTemplate.opsForValue().set(draftId, httpQuiz);
    redisTemplate.expire(draftId, Duration.ofMinutes(30));
    return draftId;
  }
}
