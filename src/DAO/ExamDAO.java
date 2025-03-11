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
    public ExamDTO create(ExamDTO examDTO) {
        String query = "INSERT INTO exams(testCode, exOrder, exCode) VALUES(?, ?, ?)";

        try (PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, examDTO.getTestCode());
            preparedStatement.setString(2, examDTO.getExOrder());
            preparedStatement.setString(3, examDTO.getExCode());
            if (preparedStatement.executeUpdate() <= 0) return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        var questions = examDTO.getQuestions();
        createMultipleExamQuestion(examDTO.getExCode(), questions);

        return examDTO;
    }

    private boolean createMultipleExamQuestion(String exCode, List<QuestionDTO> questions) {
        String query = "INSERT INTO exam_question (question_id, exCode) VALUES ";

        for (int i = 0; i < questions.size(); i++) {
            String value;

            if (i < questions.size() - 1) value = "(%d, '%s'), ";
            else value = "(%d, '%s');";

            var question = questions.get(i);
            query += String.format(value, question.getId(), exCode);
        }

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
    
    public ExamDTO randomExamByTestCode(String testCode) {
        String query = "SELECT * FROM exams WHERE testCode = ? ORDER BY RAND() LIMIT 1";
        ExamDTO exam = null;
       try {
            PreparedStatement ps = ConnectDB.getInstance().getConnection().prepareStatement(query);
            ps.setString(1, testCode);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                exam = ExamDTO.builder()
                                      .setTestCode(rs.getString("testCode"))
                                      .setExOrder(rs.getString("exOrder"))
                                      .setExCode(rs.getString("exCode"))
                                      .build();
        
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return exam;

    }
}