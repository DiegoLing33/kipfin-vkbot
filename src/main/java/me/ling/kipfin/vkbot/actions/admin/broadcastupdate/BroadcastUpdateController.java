package me.ling.kipfin.vkbot.actions.admin.broadcastupdate;

import me.ling.kipfin.core.utils.DateUtils;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.app.MessageController;
import me.ling.kipfin.vkbot.entities.BTAnswerType;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.utils.BroadcastMessage;
import me.ling.kipfin.vkbot.utils.images.LandingImage;

import java.util.List;

public class BroadcastUpdateController extends MessageController {
    @Override
    public boolean test(String text, BTUser user, ControllerArgs args) {
        return args.getMainArg().equals("/update") && user.isAdmin() && args.hasArg(0);
    }

    @Override
    protected Object execute(String text, BTUser user, ControllerArgs args) {
        String arg = args.get(0);
        if (arg.equals("global")) {
            String message = "ОБНОВЛЕНИЕ КИПФИН БОТ\n\nВерсия: 1.1 Maven Of Time\n\nЧто нового?\n\ns" +
                    "1. Изменена политика использования КИПФИН БОТ. Изменения коснулись рассылки уведомлений от бота.\n\n" +
                    "2. Бот полностью переписан с Python 3.8 на Java 13.\n\n" +
                    "3. Расписание \"на следующую неделю\" больше не отображается ввиду несостыковок с электронным журналом.\n\n" +
                    "4. Новое отображение расписания на неделю (мы вас услышали).\n\n" +
                    "5. Изменена справка.\n\n" +
                    "6. Передалано меню. Теперь у Вас под рукой нет ничего лишнего.\n\n" +
                    "7. Изменена логика функции \"расписание на завтра\". Чтобы не загружать студентов лишней информацией, расписание " +
                    "на завтра не отображается до момента публикации. Если Вы хотите смоделировать какие-то планы, используйте \"расписание на неделю\".\n\n";
            return new BroadcastMessage(message, List.of(49062753),
                    this.getClass().getResource("/moft.jpg").toString());
        } else if (arg.equals("day") && args.hasArg(1) && DateUtils.isStringDateInLocalFormat(args.get(1))) {
            String dateString = args.get(1);
            return new BroadcastMessage("@УчебнаяЧастьКИПФИН:\n\nРасписание на " + dateString +
                    " обновлено!", List.of(49062753), new LandingImage(dateString).getImage().toString());
        }
        return BTAnswerType.UNKNOWN_COMMAND.random();
    }
}
