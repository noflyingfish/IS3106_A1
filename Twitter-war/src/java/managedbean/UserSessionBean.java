package managedbean;

import entity.Users;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import session.UserControllerLocal;

@ManagedBean(name = "userSessionBean")
@SessionScoped
public class UserSessionBean implements Serializable {

    private Users u = null;
    private String username = null;
    private String password = null;
    private Long userId = -1L;
    private String dob = null;
    private String description = null;

    @EJB
    UserControllerLocal userControllerLocal;

    public UserSessionBean() {
    }

    public String createUsers() throws ParseException {
        FacesContext context = FacesContext.getCurrentInstance();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Users u = new Users();
        u.setUserName(username);
        u.setPassword(password);
        u.setBirthday(df.parse(dob)); //exception restriction on frontend
        u.setCreatedOn(new Date());

        try {
            userControllerLocal.createUsers(u);
            this.u = u;
            username = u.getUserName();
            password = u.getPassword();
            userId = u.getId();
            dob = df.format(u.getBirthday());
            return "/site/home.xhtml?faces-redirect=true";
        } catch (Exception e) {
            username = null;
            password = null;
            userId = -1L;
            dob = null;
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable create user"));
            return "signup.xhtml";
        }
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        try {
            this.u = userControllerLocal.loginUsers(username, password);
            userId = u.getId();
            password = u.getPassword();
            dob = df.format(u.getBirthday());
            description = u.getDescription();
            return "/site/home.xhtml?faces-redirect=true";
        } catch (Exception e) {
            username = null;
            password = null;
            userId = -1L;
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Incorrect username and password"));
            return "index.xhtml";
        }
    }

    public String logout() {
        username = null;
        password = null;
        userId = -1L;
        return "/index.xhtml?faces-redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Users getUser() {
        return u;
    }

    public void setUser(Users u) {
        this.u = u;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
