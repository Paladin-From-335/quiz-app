package com.quiz.quizapp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.quiz.quizapp.repository")
public class AppConfiguration {
}
