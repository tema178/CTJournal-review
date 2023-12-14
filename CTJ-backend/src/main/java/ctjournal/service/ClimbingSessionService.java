package ctjournal.service;

import ctjournal.dto.ClimbingSessionDto;

public interface ClimbingSessionService {
    ClimbingSessionDto save(ClimbingSessionDto session);

    ClimbingSessionDto getById(long id);

    void deleteById(long id);
}
