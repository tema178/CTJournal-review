package ctjournal.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private Type type;

    @ManyToOne(targetEntity = Grade.class)
    @JoinColumn(name = "grade_id")
    private Grade grade;

    private SendStyle sendStyle;

    private short attempts;

    private short attemptsForRedPoint;

    private byte rating;

    private DifficultyLevel difficultyLevel;

    private String comment;

    @ManyToOne(targetEntity = ClimbingSession.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id")
    private ClimbingSession climbingSession;
}
