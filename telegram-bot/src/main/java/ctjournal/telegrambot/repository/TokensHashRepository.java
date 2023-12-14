package ctjournal.telegrambot.repository;

import ctjournal.telegrambot.domain.States;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.telegram.abilitybots.api.db.DBContext;

import java.util.Map;

@RequiredArgsConstructor
@Repository
public class TokensHashRepository {

    private static final String TOKENS = "TOKENS";
    private final DBContext db;

    private Map<String, String> getTable() {
        return db.getMap(TOKENS);
    }

    public void save(String userId, String token) {
        getTable().put(userId, token);
    }

    public String findByUserId(String userId) {
        return getTable().get(userId);
    }
}
