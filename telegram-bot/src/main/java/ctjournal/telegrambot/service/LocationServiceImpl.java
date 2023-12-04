package ctjournal.telegrambot.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ctjournal.telegrambot.domain.Location;
import ctjournal.telegrambot.repository.TokensHashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class LocationServiceImpl implements LocationService {

    private final TokensHashRepository tokensHashRepository;

    @Override
    public long createLocation(String name, String id) {
        try {
            RestTemplate template = new RestTemplate();
            Location location = new Location(0, name, "", null, false);
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(tokensHashRepository.findByUserId(id));
            HttpEntity<String> request =
                    new HttpEntity<>(new ObjectMapper().writeValueAsString(location), headers);
            var response = template.postForEntity(
                    "http://localhost:9001/api/location", request, Location.class);
            return response.getBody().getId();

        } catch (JacksonException e) {

        }
        return 0;
    }

    @Override
    public List<Location> viewLocations(long userId) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokensHashRepository.findByUserId(userId + ""));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Location[]> response = template.exchange(
                "http://localhost:9001/api/location", HttpMethod.GET, entity, Location[].class);
        return Arrays.asList(response.getBody());
    }
}
