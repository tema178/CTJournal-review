package ctjournal.service;

import ctjournal.domain.AbstractExercise;
import ctjournal.domain.ExerciseSession;
import ctjournal.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository repository;

    @Override
    public AbstractExercise save(AbstractExercise exercise) {
        return repository.save(exercise);
    }

    @Override
    public AbstractExercise findById(long id) {
        return repository.findById(id).orElseThrow(NullPointerException::new);
    }

    @Override
    public List<AbstractExercise> findAll(long exerciseSessionId) {
        ExerciseSession session = new ExerciseSession();
        session.setId(exerciseSessionId);
        return repository.findByExerciseSession(session);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }
}
