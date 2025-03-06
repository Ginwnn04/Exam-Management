package Enum;

public enum TestResultEnum {
    PASS("Đạt"),
    FAIL("Rớt");

    private String displayText;

    private TestResultEnum(String displayText) {
        this.displayText = displayText;
    }

    public String getDisplayText() {
        return displayText;
    }
}
