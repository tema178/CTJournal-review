package ctjournal.telegrambot.dto;

import ctjournal.telegrambot.domain.DifficultyLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkoutDto {

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

    public WorkoutDto(Date date, long startTime) {
        this.date = date;
        this.startTime = startTime;
    }

    public WorkoutDto(long id) {
        this.id = id;
    }
}
