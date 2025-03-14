package DTO;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class LogDTO {
    private int logId;
    private String logContent;
    private int logUserID;
    private String logExCode;
    private Date logDate;

    public LogDTO() {

    }

    public static LogDTO builder() {
        return new LogDTO();
    }

    public int getLogId() {
        return logId;
    }

    public LogDTO setLogId(int logId) {
        this.logId = logId;
        return this;
    }

    public String getLogContent() {
        return logContent;
    }

    public LogDTO setLogContent(String logContent) {
        this.logContent = logContent;
        return this;
    }

    public int getLogUserID() {
        return logUserID;
    }

    public LogDTO setLogUserID(int logUserID) {
        this.logUserID = logUserID;
        return this;
    }

    public String getLogExCode() {
        return logExCode;
    }

    public LogDTO setLogExCode(String logExCode) {
        this.logExCode = logExCode;
        return this;
    }

    public Date getLogDate() {
        return logDate;
    }

    public LogDTO setLogDate(Date logDate) {
        this.logDate = logDate;
        return this;
    }

    public void writeLog(String message) {
        Date time = new Date(System.currentTimeMillis());

        Timestamp ts = new Timestamp(time.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        String formattedDate = formatter.format(ts);

        logContent += String.format("%s: %s\n", formattedDate, message);
    }

    public void writeLog(Date time, String message) {
        Timestamp ts = new Timestamp(time.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        String formattedDate = formatter.format(ts);

        logContent += String.format("%s: %s\n", formattedDate, message);
    }

    public void clearLog() {
        logContent = "";
    }
}
