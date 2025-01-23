package com.quiz.quizapp.utils.mapper;

import com.quiz.quizapp.model.entity.Completion;
import com.quiz.quizapp.model.httpmodel.request.CreateCompletionPageRequest;
import com.quiz.quizapp.model.httpmodel.response.CompletionPageResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CompletionMapper {

  public abstract Completion map(CreateCompletionPageRequest source);

  public abstract CompletionPageResponse map(Completion source);
}
