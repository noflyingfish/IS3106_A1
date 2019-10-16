package entity;

import entity.Users;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-16T21:24:25")
@StaticMetamodel(Tweet.class)
public class Tweet_ { 

    public static volatile SingularAttribute<Tweet, byte[]> image;
    public static volatile ListAttribute<Tweet, Users> likedByUserList;
    public static volatile SingularAttribute<Tweet, String> retweetFrom;
    public static volatile SingularAttribute<Tweet, Long> id;
    public static volatile SingularAttribute<Tweet, Date> createdOn;
    public static volatile SingularAttribute<Tweet, Users> ownedBy;
    public static volatile SingularAttribute<Tweet, String> content;

}