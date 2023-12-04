package ctjournal.telegrambot.service;

import ctjournal.telegrambot.domain.ClimbingSession;
import ctjournal.telegrambot.domain.WorkoutState;

public interface ClimbingSessionService {
    ClimbingSession create(WorkoutState workout, String id);

    ClimbingSession update(ClimbingSession session, String id);
}
