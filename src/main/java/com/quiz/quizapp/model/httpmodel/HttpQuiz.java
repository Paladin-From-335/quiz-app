package com.quiz.quizapp.model.httpmodel;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpQuiz {
  private String quizName;
  private List<HttpQuestion> questions;
}
