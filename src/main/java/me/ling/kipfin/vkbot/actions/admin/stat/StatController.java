package me.ling.kipfin.vkbot.actions.admin.stat;

import me.ling.kipfin.vkbot.analyzers.BTStatsAnalyzer;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.app.MessageController;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.managers.BTStatsManager;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public class StatController extends MessageController {
    @Override
    public boolean test(String text, @NotNull BTUser user, ControllerArgs args) {
        return user.isAdmin() && args.getMainArg().equals("/stat");
    }

    @Override
    protected Object execute(String text, BTUser user, ControllerArgs args) {
        if (args.hasArg(0) && args.getOrNull(0).equals("save")) {
            BTStatsManager.save();
            return "Статистика сохранена!";
        }
        var statsSources = BTStatsManager.loadAll();
        var stats = new BTStatsAnalyzer(statsSources);

        var today = stats.getOnDate(LocalDate.now());
        var todayUnique = BTStatsAnalyzer.unique(today);

        var month = stats.getOnMonth(LocalDate.now());
        var monthUnique = BTStatsAnalyzer.unique(month);

        return String.format("Статистика\n\nИспользований сегодня:\n%s (%s)\n\nИспользований за месяц:\n%s (%s)",
                today.size(), todayUnique.size(), month.size(), monthUnique.size());
    }
}
