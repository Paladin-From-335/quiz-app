package com.quiz.quizapp.utils;


public class URIGenerator {

  private final static String BASE_URL = "https://quiz-app.com/quiz/%s"; //TODO: change mock to real

  public static String generateURI(String publicId) {
    return String.format(BASE_URL, publicId);
  }

}
