package ctjournal.telegrambot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Route implements Serializable {

    private long id;

    private String name;

    private Type type;

    private Grade grade;

    private SendStyle sendStyle;

    private short attempts;

    private short attemptsForRedPoint;

    private byte rating;

    private DifficultyLevel difficultyLevel;

    private String comment;

    private long climbingSession;
}
