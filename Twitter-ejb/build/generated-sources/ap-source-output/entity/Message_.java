package entity;

import entity.Users;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-18T01:13:08")
@StaticMetamodel(Message.class)
public class Message_ { 

    public static volatile SingularAttribute<Message, Users> toUser;
    public static volatile SingularAttribute<Message, Users> fromUser;
    public static volatile SingularAttribute<Message, Long> id;
    public static volatile SingularAttribute<Message, Date> createdOn;
    public static volatile SingularAttribute<Message, String> content;

}