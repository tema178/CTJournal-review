package ctjournal.telegrambot.ability;

import ctjournal.telegrambot.domain.ClimbingSession;
import ctjournal.telegrambot.domain.WorkoutState;
import ctjournal.telegrambot.repository.WorkoutRepository;
import ctjournal.telegrambot.service.ClimbingSessionService;
import ctjournal.telegrambot.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.function.BiConsumer;

import static ctjournal.telegrambot.utils.KeyboardFactory.EDIT_CLIMBING_SESSION;
import static ctjournal.telegrambot.utils.KeyboardFactory.climbingSessionKeyboard;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

@Component
public class ClimbingSessionAbility implements AbilityExtension {

    @Autowired
    private AbilityBot abilityBot;
    @Autowired
    private ClimbingSessionService service;

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private WorkoutRepository workoutRepository;


    public Reply editClimbingSession() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> {
            Long id = getChatId(upd);
            WorkoutState workout = workoutRepository.findByUserId(id.toString());
            if (workout.getClimbingSession() == 0) {
                ClimbingSession climbingSession = service.create(workout, id.toString());
                workout.setClimbingSession(climbingSession.getId());
                workoutRepository.save(id.toString(), workout);
            }
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Выберите действие:");
            sendMessage.setChatId(id);
            sendMessage.setReplyMarkup(climbingSessionKeyboard());
            try {
                bot.sender().execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        };
        return Reply.of(action, upd ->
                upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(EDIT_CLIMBING_SESSION)
        );
    }

}
