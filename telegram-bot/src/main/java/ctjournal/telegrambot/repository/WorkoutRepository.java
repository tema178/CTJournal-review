package ctjournal.telegrambot.repository;

import ctjournal.telegrambot.domain.WorkoutState;

public interface WorkoutRepository {
    void save(String userId, WorkoutState workoutState);

    WorkoutState findByUserId(String userId);
}
