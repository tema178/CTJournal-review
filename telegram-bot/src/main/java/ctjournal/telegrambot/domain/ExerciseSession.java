package ctjournal.telegrambot.domain;

import ctjournal.telegrambot.dto.WorkoutDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseSession {

    private long id;

    private List<AbstractExercise> exercises;

    private WorkoutDto workout;

}
