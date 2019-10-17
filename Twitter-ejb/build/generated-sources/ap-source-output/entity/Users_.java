package entity;

import entity.Message;
import entity.Tweet;
import entity.Users;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-18T01:13:08")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, Date> birthday;
    public static volatile ListAttribute<Users, Tweet> ownedTweetList;
    public static volatile ListAttribute<Users, Tweet> userLikedList;
    public static volatile ListAttribute<Users, Users> followerList;
    public static volatile ListAttribute<Users, Users> followList;
    public static volatile SingularAttribute<Users, byte[]> profilePic;
    public static volatile SingularAttribute<Users, String> description;
    public static volatile SingularAttribute<Users, String> userName;
    public static volatile SingularAttribute<Users, Date> createdOn;
    public static volatile ListAttribute<Users, Message> messageToList;
    public static volatile SingularAttribute<Users, Users> userFollowing;
    public static volatile SingularAttribute<Users, String> password;
    public static volatile SingularAttribute<Users, Long> id;
    public static volatile SingularAttribute<Users, Users> userFollower;
    public static volatile ListAttribute<Users, Message> messageFromList;

}