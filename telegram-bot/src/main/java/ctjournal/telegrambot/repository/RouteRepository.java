package ctjournal.telegrambot.repository;

import ctjournal.telegrambot.domain.Route;

public interface RouteRepository {
    void save(String userId, Route route);

    Route findByUserId(String userId);
}
