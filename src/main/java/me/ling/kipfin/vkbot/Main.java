package me.ling.kipfin.vkbot;

import io.github.cdimascio.dotenv.Dotenv;
import me.ling.kipfin.core.Bootloader;
import me.ling.kipfin.timetable.entities.TimetableMaster;
import me.ling.kipfin.timetable.managers.TimetableManager;
import me.ling.kipfin.vkbot.actions.admin.broadcastupdate.BroadcastUpdateModel;
import me.ling.kipfin.vkbot.app.Application;
import me.ling.kipfin.vkbot.utils.ResourceManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class Main {

    public static List<Integer> ids = List.of(49062753, 327217956, 194580925, 170194620, 217942321, 211912592, 112360221, 103978927, 190179363, 186402595, 367675400, 197882994, 191914862, 529283042, 146401304, 500483426, 354205382, 347628128, 279466560, 277129950, 313452699, 205425380);

    /**
     * Точка входа
     *
     * @param args - аргументы
     */
    public static void main(String[] args) throws SQLException, IOException {

        // Загрузка стартовых данных. Библиотека kipfin.core
        Dotenv dotenv = Dotenv.load();
        Bootloader bootloader = new Bootloader(dotenv);
        bootloader.updateDatabase(false);
        TimetableManager.TIMETABLE_WEB_PATH = dotenv.get("timetable_web_path"); //fixme -- add bootloader modules

        // Получение актуальных данных из .env
        Integer groupId = Integer.parseInt(Objects.requireNonNull(dotenv.get("vk.group_id")));
        String token = dotenv.get("vk.token");

        // Распаковка ресурсов
        ResourceManager.unpack("moft.jpg");
        ResourceManager.unpack("next_day.jpg");
        ResourceManager.unpack("welcome.jpg");

        // Созданеи и запуск приложения бота
        Application application = new Application(groupId, token);
        application.start();
    }

}