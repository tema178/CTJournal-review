package ctjournal.telegrambot.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ctjournal.telegrambot.dto.WorkoutDto;
import ctjournal.telegrambot.repository.TokensHashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class WorkoutServiceImpl implements WorkoutService {

    private final TokensHashRepository tokensHashRepository;

    @Override
    public WorkoutDto create(String id) {
        WorkoutDto workout = new WorkoutDto(Date.valueOf(LocalDate.now()), System.currentTimeMillis());
        return update(workout, id);
    }

    @Override
    public WorkoutDto update(WorkoutDto workout, String id) {
        try {
            RestTemplate template = new RestTemplate();
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(tokensHashRepository.findByUserId(id));
            HttpEntity<String> request =
                    new HttpEntity<>(new ObjectMapper().writeValueAsString(workout), headers);
            var response = template.postForEntity(
                    "http://localhost:9001/api/workout", request, WorkoutDto.class);
            return response.getBody();

        } catch (JacksonException e) {

        }
        return null;
    }

    @Override
    public WorkoutDto findById(long id, String userId) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokensHashRepository.findByUserId(userId));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<WorkoutDto> response = template.exchange(
                "http://localhost:9001/api/workout/" + id, HttpMethod.GET, entity, WorkoutDto.class);
        return response.getBody();
    }

    @Override
    public WorkoutDto updateLocation(WorkoutDto workoutState, String id) {
        WorkoutDto workoutDto = findById(workoutState.getId(), id);
        workoutDto.setLocation(workoutState.getId());
        return update(workoutDto, id);
    }

    @Override
    public WorkoutDto updateClimbingSession(WorkoutDto workoutState, String id) {
        WorkoutDto workoutDto = findById(workoutState.getId(), id);
        workoutDto.setClimbingSession(workoutState.getClimbingSession());
        return update(workoutDto, id);
    }
}
