package GUI.Utils;

import java.sql.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Supplier;

import BUS.LogBUS;
import DTO.LogDTO;
import DTO.UserDTO;

public class DoExamLogger {
    private LogDTO log;
    private String exCode;
    private Supplier<HashMap<Integer, Set<Character>>> trackingQuestionsSupplier;
    private LogBUS logBUS = new LogBUS();

    public DoExamLogger(Supplier<HashMap<Integer, Set<Character>>> trackingQuestionsSupplier, String exCode) {
        this.trackingQuestionsSupplier = trackingQuestionsSupplier;
        this.exCode = exCode;

        buildLog();
    }

    private void buildLog() {
        UserDTO currentUser = UserSession.getInstance().getCurrentUser();

        log = LogDTO.builder()
                    .setLogUserID(currentUser.getId())
                    .setLogExCode(exCode)
                    .setLogContent("")
                    .setLogDate(new Date(System.currentTimeMillis()));
    }

    public void writeUserChoice(Character answer, int currentQuestionIndex, boolean isMultiChoice) {
        if (isMultiChoice) writeMultiChoice(currentQuestionIndex);
        else writeSingleChoice(answer, currentQuestionIndex);
    }

    private void writeSingleChoice(Character answer, int currentQuestionIndex) {
        String format;
        var trackingQuestion = trackingQuestionsSupplier.get();

        var current = trackingQuestion.get(currentQuestionIndex);
        if (current == null || current.size() == 0) format = "Thí sinh đã chọn đáp án %c ở câu %d";
        else if (!current.contains(answer)) format = "Thí sinh đã chuyển sang đáp án %c ở câu %d";
        else return;

        String message = String.format(format, answer, currentQuestionIndex);
        log.writeLog(new Date(System.currentTimeMillis()), message);
        System.out.println(log.getLogContent());
    }

    private void writeMultiChoice(int currentQuestionIndex) {
        String message = "Thí sinh đã chọn các đáp án ";

        var trackingQuestion = trackingQuestionsSupplier.get();
        var current = trackingQuestion.get(currentQuestionIndex);

        int i = 0;
        for (Character character : current) {
            if (current.size() == 1) {
                message = "Thí sinh đã chọn đáp án " + character + " ";
                break;
            }

            if (i < current.size() - 1) message += character + ", ";
            else message += character + " ";
            i++;
        }

        message += "ở câu " + currentQuestionIndex;
        log.writeLog(new Date(System.currentTimeMillis()), message);
        System.out.println(log.getLogContent());
    }

    /**
     * Only use when question is multi answer
     */
    public void writeRemoveUserChoice(int currentQuestionIndex, Character removedAnswer) {
        var trackingQuestion = trackingQuestionsSupplier.get();
        var current = trackingQuestion.get(currentQuestionIndex);

        String format = "Thí sinh đã bỏ chọn câu %c ở câu %d. Câu trả lời của thí sinh còn lại: ";
        String message;
        
        if (current.size() == 0) {
            message = "Thí sinh đã bỏ chọn tất cả câu trả lời ở câu " + currentQuestionIndex;
            log.writeLog(new Date(System.currentTimeMillis()), message);
            System.out.println(log.getLogContent());
            return;
        }

        message = String.format(format, removedAnswer, currentQuestionIndex);

        int i = 0;
        for (Character character : current) {
            if (i < current.size() - 1) message += character + ", ";
            else message += String.valueOf(character);
            i++;
        }

        log.writeLog(new Date(System.currentTimeMillis()), message);
        System.out.println(log.getLogContent());
    }

    public void writeQuestionChoice(int selectedQuestionIndex, int currentQuestionIndex) {
        if (currentQuestionIndex == selectedQuestionIndex) return;
        String message = "Thí sinh đã chuyển sang câu " + selectedQuestionIndex;

        log.writeLog(new Date(System.currentTimeMillis()), message);
        System.out.println(log.getLogContent());
    }

    public void save() {
        logBUS.create(log);
    }
}
