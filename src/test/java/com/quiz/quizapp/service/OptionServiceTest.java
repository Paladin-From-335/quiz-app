package com.quiz.quizapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.quiz.quizapp.model.entity.Option;
import com.quiz.quizapp.model.httpmodel.request.UpdateOptionRequest;
import com.quiz.quizapp.repository.OptionRepository;
import com.quiz.quizapp.utils.CustomJpaTestConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@CustomJpaTestConfiguration
public class OptionServiceTest {

  @Autowired
  private TestEntityManager testEntityManager;
  @Autowired
  private OptionRepository optionRepo;

  private OptionService optionService;

  @BeforeEach
  void setUp() {
    optionService = new OptionService(testEntityManager.getEntityManager());
  }

  @Test
  void testUpdateOptionData() {
    Long optionId = 999L;

    UpdateOptionRequest updateRequest = new UpdateOptionRequest(
        optionId, "Updated Option Name", true);

    optionService.updateOptionData(List.of(updateRequest));

    // Clear to ensure no stale data remains from cache
    testEntityManager.clear();

    Optional<Option> updatedQuestion = optionRepo.findById(optionId);
    assertNotNull(updatedQuestion);
    assertEquals("Updated Option Name", updatedQuestion.get().getOptionName());
    assertEquals(true, updatedQuestion.get().getIsCorrect());
  }
}
