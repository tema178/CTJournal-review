package ctjournal.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "workouts")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date date;

    private long startTime;

    private long endTime;

    @ManyToOne(targetEntity = Location.class, fetch = EAGER)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(targetEntity = ExerciseSession.class, fetch = LAZY, cascade = ALL, mappedBy = "workout")
    private List<ExerciseSession> warmUp;

    @OneToOne(targetEntity = ClimbingSession.class, fetch = LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private ClimbingSession climbingSession;

    @OneToMany(targetEntity = ExerciseSession.class, fetch = LAZY, cascade = ALL, mappedBy = "workout")
    private List<ExerciseSession> coolDown;

    private DifficultyLevel difficultyLevel;

    private String comment;

}
