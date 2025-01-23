package com.quiz.quizapp.utils.mapper;

import com.quiz.quizapp.model.entity.Quiz;
import com.quiz.quizapp.model.httpmodel.request.CreateQuizRequest;
import com.quiz.quizapp.model.httpmodel.response.QuizResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
    uses = {QuestionMapper.class, CompletionMapper.class})
public abstract class QuizMapper {

  public abstract Quiz map(CreateQuizRequest source);

  public abstract QuizResponse mapToHttp(Quiz source);
}
