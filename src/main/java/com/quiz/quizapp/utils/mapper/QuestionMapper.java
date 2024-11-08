package com.quiz.quizapp.utils.mapper;

import com.quiz.quizapp.model.entity.Question;
import com.quiz.quizapp.model.httpmodel.HttpQuestion;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring", uses = OptionMapper.class)
public abstract class QuestionMapper {

  public abstract Question map(HttpQuestion source);

  public abstract List<Question> map(List<HttpQuestion> source);

  public abstract HttpQuestion mapToHttp(Question source);

  public abstract List<HttpQuestion> mapToHttp(List<Question> source);
}
