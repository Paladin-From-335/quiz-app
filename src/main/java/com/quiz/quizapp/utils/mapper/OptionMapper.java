package com.quiz.quizapp.utils.mapper;

import com.quiz.quizapp.model.entity.Option;
import com.quiz.quizapp.model.httpmodel.request.CreateOptionRequest;
import com.quiz.quizapp.model.httpmodel.response.OptionResponse;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class OptionMapper {

  public abstract Option map(CreateOptionRequest source);

  public abstract List<Option> map(List<CreateOptionRequest> source);

  public abstract OptionResponse mapToHttp(Option source);

  public abstract List<OptionResponse> mapToHttp(List<Option> source);

}
