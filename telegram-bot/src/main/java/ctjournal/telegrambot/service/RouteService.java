package ctjournal.telegrambot.service;

import ctjournal.telegrambot.domain.ClimbingSession;
import ctjournal.telegrambot.domain.Route;

import java.util.List;

public interface RouteService {
    Route create(String name, ClimbingSession climbingSession, String id);

    Route update(Route route, String id);

    List<Route> getRoutes(long climbingSessionId, String id);

    Route getRoute(long id, String userId);
}
