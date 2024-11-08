package com.quiz.quizapp.model.httpmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpCompletion {
  private String header;
  private String body;
  @JsonProperty("time_spent")
  private Integer timeSpent;
}
