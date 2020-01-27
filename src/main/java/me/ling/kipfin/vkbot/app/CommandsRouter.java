package me.ling.kipfin.vkbot.app;

import me.ling.kipfin.core.log.WithLogger;
import me.ling.kipfin.vkbot.entities.BotUser;
import me.ling.kipfin.vkbot.entities.Controller;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Роутер по командам
 */
public class CommandsRouter extends WithLogger {

    /**
     * Контроллеры
     */
    protected List<Controller> controllers = new ArrayList<>();

    public CommandsRouter() {
        super("Router");
    }

    /**
     * Выполняет метод бота
     * @param text      - текст
     * @param botUser   - пользователь
     * @return          - ответ или null
     */
    @Nullable
    public String execute(String text, BotUser botUser){
        this.log(botUser.getUserId(), text);
        for(Controller controller: this.controllers){
            if(controller.test(text, botUser)) return controller.execute(text, botUser);
        }
        return null;
    }

    /**
     * Регистрирует контроллер
     * @param controller    - конттролер
     */
    public void addController(Controller controller){
        this.controllers.add(controller);
    }
}
