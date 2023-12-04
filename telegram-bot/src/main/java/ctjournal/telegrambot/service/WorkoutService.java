package ctjournal.telegrambot.service;

import ctjournal.telegrambot.dto.WorkoutDto;
import ctjournal.telegrambot.domain.WorkoutState;

public interface WorkoutService {
    WorkoutDto createWorkout(String id);

    WorkoutDto updateWorkout(WorkoutDto workout, String id);

    WorkoutDto findById(long id, String userId);

    WorkoutDto updateLocation(WorkoutState workoutState, String id);

    WorkoutDto updateClimbingSession(WorkoutState workoutState, String id);
}
