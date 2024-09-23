package com.quiz.quizapp.utils.mapper;

import com.quiz.quizapp.model.entity.Quiz;
import com.quiz.quizapp.model.httpmodel.HttpQuiz;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = QuestionMapper.class)
public abstract class QuizMapper {

//  @Mapping(target = "questions", ignore = true)
  public abstract Quiz map(HttpQuiz source);

  public abstract HttpQuiz mapToHttp(Quiz source);
}
