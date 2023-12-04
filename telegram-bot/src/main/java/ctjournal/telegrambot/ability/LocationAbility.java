package ctjournal.telegrambot.ability;

import ctjournal.telegrambot.domain.Location;
import ctjournal.telegrambot.domain.WorkoutState;
import ctjournal.telegrambot.repository.StatesRepository;
import ctjournal.telegrambot.repository.WorkoutRepository;
import ctjournal.telegrambot.service.LocationService;
import ctjournal.telegrambot.service.WorkoutService;
import ctjournal.telegrambot.utils.KeyboardFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.function.BiConsumer;

import static ctjournal.telegrambot.utils.KeyboardFactory.NEW_LOCATION;
import static ctjournal.telegrambot.utils.KeyboardFactory.SET_LOCATION;
import static ctjournal.telegrambot.utils.KeyboardFactory.VIEW_LOCATIONS;
import static ctjournal.telegrambot.domain.States.CREATING_LOCATION;
import static ctjournal.telegrambot.domain.States.MAIN_MENU;
import static ctjournal.telegrambot.domain.States.WAITING_LOCATION_NAME;
import static java.lang.String.format;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

@Component
public class LocationAbility implements AbilityExtension {

    @Autowired
    private AbilityBot abilityBot;
    @Autowired
    private LocationService locationService;

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private StatesRepository statesRepository;

    public Reply saveLocationName() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> {
            Long id = getChatId(upd);
            statesRepository.save(id.toString(), CREATING_LOCATION);
            String place = upd.getMessage().getText();
            long location = locationService.createLocation(place, id.toString());
            WorkoutState workout = workoutRepository.findByUserId(id.toString());
            if (workout == null) {
                workout = new WorkoutState();
            }
            workout.setLocation(location);
            workoutRepository.save(id.toString(), workout);
            workoutService.updateLocation(workout, id.toString());
            statesRepository.save(id.toString(), MAIN_MENU);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(format("Место %s создано", place));
            sendMessage.setChatId(upd.getMessage().getChatId());
            sendMessage.setReplyMarkup(KeyboardFactory.mainKeyboard());
            try {
                bot.sender().execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        };
        return Reply.of(action, update ->
                update.hasMessage()
                        && statesRepository.findByUserId(getChatId(update).toString()) == WAITING_LOCATION_NAME
        );
    }

    public Reply newLocation() {
        return Reply.of(
                (bot, upd) -> {
                    bot.silent().send("Введите название места:", getChatId(upd));
                    statesRepository.save(getChatId(upd).toString(), WAITING_LOCATION_NAME);
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(NEW_LOCATION));

    }

    public Reply viewLocations() {
        return Reply.of(
                (bot, upd) -> {
                    List<Location> locations = locationService.viewLocations(getChatId(upd));
                    String text = locations.isEmpty() ? "У вас еще нет добавленных мест:" : "Выберите место:";
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText(text);
                    sendMessage.setChatId(getChatId(upd));
                    sendMessage.setReplyMarkup(KeyboardFactory.viewLocation(locations));
                    try {
                        bot.sender().execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(VIEW_LOCATIONS));

    }

    public Reply setLocations() {
        return Reply.of(
                (bot, upd) -> {
                    long location = Long.parseLong(upd.getCallbackQuery().getData().substring(SET_LOCATION.length()));
                    Long id = getChatId(upd);
                    WorkoutState workout = workoutRepository.findByUserId(id.toString());
                    workout.setLocation(location);
                    workoutService.updateLocation(workout, id.toString());
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Место тренировки изменено");
                    sendMessage.setChatId(id);
                    sendMessage.setReplyMarkup(KeyboardFactory.mainKeyboard());
                    try {
                        bot.sender().execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().startsWith(SET_LOCATION));

    }
}
