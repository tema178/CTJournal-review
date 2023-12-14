package ctjournal.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorValue("TE")
@Entity
public class TimeExercise extends AbstractExercise {

    private short durationSeconds;

}
