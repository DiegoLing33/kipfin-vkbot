package me.ling.kipfin.vkbot.activities.broadcast.models;

import me.ling.kipfin.vkbot.database.BTValuesDB;
import me.ling.kipfin.vkbot.entities.BTValue;
import me.ling.kipfin.vkbot.entities.message.BroadcastMessage;
import me.ling.kipfin.vkbot.utils.builders.KeyboardBuilder;
import me.ling.kipfin.vkbot.utils.images.LandingImage;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BroadcastUpdateModel {

    /**
     * Возвращает идентификаторы выпользователей
     *
     * @return - идентификатор пользователя
     */
    @NotNull
    public static List<Integer> getUserIds() {
        List<Integer> ids = new ArrayList<>();
        for (BTValue value : BTValuesDB.shared.getCache().values()) {
            if (value.getType().equals("vkid")) {
                Integer kw = Integer.parseInt(value.getKey());
                if (!ids.contains(kw)) ids.add(kw);
            }
        }
        return ids;
    }

    /**
     * Создает рассылку обновления расписания
     *
     * @param date - дата
     * @return - объект рассылки
     */
    public static BroadcastMessage createUpdateTimetableBroadcast(String date) {
        return new BroadcastMessage(
                "@УчебнаяЧастьКИПФИН:\n\nРасписание на " + date + " обновлено!",
                KeyboardBuilder.whenTimetableUpdateInlineKeyboard,
                new LandingImage(date).getImage(),
                BroadcastUpdateModel.getUserIds());

    }

}
