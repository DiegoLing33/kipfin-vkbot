package me.ling.kipfin.vkbot.entities.message;

import me.ling.kipfin.vkbot.entities.keboard.Keyboard;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BroadcastMessage extends ImagedTextMessage {

    private final List<Integer> users;

    public BroadcastMessage(String message, List<Integer> users, @Nullable String image, @Nullable Keyboard keyboard) {
        super(message, keyboard, image);
        this.users = users;
    }

    public BroadcastMessage(String message, List<Integer> users, @Nullable String image) {
        super(message, null, image);
        this.users = users;
    }

    public BroadcastMessage(String message, List<Integer> users, @Nullable Keyboard keyboard) {
        super(message, keyboard, null);
        this.users = users;
    }

    public BroadcastMessage(String message, List<Integer> users) {
        super(message, null, null);
        this.users = users;
    }


    public List<Integer> getUsers() {
        return users;
    }
}
