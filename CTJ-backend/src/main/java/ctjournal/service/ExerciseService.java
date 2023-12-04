package ctjournal.service;

import ctjournal.domain.AbstractExercise;

import java.util.List;

public interface ExerciseService {
    AbstractExercise save(AbstractExercise exercise);

    AbstractExercise findById(long id);

    List<AbstractExercise> findAll(long climbingSessionId);

    void delete(long id);
}
