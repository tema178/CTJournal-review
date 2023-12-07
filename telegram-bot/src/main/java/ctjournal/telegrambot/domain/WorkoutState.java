package ctjournal.telegrambot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WorkoutState implements Serializable {

    private long id;

    private Date date;

    private long startTime;

    private long endTime;

    private long location;

    private long warmUp;

    private long climbingSession;

    private long coolDown;

    private DifficultyLevel difficultyLevel;

    private String comment;

    private String user;

    public WorkoutState(Date date, long startTime) {
        this.date = date;
        this.startTime = startTime;
    }
}
