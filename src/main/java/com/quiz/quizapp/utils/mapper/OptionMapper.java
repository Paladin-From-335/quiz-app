package com.quiz.quizapp.utils.mapper;

import com.quiz.quizapp.model.entity.Option;
import com.quiz.quizapp.model.httpmodel.request.CreateOptionRequest;
import com.quiz.quizapp.model.httpmodel.response.OptionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class OptionMapper {

  @Mapping(target = "name", source = "optionName")
  public abstract Option map(CreateOptionRequest source);

  @Mapping(target = "name", source = "optionName")
  public abstract List<Option> map(List<CreateOptionRequest> source);

  @Mapping(source = "name", target = "optionName")
  public abstract OptionResponse mapToHttp(Option source);

  @Mapping(source = "name", target = "optionName")
  public abstract List<OptionResponse> mapToHttp(List<Option> source);

}
