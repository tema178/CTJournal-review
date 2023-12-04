package ctjournal.telegrambot.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Grade implements Serializable {

    private long id;

    private String french;

    private String uiaa;

    private String usa;

    private String norway;

    private String australian;

    private String southAfrica;

    public Grade(long id) {
        this.id = id;
    }

}
