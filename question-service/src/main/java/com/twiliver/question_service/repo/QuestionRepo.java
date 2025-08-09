package com.twiliver.question_service.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.twiliver.question_service.model.Question;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {

    public List<Question> findByCategoryAllIgnoringCase(String category);

    @Query(value = "select * from question where category=?1 order by random() limit ?2", nativeQuery = true)
    public List<Question> findRandomQuestionsByCategory(String category, int numQ);

}
