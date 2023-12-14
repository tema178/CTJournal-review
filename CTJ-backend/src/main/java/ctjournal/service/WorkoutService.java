package ctjournal.service;

import ctjournal.dto.WorkoutDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface WorkoutService {
    WorkoutDto save(Authentication authentication, WorkoutDto book);

    List<WorkoutDto> getAll();

    WorkoutDto getById(long id);

    void deleteById(long id);
}
