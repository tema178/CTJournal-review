package ctjournal.telegrambot.ability;

import ctjournal.telegrambot.domain.ClimbingSession;
import ctjournal.telegrambot.domain.DifficultyLevel;
import ctjournal.telegrambot.domain.Route;
import ctjournal.telegrambot.domain.SendStyle;
import ctjournal.telegrambot.domain.Type;
import ctjournal.telegrambot.domain.WorkoutState;
import ctjournal.telegrambot.repository.RouteRepository;
import ctjournal.telegrambot.repository.StatesRepository;
import ctjournal.telegrambot.repository.WorkoutRepository;
import ctjournal.telegrambot.service.RouteService;
import ctjournal.telegrambot.service.WorkoutService;
import ctjournal.telegrambot.utils.KeyboardFactory;
import ctjournal.telegrambot.utils.RouteToStringTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.function.BiConsumer;

import static ctjournal.telegrambot.domain.States.CLIMBING_SESSION_MENU;
import static ctjournal.telegrambot.domain.States.MAIN_MENU;
import static ctjournal.telegrambot.domain.States.ROUTE_MENU;
import static ctjournal.telegrambot.domain.States.WAITING_ATTEMPTS;
import static ctjournal.telegrambot.domain.States.WAITING_RAITING;
import static ctjournal.telegrambot.domain.States.WAITING_REDPOINT_ATTEMPTS;
import static ctjournal.telegrambot.domain.States.WAITING_ROUTE_COMMENT;
import static ctjournal.telegrambot.domain.States.WAITING_ROUTE_NAME;
import static ctjournal.telegrambot.domain.States.WAITING_ROUTE_NAME_CREATE;
import static ctjournal.telegrambot.utils.KeyboardFactory.ADD_ROUTE;
import static ctjournal.telegrambot.utils.KeyboardFactory.EDIT_ATTEMPTS;
import static ctjournal.telegrambot.utils.KeyboardFactory.EDIT_COMMENT;
import static ctjournal.telegrambot.utils.KeyboardFactory.EDIT_DIFFICULTY_LEVEL;
import static ctjournal.telegrambot.utils.KeyboardFactory.EDIT_RATING;
import static ctjournal.telegrambot.utils.KeyboardFactory.EDIT_REDPOINT_ATTEMPTS;
import static ctjournal.telegrambot.utils.KeyboardFactory.EDIT_ROUTE;
import static ctjournal.telegrambot.utils.KeyboardFactory.EDIT_ROUTE_NAME;
import static ctjournal.telegrambot.utils.KeyboardFactory.EDIT_ROUTE_TYPE;
import static ctjournal.telegrambot.utils.KeyboardFactory.EDIT_SEND_STYLE;
import static ctjournal.telegrambot.utils.KeyboardFactory.RETURN_TO_CLIMBING_SESSION;
import static ctjournal.telegrambot.utils.KeyboardFactory.RETURN_TO_MAIN_MENU;
import static ctjournal.telegrambot.utils.KeyboardFactory.SET_DIFFICULTY_LEVEL;
import static ctjournal.telegrambot.utils.KeyboardFactory.SET_ROUTE;
import static ctjournal.telegrambot.utils.KeyboardFactory.SET_ROUTE_TYPE;
import static ctjournal.telegrambot.utils.KeyboardFactory.SET_SEND_STYLE;
import static ctjournal.telegrambot.utils.KeyboardFactory.VIEW_ROUTES;
import static java.lang.String.format;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

@Component
public class RouteAbility implements AbilityExtension {

    public static final String UNSUPPORTED_NUMBER = "Некорректное число. Попробуйте снова";
    @Autowired
    private AbilityBot abilityBot;
    @Autowired
    private RouteService service;

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private StatesRepository statesRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private RouteRepository routeRepository;

    public Reply addRouteName() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> {
            Long id = getChatId(upd);
            bot.silent().send("Введите название трассы:", id);
            statesRepository.save(getChatId(upd).toString(), WAITING_ROUTE_NAME_CREATE);
        };
        return Reply.of(action, upd ->
                upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(ADD_ROUTE));
    }

    public Reply createRoute() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> {
            Long id = getChatId(upd);
            String name = upd.getMessage().getText();
            WorkoutState workout = workoutRepository.findByUserId(id.toString());
            Route route = service.create(name, new ClimbingSession(workout.getClimbingSession()), id.toString());
            routeRepository.save(id.toString(), route);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(format("Трасса %s добавлена", name));
            sendMessage.setChatId(id);
            sendMessage.setReplyMarkup(KeyboardFactory.routeKeyboard(route));
            try {
                bot.sender().execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        };
        return Reply.of(action, update ->
                update.hasMessage()
                        && statesRepository.findByUserId(getChatId(update).toString()) == WAITING_ROUTE_NAME_CREATE
        );
    }

    public Reply viewRoutes() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> {
            Long id = getChatId(upd);
            WorkoutState workout = workoutRepository.findByUserId(id.toString());
            List<Route> routes = service.getRoutes(workout.getClimbingSession(), id.toString());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(RouteToStringTransformer.transform(routes));
            sendMessage.setChatId(id);
            sendMessage.setReplyMarkup(KeyboardFactory.climbingSessionKeyboard());
            try {
                bot.sender().execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        };
        return Reply.of(action, upd ->
                upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(VIEW_ROUTES));
    }


    public Reply editRoute() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> {
            Long id = getChatId(upd);
            WorkoutState workout = workoutRepository.findByUserId(id.toString());
            List<Route> routes = service.getRoutes(workout.getClimbingSession(), id.toString());
            String text;
            ReplyKeyboard replyMarkup;
            if (routes == null || routes.isEmpty()) {
                text = "Трассы не найдены";
                replyMarkup = KeyboardFactory.climbingSessionKeyboard();
            } else {
                text = "Выберите трассу";
                replyMarkup = KeyboardFactory.viewRoutes(routes);
            }
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(text);
            sendMessage.setChatId(id);
            sendMessage.setReplyMarkup(replyMarkup);
            try {
                bot.sender().execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        };
        return Reply.of(action, upd ->
                upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(EDIT_ROUTE));
    }

    public Reply setRoute() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> {
            Long id = getChatId(upd);
            long routeId = Long.parseLong(upd.getCallbackQuery().getData().substring(SET_ROUTE.length()));
            Route route = service.getRoute(routeId, id.toString());
            routeRepository.save(id.toString(), route);
            sendRouteKeyboard(bot, upd, route);
        };
        return Reply.of(action,
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().startsWith(SET_ROUTE));
    }

    public Reply editStyle() {
        return Reply.of(
                (bot, upd) -> {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Выберите стиль пролаза:");
                    sendMessage.setChatId(getChatId(upd));
                    sendMessage.setReplyMarkup(KeyboardFactory.viewStyles());
                    try {
                        bot.sender().execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(EDIT_SEND_STYLE));

    }

    public Reply setStyle() {
        return Reply.of(
                (bot, upd) -> {
                    String sendStyle = upd.getCallbackQuery().getData().substring(SET_SEND_STYLE.length());
                    Long id = getChatId(upd);
                    Route route = routeRepository.findByUserId(id.toString());
                    route.setSendStyle(SendStyle.valueOf(sendStyle));
                    routeRepository.save(id.toString(), route);
                    service.update(route,id.toString());

                    sendRouteKeyboard(bot, upd, route);
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().startsWith(SET_SEND_STYLE));

    }

    public Reply editAttempts() {
        return Reply.of(
                (bot, upd) -> {
                    Long id = getChatId(upd);
                    statesRepository.save(id.toString(), WAITING_ATTEMPTS);
                    bot.silent().send("Введите количество попыток:", getChatId(upd));
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(EDIT_ATTEMPTS));

    }

    public Reply setAttempts() {
        return Reply.of(
                (bot, upd) -> {
                    Long id = getChatId(upd);
                    short attempts;
                    try {
                        attempts = Short.parseShort(upd.getMessage().getText());
                        if (attempts < 0) {
                            bot.silent().send("Число не должно быть отрицательным. Попробуйте снова", id);
                        } else {
                            Route route = routeRepository.findByUserId(id.toString());
                            route.setAttempts(attempts);
                            routeRepository.save(id.toString(), route);
                            service.update(route, id.toString());
                            statesRepository.save(id.toString(), ROUTE_MENU);

                            sendRouteKeyboard(bot, upd, route);
                        }
                    } catch (NumberFormatException e) {
                        bot.silent().send(UNSUPPORTED_NUMBER, id);
                    }
                },
                upd ->
                        upd.hasMessage()
                                && statesRepository.findByUserId(getChatId(upd).toString()) == WAITING_ATTEMPTS);

    }

    public Reply editRedPointAttempts() {
        return Reply.of(
                (bot, upd) -> {
                    Long id = getChatId(upd);
                    statesRepository.save(id.toString(), WAITING_REDPOINT_ATTEMPTS);
                    bot.silent().send("Введите количество попыток:", getChatId(upd));
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(EDIT_REDPOINT_ATTEMPTS));

    }

    public Reply setRedPointAttempts() {
        return Reply.of(
                (bot, upd) -> {
                    Long id = getChatId(upd);
                    short attempts;
                    try {
                        attempts = Short.parseShort(upd.getMessage().getText());
                        if (attempts < 0) {
                            bot.silent().send("Число не должно быть отрицательным. Попробуйте снова", id);
                        } else {
                            Route route = routeRepository.findByUserId(id.toString());
                            route.setAttemptsForRedPoint(attempts);
                            routeRepository.save(id.toString(), route);
                            service.update(route, id.toString());
                            statesRepository.save(id.toString(), ROUTE_MENU);

                            sendRouteKeyboard(bot, upd, route);
                        }
                    } catch (NumberFormatException e) {
                        bot.silent().send(UNSUPPORTED_NUMBER, id);
                    }
                },
                upd ->
                        upd.hasMessage()
                                && statesRepository.findByUserId(getChatId(upd).toString()) == WAITING_REDPOINT_ATTEMPTS);

    }

    public Reply editRating() {
        return Reply.of(
                (bot, upd) -> {
                    Long id = getChatId(upd);
                    statesRepository.save(id.toString(), WAITING_RAITING);
                    bot.silent().send("Введите оценку 1-5:", getChatId(upd));
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(EDIT_RATING));

    }

    public Reply setRating() {
        return Reply.of(
                (bot, upd) -> {
                    Long id = getChatId(upd);
                    byte rating;
                    try {
                        rating = Byte.parseByte(upd.getMessage().getText());
                        if (rating < 1 || rating > 5) {
                            bot.silent().send("Число должно быть в диапазоне 1-5. Попробуйте снова", id);
                        } else {
                            Route route = routeRepository.findByUserId(id.toString());
                            route.setRating(rating);
                            routeRepository.save(id.toString(), route);
                            service.update(route, id.toString());
                            statesRepository.save(id.toString(), ROUTE_MENU);

                            sendRouteKeyboard(bot, upd, route);
                        }
                    } catch (NumberFormatException e) {
                        bot.silent().send(UNSUPPORTED_NUMBER, id);
                    }
                },
                upd ->
                        upd.hasMessage()
                                && statesRepository.findByUserId(getChatId(upd).toString()) == WAITING_RAITING);

    }

    public Reply editDifficultyLevel() {
        return Reply.of(
                (bot, upd) -> {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Выберите сложность:");
                    sendMessage.setChatId(getChatId(upd));
                    sendMessage.setReplyMarkup(KeyboardFactory.viewDifficultyLevel());
                    try {
                        bot.sender().execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(EDIT_DIFFICULTY_LEVEL));

    }

    public Reply setDifficultyLevel() {
        return Reply.of(
                (bot, upd) -> {
                    String difficultyLevel = upd.getCallbackQuery().getData().substring(SET_DIFFICULTY_LEVEL.length());
                    Long id = getChatId(upd);
                    Route route = routeRepository.findByUserId(id.toString());
                    route.setDifficultyLevel(DifficultyLevel.valueOf(difficultyLevel));
                    routeRepository.save(id.toString(), route);
                    service.update(route, id.toString());

                    sendRouteKeyboard(bot, upd, route);
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().startsWith(SET_DIFFICULTY_LEVEL));

    }

    public Reply editRouteType() {
        return Reply.of(
                (bot, upd) -> {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Выберите дисциплину:");
                    sendMessage.setChatId(getChatId(upd));
                    sendMessage.setReplyMarkup(KeyboardFactory.viewRouteType());
                    try {
                        bot.sender().execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(EDIT_ROUTE_TYPE));

    }

    public Reply setRouteType() {
        return Reply.of(
                (bot, upd) -> {
                    String difficultyLevel = upd.getCallbackQuery().getData().substring(SET_ROUTE_TYPE.length());
                    Long id = getChatId(upd);
                    Route route = routeRepository.findByUserId(id.toString());
                    route.setType(Type.valueOf(difficultyLevel));
                    routeRepository.save(id.toString(), route);
                    service.update(route, id.toString());

                    sendRouteKeyboard(bot, upd, route);
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().startsWith(SET_ROUTE_TYPE));

    }


    public Reply editComment() {
        return Reply.of(
                (bot, upd) -> {
                    Long id = getChatId(upd);
                    statesRepository.save(id.toString(), WAITING_ROUTE_COMMENT);
                    bot.silent().send("Введите комментарий:", getChatId(upd));
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(EDIT_COMMENT));

    }

    public Reply setComment() {
        return Reply.of(
                (bot, upd) -> {
                    Long id = getChatId(upd);
                    Route route = routeRepository.findByUserId(id.toString());
                    route.setComment(upd.getMessage().getText());
                    routeRepository.save(id.toString(), route);
                    service.update(route, id.toString());
                    statesRepository.save(id.toString(), ROUTE_MENU);

                    sendRouteKeyboard(bot, upd, route);
                },
                upd ->
                        upd.hasMessage()
                                && statesRepository.findByUserId(getChatId(upd).toString()) == WAITING_ROUTE_COMMENT);

    }

    public Reply editRouteName() {
        return Reply.of(
                (bot, upd) -> {
                    Long id = getChatId(upd);
                    statesRepository.save(id.toString(), WAITING_ROUTE_NAME);
                    bot.silent().send("Введите название:", getChatId(upd));
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(EDIT_ROUTE_NAME));

    }

    public Reply setRouteName() {
        return Reply.of(
                (bot, upd) -> {
                    Long id = getChatId(upd);
                    Route route = routeRepository.findByUserId(id.toString());
                    route.setName(upd.getMessage().getText());
                    routeRepository.save(id.toString(), route);
                    service.update(route, id.toString());
                    statesRepository.save(id.toString(), ROUTE_MENU);

                    sendRouteKeyboard(bot, upd, route);
                },
                upd ->
                        upd.hasMessage()
                                && statesRepository.findByUserId(getChatId(upd).toString()) == WAITING_ROUTE_NAME);

    }


    public Reply returnToClimbingSessionMenu() {
        return Reply.of(
                (bot, upd) -> {
                    Long id = getChatId(upd);
                    statesRepository.save(id.toString(), CLIMBING_SESSION_MENU);
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Выберите действие:");
                    sendMessage.setChatId(getChatId(upd));
                    sendMessage.setReplyMarkup(KeyboardFactory.climbingSessionKeyboard());
                    try {
                        bot.sender().execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(RETURN_TO_CLIMBING_SESSION));
    }

    public Reply returnToMainMenu() {
        return Reply.of(
                (bot, upd) -> {
                    Long id = getChatId(upd);
                    statesRepository.save(id.toString(), MAIN_MENU);
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Выберите действие:");
                    sendMessage.setChatId(getChatId(upd));
                    sendMessage.setReplyMarkup(KeyboardFactory.mainKeyboard());
                    try {
                        bot.sender().execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(RETURN_TO_MAIN_MENU));
    }

    private static void sendRouteKeyboard(BaseAbilityBot bot, Update upd, Route route) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Данные изменены:\n" + RouteToStringTransformer.transform(route));
        sendMessage.setChatId(getChatId(upd));
        sendMessage.setReplyMarkup(KeyboardFactory.routeKeyboard(route));
        try {
            bot.sender().execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
