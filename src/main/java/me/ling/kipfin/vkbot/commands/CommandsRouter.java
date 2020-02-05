package me.ling.kipfin.vkbot.commands;

import me.ling.kipfin.core.log.WithLogger;
import me.ling.kipfin.vkbot.entities.BTUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Роутер по командам
 */
public class CommandsRouter extends WithLogger {

    /**
     * Возвращает массив аргументов игнорируя первый аргумент
     *
     * @param raw - исходной массив
     * @return - массив аргументов
     */
    @NotNull
    public static ControllerArgs getArgsFromStringWithNoMainArg(@NotNull String[] raw) {
        var arr = raw.length > 1 ? Arrays.copyOfRange(raw, 1, raw.length) : new String[]{};
        return new ControllerArgs(raw[0], Arrays.asList(arr));
    }

    /**
     * Возвращает массив аргументов игнорируя первый аргумент
     *
     * @param text - исходной текст
     * @return - массив аргументов
     */
    @NotNull
    public static ControllerArgs getArgsFromStringWithNoMainArg(@NotNull String text) {
        return CommandsRouter.getArgsFromStringWithNoMainArg(text.split(" "));
    }

    /**
     * Контроллеры
     */
    protected List<Controller> controllers = new ArrayList<>();

    public CommandsRouter() {
        super("Router");
    }

    /**
     * Выполняет метод бота
     *
     * @param text   - текст
     * @param btUser - пользователь
     * @return - ответ или null
     * @warning В данном методе нет необходимости выполнять проверки, так как метод гарантированно не будет запущен,
     * если test не прошел!
     */
    @Nullable
    public String execute(String text, @NotNull BTUser btUser) {
        this.log(btUser.getUserId(), text);
        ControllerArgs args = CommandsRouter.getArgsFromStringWithNoMainArg(text);

        for (Controller controller : this.controllers) {
            var response = controller.requestExecute(text, btUser, args);
            if (response != null) return BTUser.template(response, btUser);
        }
        return null;
    }

    /**
     * Регистрирует контроллер
     *
     * @param controller - конттролер
     */
    public void addController(Controller controller) {
        this.controllers.add(controller);
    }
}
