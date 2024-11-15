package com.quiz.quizapp.utils.mapper;

import com.quiz.quizapp.model.entity.Option;
import com.quiz.quizapp.model.httpmodel.HttpOption;
import com.quiz.quizapp.model.httpmodel.request.OptionRequest;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class OptionMapper {

  public abstract Option map(HttpOption source);

  public abstract List<Option> map(List<HttpOption> source);

  public abstract Option mapRequestToEntity(OptionRequest source);

  public abstract List<Option> mapRequestToEntity(List<OptionRequest> source);

  public abstract HttpOption mapToHttp(Option source);

  public abstract List<HttpOption> mapToHttp(List<Option> source);

}
