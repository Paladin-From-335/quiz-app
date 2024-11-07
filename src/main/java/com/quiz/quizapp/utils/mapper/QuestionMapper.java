package com.quiz.quizapp.utils.mapper;

import com.quiz.quizapp.model.entity.Question;
import com.quiz.quizapp.model.httpmodel.HttpQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring", uses = OptionMapper.class)
public abstract class QuestionMapper {

//  @Mapping(target = "options", ignore = true)
  public abstract Question map(HttpQuestion source);

//  @Mapping(target = "options", ignore = true)
  public abstract List<Question> map(List<HttpQuestion> source);

  public abstract HttpQuestion mapToHttp(Question source);

  public abstract List<HttpQuestion> mapToHttp(List<Question> source);
}
