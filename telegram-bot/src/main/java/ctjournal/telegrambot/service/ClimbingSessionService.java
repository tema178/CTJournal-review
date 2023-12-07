package ctjournal.telegrambot.service;

import ctjournal.telegrambot.domain.WorkoutState;
import ctjournal.telegrambot.dto.ClimbingSessionDto;

public interface ClimbingSessionService {
    ClimbingSessionDto create(WorkoutState workout, String id);

    ClimbingSessionDto update(ClimbingSessionDto session, String id);
}
