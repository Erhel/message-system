package com.epam.vitebsk.model.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.epam.vitebsk.model.dao.Mapper;
import com.epam.vitebsk.model.dao.MessageDao;
import com.epam.vitebsk.model.entity.Message;
import com.epam.vitebsk.model.entity.User;

public class MessageJdbcDao extends JdbcDaoSupport implements MessageDao {

    private static final String SQL_RESOURCE = "/sql.properties";
    private static final String INSERT_MESSAGE = "message.insert";
    private static final String UPDATE_MESSAGE = "message.update";
    private static final String DELETE_MESSAGE = "message.delete";
    private static final String SELECT_MESSAGE_BY_ID = "message.selectById";
    private static final String SELECT_MESSAGES_BY_SENDER_ID = "message.selectListBySenderId";
    private static final String SELECT_MESSAGES_BY_RECIPIENT_ID = "message.selectListByRecipientId";

    private Mapper<Message> mapper = new Mapper<Message>() {
        @Override
        public Message map(ResultSet resultSet) {
            try {
                Long id = resultSet.getLong(1);
                String subject = resultSet.getString(2);
                String messageText = resultSet.getString(3);
                Long senderId = resultSet.getLong(4);
                Long recipientId = resultSet.getLong(5);
                Message message = new Message(id, subject, messageText, new User(senderId, null, null, null),
                        new User(recipientId, null, null, null));
                return message;
            } catch (SQLException e) {
                // TODO: exception
                throw new RuntimeException(e);
            }
        }
    };

    public MessageJdbcDao() {
        try {
            init(SQL_RESOURCE);
        } catch (SQLException e) {

            // TODO: exception

            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(Message entity) {
        update(getSql(INSERT_MESSAGE), entity.getSubject(), entity.getMessage(), entity.getSender().getId(),
                entity.getRecipient().getId());
    }

    @Override
    public Message read(Long id) {
        return selectOne(getSql(SELECT_MESSAGE_BY_ID), mapper, id);
    }

    @Override
    public void update(Message entity) {
        update(getSql(UPDATE_MESSAGE), entity.getSubject(), entity.getMessage(), entity.getSender().getId(),
                entity.getRecipient().getId(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        update(getSql(DELETE_MESSAGE), id);
    }

    @Override
    public List<Message> readBySenderId(Long id) {
        return selectList(getSql(SELECT_MESSAGES_BY_SENDER_ID), mapper, id);
    }

    @Override
    public List<Message> readByRecipientId(Long id) {
        return selectList(getSql(SELECT_MESSAGES_BY_RECIPIENT_ID), mapper, id);
    }
}
