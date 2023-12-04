package ctjournal.telegrambot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class AuthorizationServiceImpl {

    public String getToken(String name, String password) {
        RestTemplate template = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setBasicAuth(name, password);
        HttpEntity<String> request =
                new HttpEntity<>("", headers);
        var response = template.postForEntity(
                "http://localhost:9001/api/token", request, String.class);
        return response.getBody();
    }
}
