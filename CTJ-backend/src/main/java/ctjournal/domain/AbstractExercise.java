package ctjournal.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "exercises")
public abstract class AbstractExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private int numberOfSets;

    private String comment;

    @ManyToOne(targetEntity = ExerciseSession.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_session_id")
    private ExerciseSession exerciseSession;
}
