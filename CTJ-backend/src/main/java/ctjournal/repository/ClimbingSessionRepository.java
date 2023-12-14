package ctjournal.repository;

import ctjournal.domain.ClimbingSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClimbingSessionRepository extends JpaRepository<ClimbingSession, Long> {
}
