package session;

import entity.Users;
import exception.NoResultFoundException;
import exception.UsernameAlreadyExistException;
import java.util.List;
import javax.ejb.Local;

@Local
public interface UserControllerLocal {
    
    public Users createUsers (Users u) throws UsernameAlreadyExistException;
    public Users loginUsers(String username, String password) throws NoResultFoundException;
    public void updateUsers(Users u) throws NoResultFoundException;
    public void followUsers(Users u, Users follow);
    public void unfollowUsers(Users u, Users follow);
    public List<Users> searchUsers(String name);
    public Users searchUsersById(Long uId);
    public List<Users> getFollowerList (Users u);
    public List<Users> getFollowingList (Users u);
    public byte[] getImageByUser(Users u);
}
