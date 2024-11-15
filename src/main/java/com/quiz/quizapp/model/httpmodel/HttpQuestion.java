package com.quiz.quizapp.model.httpmodel;

import com.quiz.quizapp.model.httpmodel.request.OptionRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpQuestion {
  private String questionName;
  private List<OptionRequest> options;
}
