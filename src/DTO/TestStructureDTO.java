package DTO;

public class TestStructureDTO {
    private String testCode;
    private int tpID;
    private int numEasy;
    private int numMedium;
    private int numDiff;

    public TestStructureDTO() {

    }

    public String getTestCode() {
        return testCode;
    }

    public TestStructureDTO setTestCode(String testCode) {
        this.testCode = testCode;
        return this;
    }

    public int getTopicID() {
        return tpID;
    }

    public TestStructureDTO setTopicID(int tpID) {
        this.tpID = tpID;
        return this;
    }

    public int getNumEasy() {
        return numEasy;
    }

    public TestStructureDTO setNumEasy(int numEasy) {
        this.numEasy = numEasy;
        return this;
    }

    public int getNumMedium() {
        return numMedium;
    }

    public TestStructureDTO setNumMedium(int numMedium) {
        this.numMedium = numMedium;
        return this;
    }

    public int getNumDiff() {
        return numDiff;
    }

    public TestStructureDTO setNumDiff(int numDiff) {
        this.numDiff = numDiff;
        return this;
    }

    public static TestStructureDTO builder() {
        return new TestStructureDTO();
    }
}
