
package BUS;
import DAO.TopicDAO;
import DTO.TopicDTO;


import java.util.ArrayList;
import java.util.List;

public class TopicBUS {
    private TopicDAO topicDAO = new TopicDAO();
      

    public List<TopicDTO> getAllTopic() {
        return topicDAO.getAll(true);
    }


    public TopicDTO addTopic(TopicDTO topic) {
        return topicDAO.create(topic);
    }

     public boolean updateTopic(TopicDTO topic,Integer id) {
        return topicDAO.update(id,topic);
    }
   
    public boolean deleteTopic(Integer id) {
        return topicDAO.delete(id);
    }
   
    
}
    

