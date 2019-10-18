package session;

import entity.Message;
import entity.Users;
import java.util.List;
import javax.ejb.Local;

@Local
public interface MessageControllerLocal {
    
    public void addMessage(Message m);
    public List<Message> getMessageBySender(Users u);
    public List<Message> getMessageByReceiver(Users u);
}
