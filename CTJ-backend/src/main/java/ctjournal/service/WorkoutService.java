package ctjournal.service;

import ctjournal.domain.Workout;

import java.util.List;

public interface WorkoutService {
    Workout save(Workout book);

    List<Workout> getAll();

    Workout getById(long id);

    void deleteById(long id);
}
