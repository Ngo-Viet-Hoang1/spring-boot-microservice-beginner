package com.twiliver.question_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.twiliver.question_service.model.Answer;
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

    public ResponseEntity<List<Integer>> getQuestionsForQuizz(String categoryName, Integer numQ) {
        List<Integer> questionIds = questionRepo.findRandomQuestionsByCategory(categoryName, numQ);
        return ResponseEntity.ok(questionIds);
    }

    public ResponseEntity<List<Question>> getQuestionsFromIds(List<Integer> questionIds) {
        List<Question> questions = new ArrayList<>();

        questionIds.forEach(id -> {
            questionRepo.findById(id).ifPresent(questions::add);
        });

        return ResponseEntity.ok(questions);
    }

    public ResponseEntity<Integer> getScore(List<Answer> answers) {

        int score = (int) answers.stream()
                .filter(answer -> questionRepo.findById(answer.getId())
                .map(question -> question.getRightAnswer().equals(answer.getAnswer()))
                .orElse(false)
                ).count();

        return ResponseEntity.ok(score);
    }

}
