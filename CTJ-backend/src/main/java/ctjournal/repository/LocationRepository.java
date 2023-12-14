package ctjournal.repository;

import ctjournal.domain.Location;
import ctjournal.domain.User;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@NonNullApi
public interface LocationRepository extends JpaRepository<Location, Long> {

    @EntityGraph(attributePaths = {"user"})
    @Override
    Optional<Location> findById(Long id);

    List<Location> findAllByUser(User user);

}
