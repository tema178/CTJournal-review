package ctjournal.telegrambot.service;

import ctjournal.telegrambot.domain.Grade;
import ctjournal.telegrambot.repository.TokensHashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GradeServiceImpl implements GradeService {

    private final TokensHashRepository tokensHashRepository;

    @Override
    public List<Grade> getGrades(String id) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokensHashRepository.findByUserId(id));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Grade[]> response = template.exchange(
                "http://localhost:9001/api/grade", HttpMethod.GET, entity, Grade[].class);
        return List.of(response.getBody());
    }

    @Override
    public Grade getGrade(long id, String userId) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokensHashRepository.findByUserId(userId));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Grade> response = template.exchange(
                "http://localhost:9001/api/grade/" + id, HttpMethod.GET, entity, Grade.class);
        return response.getBody();
    }
}
