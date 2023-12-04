package ctjournal.service;

import ctjournal.domain.ClimbingSession;

import java.util.List;

public interface ClimbingSessionService {
    ClimbingSession save(ClimbingSession session);

    List<ClimbingSession> getAll();

    ClimbingSession getById(long id);

    void deleteById(long id);
}
