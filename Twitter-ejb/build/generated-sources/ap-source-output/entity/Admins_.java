package entity;

import entity.Tweet;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-18T01:13:08")
@StaticMetamodel(Admins.class)
public class Admins_ { 

    public static volatile SingularAttribute<Admins, String> password;
    public static volatile ListAttribute<Admins, Tweet> reportList;
    public static volatile SingularAttribute<Admins, Long> id;
    public static volatile SingularAttribute<Admins, String> userName;

}