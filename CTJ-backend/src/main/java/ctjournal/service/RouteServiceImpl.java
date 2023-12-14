package ctjournal.service;

import ctjournal.dto.RouteDto;
import ctjournal.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository repository;

    @Override
    public RouteDto save(RouteDto route) {
        return new RouteDto((repository.save(route.toDomain())));
    }

    @Override
    public RouteDto findById(long id) {
        return new RouteDto((repository.findById(id).orElseThrow(NullPointerException::new)));
    }

    @Override
    public List<RouteDto> findByClimbingSessionId(long climbingSessionId) {
        return RouteDto.domainToDto(repository.findByClimbingSessionId(climbingSessionId));
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }
}
