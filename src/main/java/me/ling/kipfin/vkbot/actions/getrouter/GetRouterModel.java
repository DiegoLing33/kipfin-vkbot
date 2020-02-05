package me.ling.kipfin.vkbot.actions.getrouter;

import me.ling.kipfin.core.entities.university.Teacher;
import me.ling.kipfin.database.university.GroupsDB;
import me.ling.kipfin.database.university.TeachersDB;
import me.ling.kipfin.vkbot.entities.BTAnswerType;
import me.ling.kipfin.vkbot.utils.BTUtils;

public class GetRouterModel {

    private final String state;

    public GetRouterModel(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    /**
     * Возвращает ответ
     *
     * @return -    ответ
     */
    public String getResponse() {
        try {
            if (BTUtils.isStateStudent(this.getState())) {
                var group = GroupsDB.shared.easy(this.getState());
                for (Teacher t : TeachersDB.shared.getCache().values()) {
                    if (t.getGroupId().equals(group.getGroupId()))
                        return t.getName();
                }
            } else if (BTUtils.isStateTeacher(getState())) {
                for (Teacher t : TeachersDB.shared.getCache().values()) {
                    if (t.getName().equals(this.getState())) {
                        var groupId = t.getGroupId();
                        return GroupsDB.shared.getById(groupId).getTitle();
                    }
                }
            }
            return BTAnswerType.NO_ROUTING.random(BTUtils.isStateTeacher(this.getState()));
        } catch (Exception e) {
            return BTAnswerType.NO_ROUTING.random(BTUtils.isStateTeacher(this.getState()));
        }
    }

    public GetRouterComponent getComponent() {
        return new GetRouterComponent(this.getState(), this.getResponse());
    }
}
