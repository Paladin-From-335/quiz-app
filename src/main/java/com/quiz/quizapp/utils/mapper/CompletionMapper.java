package com.quiz.quizapp.utils.mapper;

import com.quiz.quizapp.model.entity.CompletionData;
import com.quiz.quizapp.model.httpmodel.HttpCompletionData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CompletionMapper {

  public abstract CompletionData map(HttpCompletionData source);

  public abstract HttpCompletionData map(CompletionData source);
}
