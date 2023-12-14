package ctjournal.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@DiscriminatorValue("RE")
@Entity
public class RepeatsExercise extends AbstractExercise {

    private short repeats;

}
