package me.ling.kipfin.vkbot.utils;

import me.ling.kipfin.core.utils.URLUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Утилиты пользователей бота
 */
public class BotUserUtils {


    /**
     * Возвращает информацию о пользователе
     *
     * @param userId - идентификатор пользователя
     * @param token  - ключ доступа к API
     * @return - результаты
     */
    @NotNull
    public static JSONObject getUserInfo(@NotNull Integer userId, String token) {
        String url = "https://api.vk.com/method/users.get?v=5.8&user_ids=" + userId.toString() +
                "&fields=sex&access_token=" + token;
        try {
            JSONObject result = new JSONObject(URLUtils.downloadString(url));
            JSONArray response = result.getJSONArray("response");
            return response.getJSONObject(0);
        } catch (IOException e) {
            return new JSONObject();
        }
    }

}
