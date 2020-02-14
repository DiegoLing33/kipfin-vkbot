package me.ling.kipfin.vkbot.actions.controllers.group;

import com.vk.api.sdk.objects.messages.Keyboard;
import com.vk.api.sdk.objects.messages.KeyboardButton;
import me.ling.kipfin.core.entities.university.UniversityGroup;
import me.ling.kipfin.database.university.GroupsDB;
import me.ling.kipfin.vkbot.app.BTActivity;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.builders.KeyboardBuilder;
import me.ling.kipfin.vkbot.actions.controllers.TimetableController;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Контроллер выбора группы
 */
@BTActivity
public class SelectGroupNextController extends TimetableController {

    @Override
    public boolean test(String text, VKUser user, ControllerArgs args) {
        return this.testAlias(text, "1 курс", "2 курс", "3 курс", "4 курс");
    }

    @NotNull
    @Override
    public TextMessage execute(String text, VKUser user, ControllerArgs args) {
        String first = text.substring(0, 1);
        List<String> groups = GroupsDB.shared.getCache().values().stream()
                .filter(universityGroup -> universityGroup.getTitle().substring(0, 1).equals(first))
                .map(UniversityGroup::getTitle).collect(Collectors.toCollection(Stack::new));
        user.setKeyboard(this.getKeyboard(groups));
        return new TextMessage("Выберите группу ⚡️");
    }

    /**
     * Создает клавиатуру из групп
     *
     * @param strings - массив строк
     * @return - клавиатур
     */
    protected Keyboard getKeyboard(List<String> strings) {
        Keyboard keyboard = new Keyboard().setOneTime(false);
        List<List<KeyboardButton>> buttons = new ArrayList<>();
        List<KeyboardButton> buttonsRow = new ArrayList<>();
        int row = 0;
        for (int i = 0; i < strings.size(); i += 2) {
            buttonsRow.add(KeyboardBuilder.Buttons.createTextButton(strings.get(i)));
            if (i + 1 < strings.size()){
                buttonsRow.add(KeyboardBuilder.Buttons.createTextButton(strings.get(i + 1)));
            }
            buttons.add(buttonsRow);
            buttonsRow = new ArrayList<>();
        }
        buttons.add(List.of(KeyboardBuilder.Buttons.homeButton));

        return keyboard.setButtons(buttons);
    }
}
