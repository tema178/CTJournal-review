package ctjournal.service;

import ctjournal.domain.Workout;
import ctjournal.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository repository;


    @Override
    public Workout save(Workout workout) {
        return repository.save(workout);
    }

    @Override
    public List<Workout> getAll() {
        return repository.findAll();
    }

    @Override
    public Workout getById(long id) {
        return repository.findById(id).orElseThrow(NullPointerException::new);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
