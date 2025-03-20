package com.quiz.quizapp.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.quiz.quizapp.model.httpmodel.request.CreateCompletionPageRequest;
import com.quiz.quizapp.model.httpmodel.request.CreateQuizRequest;
import com.quiz.quizapp.model.httpmodel.request.UpdateQuizRequest;
import com.quiz.quizapp.model.httpmodel.response.QuizResponse;
import com.quiz.quizapp.service.CompletionService;
import com.quiz.quizapp.service.QuizService;
import com.quiz.quizapp.utils.URIGenerator;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(QuizCreatorController.class)
public class QuizCreatorControllerTest {

  @MockBean
  private QuizService quizService;
  @MockBean
  private CompletionService completionService;
  @Autowired
  private MockMvc mvc;

  @Test
  void testCreateQuiz() throws Exception {
    String publicId = UUID.randomUUID().toString();

    when(quizService.saveQuiz(any(CreateQuizRequest.class))).thenReturn(publicId);

    mvc.perform(post("/quiz-creator")
            .contentType("application/json")
            .content("""
                {
                  "quizName": "Test Quiz",
                  "questions": null,
                  "completionPageRequest": null
                }
                """))
        .andExpect(status().isCreated())
        .andExpect(header().string("Quiz-UUID", publicId));

    verify(quizService, times(1)).saveQuiz(any(CreateQuizRequest.class));
  }

  @Test
  void testCreateQuiz_badRequest() throws Exception {
    mvc.perform(post("/quiz-creator")
            .contentType("application/json")
            .content(""))
        .andExpect(status().isBadRequest());

    verify(quizService, never()).saveQuiz(any());
  }

  @Test
  void testGetQuiz() throws Exception {
    String publicId = UUID.randomUUID().toString();
    QuizResponse quizResponse = new QuizResponse("Test Quiz", null);

    when(quizService.getQuiz(publicId)).thenReturn(quizResponse);

    mvc.perform(get("/quiz-creator/{uuid}", publicId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.quizName").value("Test Quiz"));

    verify(quizService, times(1)).getQuiz(publicId);
  }

  @Test
  void testUpdateQuiz() throws Exception {
    String publicId = UUID.randomUUID().toString();
    QuizResponse quizResponse = new QuizResponse("Updated Quiz", null);

    when(quizService.updateQuiz(any(String.class), any(UpdateQuizRequest.class))).thenReturn(quizResponse);

    mvc.perform(put("/quiz-creator/{uuid}", publicId)
            .contentType("application/json")
            .content("""
                {
                  "quizName": "Updated Quiz",
                  "questions": null,
                  "completionPageRequest": null
                }
                """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.quizName").value("Updated Quiz"));

    verify(quizService, times(1)).updateQuiz(eq(publicId), any(UpdateQuizRequest.class));
  }

  @Test
  void testUpdateQuiz_badRequest() throws Exception {
    mvc.perform(put("/quiz-creator/{uuid}", "publicId")
            .contentType("application/json")
            .content(""))
        .andExpect(status().isBadRequest());

    verify(quizService, never()).updateQuiz(anyString(), any());
  }

  @Test
  void testDeleteQuiz() throws Exception {
    String publicId = UUID.randomUUID().toString();

    mvc.perform(delete("/quiz-creator/{uuid}", publicId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value("Your quiz no longer exist"));

    verify(quizService, times(1)).deleteQuiz(publicId);
  }

  @Test
  void testCreateCompletionPage() throws Exception {
    String publicId = UUID.randomUUID().toString();
    String respUrl = URIGenerator.generateURI(publicId);

    CreateCompletionPageRequest request = new CreateCompletionPageRequest("Final", "Thank you!");

    mvc.perform(post("/quiz-creator/complete")
            .header("Quiz-UUID", publicId)
            .contentType("application/json")
            .content("""
                {
                  "title": "Final",
                  "body": "Thank you!"
                }
                """))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.url").value(respUrl));

    verify(completionService, times(1)).createCompletionPage(publicId, request);
  }

  @Test
  void testCreateCompletionPage_badRequest() throws Exception {
    mvc.perform(post("/quiz-creator/complete")
            .contentType("application/json")
            .content(""))
        .andExpect(status().isBadRequest());

    verify(completionService, never()).createCompletionPage(any(), any());
  }


}
