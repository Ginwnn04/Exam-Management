package DTO;

import java.sql.Date;

public class ResultDTO {
    private short resNum;
    private int userId;
    private String exCode;
    private String rsAnswer;
    private int rsMark;
    private Date rsDate;

    public short getResNum() {
        return resNum;
    }

    public ResultDTO setResNum(short resNum) {
        this.resNum = resNum;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public ResultDTO setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getExCode() {
        return exCode;
    }

    public ResultDTO setExCode(String exCode) {
        this.exCode = exCode;
        return this;
    }

    public String getRsAnswer() {
        return rsAnswer;
    }

    public ResultDTO setRsAnswer(String rsAnswer) {
        this.rsAnswer = rsAnswer;
        return this;
    }

    public int getRsMark() {
        return rsMark;
    }

    public ResultDTO setRsMark(int rsMark) {
        this.rsMark = rsMark;
        return this;
    }

    public Date getRsDate() {
        return rsDate;
    }

    public ResultDTO setRsDate(Date rsDate) {
        this.rsDate = rsDate;
        return this;
    }

    public static ResultDTO builder(){
        return new ResultDTO();
    }
}
