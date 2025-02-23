/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author quang
 */
public class QuestionDTO {
    private int id;
    private String content;
    private String picture;
    private int topicId;
    private String level;
    private boolean status;

    
    public QuestionDTO setId(int id) {
            this.id = id;
            return this;
        }

    public QuestionDTO setContent(String content) {
        this.content = content;
        return this;
    }

    public QuestionDTO setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public QuestionDTO setTopicId(int topicId) {
        this.topicId = topicId;
        return this;
    }

    public QuestionDTO setLevel(String level) {
        this.level = level;
        return this;
    }

    public QuestionDTO setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public static QuestionDTO builder() {
        return new QuestionDTO();
    }

    public QuestionDTO build() {
        return this;
    }
    
    
    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getPicture() {
        return picture;
    }

    public int getTopicId() {
        return topicId;
    }

    public String getLevel() {
        return level;
    }

    public boolean isStatus() {
        return status;
    }  
}