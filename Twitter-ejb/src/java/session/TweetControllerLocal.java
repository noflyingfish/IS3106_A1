package session;

import entity.Tweet;
import entity.Users;
import java.util.List;
import javax.ejb.Local;

@Local
public interface TweetControllerLocal {
    
    public void createTweet(Users u, Tweet t);
    public void removeTweet(Users u, Tweet t);
    public void likeTweet(Users u, Tweet t);
    public void unlikeTweet(Users u, Tweet t);
    public List<Tweet> searchUserTweetList(Users u);
    public List<Tweet> searchAllTweet(String s);
    public List<Tweet> searchFollowingTweet(Users u);
    public byte[] getImageByTweet(Tweet t);
}
