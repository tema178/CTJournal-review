package ctjournal.repository;

import ctjournal.domain.AbstractExercise;
import ctjournal.domain.ExerciseSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<AbstractExercise, Long> {

    List<AbstractExercise> findByExerciseSession(ExerciseSession session);

}
