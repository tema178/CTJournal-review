package ctjournal.dto;

import ctjournal.domain.ClimbingSession;
import ctjournal.domain.DifficultyLevel;
import ctjournal.domain.Grade;
import ctjournal.domain.Route;
import ctjournal.domain.SendStyle;
import ctjournal.domain.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RouteDto {

    private long id;

    private String name;

    private Type type;

    private Grade grade;

    private SendStyle sendStyle;

    private short attempts;

    private short attemptsForRedPoint;

    private byte rating;

    private DifficultyLevel difficultyLevel;

    private String comment;

    private long climbingSession;

    public RouteDto(Route route) {
        id = route.getId();
        name = route.getName();
        type = route.getType();
        grade = route.getGrade();
        sendStyle = route.getSendStyle();
        attempts = route.getAttempts();
        attemptsForRedPoint = route.getAttemptsForRedPoint();
        rating = route.getRating();
        difficultyLevel = route.getDifficultyLevel();
        comment = route.getComment();
        climbingSession = route.getClimbingSession().getId();
    }

    public Route toDomain() {
        return new Route(this.getId(), this.getName(), this.getType(), this.getGrade(),
                this.getSendStyle(), this.getAttempts(), this.getAttemptsForRedPoint(), this.getRating(),
                this.getDifficultyLevel(), this.getComment(), new ClimbingSession(this.getClimbingSession()));
    }

    public static List<Route> toDomain(@NonNull List<RouteDto> routeDtoList) {
        var routes = new ArrayList<Route>();
        routeDtoList.forEach(dto -> routes.add(dto.toDomain()));
        return routes;
    }

    public static List<RouteDto> domainToDto(@NonNull List<Route> routes) {
        var routeDtos = new ArrayList<RouteDto>();
        routes.forEach(route -> routeDtos.add(new RouteDto(route)));
        return routeDtos;
    }

}
