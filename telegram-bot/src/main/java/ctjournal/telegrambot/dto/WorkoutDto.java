package ctjournal.telegrambot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkoutDto implements Serializable {

    private long id;

    private Date date;

    private long startTime;

    private long endTime;

    private long location;

    private long warmUp;

    private long climbingSession;

    private long coolDown;

    private byte efforts;

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
