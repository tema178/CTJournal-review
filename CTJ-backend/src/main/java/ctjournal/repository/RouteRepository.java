package ctjournal.repository;

import ctjournal.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {

    List<Route> findByClimbingSessionId(long climbingSessionId);
}
