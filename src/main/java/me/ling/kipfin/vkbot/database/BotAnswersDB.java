package me.ling.kipfin.vkbot.database;

import me.ling.kipfin.core.EntityDB;
import me.ling.kipfin.core.sql.SQLObjectMapper;
import me.ling.kipfin.exceptions.DatabaseEntityNotFoundException;
import me.ling.kipfin.vkbot.entities.BotAnswer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Ответы бота (БД)
 */
public class BotAnswersDB extends EntityDB<BotAnswer, DatabaseEntityNotFoundException> {

    /**
     *  Ответы бота
     */
    public static BotAnswersDB shared = new BotAnswersDB();

    @Override
    public Map<Integer, BotAnswer> getAll() throws SQLException {
        return SQLObjectMapper.selectAllAndMap(BotAnswer.class, "bot_answers", "bot_answer_id");
    }

    /**
     * Вовзаращет ответы по типу
     * @param type  - тип
     * @return      - список ответов
     */
    public List<String> getAnswersByType(String type){
        List<String> list = new ArrayList<>();
        for(BotAnswer botAnswer: this.getCache().values()){
            if(botAnswer.getType().equals(type)) list.add(botAnswer.getText());
        }
        return list;
    }
}
