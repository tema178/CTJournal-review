package ctjournal.telegrambot.ability;

import ctjournal.telegrambot.domain.Grade;
import ctjournal.telegrambot.domain.Route;
import ctjournal.telegrambot.repository.RouteRepository;
import ctjournal.telegrambot.service.GradeService;
import ctjournal.telegrambot.service.RouteService;
import ctjournal.telegrambot.utils.KeyboardFactory;
import ctjournal.telegrambot.utils.RouteToStringTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static ctjournal.telegrambot.utils.KeyboardFactory.EDIT_GRADE;
import static ctjournal.telegrambot.utils.KeyboardFactory.SET_GRADE;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

@Component
public class GradeAbility implements AbilityExtension {

    @Autowired
    private AbilityBot abilityBot;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private RouteService routeService;

    public Reply editGrade() {
        return Reply.of(
                (bot, upd) -> {
                    List<Grade> grades = gradeService.getGrades(getChatId(upd).toString());
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Выберите категорию:");
                    sendMessage.setChatId(getChatId(upd));
                    sendMessage.setReplyMarkup(KeyboardFactory.viewGrades(grades));
                    try {
                        bot.sender().execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(EDIT_GRADE));

    }

    public Reply setGrade() {
        return Reply.of(
                (bot, upd) -> {
                    long gradeId = Long.parseLong(upd.getCallbackQuery().getData().substring(SET_GRADE.length()));
                    Long id = getChatId(upd);
                    Route route = routeRepository.findByUserId(id.toString());
                    Grade grade = gradeService.getGrade(gradeId, id.toString());
                    route.setGrade(grade);
                    routeRepository.save(id.toString(), route);
                    routeService.update(route, id.toString());

                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Категория изменена\n" + RouteToStringTransformer.transform(route));
                    sendMessage.setChatId(getChatId(upd));
                    sendMessage.setReplyMarkup(KeyboardFactory.routeKeyboard(route));
                    try {
                        bot.sender().execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().startsWith(SET_GRADE));

    }
}
