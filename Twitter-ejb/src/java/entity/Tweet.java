package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
        
@Entity
public class Tweet implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    private String content;
    @Lob
    private byte[] image;
    
    private String retweetFrom; //username
    
    @ManyToOne
    private Users ownedBy;
    @ManyToMany
    private List<Users> likedByUserList;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Users getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(Users ownedBy) {
        this.ownedBy = ownedBy;
    }

    public List<Users> getLikedByUserList() {
        return likedByUserList;
    }

    public void setLikedByUserList(List<Users> likedByUserList) {
        this.likedByUserList = likedByUserList;
    }

    public String getRetweetFrom() {
        return retweetFrom;
    }

    public void setRetweetFrom(String retweetFrom) {
        this.retweetFrom = retweetFrom;
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
        if (!(object instanceof Tweet)) {
            return false;
        }
        Tweet other = (Tweet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Tweet[ id=" + id + " ]";
    }
    
}
