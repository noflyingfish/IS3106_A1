package managedbean;

import entity.Message;
import entity.Users;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import session.MessageControllerLocal;

@ManagedBean
@ViewScoped
public class MessageViewBean implements Serializable {

    @EJB
    MessageControllerLocal messageControllerLocal;

    UserSessionBean userSessionBean;

    private List<Users> userMessageList;
    private Message m;
    private String content;

    public MessageViewBean() {
    }

    public void sendMessage(Users to) {
        FacesContext context = FacesContext.getCurrentInstance();
        userSessionBean = (UserSessionBean) context.getApplication().evaluateExpressionGet(context, "#{userSessionBean}", UserSessionBean.class);

        System.out.println(content + "QWEWQE");
        Users from = userSessionBean.getUser();
        Message msg = new Message();
        msg.setFromUser(from);
        msg.setToUser(to);
        msg.setCreatedOn(new Date());
        msg.setContent(content);

        messageControllerLocal.addMessage(msg);
    }

    public List<Message> getReceiveList() {
        FacesContext context = FacesContext.getCurrentInstance();
        userSessionBean = (UserSessionBean) context.getApplication().evaluateExpressionGet(context, "#{userSessionBean}", UserSessionBean.class);
        Users curr = userSessionBean.getUser();
        List<Message> a = messageControllerLocal.getMessageByReceiver(curr);
        List<Message> b = messageControllerLocal.getMessageBySender(curr);
        System.out.println(a.size());
        return a;
    }
    
     public List<Message> getSendList() {
        FacesContext context = FacesContext.getCurrentInstance();
        userSessionBean = (UserSessionBean) context.getApplication().evaluateExpressionGet(context, "#{userSessionBean}", UserSessionBean.class);
        Users curr = userSessionBean.getUser();
        List<Message> b = messageControllerLocal.getMessageBySender(curr);
        return b;
    }

    public List<Users> getUserMessageList() {
        return userMessageList;
    }

    public void setUserMessageList(List<Users> userMessageList) {
        this.userMessageList = userMessageList;
    }

    public Message getMessage() {
        return m;
    }

    public void setMessage(Message m) {
        this.m = m;
    }

    public Message getM() {
        return m;
    }

    public void setM(Message m) {
        this.m = m;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
