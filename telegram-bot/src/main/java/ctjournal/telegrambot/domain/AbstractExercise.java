package ctjournal.telegrambot.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractExercise {

    private long id;

    private String name;

    private int numberOfSets;

    private String comment;

    private ExerciseSession exerciseSession;
}
