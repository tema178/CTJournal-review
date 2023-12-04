package ctjournal.restController;

import ctjournal.domain.Workout;
import ctjournal.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class WorkoutRestController {

    private final WorkoutService service;

    @PostMapping("/api/workout")
    public Workout save(@RequestBody Workout workout) {
        return service.save(workout);
    }

    @GetMapping("/api/workout/{id}")
    public Workout findById(@PathVariable long id) {
        return service.getById(id);
    }

//    @GetMapping("/api/workout/session/{exerciseSessionId}") //TODO users workouts
//    public List<Workout> findAll(long exerciseSessionId) {
//        return service.getAll(exerciseSessionId);
//    }

    @DeleteMapping("/api/workout/{id}")
    public void delete(@PathVariable long id) {
        service.deleteById(id);
    }
}
