package ctjournal.telegrambot.ability;

import ctjournal.telegrambot.repository.TokensHashRepository;
import ctjournal.telegrambot.service.AuthorizationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.util.AbilityExtension;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

@Component
public class AuthorizationAbility implements AbilityExtension {

    @Autowired
    private TokensHashRepository tokensHashRepository;

    @Autowired
    private AuthorizationServiceImpl authorizationService;

    public Ability start() {
        return Ability.builder()
                .name("authorize")
                .info("Авторизоваться")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(this::authorize)
                .build();
    }

    private void authorize(MessageContext ctx) {
        Long id = ctx.user().getId();
        String token = authorizationService.getToken("user", "password");
        tokensHashRepository.save(id.toString(), token);
        ctx.bot().silent().send("Авторизация успешно пройдена", id);
    }

}
