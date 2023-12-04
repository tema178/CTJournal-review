package ctjournal.telegrambot.initializer;

import ctjournal.telegrambot.ability.AuthorizationAbility;
import ctjournal.telegrambot.ability.ClimbingSessionAbility;
import ctjournal.telegrambot.ability.GradeAbility;
import ctjournal.telegrambot.ability.LocationAbility;
import ctjournal.telegrambot.ability.RouteAbility;
import ctjournal.telegrambot.ability.WorkoutAbility;
import ctjournal.telegrambot.config.BotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.db.DBContext;

@Component
public class TelegramBot extends AbilityBot {

    private final BotConfig botConfig;

    @Autowired
    public TelegramBot(BotConfig botConfig,
                       DBContext context,
                       @Lazy WorkoutAbility workoutAbility,
                       @Lazy LocationAbility locationAbility,
                       @Lazy ClimbingSessionAbility climbingSessionAbility,
                       @Lazy RouteAbility routeAbility,
                       @Lazy AuthorizationAbility authorizationAbility,
                       @Lazy GradeAbility gradeAbility) {
        super(botConfig.getToken(), botConfig.getBotName(), context);
        this.botConfig = botConfig;
        addExtensions(locationAbility, workoutAbility, climbingSessionAbility, routeAbility, gradeAbility, authorizationAbility);
    }

    @Override
    public long creatorId() {
        return botConfig.getCreatorId();
    }
}
