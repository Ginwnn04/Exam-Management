package DTO;

import java.sql.Date;

public class TestExamDTO {
    private int id;
    private String testCode;
    private String title;
    private int topicId;
    private int easyQuestionCount;
    private int mediumQuestionCount;
    private int diffQuestionCount;
    private short testLimit;
    private int testTime;
    private Date testDate;
    private boolean testStatus;

    public TestExamDTO() {

    }

    public TestExamDTO setId(int id) {
        this.id = id;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getTestCode() {
        return testCode;
    }

    public TestExamDTO setTestCode(String testCode) {
        this.testCode = testCode;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TestExamDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getTopicId() {
        return topicId;
    }

    public TestExamDTO setTopicId(int topicId) {
        this.topicId = topicId;
        return this;
    }

    public int getEasyQuestionCount() {
        return easyQuestionCount;
    }

    public TestExamDTO setEasyQuestionCount(int easyQuestionCount) {
        this.easyQuestionCount = easyQuestionCount;
        return this;
    }

    public int getMediumQuestionCount() {
        return mediumQuestionCount;
    }

    public TestExamDTO setMediumQuestionCount(int mediumQuestionCount) {
        this.mediumQuestionCount = mediumQuestionCount;
        return this;
    }

    public int getDiffQuestionCount() {
        return diffQuestionCount;
    }

    public TestExamDTO setDiffQuestionCount(int diffQuestionCount) {
        this.diffQuestionCount = diffQuestionCount;
        return this;
    }

    public short getTestLimit() {
        return testLimit;
    }

    public TestExamDTO setTestLimit(short testLimit) {
        this.testLimit = testLimit;
        return this;
    }

    public int getTestTime() {
        return testTime;
    }

    public TestExamDTO setTestTime(int testTime) {
        this.testTime = testTime;
        return this;
    }

    public Date getTestDate() {
        return testDate;
    }

    public TestExamDTO setTestDate(Date testDate) {
        this.testDate = testDate;
        return this;
    }

    public boolean isTestStatus() {
        return testStatus;
    }

    public TestExamDTO setTestStatus(boolean testStatus) {
        this.testStatus = testStatus;
        return this;
    }

    public static TestExamDTO builder(){
        return new TestExamDTO();
    }
}
