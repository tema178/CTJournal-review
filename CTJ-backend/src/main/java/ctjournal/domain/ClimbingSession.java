package ctjournal.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "climbing_sessions")
public class ClimbingSession {

    @Id
    private long id;

    private long startTime;

    private long endTime;

    @OneToMany(targetEntity = Route.class, fetch = FetchType.LAZY, mappedBy = "climbingSession")
    private List<Route> routes;

    @MapsId
    @OneToOne(targetEntity = Workout.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Workout workout;

    public ClimbingSession(long id) {
        this.id = id;
    }
}
