package me.ling.kipfin.vkbot.messagecomponents.group;

import me.ling.kipfin.vkbot.messagecomponents.MessageComponent;
import me.ling.kipfin.vkbot.utils.BTUtils;

public class GetRouterComponent extends MessageComponent {

    protected final String state;
    protected final String response;

    public GetRouterComponent(String state, String response) {
        this.state = state;
        this.response = response;
    }

    /**
     * Возвращает состояние
     * @return - состояние
     */
    public String getState() {
        return state;
    }

    /**
     * Возвращает ответ
     * @return  - ответ
     */
    public String getResponse() {
        return response;
    }

    /**
     * Возвращает строку информации
     *
     * @return - строка информации
     */
    public String getInfoLine() {
        return String.format("%s:\n%s",
                BTUtils.isStateTeacher(this.getState()) ? "Преподаватель" : "Группа",
                this.getState());
    }

    public String getResponseLine(){
        return String.format("%s:\n%s",
                BTUtils.isStateTeacher(this.getState()) ? "Учебная группа" : "Классный руководитель",
                this.getResponse());
    }

    @Override
    public String toString() {
        return String.format("%s\n\n%s", this.getInfoLine(), this.getResponseLine());
    }
}
