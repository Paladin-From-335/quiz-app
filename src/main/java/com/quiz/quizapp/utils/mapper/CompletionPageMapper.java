package com.quiz.quizapp.utils.mapper;

import com.quiz.quizapp.model.entity.CompletionPage;
import com.quiz.quizapp.model.httpmodel.request.CreateCompletionPageRequest;
import com.quiz.quizapp.model.httpmodel.response.CompletionPageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class CompletionPageMapper {

  public abstract CompletionPage map(CreateCompletionPageRequest source);

  public abstract CompletionPageResponse map(CompletionPage source);
}
