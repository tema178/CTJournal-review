package ctjournal.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

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

    @OneToOne(targetEntity = ExerciseSession.class, fetch = LAZY, mappedBy = "workout")
    private ExerciseSession warmUp;

    @OneToOne(targetEntity = ClimbingSession.class, fetch = LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private ClimbingSession climbingSession;

    @OneToOne(targetEntity = ExerciseSession.class, fetch = LAZY, mappedBy = "workout")
    private ExerciseSession coolDown;

    private DifficultyLevel difficultyLevel;

    private String comment;

    @ManyToOne(targetEntity = User.class, fetch = EAGER)
    @JoinColumn(name = "username", nullable = false)
    private User user;

    public Workout(long id) {
        this.id = id;
        user = new User();
    }
}
