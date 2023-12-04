package ctjournal.restController;

import ctjournal.dto.RouteDto;
import ctjournal.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RouteRestController {

    private final RouteService service;

    @PostMapping("/api/route")
    public RouteDto save(@RequestBody RouteDto route) {
        return service.save(route);
    }

    @GetMapping("/api/route/{id}")
    public RouteDto findById(@PathVariable long id) {
        return service.findById(id);
    }

    @GetMapping("/api/route/session/{climbingSessionId}")
    public List<RouteDto> findAll(@PathVariable long climbingSessionId) {
        return service.findByClimbingSessionId(climbingSessionId);
    }

    @DeleteMapping("/api/route/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
