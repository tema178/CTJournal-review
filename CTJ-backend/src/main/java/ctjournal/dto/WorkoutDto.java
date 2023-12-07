package ctjournal.dto;

import ctjournal.domain.ClimbingSession;
import ctjournal.domain.DifficultyLevel;
import ctjournal.domain.ExerciseSession;
import ctjournal.domain.Location;
import ctjournal.domain.User;
import ctjournal.domain.Workout;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
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

    private long location;

    private long warmUp;

    private long climbingSession;

    private long coolDown;

    private DifficultyLevel difficultyLevel;

    private String comment;

    private String user;

    public Workout toDomain() {
        Location location1 = location == 0 ? null : new Location(location);
        ExerciseSession warmUp1 = warmUp == 0 ? null : new ExerciseSession(warmUp);
        ClimbingSession session = climbingSession == 0 ? null : new ClimbingSession(climbingSession);
        ExerciseSession coolDown1 = coolDown == 0 ? null : new ExerciseSession(coolDown);
        return new Workout(id, date, startTime, endTime, location1, warmUp1,
                session, coolDown1, difficultyLevel, comment, new User(user));
    }

    public static WorkoutDto toDto(Workout workout) {
        long locationId = workout.getLocation() == null ? 0 :workout.getLocation().getId();
        long warmUpId = workout.getWarmUp() == null ? 0 :workout.getWarmUp().getId();
        long climbingSessionId = workout.getClimbingSession() == null ? 0 :workout.getClimbingSession().getId();
        long coolDownId = workout.getCoolDown() == null ? 0 :workout.getCoolDown().getId();
        String userName = workout.getUser() == null ? null : workout.getUser().getUsername();
        return new WorkoutDto(workout.getId(), workout.getDate(), workout.getStartTime(), workout.getEndTime(),
                locationId, warmUpId, climbingSessionId, coolDownId,
                workout.getDifficultyLevel(), workout.getComment(), userName);
    }

    public static List<WorkoutDto> toDto(List<Workout> workouts) {
        var dtos = new ArrayList<WorkoutDto>(workouts.size());
        workouts.forEach(workout -> dtos.add(toDto(workout)));
        return dtos;
    }
}
