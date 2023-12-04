package ctjournal.telegrambot.utils;

import ctjournal.telegrambot.domain.DifficultyLevel;
import ctjournal.telegrambot.domain.Grade;
import ctjournal.telegrambot.domain.Location;
import ctjournal.telegrambot.domain.Route;
import ctjournal.telegrambot.domain.SendStyle;
import ctjournal.telegrambot.domain.Type;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class KeyboardFactory {

    public static final String NEW_LOCATION = "new location";
    public static final String VIEW_LOCATIONS = "view locations";
    public static final String SET_LOCATION = "set location";
    public static final String ADD_ROUTE = "add route";
    public static final String EDIT_ROUTE = "edit route";
    public static final String VIEW_ROUTES = "view routes";
    public static final String EDIT_CLIMBING_SESSION = "edit climbing session";
    public static final String EDIT_GRADE = "edit grade";
    public static final String SET_GRADE = "set grade";
    public static final String EDIT_SEND_STYLE = "edit send style";
    public static final String SET_SEND_STYLE = "set send style";
    public static final String SET_DIFFICULTY_LEVEL = "set difficulty level";
    public static final String SET_ROUTE_TYPE = "set route type";
    public static final String EDIT_ATTEMPTS = "edit attempts";
    public static final String EDIT_REDPOINT_ATTEMPTS = "edit redpoint attempts";
    public static final String EDIT_RATING = "edit rating";
    public static final String EDIT_DIFFICULTY_LEVEL = "edit difficulty level";
    public static final String EDIT_COMMENT = "edit comment";
    public static final String RETURN_TO_CLIMBING_SESSION = "return to climbing session";
    public static final String EDIT_ROUTE_TYPE = "edit route type";
    public static final String EDIT_ROUTE_NAME = "edit route name";
    public static final String SET_ROUTE = "set route";
    public static final String RETURN_TO_MAIN_MENU = "return to main menu";

    private KeyboardFactory() {
    }

    public static ReplyKeyboard mainKeyboard() {
        InlineKeyboardButton warmUp = InlineKeyboardButton.builder()
                .text("Добавить разминку")
                .callbackData("add warmUp")
                .build();
        InlineKeyboardButton climbingSession = InlineKeyboardButton.builder()
                .text("Редактировать лазательную сессию")
                .callbackData(EDIT_CLIMBING_SESSION)
                .build();
        InlineKeyboardButton coolDown = InlineKeyboardButton.builder()
                .text("Добавить заминку")
                .callbackData("add coolDown")
                .build();
        InlineKeyboardButton comment = InlineKeyboardButton.builder()
                .text("Добавить комментарий")
                .callbackData("add comment")
                .build();
        InlineKeyboardButton changeLocation = InlineKeyboardButton.builder()
                .text("Изменить место")
                .callbackData(VIEW_LOCATIONS)
                .build();
        InlineKeyboardButton addRate = InlineKeyboardButton.builder()
                .text("Добавить оценку")
                .callbackData("add rate")
                .build();
        List<List<InlineKeyboardButton>> matrix = new ArrayList<>();
        matrix.add(List.of(warmUp));
        matrix.add(List.of(climbingSession));
        matrix.add(List.of(coolDown));
        matrix.add(List.of(comment));
        matrix.add(List.of(changeLocation));
        matrix.add(List.of(addRate));
        return new InlineKeyboardMarkup(matrix);
    }

    public static ReplyKeyboard climbingSessionKeyboard() {
        InlineKeyboardButton addRoute = InlineKeyboardButton.builder()
                .text("Добавить трассу")
                .callbackData(ADD_ROUTE)
                .build();
        InlineKeyboardButton editRoute = InlineKeyboardButton.builder()
                .text("Редактировать трассу")
                .callbackData(EDIT_ROUTE)
                .build();
        InlineKeyboardButton viewRoutes = InlineKeyboardButton.builder()
                .text("Просмотреть трассы")
                .callbackData(VIEW_ROUTES)
                .build();
        InlineKeyboardButton returnToMainMenu = InlineKeyboardButton.builder()
                .text("Вернуться в главное меню")
                .callbackData(RETURN_TO_MAIN_MENU)
                .build();
        List<List<InlineKeyboardButton>> matrix = new ArrayList<>();
        matrix.add(List.of(addRoute));
        matrix.add(List.of(editRoute));
        matrix.add(List.of(viewRoutes));
        matrix.add(List.of(returnToMainMenu));
        return new InlineKeyboardMarkup(matrix);
    }

    public static ReplyKeyboard routeKeyboard(Route route) {
        InlineKeyboardButton editName = InlineKeyboardButton.builder()
                .text("Редактировать название")
                .callbackData(EDIT_ROUTE_NAME)
                .build();
        InlineKeyboardButton editGrade = InlineKeyboardButton.builder()
                .text("Редактировать категорию")
                .callbackData(EDIT_GRADE)
                .build();
        InlineKeyboardButton editRouteType = InlineKeyboardButton.builder()
                .text("Указать дисциплину")
                .callbackData(EDIT_ROUTE_TYPE)
                .build();
        InlineKeyboardButton editSendStyle = InlineKeyboardButton.builder()
                .text("Указать стиль пролаза")
                .callbackData(EDIT_SEND_STYLE)
                .build();
        InlineKeyboardButton editAttempts = InlineKeyboardButton.builder()
                .text("Указать количество попыток")
                .callbackData(EDIT_ATTEMPTS)
                .build();
        InlineKeyboardButton editAttemptsForRedPoint = InlineKeyboardButton.builder()
                .text("Указать количество попыток - redpoint")
                .callbackData(EDIT_REDPOINT_ATTEMPTS)
                .build();
        InlineKeyboardButton editRating = InlineKeyboardButton.builder()
                .text("Добавить оценку")
                .callbackData(EDIT_RATING)
                .build();
        InlineKeyboardButton editDifficultyLevel = InlineKeyboardButton.builder()
                .text("Указать уровень сложности")
                .callbackData(EDIT_DIFFICULTY_LEVEL)
                .build();
        InlineKeyboardButton editComment = InlineKeyboardButton.builder()
                .text("Добавить комментарий")
                .callbackData(EDIT_COMMENT)
                .build();
        InlineKeyboardButton returnToClimbingSession = InlineKeyboardButton.builder()
                .text("Вернуться")
                .callbackData(RETURN_TO_CLIMBING_SESSION)
                .build();
        List<List<InlineKeyboardButton>> matrix = new ArrayList<>();
        matrix.add(List.of(editName));
        matrix.add(List.of(editGrade));
        matrix.add(List.of(editRouteType));
        matrix.add(List.of(editSendStyle));
        matrix.add(List.of(editAttempts));
        matrix.add(List.of(editAttemptsForRedPoint));
        matrix.add(List.of(editRating));
        matrix.add(List.of(editDifficultyLevel));
        matrix.add(List.of(editComment));
        matrix.add(List.of(returnToClimbingSession));
        return new InlineKeyboardMarkup(matrix);
    }

    public static ReplyKeyboard location() {
        InlineKeyboardButton newLocation = InlineKeyboardButton.builder()
                .text("Указать новое место")
                .callbackData(NEW_LOCATION)
                .build();
        InlineKeyboardButton viewLocations = InlineKeyboardButton.builder()
                .text("Выбрать место")
                .callbackData(VIEW_LOCATIONS)
                .build();
        List<List<InlineKeyboardButton>> matrix = new ArrayList<>();
        matrix.add(List.of(newLocation));
        matrix.add(List.of(viewLocations));
        return new InlineKeyboardMarkup(matrix);
    }

    public static ReplyKeyboard viewLocation(List<Location> locations) {
        List<List<InlineKeyboardButton>> matrix = new ArrayList<>();
        for (Location location : locations) {
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(location.getName())
                    .callbackData(SET_LOCATION + location.getId())
                    .build();
            matrix.add(List.of(button));
        }
        InlineKeyboardButton newLocation = InlineKeyboardButton.builder()
                .text("Указать новое место")
                .callbackData(NEW_LOCATION)
                .build();
        matrix.add(List.of(newLocation));
        return new InlineKeyboardMarkup(matrix);
    }

    public static ReplyKeyboard viewGrades(List<Grade> grades) {
        List<List<InlineKeyboardButton>> matrix = new ArrayList<>();
        for (Grade gradeDto : grades) {
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(gradeDto.getFrench())
                    .callbackData(SET_GRADE + gradeDto.getId())
                    .build();
            matrix.add(List.of(button));
        }
        return new InlineKeyboardMarkup(matrix);
    }

    public static ReplyKeyboard viewRoutes(List<Route> routes) {
        List<List<InlineKeyboardButton>> matrix = new ArrayList<>();
        for (var route : routes) {
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(route.getName())
                    .callbackData(SET_ROUTE + route.getId())
                    .build();
            matrix.add(List.of(button));
        }
        return new InlineKeyboardMarkup(matrix);
    }

    public static ReplyKeyboard viewStyles() {
        List<List<InlineKeyboardButton>> matrix = new ArrayList<>();
        for (var style : SendStyle.values()) {
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(style.name())
                    .callbackData(SET_SEND_STYLE + style.name())
                    .build();
            matrix.add(List.of(button));
        }
        return new InlineKeyboardMarkup(matrix);
    }

    public static ReplyKeyboard viewDifficultyLevel() {
        List<List<InlineKeyboardButton>> matrix = new ArrayList<>();
        for (var difficultyLevel : DifficultyLevel.values()) {
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(difficultyLevel.name())
                    .callbackData(SET_DIFFICULTY_LEVEL + difficultyLevel.name())
                    .build();
            matrix.add(List.of(button));
        }
        return new InlineKeyboardMarkup(matrix);
    }

    public static ReplyKeyboard viewRouteType() {
        List<List<InlineKeyboardButton>> matrix = new ArrayList<>();
        for (var type : Type.values()) {
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(type.name())
                    .callbackData(SET_ROUTE_TYPE + type.name())
                    .build();
            matrix.add(List.of(button));
        }
        return new InlineKeyboardMarkup(matrix);
    }
}
