/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author pc
 */
public class AnwserDTO {
    private int id;
    private int questionId;
    private String content;
    private String picture;
    private boolean isRight;
    private boolean status;

    public AnwserDTO setId(int id) {
        this.id = id;
        return this;
    }

    public AnwserDTO setQuestionId(int questionId) {
        this.questionId = questionId;
        return this;
    }

    public AnwserDTO setContent(String content) {
        this.content = content;
        return this;

    }

    public AnwserDTO setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public AnwserDTO setIsRight(boolean isRight) {
        this.isRight = isRight;
        return this;
    }

    public AnwserDTO setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public static AnwserDTO builder() {
        return new AnwserDTO();
    }

    public AnwserDTO build() {
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
