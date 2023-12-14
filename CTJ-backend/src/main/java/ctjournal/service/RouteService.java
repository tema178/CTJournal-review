package ctjournal.service;

import ctjournal.dto.RouteDto;

import java.util.List;

public interface RouteService {
    RouteDto save(RouteDto route);

    RouteDto findById(long id);

    List<RouteDto> findByClimbingSessionId(long climbingSessionId);

    void delete(long id);
}
