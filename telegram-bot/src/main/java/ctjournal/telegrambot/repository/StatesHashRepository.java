package ctjournal.telegrambot.repository;

import ctjournal.telegrambot.domain.States;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.telegram.abilitybots.api.db.DBContext;

import java.util.Map;

@RequiredArgsConstructor
@Repository
public class StatesHashRepository implements StatesRepository {

    private static final String STATES = "STATES";
    private final DBContext db;

    private Map<String, States> getTable() {
        return db.getMap(STATES);
    }

    @Override
    public void save(String userId, States state) {
        getTable().put(userId, state);
    }

    @Override
    public States findByUserId(String userId) {
        return getTable().get(userId);
    }
}
