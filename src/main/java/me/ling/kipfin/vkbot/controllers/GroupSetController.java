package me.ling.kipfin.vkbot.controllers;

import me.ling.kipfin.database.university.GroupsDB;
import me.ling.kipfin.vkbot.entities.BotUser;
import me.ling.kipfin.vkbot.entities.Controller;

public class GroupSetController extends Controller {
    @Override
    public boolean test(String text, BotUser user) {
        try {
            return GroupsDB.shared.easy(text).getTitle() != null;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public String execute(String text, BotUser user) {
        try {
            var group = GroupsDB.shared.easy(text).getTitle();
            return "Установлена группа " + group;
        }catch (Exception e) {
            return "";
        }
    }
}
