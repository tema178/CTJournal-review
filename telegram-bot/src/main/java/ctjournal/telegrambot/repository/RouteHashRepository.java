package ctjournal.telegrambot.repository;

import ctjournal.telegrambot.domain.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.telegram.abilitybots.api.db.DBContext;

import java.util.Map;

@RequiredArgsConstructor
@Repository
public class RouteHashRepository implements RouteRepository {

    private static final String ROUTES = "ROUTES";
    private final DBContext db;

    private Map<String, Route> getTable() {
        return db.getMap(ROUTES);
    }

    @Override
    public void save(String userId, Route route) {
        getTable().put(userId, route);
    }

    @Override
    public Route findByUserId(String userId) {
        return getTable().get(userId);
    }
}
