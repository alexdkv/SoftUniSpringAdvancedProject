package com.finalProject.SoftUniProject.repository;

import com.finalProject.SoftUniProject.model.entity.Exercise;
import com.finalProject.SoftUniProject.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByCoach(User coach);


    List<Exercise> findByCoachNot(User coach);
}
