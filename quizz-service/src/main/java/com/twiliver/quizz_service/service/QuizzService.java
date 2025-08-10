package com.twiliver.quizz_service.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.twiliver.quizz_service.feign.QuizzInterface;
import com.twiliver.quizz_service.model.Answer;
import com.twiliver.quizz_service.model.Question;
import com.twiliver.quizz_service.model.Quizz;
import com.twiliver.quizz_service.repo.QuizzRepo;

@Service
public class QuizzService {

    private final QuizzRepo quizzRepo;
    private final QuizzInterface quizzInterface;

    public QuizzService(QuizzRepo quizzRepo, QuizzInterface quizzInterface) {
        this.quizzRepo = quizzRepo;
        this.quizzInterface = quizzInterface;
    }

    public ResponseEntity<Quizz> createQuizz(String title, int numQ, String category) {
        try {
            ResponseEntity<List<Integer>> response = quizzInterface.getQuestions(category, numQ);
            if (response.getStatusCode().is2xxSuccessful()) {
                List<Integer> questionIds = response.getBody();
                Quizz quizz = new Quizz();
                quizz.setTitle(title);
                quizz.setQuestionIds(questionIds);
                quizzRepo.save(quizz);
                return ResponseEntity.ok(quizz);
            } else {
                return ResponseEntity.status(response.getStatusCode()).body(new Quizz());
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Quizz());
        }
    }

    public ResponseEntity<Quizz> getQuizzById(Integer id) {
        try {
            return quizzRepo.findById(id)
                    .map(quizz -> ResponseEntity.ok(quizz))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Quizz());
        }
    }

    public ResponseEntity<List<Question>> getQuestionsByQuizzId(Integer id) {
        try {
            var quizz = quizzRepo.findById(id);
            List<Integer> questionIds = quizz.map(Quizz::getQuestionIds).orElse(List.of());
            ResponseEntity<List<Question>> questionsResponse = quizzInterface.getQuestionsFromIds(questionIds);

            if (questionsResponse.getStatusCode().is2xxSuccessful()) {
                List<Question> questions = questionsResponse.getBody();
                return ResponseEntity.ok(questions);
            } else {
                return ResponseEntity.status(questionsResponse.getStatusCode()).body(List.of());
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(List.of());
        }
    }

    public String calculateResult(List<Answer> answers) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
