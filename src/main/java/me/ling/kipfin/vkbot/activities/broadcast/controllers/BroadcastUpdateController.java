package me.ling.kipfin.vkbot.activities.broadcast.controllers;

import me.ling.kipfin.core.io.ResourcesManager;
import me.ling.kipfin.core.utils.DateUtils;
import me.ling.kipfin.vkbot.activities.broadcast.models.BroadcastUpdateModel;
import me.ling.kipfin.vkbot.app.BTActivity;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.app.MessageController;
import me.ling.kipfin.vkbot.utils.builders.KeyboardBuilder;
import me.ling.kipfin.vkbot.entities.VKBTAnswer;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.entities.message.BroadcastMessage;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@BTActivity
public class BroadcastUpdateController extends MessageController {
    @Override
    public boolean test(String text, VKUser user, ControllerArgs args) {
        return args.getMainArg().equals("/update") && user.isAdmin() && args.hasArg(0);
    }

    @NotNull
    @Override
    protected TextMessage execute(String text, VKUser user, ControllerArgs args) {
        String arg = args.get(0);
        if (arg.equals("global")) {
            String message = "ОБНОВЛЕНИЕ КИПФИН БОТ\n\nВерсия: 1.1 Maven Of Time\n\nЧто нового?\n\n" +
                    "1. Изменена политика использования КИПФИН БОТ. Изменения коснулись рассылки уведомлений от бота.\n\n" +
                    "2. Бот полностью переписан с Python 3.8 на Java 13.\n\n" +
                    "3. Расписание \"на следующую неделю\" больше не отображается ввиду несостыковок с электронным журналом.\n\n" +
                    "4. Новое отображение расписания на неделю (мы вас услышали).\n\n" +
                    "5. Изменена справка.\n\n" +
                    "6. Изменена логика функции \"расписание на завтра\". Чтобы не загружать студентов лишней информацией, расписание " +
                    "на завтра не отображается до момента публикации. Если Вы хотите смоделировать какие-то планы, используйте \"расписание на неделю\".\n\n";

            return new BroadcastMessage(message, List.of(49062753), ResourcesManager.get("moft.jpg"), KeyboardBuilder.startInlineKeyboard);
        } else if (arg.equals("day") && args.hasArg(1) && DateUtils.isStringDateInLocalFormat(args.get(1))) {
            String dateString = args.get(1);
            return BroadcastUpdateModel.createUpdateTimetableBroadcast(dateString);
        }
        return VKBTAnswer.UNKNOWN_COMMAND.toTextMessage();
    }
}
