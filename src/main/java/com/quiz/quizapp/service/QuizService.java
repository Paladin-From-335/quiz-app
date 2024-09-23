package com.quiz.quizapp.service;

import com.quiz.quizapp.model.entity.Question;
import com.quiz.quizapp.model.entity.Quiz;
import com.quiz.quizapp.model.httpmodel.HttpQuiz;
import com.quiz.quizapp.repository.QuizRepository;
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
    Quiz quiz = quizMapper.map(httpQuiz);
     return quizRepo.save(quiz);
  }

  public HttpQuiz getQuiz(Long id) {
    return quizMapper.mapToHttp(quizRepo.findById(id).orElseThrow());
  }
}