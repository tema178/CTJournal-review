package ctjournal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ctjournal.domain.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

//    @EntityGraph(attributePaths = {"author", "genre"})
//    @Override
//    List<Book> findAll();
}
