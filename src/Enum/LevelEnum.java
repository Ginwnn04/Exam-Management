/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Enum;

/**
 *
 * @author quang
 */
public enum LevelEnum {
    EASY("Dễ"),
    MEDIUM("Trung bình"),
    HARD("Khó");
    private String desc;

    private LevelEnum(String desc) {
        this.desc = desc;
    }
    
    public String getDesc() {
        return desc;
    }
}
