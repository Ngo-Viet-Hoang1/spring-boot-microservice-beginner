package com.twiliver.question_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.twiliver.question_service.model.Question;
import com.twiliver.question_service.repo.QuestionRepo;

@Service
public class QuestionService {

    private final QuestionRepo questionRepo;

    public QuestionService(QuestionRepo questionRepo) {
        this.questionRepo = questionRepo;
    }

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            List<Question> questions = questionRepo.findAll();

            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    public List<Question> getAllQuestionsByCategory(String category) {
        return questionRepo.findByCategoryAllIgnoringCase(category);
    }

    public Question createQuestion(Question question) {
        return questionRepo.save(question);
    }

    public void deleteQuestion(Integer id) {
        questionRepo.deleteById(id);
    }

}
