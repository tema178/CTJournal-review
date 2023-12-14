package ctjournal.telegrambot.repository;

import ctjournal.telegrambot.dto.WorkoutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.telegram.abilitybots.api.db.DBContext;

import java.util.Map;

@RequiredArgsConstructor
@Repository
public class WorkoutHashRepository implements WorkoutRepository {

    private static final String WORKOUTS = "WORKOUTS";
    private final DBContext db;

    private Map<String, WorkoutDto> getTable() {
        return db.getMap(WORKOUTS);
    }

    @Override
    public void save(String userId, WorkoutDto workoutState) {
        getTable().put(userId, workoutState);
    }

    @Override
    public WorkoutDto findByUserId(String userId) {
        return getTable().get(userId);
    }
}
