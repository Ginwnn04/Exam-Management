package DAO;

import DTO.ExamDTO;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExamDAO implements BaseDAO<ExamDTO, Integer> {
    @Override
    public ArrayList<ExamDTO> getAll(boolean active) {
        ArrayList<ExamDTO> list_exams = new ArrayList<ExamDTO>();
        String query = "SELECT * FROM exams";
        try (PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ExamDTO exam = ExamDTO.builder()
                    .setTestCode(resultSet.getString("testCode"))
                    .setExOrder(resultSet.getString("exOrder"))
                    .setExCode(resultSet.getString("exCode"))
                    .build();
                list_exams.add(exam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_exams;
    }

    public ExamDTO findByID(Integer id) {
        String query = "SELECT * FROM exams WHERE id = ?";
        try (PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ExamDTO exam = ExamDTO.builder()
                    .setTestCode(resultSet.getString("testCode"))
                    .setExOrder(resultSet.getString("exOrder"))
                    .setExCode(resultSet.getString("exCode"))
                    .build();
                return exam;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isExist(String testCode) {
        String query = "SELECT * FROM exams WHERE testCode = ?";
        try (PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, testCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean create(ExamDTO examDTO) {
        String query = "INSERT INTO exams(testCode, exOrder, exCode) VALUES(?, ?, ?)";

        try (PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, examDTO.getTestCode());
            preparedStatement.setString(2, examDTO.getExOrder());
            preparedStatement.setString(3, examDTO.getExCode());
            if (preparedStatement.executeUpdate() <= 0) return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
        var questions = examDTO.getQuestions();

        for (var question : questions) {
            if (!createExamQuestion(question.getId(), examDTO)) return false;
        }

        return true;
    }

    private boolean createExamQuestion(int questionId, ExamDTO examDTO) {
        String query = "INSERT INTO exam_question(question_id, exCode) VALUES(?, ?)";

        try (PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, questionId);
            preparedStatement.setString(2, examDTO.getExCode());
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Integer id, ExamDTO examDTO) {
        String query = "UPDATE exams SET testCode = ?, exOrder = ?, exCode = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, examDTO.getTestCode());
            preparedStatement.setString(2, examDTO.getExOrder());
            preparedStatement.setString(3, examDTO.getExCode());
            preparedStatement.setInt(4, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        String query = "DELETE FROM exams WHERE id = ?";
        try (PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}