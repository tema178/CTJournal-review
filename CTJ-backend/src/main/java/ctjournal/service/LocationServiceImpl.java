package ctjournal.service;

import ctjournal.domain.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ctjournal.domain.Location;
import ctjournal.repository.LocationRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository repository;

    public Location findById(long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Location> findAll(String name) {
        User user = new User(name);
        user.setUsername(name);
        return repository.findAllByUser(user);
    }

    public List<Location> findAll() {
        return repository.findAll();
    }

    public Location save(Location location) {
        return repository.save(location);
    }

    public Location setFavourite(long id, boolean favourite) {
        Location location = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        location.setFavourite(favourite);
        return save(location);
    }

}
