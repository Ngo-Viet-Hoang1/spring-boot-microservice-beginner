package com.twiliver.quizz_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twiliver.quizz_service.model.Quizz;

@Repository
public interface QuizzRepo extends JpaRepository<Quizz, Integer> {

}
