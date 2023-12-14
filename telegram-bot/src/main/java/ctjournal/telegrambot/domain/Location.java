package ctjournal.telegrambot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Location {

    private long id;

    private String name;

    private String region;

    private User user;

    private boolean favourite;

    public Location(long id) {
        this.id = id;
    }
}
