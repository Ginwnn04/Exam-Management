/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author pc
 */
public class AnswerDTO {
    private int id;
    private int questionId;
    private String content;
    private String picture;
    private boolean isRight;
    private boolean status;

    public AnswerDTO setId(int id) {
        this.id = id;
        return this;
    }

    public AnswerDTO setQuestionId(int questionId) {
        this.questionId = questionId;
        return this;
    }

    public AnswerDTO setContent(String content) {
        this.content = content;
        return this;

    }

    public AnswerDTO setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public AnswerDTO setIsRight(boolean isRight) {
        this.isRight = isRight;
        return this;
    }

    public AnswerDTO setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public static AnswerDTO builder() {
        return new AnswerDTO();
    }

    public AnswerDTO build() {
        return this;
    }
    
    
    public int getId() {
        return id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getContent() {
        return content;
    }

    public String getPicture() {
        return picture;
    }

    public boolean isIsRight() {
        return isRight;
    }

    public boolean isStatus() {
        return status;
    }
    
    
    
    
    
}
