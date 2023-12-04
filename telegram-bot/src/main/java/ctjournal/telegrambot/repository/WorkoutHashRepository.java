package ctjournal.telegrambot.repository;

import ctjournal.telegrambot.domain.WorkoutState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.telegram.abilitybots.api.db.DBContext;

import java.util.Map;

@RequiredArgsConstructor
@Repository
public class WorkoutHashRepository implements WorkoutRepository {

    private static final String WORKOUTS = "WORKOUTS";
    private final DBContext db;

    private Map<String, WorkoutState> getTable() {
        return db.getMap(WORKOUTS);
    }

    @Override
    public void save(String userId, WorkoutState workoutState) {
        getTable().put(userId, workoutState);
    }

    @Override
    public WorkoutState findByUserId(String userId) {
        return getTable().get(userId);
    }
}
