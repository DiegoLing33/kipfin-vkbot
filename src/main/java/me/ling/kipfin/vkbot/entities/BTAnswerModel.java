package me.ling.kipfin.vkbot.entities;

import me.ling.kipfin.core.sql.Column;

public class BTAnswerModel {

    @Column(name = "bot_answer_id", type = Integer.class)
    protected Integer id;

    @Column(name = "bot_answer_type")
    protected String type;

    @Column(name = "bot_answer_text")
    protected String text;

    @Column(name = "bot_answer_id", type = Integer.class)
    protected Integer author;

    public BTAnswerModel() {
    }

    public BTAnswerModel(Integer id, String type, String text, Integer author) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.author = author;
    }

    /**
     * Возвращает идентификатор
     * @return  - идентификатор ответа
     */
    public Integer getId() {
        return id;
    }

    /**
     * Возвращает тип ответа
     * @return  - тип ответа
     */
    public String getType() {
        return type;
    }

    /**
     * Возвращает текст ответа
     * @return  - текст ответа
     */
    public String getText() {
        return text;
    }

    /**
     * Возвращает втора ответа
     * @return  - автор ответа
     */
    public Integer getAuthor() {
        return author;
    }
}
