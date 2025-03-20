package com.quiz.quizapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.quiz.quizapp.model.entity.Question;
import com.quiz.quizapp.model.httpmodel.request.UpdateQuestionRequest;
import com.quiz.quizapp.repository.QuestionRepository;
import com.quiz.quizapp.utils.CustomJpaTestConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.redis.core.RedisTemplate;

@CustomJpaTestConfiguration
public class QuestionServiceTest {

  @Mock
  private RedisTemplate<String, Object> redisTemplate;

  @Autowired
  private QuestionRepository questionRepo;
  @Autowired
  private TestEntityManager testEntityManager;

  private QuestionService questionService;

  @BeforeEach
  void setUp() {
    questionService = new QuestionService(testEntityManager.getEntityManager(), redisTemplate);
  }

  @Test
  void testUpdateQuestionData() {
    Long questionId = 999L;
    UpdateQuestionRequest updateRequest = new UpdateQuestionRequest(
        questionId, "Updated Question Name", null);

    questionService.updateQuestionData(List.of(updateRequest));

    // Clear to ensure no stale data remains from cache
    testEntityManager.clear();

    Optional<Question> updatedQuestion = questionRepo.findById(questionId);
    assertNotNull(updatedQuestion);
    assertEquals("Updated Question Name", updatedQuestion.get().getQuestionName());
  }
}
