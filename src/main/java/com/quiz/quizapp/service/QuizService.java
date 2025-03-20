package com.quiz.quizapp.service;

import static com.quiz.quizapp.utils.helper.DynamicUpdateHelper.setDynamicUpdates;

import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.quiz.quizapp.exception.QuizNotFoundException;
import com.quiz.quizapp.model.entity.QQuiz;
import com.quiz.quizapp.model.entity.Quiz;
import com.quiz.quizapp.model.httpmodel.request.CreateQuizRequest;
import com.quiz.quizapp.model.httpmodel.request.UpdateQuizRequest;
import com.quiz.quizapp.model.httpmodel.response.QuizResponse;
import com.quiz.quizapp.repository.QuizRepository;
import com.quiz.quizapp.utils.UUIDUtil;
import com.quiz.quizapp.utils.mapper.QuizMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizService {

  private final QuizRepository quizRepo;
  private final QuizMapper quizMapper;
  private final QuestionService questionService;
  private final OptionService optionService;

  @PersistenceContext
  private final EntityManager entityManager;

  /**
   * Creates and saves a new quiz in the database and generates a unique public ID for it.
   *
   * @param request - the request object containing the data to create a new quiz
   * @return the generated unique public UUID for the quiz
   */
  @Transactional
  public String saveQuiz(CreateQuizRequest request) {
    String publicId = UUIDUtil.generateUUID();
    Quiz quiz = quizMapper.map(request);
    quiz.setPublicId(publicId);
    quizRepo.save(quiz);
    return publicId;
  }

  /**
   * Retrieves quiz details by its public UUID. Throws {@link QuizNotFoundException}
   * if the quiz does not exist. Caches the quiz questions to reduce database load.
   *
   * @param publicId - the public UUID of the quiz
   * @return the quiz data mapped to a response object
   */
  public QuizResponse getQuiz(String publicId) {
    Quiz quiz = quizRepo.findByPublicId(publicId).orElseThrow(() -> new QuizNotFoundException(publicId));
    questionService.cacheQuestions(quiz.getQuestions(), publicId);
    return quizMapper.mapToHttp(quiz);
  }

  /**
   * @param publicId - UUID of the quiz
   * @param request  - create/update quiz data request. Will be changed to a separate request model
   * @return the quiz data mapped to a response object
   */
  @Transactional
  public QuizResponse updateQuiz(String publicId, UpdateQuizRequest request) {
    updateQuizData(publicId, request);
    if (request.questions() != null && !request.questions().isEmpty()) {
      questionService.updateQuestionData(request.questions());
      request.questions().forEach(q -> {
        if (q.options() != null && !q.options().isEmpty()) {
          optionService.updateOptionData(q.options());
        }
      });
    }

    return quizMapper.mapToHttp(quizRepo.findByPublicId(publicId)
        .orElseThrow(() -> new QuizNotFoundException(publicId)));
  }

  public void deleteQuiz(String publicId) {
    quizRepo.deleteByPublicId(publicId);
  }

  /**
   * @param publicId - UUID of the quiz
   * @param request  - create/update quiz data request. Will be changed to a separate request model
   */
  @Transactional
  public void updateQuizData(String publicId, UpdateQuizRequest request) {
    QQuiz quiz = QQuiz.quiz;
    JPAUpdateClause updateClause = new JPAUpdateClause(entityManager, quiz)
        .where(quiz.publicId.eq(publicId));

    // **Dynamically update non-null fields**
    setDynamicUpdates(updateClause, request, new PathBuilder<>(QQuiz.class, "quiz"));

    updateClause.execute();
  }
}