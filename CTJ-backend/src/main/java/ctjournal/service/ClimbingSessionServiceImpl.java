package ctjournal.service;

import ctjournal.domain.ClimbingSession;
import ctjournal.domain.Workout;
import ctjournal.repository.ClimbingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClimbingSessionServiceImpl implements ClimbingSessionService {

    private final ClimbingSessionRepository repository;

    private final WorkoutService workoutService;

    @Override
    public ClimbingSession save(ClimbingSession session) {
        Workout workout = workoutService.getById(session.getWorkout().getId()); //TODO detached entity passed to persist
        session.setWorkout(workout);
        return repository.save(session);
    }

    @Override
    public List<ClimbingSession> getAll() {
        return repository.findAll();
    }

    @Override
    public ClimbingSession getById(long id) {
        return repository.findById(id).orElseThrow(NullPointerException::new);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
