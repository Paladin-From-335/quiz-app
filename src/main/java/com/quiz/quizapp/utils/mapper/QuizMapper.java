package com.quiz.quizapp.utils.mapper;

import com.quiz.quizapp.model.entity.Quiz;
import com.quiz.quizapp.model.httpmodel.HttpQuiz;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
    uses = {QuestionMapper.class, CompletionMapper.class})
public abstract class QuizMapper {

  public abstract Quiz map(HttpQuiz source);

  public abstract HttpQuiz mapToHttp(Quiz source);
}
