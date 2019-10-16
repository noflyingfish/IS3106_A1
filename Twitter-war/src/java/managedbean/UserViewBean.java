package managedbean;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import entity.Users;
import exception.NoResultFoundException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;
import session.UserControllerLocal;

@ManagedBean
@ViewScoped
public class UserViewBean implements Serializable {

    @EJB
    UserControllerLocal userControllerLocal;

    private Long userId = -1L;
    private String dob = null;
    private String oldUsername = null;
    private String oldPassword = null;
    private String description = null;
    private String newUsername = null;
    private String newPassword = null;

    private UploadedFile pp;
    private byte[] file;

    UserSessionBean userSessionBean;
    private String searchString;
    private List<Users> userList;

    private Users selectedUser;

    private List<Users> followerList;
    private List<Users> followingList;

    public UserViewBean() {
    }

    @PostConstruct
    public void init() {
        followerList();
        followingList();
    }

    public String editProfile() throws ParseException, NoResultFoundException {
        FacesContext context = FacesContext.getCurrentInstance();
        userSessionBean = (UserSessionBean) context.getApplication().evaluateExpressionGet(context, "#{userSessionBean}", UserSessionBean.class);
        Users currUser = userSessionBean.getUser();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        // check old username/ password
        if (!oldUsername.equals(currUser.getUserName()) || !oldPassword.equals(currUser.getPassword())) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Old username/password incorrect"));
            return null;
        }
        if (pp != null) {
            String fileName = pp.getFileName();
            String contentType = pp.getContentType();
            file = pp.getContents();
            System.out.println(fileName + " profile_pic " + contentType);
        } else {
            System.out.println("image null");
        }

        if ("".equals(newUsername.trim())) {
            newUsername = currUser.getUserName();
        }
        if ("".equals(newPassword.trim())) {
            newPassword = currUser.getPassword();
        }

        currUser.setUserName(newUsername.trim());
        currUser.setPassword(newPassword.trim());
        currUser.setProfilePic(file);
        currUser.setDescription(userSessionBean.getDescription().trim());
        currUser.setBirthday(df.parse(userSessionBean.getDob()));
        if (file != null) {
            currUser.setProfilePic(file);
        }

        userControllerLocal.updateUsers(currUser);
        userSessionBean.setPassword(newPassword);
        userSessionBean.setUsername(newUsername);

        return null;
    }

    public boolean checkPhoto() {
        FacesContext context = FacesContext.getCurrentInstance();
        userSessionBean = (UserSessionBean) context.getApplication().evaluateExpressionGet(context, "#{userSessionBean}", UserSessionBean.class);
        Users currUser = userSessionBean.getUser();
        if (currUser.getProfilePic() == null) {
            return false;
        } else {
            return true;
        }
    }

    public String displayImage() {
        FacesContext context = FacesContext.getCurrentInstance();
        userSessionBean = (UserSessionBean) context.getApplication().evaluateExpressionGet(context, "#{userSessionBean}", UserSessionBean.class);
        Users currUser = userSessionBean.getUser();
        byte[] photo = userControllerLocal.getImageByUser(currUser);
        String a = Base64.encode(photo);
        return a;
    }

    public boolean checkPhotoSelectedUser() {
        if (selectedUser.getProfilePic() == null) {
            return false;
        } else {
            return true;
        }
    }

    public String displayImageSelectedUser() {
        byte[] photo = userControllerLocal.getImageByUser(selectedUser);
        String a = Base64.encode(photo);
        return a;
    }

    public List<Users> searchUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        userSessionBean = (UserSessionBean) context.getApplication().evaluateExpressionGet(context, "#{userSessionBean}", UserSessionBean.class);
        userList = userControllerLocal.searchUsers(searchString);
        userList.remove(userSessionBean.getUser());
        for (Users u : userList) {
            System.out.println(u.getUserName());
        }
        return null;
    }

    public String followSelectedUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        userSessionBean = (UserSessionBean) context.getApplication().evaluateExpressionGet(context, "#{userSessionBean}", UserSessionBean.class);
        Users currUser = userSessionBean.getUser();
        try {
            userControllerLocal.followUsers(currUser, selectedUser);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to follow user"));
        }
        return null;
    }

    public void followerList() {
        FacesContext context = FacesContext.getCurrentInstance();
        userSessionBean = (UserSessionBean) context.getApplication().evaluateExpressionGet(context, "#{userSessionBean}", UserSessionBean.class);
        Users u = userSessionBean.getUser();
        followerList = userControllerLocal.getFollowerList(u);
    }

    public void followingList() {
        FacesContext context = FacesContext.getCurrentInstance();
        userSessionBean = (UserSessionBean) context.getApplication().evaluateExpressionGet(context, "#{userSessionBean}", UserSessionBean.class);
        Users u = userSessionBean.getUser();
        followingList = userControllerLocal.getFollowingList(u);
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public List<Users> getUserList() {
        return userList;
    }

    public void setUserList(List<Users> userList) {
        this.userList = userList;
    }

    public Users getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Users selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<Users> getFollowerList() {
        return followerList;
    }

    public void setFollowerList(List<Users> followerList) {
        this.followerList = followerList;
    }

    public List<Users> getFollowingList() {
        return followingList;
    }

    public void setFollowingList(List<Users> followingList) {
        this.followingList = followingList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getOldUsername() {
        return oldUsername;
    }

    public void setOldUsername(String oldUsername) {
        this.oldUsername = oldUsername;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public UploadedFile getPp() {
        return pp;
    }

    public void setPp(UploadedFile pp) {
        this.pp = pp;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

}
