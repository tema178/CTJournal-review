package ctjournal.telegrambot.ability;

import ctjournal.telegrambot.dto.WorkoutDto;
import ctjournal.telegrambot.repository.StatesRepository;
import ctjournal.telegrambot.repository.WorkoutRepository;
import ctjournal.telegrambot.service.WorkoutService;
import ctjournal.telegrambot.utils.KeyboardFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static ctjournal.telegrambot.ability.RouteAbility.UNSUPPORTED_NUMBER;
import static ctjournal.telegrambot.domain.States.FINISHED;
import static ctjournal.telegrambot.domain.States.ROUTE_MENU;
import static ctjournal.telegrambot.domain.States.WAITING_WORKOUT_COMMENT;
import static ctjournal.telegrambot.domain.States.WAITING_WORKOUT_RATING;
import static ctjournal.telegrambot.utils.KeyboardFactory.EDIT_WORKOUT_COMMENT;
import static ctjournal.telegrambot.utils.KeyboardFactory.EDIT_WORKOUT_EFFORTS;
import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

@Component
public class WorkoutAbility implements AbilityExtension {

    @Autowired
    private AbilityBot abilityBot;
    @Autowired
    private WorkoutService service;

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
            long workout = service.create(id).getId();
            workoutRepository.save(id, new WorkoutDto(workout));
            sendKeyboardWithMessage("Где сегодня тренируемся?", ctx.chatId(), KeyboardFactory.location());
        } else {
            sendKeyboardWithMessage("У вас есть не завершенная тренировка", ctx.chatId(), KeyboardFactory.mainKeyboard());
        }
    }


    public Reply editComment() {
        return Reply.of(
                (bot, upd) -> {
                    Long id = getChatId(upd);
                    statesRepository.save(id.toString(), WAITING_WORKOUT_COMMENT);
                    bot.silent().send("Введите комментарий:", getChatId(upd));
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(EDIT_WORKOUT_COMMENT));

    }

    public Reply setComment() {
        return Reply.of(
                (bot, upd) -> {
                    Long id = getChatId(upd);
                    WorkoutDto workout = workoutRepository.findByUserId(id.toString());
                    workout.setComment(upd.getMessage().getText());
                    workoutRepository.save(id.toString(), workout);
                    service.update(workout, id.toString());
                    statesRepository.save(id.toString(), ROUTE_MENU);

                    sendKeyboardWithMessage("Комментарий сохранён", id, KeyboardFactory.mainKeyboard());
                },
                upd ->
                        upd.hasMessage()
                                && statesRepository.findByUserId(getChatId(upd).toString()) == WAITING_WORKOUT_COMMENT);

    }

    public Reply editRating() {
        return Reply.of(
                (bot, upd) -> {
                    Long id = getChatId(upd);
                    statesRepository.save(id.toString(), WAITING_WORKOUT_RATING);
                    bot.silent().send("Введите оценку 1-10:", getChatId(upd));
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(EDIT_WORKOUT_EFFORTS));

    }

    public Reply setRating() {
        return Reply.of(
                (bot, upd) -> {
                    Long id = getChatId(upd);
                    byte rating;
                    try {
                        rating = Byte.parseByte(upd.getMessage().getText());
                        if (rating < 1 || rating > 10) {
                            bot.silent().send("Число должно быть в диапазоне 1-10. Попробуйте снова", id);
                        } else {
                            WorkoutDto workoutDto = workoutRepository.findByUserId(id.toString());
                            workoutDto.setEfforts(rating);
                            workoutRepository.save(id.toString(), workoutDto);
                            service.update(workoutDto, id.toString());
                            statesRepository.save(id.toString(), ROUTE_MENU);

                            sendKeyboardWithMessage("Оценка сохранена", id, KeyboardFactory.mainKeyboard());
                        }
                    } catch (NumberFormatException e) {
                        bot.silent().send(UNSUPPORTED_NUMBER, id);
                    }
                },
                upd ->
                        upd.hasMessage()
                                && statesRepository.findByUserId(getChatId(upd).toString()) == WAITING_WORKOUT_RATING);

    }

    private void sendKeyboardWithMessage(String text, Long id, ReplyKeyboard replyMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(id);
        sendMessage.setReplyMarkup(replyMarkup);
        try {
            abilityBot.sender().execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
