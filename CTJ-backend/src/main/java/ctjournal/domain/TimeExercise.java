package ctjournal.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TimeExercise extends AbstractExercise {

    private short durationSeconds;

}
