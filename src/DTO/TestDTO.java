package DTO;

import java.sql.Date;

public class TestDTO {
    private int id;
    private String testCode;
    private String title;
    private short testLimit;
    private long testTime;
    private Date testDate;
    private boolean testStatus;

    public TestDTO() {

    }

    public TestDTO setId(int id) {
        this.id = id;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getTestCode() {
        return testCode;
    }

    public TestDTO setTestCode(String testCode) {
        this.testCode = testCode;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TestDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public short getTestLimit() {
        return testLimit;
    }

    public TestDTO setTestLimit(short testLimit) {
        this.testLimit = testLimit;
        return this;
    }

    public long getTestTime() {
        return testTime;
    }

    public TestDTO setTestTime(long testTime) {
        this.testTime = testTime;
        return this;
    }

    public Date getTestDate() {
        return testDate;
    }

    public TestDTO setTestDate(Date testDate) {
        this.testDate = testDate;
        return this;
    }

    public boolean isTestStatus() {
        return testStatus;
    }

    public TestDTO setTestStatus(boolean testStatus) {
        this.testStatus = testStatus;
        return this;
    }

    public static TestDTO builder(){
        return new TestDTO();
    }
}
