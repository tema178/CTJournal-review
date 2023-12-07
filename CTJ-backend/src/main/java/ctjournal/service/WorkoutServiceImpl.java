package ctjournal.service;

import ctjournal.dto.WorkoutDto;
import ctjournal.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository repository;


    @Override
    public WorkoutDto save(Authentication authentication, WorkoutDto workoutDto) {
        workoutDto.setUser(authentication.getName());
        return WorkoutDto.toDto(repository.save(workoutDto.toDomain()));
    }

    @Override
    public List<WorkoutDto> getAll() {
        return WorkoutDto.toDto(repository.findAll());
    }

    @Override
    public WorkoutDto getById(long id) {
        return WorkoutDto.toDto(repository.findById(id).orElseThrow(NullPointerException::new));
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
