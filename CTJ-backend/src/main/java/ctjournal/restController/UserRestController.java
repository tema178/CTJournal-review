package ctjournal.restController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ctjournal.dto.UserDto;
import ctjournal.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping("/api/user")
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/api/user/{name}")
    public UserDto find(@PathVariable String name) {
        return userService.find(name);
    }
}
