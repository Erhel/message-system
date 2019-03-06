package com.epam.vitebsk.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.epam.vitebsk.dao.Mapper;
import com.epam.vitebsk.dao.MessageDao;
import com.epam.vitebsk.entity.Message;
import com.epam.vitebsk.entity.User;

public class MessageJdbcDao extends JdbcDaoSupport implements MessageDao {

    private Mapper<Message> mapper = new Mapper<Message>() {
        @Override
        public Message map(ResultSet rs) {
           try {
                Long id = rs.getLong(1);
                String subject = rs.getString(2);
                String message = rs.getString(3);
                Long senderId = rs.getLong(4);
                Long recipientId = rs.getLong(5);
                Message msg = new Message(id, subject, message, new User(senderId, null, null, null), new User(recipientId, null, null, null));
                return msg;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    };
    
    public MessageJdbcDao() {
        init("/sql.properties");
    }
    
    @Override
    public void create(Message entity) {
        update(getSql("message.insert"), entity.getSubject(), entity.getMessage(), entity.getSender().getId(), entity.getRecipient().getId());        
    }

    @Override
    public Message read(Long id) {
        return selectOne(getSql("message.selectById"), mapper, id);
    }

    @Override
    public void update(Message entity) {
        update(getSql("message.update"), entity.getSubject(), entity.getMessage(), entity.getSender().getId(), entity.getRecipient().getId(), entity.getId());        
    }

    @Override
    public void delete(Long id) {
        update(getSql("message.delete"), id);        
    }

    @Override
    public List<Message> readBySenderId(Long id) {
        return selectList(getSql("message.selectListBySenderId"), mapper, id);
    }
}
