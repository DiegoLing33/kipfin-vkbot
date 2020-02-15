package me.ling.kipfin.vkbot.app;

import me.ling.kipfin.core.log.Logger;
import me.ling.kipfin.core.log.WithLogger;
import me.ling.kipfin.vkbot.entities.VKBTAnswer;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.entities.message.CoreMessage;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Роутер по командам
 */
public class CommandsRouter extends WithLogger {

    /**
     * Выполняет поиск активити в системе
     */
    public void findActivities() {
        Logger.logAs("ACM", "Поиск активити...");
        Reflections ref = new Reflections("me.ling.kipfin.vkbot.activities");
        for (Class<?> cl : ref.getTypesAnnotatedWith(BTActivity.class)) {
            Logger.logAs("ACM", "Найден активити:", cl.getSimpleName());
            try {
                this.addController((MessageController) cl.getConstructors()[0].newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

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
    protected final List<MessageController> controllers = new ArrayList<>();

    public CommandsRouter() {
        super("Router");
    }

    /**
     * Выполняет метод бота
     *
     * @param text   - текст
     * @param btUser - пользователь
     * @return - ответ
     * @warning В данном методе нет необходимости выполнять проверки, так как метод гарантированно не будет запущен,
     * если test не прошел!
     */
    @NotNull
    public CoreMessage execute(String text, @NotNull VKUser btUser) {
        this.log(btUser.getUserId(), text);
        ControllerArgs args = CommandsRouter.getArgsFromStringWithNoMainArg(text);

        for (MessageController controller : this.controllers) {
            CoreMessage response = controller.requestExecute(text, btUser, args);
            if (response != null) return response;
        }
        return VKBTAnswer.UNKNOWN_COMMAND.toTextMessage();
    }

    /**
     * Регистрирует контроллер
     *
     * @param controller - конттролер
     */
    public void addController(MessageController controller) {
        this.controllers.add(controller);
    }
}
