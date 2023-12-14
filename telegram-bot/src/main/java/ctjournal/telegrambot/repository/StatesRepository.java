package ctjournal.telegrambot.repository;

import ctjournal.telegrambot.domain.States;

public interface StatesRepository {
    void save(String userId, States state);

    States findByUserId(String userId);
}
