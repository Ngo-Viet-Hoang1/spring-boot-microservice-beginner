package com.twiliver.quizz_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twiliver.quizz_service.model.Answer;
import com.twiliver.quizz_service.model.Question;
import com.twiliver.quizz_service.model.Quizz;
import com.twiliver.quizz_service.model.QuizzDto;
import com.twiliver.quizz_service.service.QuizzService;

@RestController
@RequestMapping("quizz")
public class QuizzController {

    private final QuizzService quizzService;

    public QuizzController(QuizzService quizzService) {
        this.quizzService = quizzService;
    }

    @PostMapping("create")
    public ResponseEntity<Quizz> createQuizz(@RequestBody QuizzDto quizzDto) {
        return quizzService.createQuizz(quizzDto.getTitle(), quizzDto.getNumQ(), quizzDto.getCategory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quizz> getQuizz(@PathVariable Integer id) {
        return quizzService.getQuizzById(id);
    }

    @GetMapping("/{id}/questions")
    public ResponseEntity<List<Question>> getQuestionsByQuizzId(@PathVariable Integer id) {
        return quizzService.getQuestionsByQuizzId(id);
    }

    @GetMapping("calculateResult")
    public String calculateResult(@RequestParam List<Answer> answers) {
        return quizzService.calculateResult(answers);
    }

}
