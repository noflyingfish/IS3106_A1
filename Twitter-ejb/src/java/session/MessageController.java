package session;

import entity.Message;
import entity.Users;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class MessageController implements MessageControllerLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addMessage(Message m) {

        Users sender = m.getFromUser();
        List<Message> toList = sender.getMessageToList();
        toList.add(m);
        sender.setMessageToList(toList);

        Users receiver = m.getToUser();
        List<Message> fromList = receiver.getMessageFromList();
        fromList.add(m);
        sender.setMessageFromList(fromList);

        em.persist(m);
    }

    @Override
    public List<Message> getMessageBySender(Users u) {
        String jpql = "SELECT m FROM Message as m Where m.fromUser = :user";
        Query query = em.createQuery(jpql);
        query.setParameter("user", u);
        List<Message> messageList = query.getResultList();

        return messageList;
    }

    @Override
    public List<Message> getMessageByReceiver(Users u) {
        String jpql = "SELECT m FROM Message as m Where m.toUser = :user";
        Query query = em.createQuery(jpql);
        query.setParameter("user", u);
        List<Message> messageList = query.getResultList();

        return messageList;
    }

}
