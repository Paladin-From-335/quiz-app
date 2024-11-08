package com.quiz.quizapp.service;

import com.quiz.quizapp.model.entity.Quiz;
import com.quiz.quizapp.model.httpmodel.HttpQuiz;
import com.quiz.quizapp.repository.QuizRepository;
import com.quiz.quizapp.utils.URIGenerator;
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
  public String saveQuiz(HttpQuiz httpQuiz) {
    String publicId = UUIDUtil.generateUUID();
    Quiz quiz = quizMapper.map(httpQuiz);
    quiz.setPublicId(publicId);
    quizRepo.save(quiz);
    return URIGenerator.generateURI(publicId);
  }

  public HttpQuiz getQuiz(Long id) {
    return quizMapper.mapToHttp(quizRepo.findById(id).orElseThrow());
  }

  @Transactional
  public HttpQuiz updateQuiz(Long id, HttpQuiz httpQuiz) {
    Quiz quiz = quizMapper.map(httpQuiz);
    quiz.setId(id);
    return quizMapper.mapToHttp(quizRepo.save(quiz));
  }

  public void deleteQuiz(Long id) {
    quizRepo.deleteById(id);
  }

}