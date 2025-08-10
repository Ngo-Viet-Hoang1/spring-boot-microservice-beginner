package com.twiliver.quizz_service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.twiliver.quizz_service.model.Answer;
import com.twiliver.quizz_service.model.Question;

@FeignClient(name = "QUESTION-SERVICE")
public interface QuizzInterface {

    @GetMapping("question/generate")
    public ResponseEntity<List<Integer>> getQuestions(@RequestParam String categoryName, @RequestParam Integer numQ);

    @PostMapping("question/getQuestions")
    public ResponseEntity<List<Question>> getQuestionsFromIds(@RequestBody List<Integer> questionIds);

    @PostMapping("question/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Answer> answers);
}
