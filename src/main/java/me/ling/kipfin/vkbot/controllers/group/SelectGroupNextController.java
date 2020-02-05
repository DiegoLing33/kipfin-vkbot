package me.ling.kipfin.vkbot.controllers.group;

import me.ling.kipfin.core.entities.university.UniversityGroup;
import me.ling.kipfin.database.university.GroupsDB;
import me.ling.kipfin.vkbot.commands.ControllerArgs;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.commands.Controller;
import me.ling.kipfin.vkbot.entities.keboard.Button;
import me.ling.kipfin.vkbot.entities.keboard.Keyboard;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Контроллер выбора группы
 */
public class SelectGroupNextController extends Controller {

    @Override
    public boolean test(String text, BTUser user, ControllerArgs args) {
        return args.test("1 курс", "2 курс", "3 курс", "4 курс");
    }

    @Override
    public Object execute(String text, BTUser user, ControllerArgs args) {
        String first = text.substring(0, 1);
        List<String> groups = GroupsDB.shared.getCache().values().stream()
                .filter(universityGroup -> universityGroup.getTitle().substring(0, 1).equals(first))
                .map(UniversityGroup::getTitle).collect(Collectors.toCollection(Stack::new));
        user.setKeyboard(this.getKeyboard(groups));
        return "Выберите группу ⚡️";
    }

    /**
     * Создает клавиатуру из групп
     *
     * @param strings - массив строк
     * @return - клавиатур
     */
    protected Keyboard getKeyboard(List<String> strings) {
        Keyboard keyboard = new Keyboard();
        int row = 0;
        for (int i = 0; i < strings.size(); i += 2) {
            keyboard.add(new Button(strings.get(i)), row);
            if (i + 1 < strings.size()) keyboard.add(new Button(strings.get(i + 1)), row);
            row++;
        }
        keyboard.add(new Button("Домой", Button.SECONDARY), row);
        return keyboard;
    }
}
