package me.ling.kipfin.vkbot.actions.admin.broadcastupdate;

import me.ling.kipfin.core.utils.DateUtils;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.app.MessageController;
import me.ling.kipfin.vkbot.entities.BTAnswerType;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.utils.BroadcastMessage;
import me.ling.kipfin.vkbot.utils.images.LandingImage;

import java.util.List;

public class BroadcastNewTimetable extends MessageController {
    @Override
    public boolean test(String text, BTUser user, ControllerArgs args) {
        return args.getMainArg().equals("/send-update") && DateUtils.isStringDateInLocalFormat(args.get(0)) && user.isAdmin();
    }

    @Override
    protected Object execute(String text, BTUser user, ControllerArgs args) {
        String dateString = args.get(0);
        return new BroadcastMessage("@УчебнаяЧастьКИПФИН:\n\nРасписание на " + dateString +
                " обновлено!", List.of(49062753), new LandingImage(dateString).getImage().toString());
    }
}
