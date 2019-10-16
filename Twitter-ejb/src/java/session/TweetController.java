package session;

import entity.Tweet;
import entity.Users;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class TweetController implements TweetControllerLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createTweet(Users u, Tweet t) {

        List<Tweet> ownTweetList = u.getOwnedTweetList();
        ownTweetList.add(t);
        u.setOwnedTweetList(ownTweetList);
        //em.merge(u);
        em.persist(t);
    }

    @Override
    public void removeTweet(Users u1, Tweet t1) {
        Users u = em.find(Users.class,u1.getId());
        Tweet t = em.find(Tweet.class,t1.getId());
        
        if (em.contains(u)) System.out.println("u conains");
        if (em.contains(t)) System.out.println("t conains");
      
        List<Tweet> ownTweetList = searchUserTweetList(u);
        ownTweetList.remove(t);
        u.setOwnedTweetList(ownTweetList);
        em.merge(u);

        List<Users> userLikeList = t.getLikedByUserList();
        for (Users user : userLikeList) {
            user.getUserLikedList().remove(t);
            em.merge(user);
        }
        em.remove(t);
    }

    @Override
    public void likeTweet(Users u1, Tweet t1) {
        Users u = em.find(Users.class,u1.getId());
        Tweet t = em.find(Tweet.class,t1.getId());
        
        List<Users> likeList = t.getLikedByUserList();
        likeList.add(u);
        t.setLikedByUserList(likeList);
        em.merge(t);

        List<Tweet> userLikeList = u.getUserLikedList();
        userLikeList.add(t);
        u.setUserLikedList(userLikeList);
        em.merge(u);
    }

    @Override
    public void unlikeTweet(Users u1, Tweet t1) {
        Users u = em.find(Users.class,u1.getId());
        Tweet t = em.find(Tweet.class,t1.getId());
        
        List<Users> likeList = t.getLikedByUserList();
        likeList.remove(u);
        t.setLikedByUserList(likeList);
        em.merge(t);

        List<Tweet> userLikeList = u.getUserLikedList();
        userLikeList.remove(t);
        u.setUserLikedList(userLikeList);
        em.merge(u);
    }

    @Override
    public List<Tweet> searchUserTweetList(Users u) {
        String jpql = "SELECT t FROM Tweet as t Where t.ownedBy = :user";
        Query query = em.createQuery(jpql);
        query.setParameter("user", u);
        List<Tweet> tweetList = query.getResultList();

        return tweetList;
    }

    @Override
    public List<Tweet> searchAllTweet(String s) {
        String jpql = "SELECT t FROM Tweet as t Where t.content LIKE :content";
        Query query = em.createQuery(jpql);
        query.setParameter("content", "%" + s + "%");
        List<Tweet> tweetList = query.getResultList();

        return tweetList;
    }
    
    @Override
    public byte[] getImageByTweet(Tweet t1){
        String jpql = "SELECT t.image FROM Tweet as t Where t.id =:id";
        Query query = em.createQuery(jpql);
        query.setParameter("id", t1.getId());    
        byte[] img = (byte[]) query.getSingleResult();
        
        return img;
    }

}
