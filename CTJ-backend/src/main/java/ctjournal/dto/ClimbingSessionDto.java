package ctjournal.dto;

import ctjournal.domain.ClimbingSession;
import ctjournal.domain.Workout;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
    @NonNull
    private List<RouteDto> routes;

    private long workout;

    public ClimbingSessionDto(ClimbingSession session) {
        id = session.getId();
        startTime = session.getStartTime();
        endTime = session.getEndTime();
        routes = RouteDto.domainToDto(session.getRoutes());
        workout = session.getWorkout().getId();
    }

    public ClimbingSession toDomain() {
        return new ClimbingSession(getId(), getStartTime(), getEndTime(),
                RouteDto.toDomain(getRoutes()), new Workout(getWorkout()));
    }
}
