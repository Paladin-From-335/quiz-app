package com.quiz.quizapp.model.httpmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpOption {
  private String name;
  private Boolean isCorrect;
}
