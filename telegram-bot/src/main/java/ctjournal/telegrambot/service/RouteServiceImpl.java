package ctjournal.telegrambot.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ctjournal.telegrambot.domain.ClimbingSession;
import ctjournal.telegrambot.domain.Route;
import ctjournal.telegrambot.repository.TokensHashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RouteServiceImpl implements RouteService {

    private final TokensHashRepository tokensHashRepository;

    @Override
    public Route create(String name, ClimbingSession climbingSession, String id) {
        Route route = new Route();
        route.setName(name);
        route.setClimbingSession(climbingSession.getId());
        return update(route, id);
    }

    @Override
    public Route update(Route route, String id) {
        try {
            RestTemplate template = new RestTemplate();
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(tokensHashRepository.findByUserId(id));
            HttpEntity<String> request =
                    new HttpEntity<>(new ObjectMapper().writeValueAsString(route), headers);
            var response = template.postForEntity(
                    "http://localhost:9001/api/route", request, Route.class);
            return response.getBody();

        } catch (JacksonException e) {

        }
        return null;
    }

    @Override
    public List<Route> getRoutes(long climbingSessionId, String id) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokensHashRepository.findByUserId(id));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Route[]> response = template.exchange(
                "http://localhost:9001/api/route/session/" + climbingSessionId, HttpMethod.GET, entity, Route[].class);
        return List.of(response.getBody());
    }

    @Override
    public Route getRoute(long id, String userId) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokensHashRepository.findByUserId(userId));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Route> response = template.exchange(
                "http://localhost:9001/api/route/" + id, HttpMethod.GET, entity, Route.class);
        return response.getBody();
    }
}
