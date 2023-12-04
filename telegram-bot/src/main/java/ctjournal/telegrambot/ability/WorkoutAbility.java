package ctjournal.telegrambot.ability;

import ctjournal.telegrambot.domain.WorkoutState;
import ctjournal.telegrambot.repository.StatesRepository;
import ctjournal.telegrambot.repository.WorkoutRepository;
import ctjournal.telegrambot.service.WorkoutService;
import ctjournal.telegrambot.utils.KeyboardFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static ctjournal.telegrambot.domain.States.FINISHED;
import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

@Component
public class WorkoutAbility implements AbilityExtension {

    @Autowired
    private AbilityBot abilityBot;
    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private StatesRepository statesRepository;

    public Ability start() {
        return Ability.builder()
                .name("start")
                .info("Создать новую тренировку")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(this::createWorkout)
                .build();
    }

    private void createWorkout(MessageContext ctx) {
        String id = ctx.user().getId().toString();
        if (workoutRepository.findByUserId(id) == null || statesRepository.findByUserId(id) == null || statesRepository.findByUserId(id) == FINISHED) {
            long workout = workoutService.createWorkout(id).getId();
            workoutRepository.save(id, WorkoutState.builder().id(workout).build());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Где сегодня тренируемся?");
            sendMessage.setChatId(ctx.chatId());
            sendMessage.setReplyMarkup(KeyboardFactory.location());
            try {
                abilityBot.sender().execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("У вас есть не завершенная тренировка");
            sendMessage.setChatId(ctx.chatId());
            sendMessage.setReplyMarkup(KeyboardFactory.mainKeyboard());
            try {
                abilityBot.sender().execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
