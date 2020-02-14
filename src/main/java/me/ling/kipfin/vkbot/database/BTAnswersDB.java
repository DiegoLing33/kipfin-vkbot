package me.ling.kipfin.vkbot.database;

import me.ling.kipfin.core.EntityDB;
import me.ling.kipfin.core.sql.SQLObjectMapper;
import me.ling.kipfin.exceptions.DatabaseEntityNotFoundException;
import me.ling.kipfin.vkbot.entities.BTAnswerModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Ответы бота (БД)
 */
public class BTAnswersDB extends EntityDB<BTAnswerModel, DatabaseEntityNotFoundException> {

    /**
     *  Ответы бота
     */
    public static final BTAnswersDB shared = new BTAnswersDB();

    @Override
    public Map<Integer, BTAnswerModel> getAll() throws SQLException {
        return SQLObjectMapper.selectAllAndMap(BTAnswerModel.class, "bot_answers", "bot_answer_id");
    }

    /**
     * Вовзаращет ответы по типу
     * @param type  - тип
     * @return      - список ответов
     */
    public List<String> getAnswersByType(String type){
        List<String> list = new ArrayList<>();
        for(BTAnswerModel botAnswer: this.getCache().values()){
            if(botAnswer.getType().equals(type)) list.add(botAnswer.getText());
        }
        return list;
    }
}
