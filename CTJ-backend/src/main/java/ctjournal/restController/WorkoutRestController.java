package ctjournal.restController;

import ctjournal.dto.WorkoutDto;
import ctjournal.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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
    public WorkoutDto save(Authentication authentication,
                           @RequestBody WorkoutDto workout) {
        return service.save(authentication, workout);
    }

    @GetMapping("/api/workout/{id}")
    public WorkoutDto findById(@PathVariable long id) {
        return service.getById(id);
    }

    @DeleteMapping("/api/workout/{id}")
    public void delete(@PathVariable long id) {
        service.deleteById(id);
    }
}
