package DTO;

import java.util.ArrayList;

public class ExamDTO {
    private String testCode;
    private String exOrder;
    private String exCode;
    private ArrayList<QuestionDTO> questions;
    private boolean status;

    public static ExamDTO builder() {
        return new ExamDTO();
    }

    public ExamDTO build() {
        return this;
    }

    public String getTestCode() {
        return testCode;
    }

    public ExamDTO setTestCode(String testCode) {
        this.testCode = testCode;
        return this;
    }

    public String getExOrder() {
        return exOrder;
    }

    public ExamDTO setExOrder(String exOrder) {
        this.exOrder = exOrder;
        return this;
    }

    public String getExCode() {
        return exCode;
    }

    public ExamDTO setExCode(String exCode) {
        this.exCode = exCode;
        return this;
    }

    public ArrayList<QuestionDTO> getQuestions() {
        return questions;
    }

    public ExamDTO setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public boolean getStatus() {
        return status;
    }

    public ExamDTO setQuestions(ArrayList<QuestionDTO> questions) {
        this.questions = questions;
        return this;
    }
}
