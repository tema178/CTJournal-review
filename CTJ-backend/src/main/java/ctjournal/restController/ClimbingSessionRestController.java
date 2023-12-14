package ctjournal.restController;

import ctjournal.dto.ClimbingSessionDto;
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
    public ClimbingSessionDto save(@RequestBody ClimbingSessionDto session) {
        return service.save(session);
    }

    @GetMapping("/api/session/climbing/{id}")
    public ClimbingSessionDto findById(@PathVariable long id) {
        return service.getById(id);
    }

    @DeleteMapping("/api/session/climbing/{id}")
    public void delete(@PathVariable long id) {
        service.deleteById(id);
    }
}
