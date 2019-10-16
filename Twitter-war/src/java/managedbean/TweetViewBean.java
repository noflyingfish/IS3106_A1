package managedbean;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import entity.Tweet;
import entity.Users;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;
import session.TweetControllerLocal;
import session.UserControllerLocal;

@ManagedBean
@ViewScoped
public class TweetViewBean implements Serializable {

    @EJB
    TweetControllerLocal tweetControllerLocal;
    @EJB
    UserControllerLocal userControllerLocal;

    UserSessionBean userSessionBean;

    private String content;
    private Users u;
    private Tweet t;
    private String searchString;
    private List<Tweet> tweetList;
    private Users selectedUser;
    private String searchType;

    private UploadedFile image;
    private byte[] file;

    private List<Tweet> wallList;
    private List<Tweet> ownList;
    private List<Tweet> likedList;

    @PostConstruct
    public void init() {
        getOwnTweetList();
        getLikedTweetList();
        getWallTweetList();
    }

    public String addTweet() {
        FacesContext context = FacesContext.getCurrentInstance();
        userSessionBean = (UserSessionBean) context.getApplication().evaluateExpressionGet(context, "#{userSessionBean}", UserSessionBean.class);

        if (image != null) {
            String fileName = image.getFileName();
            String contentType = image.getContentType();
            file = image.getContents();
            System.out.println(fileName + " asdas " + contentType);
        }

        this.u = userSessionBean.getUser();
        Tweet t = new Tweet();
        try {
            t.setOwnedBy(u);
            t.setContent(content);
            t.setCreatedOn(new Date());
            if (file != null) {
                t.setImage(file);
            }
            tweetControllerLocal.createTweet(u, t);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Incorrect username and password"));
        }
        return "/site/home.xhtml?faces-redirect=true";
    }

    public String searchTweet() {
        FacesContext context = FacesContext.getCurrentInstance();
        userSessionBean = (UserSessionBean) context.getApplication().evaluateExpressionGet(context, "#{userSessionBean}", UserSessionBean.class);
        tweetList = tweetControllerLocal.searchAllTweet(searchString);
        return null;
    }

    public boolean checkPhoto(Tweet t) {
        if (t == null) {
            System.out.println("NULL T");
        }
        if (t.getImage() == null) {
            return false;
        } else {
            return true;
        }
    }

    public String displayImage(Tweet t) {
        byte[] photo = tweetControllerLocal.getImageByTweet(t);
        String a = Base64.encode(photo);
        return a;
    }

    public boolean checkLikeTweet(Tweet t) {
        FacesContext context = FacesContext.getCurrentInstance();
        userSessionBean = (UserSessionBean) context.getApplication().evaluateExpressionGet(context, "#{userSessionBean}", UserSessionBean.class);
        u = userSessionBean.getUser();
        List<Tweet> lst = userControllerLocal.searchUsersById(u.getId()).getUserLikedList();

        if (lst.contains(t)) {
            System.out.println("yes");
        }

        return lst.contains(t);
    }

    public boolean checkUnlikeTweet(Tweet t) {
        FacesContext context = FacesContext.getCurrentInstance();
        userSessionBean = (UserSessionBean) context.getApplication().evaluateExpressionGet(context, "#{userSessionBean}", UserSessionBean.class);
        u = userSessionBean.getUser();
        List<Tweet> lst = userControllerLocal.searchUsersById(u.getId()).getUserLikedList();
        return !lst.contains(t);
    }

    public String unlikeTweet(Tweet t) {
        FacesContext context = FacesContext.getCurrentInstance();
        userSessionBean = (UserSessionBean) context.getApplication().evaluateExpressionGet(context, "#{userSessionBean}", UserSessionBean.class);
        u = userSessionBean.getUser();
        try {
            tweetControllerLocal.unlikeTweet(u, t);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error Unlikeing tweet"));
        }
        init();
        return null;
    }

    public String likeTweet(Tweet t) {
        System.out.println("t.id: " + t.getId());
        FacesContext context = FacesContext.getCurrentInstance();
        userSessionBean = (UserSessionBean) context.getApplication().evaluateExpressionGet(context, "#{userSessionBean}", UserSessionBean.class);
        u = userSessionBean.getUser();
        try {
            tweetControllerLocal.likeTweet(u, t);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error likeing tweet"));
        }
        init();
        return "/site/home.xhtml?faces-redirect=true";
    }

    public boolean checkRT(Tweet t) {
        if (t == null) {
            System.out.println("NULL T, checkRT");
        }
        if (t.getRetweetFrom() == null) {
            return false;
        } else {
            return true;
        }
    }

    public String retweetTweet(Tweet t) {
        FacesContext context = FacesContext.getCurrentInstance();
        userSessionBean = (UserSessionBean) context.getApplication().evaluateExpressionGet(context, "#{userSessionBean}", UserSessionBean.class);
        u = userSessionBean.getUser();

        Tweet rt = new Tweet();
        
        rt.setOwnedBy(u);
        rt.setContent(t.getContent());
        rt.setCreatedOn(new Date());
        rt.setRetweetFrom(t.getOwnedBy().getUserName());
        rt.setImage(t.getImage());

        try {
            System.out.println("YYY");
            tweetControllerLocal.createTweet(u, rt);
            System.out.println("XXX");
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Retweet Error"));
        }
        init();
        return null;
    }

    public void getOwnTweetList() {
        FacesContext context = FacesContext.getCurrentInstance();
        userSessionBean = (UserSessionBean) context.getApplication().evaluateExpressionGet(context, "#{userSessionBean}", UserSessionBean.class);
        u = userSessionBean.getUser();
        ownList = tweetControllerLocal.searchUserTweetList(u);
    }

    public void getLikedTweetList() {
        FacesContext context = FacesContext.getCurrentInstance();
        userSessionBean = (UserSessionBean) context.getApplication().evaluateExpressionGet(context, "#{userSessionBean}", UserSessionBean.class);
        Users u1 = userSessionBean.getUser();
        Users u = userControllerLocal.searchUsersById(u1.getId());
        likedList = u.getUserLikedList();
    }

    public void getWallTweetList() {
        FacesContext context = FacesContext.getCurrentInstance();
        userSessionBean = (UserSessionBean) context.getApplication().evaluateExpressionGet(context, "#{userSessionBean}", UserSessionBean.class);
        Users u = userSessionBean.getUser();
        wallList = tweetControllerLocal.searchUserTweetList(u);
    }

    public String deleteTweet(Tweet t) {
        FacesContext context = FacesContext.getCurrentInstance();
        userSessionBean = (UserSessionBean) context.getApplication().evaluateExpressionGet(context, "#{userSessionBean}", UserSessionBean.class);
        u = userSessionBean.getUser();
        tweetControllerLocal.removeTweet(u, t);
        init();
        return null;
    }

    public void viewUser(Tweet t) {
        u = t.getOwnedBy();
        selectedUser = userControllerLocal.searchUsersById(u.getId());
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Users getUser() {
        return u;
    }

    public void setUser(Users u) {
        this.u = u;
    }

    public UserSessionBean getUserSessionBean() {
        return userSessionBean;
    }

    public void setUserSessionBean(UserSessionBean usb) {
        this.userSessionBean = userSessionBean;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public List<Tweet> getTweetList() {
        return tweetList;
    }

    public void setTweetList(List<Tweet> tweetList) {
        this.tweetList = tweetList;
    }

    public Users getU() {
        return u;
    }

    public void setU(Users u) {
        this.u = u;
    }

    public Tweet getT() {
        return t;
    }

    public void setT(Tweet t) {
        this.t = t;
    }

    public List<Tweet> getWallList() {
        return wallList;
    }

    public void setWallList(List<Tweet> wallList) {
        this.wallList = wallList;
    }

    public List<Tweet> getOwnList() {
        return ownList;
    }

    public void setOwnList(List<Tweet> ownList) {
        this.ownList = ownList;
    }

    public List<Tweet> getLikedList() {
        return likedList;
    }

    public void setLikedList(List<Tweet> likedList) {
        this.likedList = likedList;
    }

    public Users getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Users selectedUser) {
        this.selectedUser = selectedUser;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public UploadedFile getImage() {
        return image;
    }

    public void setImage(UploadedFile image) {;
        this.image = image;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
    
    
    
}
