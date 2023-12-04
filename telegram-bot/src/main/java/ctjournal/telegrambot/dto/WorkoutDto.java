package ctjournal.telegrambot.dto;

import ctjournal.telegrambot.domain.ClimbingSession;
import ctjournal.telegrambot.domain.DifficultyLevel;
import ctjournal.telegrambot.domain.ExerciseSession;
import ctjournal.telegrambot.domain.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkoutDto {

    private long id;

    private Date date;

    private long startTime;

    private long endTime;

    private Location location;

    private List<ExerciseSession> warmUp;

    private ClimbingSession climbingSession;

    private List<ExerciseSession> coolDown;

    private DifficultyLevel difficultyLevel;

    private String comment;

    public WorkoutDto(Date date, long startTime) {
        this.date = date;
        this.startTime = startTime;
    }

    public WorkoutDto(long id) {
        this.id = id;
    }
}
