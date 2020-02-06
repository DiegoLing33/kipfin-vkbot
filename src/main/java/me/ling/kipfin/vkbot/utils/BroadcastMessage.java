package me.ling.kipfin.vkbot.utils;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BroadcastMessage {

    private final String message;
    private final List<Integer> users;
    @Nullable
    private final String image;

    public BroadcastMessage(String message, List<Integer> users, @Nullable String image) {
        this.message = message;
        this.users = users;
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public List<Integer> getUsers() {
        return users;
    }

    @Nullable
    public String getImage() {
        return image;
    }
}
