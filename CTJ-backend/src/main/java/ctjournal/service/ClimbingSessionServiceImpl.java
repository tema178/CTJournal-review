package ctjournal.service;

import ctjournal.domain.ClimbingSession;
import ctjournal.domain.Workout;
import ctjournal.dto.ClimbingSessionDto;
import ctjournal.repository.ClimbingSessionRepository;
import ctjournal.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClimbingSessionServiceImpl implements ClimbingSessionService {

    private final ClimbingSessionRepository repository;

    private final WorkoutRepository workoutRepository;

    @Override
    public ClimbingSessionDto save(ClimbingSessionDto session) {
        ClimbingSession sessionDomain = session.toDomain();
        Workout workout = workoutRepository.findById(session.getWorkout()).orElseThrow(NullPointerException::new);
        sessionDomain.setWorkout(workout);
        return new ClimbingSessionDto(repository.save(sessionDomain));
    }

    @Override
    public ClimbingSessionDto getById(long id) {
        return new ClimbingSessionDto(repository.findById(id).orElseThrow(NullPointerException::new));
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
