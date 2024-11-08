package com.quiz.quizapp.utils.mapper;

import com.quiz.quizapp.model.entity.Completion;
import com.quiz.quizapp.model.httpmodel.HttpCompletion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CompletionMapper {

  public abstract Completion map(HttpCompletion source);

  public abstract HttpCompletion map(Completion source);
}
