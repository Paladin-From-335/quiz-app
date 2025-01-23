package com.quiz.quizapp.utils.mapper;

import com.quiz.quizapp.model.entity.Question;
import com.quiz.quizapp.model.httpmodel.request.CreateQuestionRequest;
import com.quiz.quizapp.model.httpmodel.response.QuestionResponse;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring", uses = OptionMapper.class)
public abstract class QuestionMapper {

  public abstract Question map(CreateQuestionRequest source);

  public abstract List<Question> map(List<CreateQuestionRequest> source);

  public abstract QuestionResponse mapToHttp(Question source);

  public abstract List<QuestionResponse> mapToHttp(List<Question> source);
}
