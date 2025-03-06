package com.quiz.quizapp.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.quiz.quizapp.model.entity.Option;
import com.quiz.quizapp.model.entity.Question;
import com.quiz.quizapp.model.entity.Quiz;
import com.quiz.quizapp.model.httpmodel.request.CreateOptionRequest;
import com.quiz.quizapp.model.httpmodel.request.CreateQuestionRequest;
import com.quiz.quizapp.model.httpmodel.request.CreateQuizRequest;
import com.quiz.quizapp.model.httpmodel.response.QuizResponse;
import com.quiz.quizapp.repository.QuizRepository;
import com.quiz.quizapp.utils.mapper.QuizMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class QuizServiceTest {

  @Autowired
  private QuizRepository quizRepo;

  @Autowired
  private QuizMapper quizMapper;

  @Autowired
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
        List.of(queReq),
        null
    );
    String generatedPublicId = "test-public-id";
    Quiz quiz = new Quiz();
    quiz.setPublicId(generatedPublicId);

    String result = quizService.saveQuiz(request);
    assertAll(
        () -> assertEquals(request.quizName(), quiz.getQuizName()),
        () -> assertEquals(request.questions().size(), quiz.getQuestions().size()),
        () -> assertEquals(queReq.questionName(), quiz.getQuestions().getFirst().getQuestionName()),
        () -> assertEquals(queReq.options().size(), quiz.getQuestions().getFirst().getOptions().size()),
        () -> assertEquals(optReq.name(), quiz.getQuestions().getFirst().getOptions().getFirst().getName()),
        () -> assertEquals(optReq.isCorrect(), quiz.getQuestions().getFirst().getOptions().getFirst().getIsCorrect())
    );
    assertEquals(generatedPublicId, result);
    verify(quizRepo, times(1)).save(quiz);
  }

  @Test
  void testGetQuiz() {
    String publicId = "test-quiz-public-id";
    Quiz quiz = getMockQuiz();
    QuizResponse result = quizService.getQuiz(publicId);

    assertNotNull(result);
    assertAll(
        () -> assertEquals(quiz.getQuizName(), result.quizName()),
        () -> assertEquals(quiz.getQuestions().size(), result.questions().size()),
        () -> assertEquals(quiz.getQuestions().getFirst().getQuestionName(), result.questions().getFirst().questionName()),
        () -> assertNull(quiz.getCompletion()) //tmp
    );
    verify(questionService, times(1)).cacheQuestions(quiz.getQuestions(), publicId);
  }

  @Test
  void testUpdateQuiz() {
    String publicId = "test-quiz-public-id";
    Long quizId = 1L;
    CreateOptionRequest optReq = new CreateOptionRequest("answer", true);
    CreateQuestionRequest queReq = new CreateQuestionRequest("questionName", List.of(optReq));
    CreateQuizRequest request = new CreateQuizRequest(
        "name",
        List.of(queReq),
        null
    );
    Quiz quiz = new Quiz();
    quiz.setId(quizId);
    quiz.setPublicId(publicId);

    QuizResponse result = quizService.updateQuiz(publicId, request);
    assertNotNull(result);
    assertAll(
        () -> assertEquals(request.quizName(), result.quizName()),
        () -> assertEquals(request.questions().size(), result.questions().size()),
        () -> assertEquals(queReq.questionName(), result.questions().getFirst().questionName()),
        () -> assertEquals(queReq.options().size(), result.questions().getFirst().options().size()),
        () -> assertEquals(request.completionPageRequest(), result.completionPageRequest())
    );
    verify(quizRepo, times(1)).save(quiz);
  }

  @Test
  void testDeleteQuiz() {
    String publicId = "test-quiz-public-id";

    quizService.deleteQuiz(publicId);

    verify(quizRepo, times(1)).deleteByPublicId(publicId);
  }

  private Quiz getMockQuiz() {
    Option option = Option
        .builder()
        .name("answer")
        .isCorrect(true)
        .build();
    Question question = new Question();
    question.setQuestionName("name");
    question.setOptions(List.of(option));
    Quiz quiz = new Quiz();
    quiz.setQuizName("QuizName");
    quiz.setQuestions(List.of(question));
    return quiz;
  }
}
