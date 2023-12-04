package ctjournal.telegrambot.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class User {

    private String username;

    private String password;

    private boolean enabled;

    private List<User> student;
}
