package ctjournal.telegrambot.dto;

import ctjournal.telegrambot.domain.Route;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClimbingSessionDto {

    private long id;

    private long startTime;

    private long endTime;

    private List<Route> routes;

    private WorkoutDto workout;

    public ClimbingSessionDto(long id) {
        this.id = id;
    }
}
