package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Users implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String userName;
    private String password;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Lob
    private byte[] profilePic;
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;
    private String description;
    
    @OneToMany(mappedBy = "ownedBy")
    private List<Tweet> ownedTweetList;
    @ManyToMany(mappedBy = "likedByUserList")
    private List<Tweet> userLikedList;
    
    @OneToMany(mappedBy = "fromUser")
    private List<Message> messageToList;
    @OneToMany(mappedBy = "toUser")
    private List<Message> messageFromList;
    
    @ManyToOne
    private Users userFollowing;
    @ManyToOne
    private Users userFollower;
    
    @OneToMany(mappedBy = "userFollower")
    private List<Users> followList;
    @OneToMany(mappedBy = "userFollowing")
    private List<Users> followerList;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Tweet> getOwnedTweetList() {
        return ownedTweetList;
    }

    public void setOwnedTweetList(List<Tweet> ownedTweetList) {
        this.ownedTweetList = ownedTweetList;
    }

    public List<Message> getMessageToList() {
        return messageToList;
    }

    public void setMessageToList(List<Message> messageToList) {
        this.messageToList = messageToList;
    }

    public List<Message> getMessageFromList() {
        return messageFromList;
    }

    public void setMessageFromList(List<Message> messageFromList) {
        this.messageFromList = messageFromList;
    }

    public Users getUserFollowing() {
        return userFollowing;
    }

    public void setUserFollowing(Users userFollowing) {
        this.userFollowing = userFollowing;
    }

    public Users getUserFollower() {
        return userFollower;
    }

    public void setUserFollower(Users userFollower) {
        this.userFollower = userFollower;
    }

    public List<Users> getFollowList() {
        return followList;
    }

    public void setFollowList(List<Users> followList) {
        this.followList = followList;
    }

    public List<Users> getFollowerList() {
        return followerList;
    }

    public void setFollowerList(List<Users> followerList) {
        this.followerList = followerList;
    }

    public List<Tweet> getUserLikedList() {
        return userLikedList;
    }

    public void setUserLikedList(List<Tweet> userLikedList) {
        this.userLikedList = userLikedList;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Users[ id=" + id + " ]";
    }
    
}
