package ctjournal.restController;

import ctjournal.domain.Location;
import ctjournal.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LocationRestController {

    private final LocationService service;

    @GetMapping("/api/location/{id}")
    public Location findById(@PathVariable long id) {
        return service.findById(id);
    }

    @GetMapping("/api/location/user/{name}")
    public List<Location> findAll(@PathVariable String name) {
        return service.findAll(name);
    }

    @GetMapping("/api/location")
    public List<Location> findAll() {
        return service.findAll();
    }

    @PostMapping("/api/location")
    public Location save(@RequestBody Location location) {
        return service.save(location);
    }

    @PatchMapping("/api/location/{id}/favourite")
    public Location setFavourite(@PathVariable long id, boolean favourite) {
        return service.setFavourite(id, favourite);
    }
}
