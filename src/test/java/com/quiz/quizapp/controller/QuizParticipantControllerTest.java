package com.quiz.quizapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.quiz.quizapp.exception.QuizNotCompletedException;
import com.quiz.quizapp.model.httpmodel.request.AnswerRequest;
import com.quiz.quizapp.model.httpmodel.response.CompletionPageResponse;
import com.quiz.quizapp.model.httpmodel.response.QuizResponse;
import com.quiz.quizapp.service.AnswerValidationService;
import com.quiz.quizapp.service.CompletionService;
import com.quiz.quizapp.service.QuizService;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(QuizParticipantController.class)
public class QuizParticipantControllerTest {

  @MockBean
  private QuizService quizService;
  @MockBean
  private CompletionService completionService;
  @MockBean
  private AnswerValidationService validationService;

  @Autowired
  private MockMvc mvc;

  @Test
  void testGetQuiz() throws Exception {
    String publicId = UUID.randomUUID().toString();
    QuizResponse quizResponse = new QuizResponse("Sample Quiz", null, null);

    when(quizService.getQuiz(publicId)).thenReturn(quizResponse);

    mvc.perform(get("/quiz/{uuid}", publicId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.quizName").value("Sample Quiz"));

    verify(quizService, times(1)).getQuiz(publicId);
  }

  @Test
  void testGetCompletionPage() throws Exception {
    String publicId = UUID.randomUUID().toString();
    CompletionPageResponse response = new CompletionPageResponse(
        "Congratulations!",
        "You successfully completed the quiz.",
        Duration.ofSeconds(1)
    );

    when(completionService.getCompletionData(publicId, true))
        .thenReturn(response);

    mvc.perform(get("/quiz/{uuid}/complete", publicId)
            .param("isCompleted", "true"))
        .andExpect(status().isOk());

    verify(completionService, times(1)).getCompletionData(publicId, true);
  }

  @Test
  void testGetCompletionPage_incomplete() throws Exception {
    String publicId = UUID.randomUUID().toString();

    when(completionService.getCompletionData(publicId, false))
        .thenThrow(new QuizNotCompletedException());

    mvc.perform(get("/quiz/{uuid}/complete", publicId)
            .param("isCompleted", "false"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$").value("Do not cheat. Quiz is not completed!"));

    verify(completionService, times(1)).getCompletionData(publicId, false);
  }

  @Test
  void testSubmitAnswer() throws Exception {
    String publicId = UUID.randomUUID().toString();
    AnswerRequest request = new AnswerRequest(1L, List.of(1L, 2L, 3L));

    when(validationService.validateQuestionAnswers(any(), any())).thenReturn(true);

    mvc.perform(post("/quiz/{uuid}", publicId)
            .contentType("application/json")
            .content("""
                {
                  "questionId": 1,
                  "participantAnswers": [1,2,3]
                }
                """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(true));

    verify(validationService, times(1)).validateQuestionAnswers(publicId, request);
  }

  @Test
  void testSubmitAnswer_badRequest() throws Exception {
    mvc.perform(post("/quiz/{uuid}", "publicId")
            .contentType("application/json")
            .content(""))
        .andExpect(status().isBadRequest());

    verify(validationService, never()).validateQuestionAnswers(any(), any());
  }
}
