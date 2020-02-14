package me.ling.kipfin.vkbot.activities.watcher.controlles;

import me.ling.kipfin.vkbot.analyzers.BTStatsAnalyzer;
import me.ling.kipfin.vkbot.app.BTActivity;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.app.MessageController;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import me.ling.kipfin.vkbot.managers.BTStatsManager;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@BTActivity
public class StatController extends MessageController {
    @Override
    public boolean test(String text, @NotNull VKUser user, ControllerArgs args) {
        return user.isAdmin() && args.getMainArg().equals("/stat");
    }

    @NotNull
    @Override
    protected TextMessage execute(String text, VKUser user, ControllerArgs args) {
        if (args.hasArg(0) && args.getOrNull(0).equals("save")) {
            BTStatsManager.save();
            return new TextMessage("Статистика сохранена!");
        }
        var statsSources = BTStatsManager.loadAll();
        var stats = new BTStatsAnalyzer(statsSources);

        var today = stats.getOnDate(LocalDate.now());
        var todayUnique = BTStatsAnalyzer.unique(today);

        var month = stats.getOnMonth(LocalDate.now());
        var monthUnique = BTStatsAnalyzer.unique(month);

        return new TextMessage(String.format("Статистика\n\nИспользований сегодня:\n%s (%s)\n\nИспользований за месяц:\n%s (%s)",
                today.size(), todayUnique.size(), month.size(), monthUnique.size()));
    }
}
