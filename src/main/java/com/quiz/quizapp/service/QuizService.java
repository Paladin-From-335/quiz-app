package com.quiz.quizapp.service;

import com.quiz.quizapp.exception.QuizNotFoundException;
import com.quiz.quizapp.model.entity.Quiz;
import com.quiz.quizapp.model.httpmodel.request.CreateQuizRequest;
import com.quiz.quizapp.model.httpmodel.response.QuizResponse;
import com.quiz.quizapp.repository.QuizRepository;
import com.quiz.quizapp.utils.UUIDUtil;
import com.quiz.quizapp.utils.mapper.QuizMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizService {

  private final QuizRepository quizRepo;
  private final QuizMapper quizMapper;
  private final QuestionService questionService;

  @Transactional
  public Quiz saveQuiz(CreateQuizRequest request) {
    String publicId = UUIDUtil.generateUUID();
    Quiz quiz = quizMapper.map(request);
    quiz.setPublicId(publicId);
    return quizRepo.save(quiz);
  }

  public QuizResponse getQuiz(String publicId) {
    Quiz quiz = quizRepo.findByPublicId(publicId).orElseThrow(() -> new QuizNotFoundException(publicId));
    questionService.cacheQuestions(quiz.getQuestions(), publicId);
    return quizMapper.mapToHttp(quiz);
  }

  /**
   *
   * @param publicId - UUID of the quiz
   * @param request - create/update quiz data request. Will be changed to a separate request model
   * @return quiz data
   */
  @Transactional
  public QuizResponse updateQuiz(String publicId, CreateQuizRequest request) {
    Long quizId = quizRepo.getQuizIdByPublicId(publicId).orElseThrow();
    Quiz quiz = quizMapper.map(request);
    quiz.setId(quizId);
    return quizMapper.mapToHttp(quizRepo.save(quiz));
  }

  public void deleteQuiz(String publicId) {
    quizRepo.deleteByPublicId(publicId);
  }

}