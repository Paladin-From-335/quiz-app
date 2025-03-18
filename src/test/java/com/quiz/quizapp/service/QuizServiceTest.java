package com.quiz.quizapp.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.quiz.quizapp.model.entity.Quiz;
import com.quiz.quizapp.model.httpmodel.request.CreateOptionRequest;
import com.quiz.quizapp.model.httpmodel.request.CreateQuestionRequest;
import com.quiz.quizapp.model.httpmodel.request.CreateQuizRequest;
import com.quiz.quizapp.model.httpmodel.response.QuizResponse;
import com.quiz.quizapp.repository.QuizRepository;
import com.quiz.quizapp.utils.AllMappers;
import com.quiz.quizapp.utils.mapper.QuizMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Transactional
@Sql(scripts = "classpath:/data.sql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(AllMappers.class)
public class QuizServiceTest {

  @Autowired
  private QuizRepository quizRepo;

  @Autowired
  private QuizMapper quizMapper;

  @MockBean
  private QuestionService questionService;

  private QuizService quizService;

  @BeforeEach
  void setUp() {
    quizService = new QuizService(quizRepo, quizMapper, questionService);

  }

  @Test
  void testSaveQuiz() {
    CreateOptionRequest optReq = new CreateOptionRequest("answer", true);
    CreateQuestionRequest queReq = new CreateQuestionRequest("questionName", List.of(optReq));
    CreateQuizRequest request = new CreateQuizRequest(
        "name",
        List.of(queReq)
    );
    String result = quizService.saveQuiz(request);
    Quiz quiz = quizRepo.findByPublicId(result).get();
    assertAll(
        () -> assertEquals(request.quizName(), quiz.getQuizName()),
        () -> assertEquals(request.questions().size(), quiz.getQuestions().size()),
        () -> assertEquals(queReq.questionName(), quiz.getQuestions().getFirst().getQuestionName()),
        () -> assertEquals(queReq.options().size(), quiz.getQuestions().getFirst().getOptions().size()),
        () -> assertEquals(optReq.optionName(), quiz.getQuestions().getFirst().getOptions().getFirst().getOptionName()),
        () -> assertEquals(optReq.isCorrect(), quiz.getQuestions().getFirst().getOptions().getFirst().getIsCorrect())
    );
  }

  @Test
  void testGetQuiz() {
    String publicId = "public-uuid-1";
    QuizResponse result = quizService.getQuiz(publicId);

    assertNotNull(result);
    assertAll(
        () -> assertEquals("Math Quiz", result.quizName()),
        () -> assertEquals(3, result.questions().size()),
        () -> assertEquals("What is the capital of France?", result.questions().getFirst().questionName())
    );
    verify(questionService, times(1)).cacheQuestions(any(), eq(publicId));
  }

  @Test
  void testDeleteQuiz() {
    String publicId = "public-uuid-2";

    quizService.deleteQuiz(publicId);

    assertEquals(Optional.empty(), quizRepo.getQuizIdByPublicId(publicId));
  }
}
