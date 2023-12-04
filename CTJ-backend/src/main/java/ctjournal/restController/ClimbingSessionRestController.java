package ctjournal.restController;

import ctjournal.domain.ClimbingSession;
import ctjournal.service.ClimbingSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ClimbingSessionRestController {

    private final ClimbingSessionService service;

    @PostMapping("/api/session/climbing")
    public ClimbingSession save(@RequestBody ClimbingSession session) {
        return service.save(session);
    }

    @GetMapping("/api/session/climbing/{id}")
    public ClimbingSession findById(@PathVariable long id) {
        return service.getById(id);
    }

//    @GetMapping("/api/ClimbingSession/session/{exerciseSessionId}") //TODO users ClimbingSessions
//    public List<ClimbingSession> findAll(long exerciseSessionId) {
//        return service.getAll(exerciseSessionId);
//    }

    @DeleteMapping("/api/session/climbing/{id}")
    public void delete(@PathVariable long id) {
        service.deleteById(id);
    }
}
