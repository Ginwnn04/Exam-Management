package DAO;

import DTO.ExamDTO;
import DTO.QuestionDTO;
import Helper.ConnectDB;

import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExamDAO implements BaseDAO<ExamDTO, Integer> {
    @Override
    public ArrayList<ExamDTO> getAll(boolean active) {
        int isGet = active ? 1 : 0;
        ArrayList<ExamDTO> list_exams = new ArrayList<ExamDTO>();
        String query = "SELECT * FROM exams WHERE status = " + isGet;
        try (PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ExamDTO exam = ExamDTO.builder()
                    .setTestCode(resultSet.getString("testCode"))
                    .setExOrder(resultSet.getString("exOrder"))
                    .setExCode(resultSet.getString("exCode"))
                    .setStatus(resultSet.getBoolean("status"))
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
                    .setStatus(resultSet.getBoolean("status"))
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
    public ExamDTO create(ExamDTO examDTO) {
        String query = "INSERT INTO exams(testCode, exOrder, exCode, status) VALUES(?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, examDTO.getTestCode());
            preparedStatement.setString(2, examDTO.getExOrder());
            preparedStatement.setString(3, examDTO.getExCode());
            preparedStatement.setBoolean(4, examDTO.getStatus());
            if (preparedStatement.executeUpdate() <= 0) return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        System.out.println(query);
        
        var questions = examDTO.getQuestions();
        createMultipleExamQuestion(examDTO.getExCode(), questions);

        return examDTO;
    }

    public boolean createMultipleExam(List<ExamDTO> requests) {
        String query = "INSERT INTO exams(testCode, exOrder, exCode, status) VALUES ";
        boolean result = false;

        for (int i = 0; i < requests.size(); i++) {
            String value;
            var model = requests.get(i);

            if (i < requests.size() - 1) value = ("('%s', '%s', '%s', %d), ");
            else value = ("('%s', '%s', '%s', %d);");

            query += String.format(value, 
                                   model.getTestCode(), 
                                   model.getExOrder(), 
                                   model.getExCode(), 
                                   model.getStatus() ? 1 : 0);
        }

        System.out.println(query);

        try (PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            result = preparedStatement.executeUpdate() > 0;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (!result) return false;

        for (ExamDTO model : requests) {
            result = createMultipleExamQuestion(model.getExCode(), model.getQuestions());
        }

        return result;
    }

    private boolean createMultipleExamQuestion(String exCode, List<QuestionDTO> questions) {
        String query = "INSERT INTO exam_question (question_id, exCode, status) VALUES ";

        for (int i = 0; i < questions.size(); i++) {
            String value;

            if (i < questions.size() - 1) value = "(%d, '%s', %d), ";
            else value = "(%d, '%s', %d);";

            var question = questions.get(i);
            query += String.format(value, question.getId(), exCode, 1);
        }

        System.out.println(query);

        try (PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            return preparedStatement.executeUpdate() > 0;
        } 
        catch (Exception e) {
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

    public boolean delete(String testCode) {
        String query = "UPDATE exams SET status = 0 WHERE testCode = " + testCode;
        boolean result = false;

        try (PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            result = preparedStatement.executeUpdate() > 0;
            result = deleteExamQuestion(testCode);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }

    public boolean deleteExamQuestion(String testCode) {
        String query = "UPDATE exam_question SET status = 0 WHERE INSTR(exCode, ?) > 0";

        try (PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, testCode);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ExamDTO findByExCode(String exCode) {
        String query = "SELECT * FROM exams WHERE exCode = ?";
        try (PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, exCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ExamDTO exam = ExamDTO.builder()
                    .setTestCode(resultSet.getString("testCode"))
                    .setExOrder(resultSet.getString("exOrder"))
                    .setExCode(resultSet.getString("exCode"))
                    .setStatus(resultSet.getBoolean("status"))
                    .build();
                return exam;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ExamDTO> findByTestCode(String testCode) {
        String query = "SELECT * FROM exams WHERE testCode = " + testCode;
        ArrayList<ExamDTO> result = new ArrayList<>();

       try {
            PreparedStatement ps = ConnectDB.getInstance().getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                ExamDTO exam = ExamDTO.builder()
                                      .setTestCode(rs.getString("testCode"))
                                      .setExOrder(rs.getString("exOrder"))
                                      .setExCode(rs.getString("exCode"))
                                      .setStatus(rs.getBoolean("status"))
                                      .build();

                result.add(exam);
            }

            return result;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}