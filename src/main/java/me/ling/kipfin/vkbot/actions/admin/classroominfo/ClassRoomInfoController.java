package me.ling.kipfin.vkbot.actions.admin.classroominfo;

import me.ling.kipfin.core.utils.DateUtils;
import me.ling.kipfin.timetable.managers.TimetableManager;
import me.ling.kipfin.vkbot.app.BTActivity;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.actions.controllers.TimetableController;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import org.jetbrains.annotations.NotNull;

@BTActivity
public class ClassRoomInfoController extends TimetableController {

    @Override
    public boolean test(String text, VKUser user, ControllerArgs args) {
        return args.testMainArg("аудитория", "ауд");
    }

    @NotNull
    @Override
    protected TextMessage execute(String text, VKUser user, ControllerArgs args) {
        if (args.size() < 2 || (args.hasArg(1) && !DateUtils.isStringDateInLocalFormat(args.getOrNull(1))))
            return new TextMessage("Ошибка! Проверьте правильность написания команды.\n\n" +
                    "Формат: Аудитория НОМЕР ДАТА\n\n" +
                    "Формат даты: ДД.ММ.ГГГГ, например: 30.01.2020");

        var master = TimetableManager.downloadOrGetCache(args.get(1));
        var model = new ClassRoomInfoModel(args.get(0), master);
        return model.getComponent().toTextMessage();
    }
}
