package ctjournal.telegrambot.repository;

import ctjournal.telegrambot.dto.WorkoutDto;

public interface WorkoutRepository {
    void save(String userId, WorkoutDto workoutState);

    WorkoutDto findByUserId(String userId);
}
