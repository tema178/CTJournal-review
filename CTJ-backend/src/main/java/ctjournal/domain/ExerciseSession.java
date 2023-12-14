package ctjournal.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.FetchType.EAGER;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exercise_sessions")
public class ExerciseSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(targetEntity = AbstractExercise.class, fetch = EAGER, mappedBy = "exerciseSession")
    private List<AbstractExercise> exercises;

    @OneToOne(targetEntity = Workout.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id")
    private Workout workout;

    public ExerciseSession (long id) {
        this.id = id;
    }
}
