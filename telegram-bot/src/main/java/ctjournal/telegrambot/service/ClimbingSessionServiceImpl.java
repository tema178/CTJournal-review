package ctjournal.telegrambot.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ctjournal.telegrambot.domain.ClimbingSession;
import ctjournal.telegrambot.domain.WorkoutState;
import ctjournal.telegrambot.repository.TokensHashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClimbingSessionServiceImpl implements ClimbingSessionService {
    @Autowired
    private TokensHashRepository tokensHashRepository;

    @Override
    public ClimbingSession create(WorkoutState workout, String id) {
        return update(new ClimbingSession(workout), id);
    }

    @Override
    public ClimbingSession update(ClimbingSession session, String id) {
        try {
            RestTemplate template = new RestTemplate();
            var headers = new HttpHeaders();
            headers.setBearerAuth(tokensHashRepository.findByUserId(id));
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request =
                    new HttpEntity<>(new ObjectMapper().writeValueAsString(session), headers);
            var response = template.postForEntity(
                    "http://localhost:9001/api/session/climbing", request, ClimbingSession.class);
            return response.getBody();

        } catch (JacksonException e) {

        }
        return null;
    }

}
