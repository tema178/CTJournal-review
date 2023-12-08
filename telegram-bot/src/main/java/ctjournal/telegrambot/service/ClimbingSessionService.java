package ctjournal.telegrambot.service;

import ctjournal.telegrambot.dto.WorkoutDto;
import ctjournal.telegrambot.dto.ClimbingSessionDto;

public interface ClimbingSessionService {
    ClimbingSessionDto create(WorkoutDto workout, String id);

    ClimbingSessionDto update(ClimbingSessionDto session, String id);
}
