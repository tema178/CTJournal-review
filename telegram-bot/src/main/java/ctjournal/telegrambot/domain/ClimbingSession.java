package ctjournal.telegrambot.domain;

import ctjournal.telegrambot.dto.WorkoutDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClimbingSession {

    private long id;

    private long startTime;

    private long endTime;

    private List<Route> routes;

    private WorkoutDto workout;

    public ClimbingSession(long id) {
        this.id = id;
    }

    public ClimbingSession(WorkoutState workoutState) {
        this.workout = new WorkoutDto(workoutState.getId());
    }
}
