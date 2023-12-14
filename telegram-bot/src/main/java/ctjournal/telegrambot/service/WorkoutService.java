package ctjournal.telegrambot.service;

import ctjournal.telegrambot.dto.WorkoutDto;

public interface WorkoutService {
    WorkoutDto create(String id);

    WorkoutDto update(WorkoutDto workout, String id);

    WorkoutDto findById(long id, String userId);

    WorkoutDto updateLocation(WorkoutDto workoutState, String id);

    WorkoutDto updateClimbingSession(WorkoutDto workoutState, String id);
}
