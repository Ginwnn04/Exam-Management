
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package DTO;

public class TopicDTO {
    private int id;
    private String title;
    private String parent;
    private boolean status;
    public TopicDTO(){
    }
    public TopicDTO setId(int id) {
            this.id = id;
            return this;
        }

    public TopicDTO setTitle(String title) {
        this.title = title;
        return this;
    }


    public TopicDTO setParent(int parent) {
        this.parent = parent;
        return this;
    }

    public TopicDTO setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public static TopicDTO builder() {
        return new TopicDTO();
    }

    public TopicDTO build() {
        return this;
    }
    
    
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getParent() {
        return parent;
    }

    public boolean isStatus() {
        return status;
    }
}

