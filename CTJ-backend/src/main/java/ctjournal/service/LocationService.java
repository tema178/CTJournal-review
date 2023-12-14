package ctjournal.service;

import ctjournal.domain.Location;

import java.util.List;

public interface LocationService {

    Location findById(long id);

    List<Location> findAll(String name);

    Location save(Location location);

    Location setFavourite(long id, boolean favourite);

    List<Location> findAll();
}
