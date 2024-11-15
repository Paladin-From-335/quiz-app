package com.quiz.quizapp.service;

import com.quiz.quizapp.model.entity.Quiz;
import com.quiz.quizapp.model.httpmodel.HttpQuiz;
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

  @Transactional
  public Quiz saveQuiz(HttpQuiz httpQuiz) {
    String publicId = UUIDUtil.generateUUID();
    Quiz quiz = quizMapper.map(httpQuiz);
    quiz.setPublicId(publicId);
    return quizRepo.save(quiz);
  }

  public HttpQuiz getQuiz(String publicId) {
    return quizMapper.mapToHttp(quizRepo.findByPublicId(publicId).orElseThrow());
  }

  @Transactional
  public HttpQuiz updateQuiz(String publicId, HttpQuiz httpQuiz) {
    Long quizId = quizRepo.getQuizIdByPublicId(publicId).orElseThrow();
    Quiz quiz = quizMapper.map(httpQuiz);
    quiz.setId(quizId);
    return quizMapper.mapToHttp(quizRepo.save(quiz));
  }

  public void deleteQuiz(String publicId) {
    quizRepo.deleteByPublicId(publicId);
  }

}