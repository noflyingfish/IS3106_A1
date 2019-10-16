package session;

import entity.Users;
import exception.NoResultFoundException;
import exception.UsernameAlreadyExistException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class UserController implements UserControllerLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Users createUsers(Users u) throws UsernameAlreadyExistException {
        try {
            em.persist(u);
        } catch (Exception e) {
            throw new UsernameAlreadyExistException("Username has been used.");
        }
        return u;
    }

    @Override
    public Users loginUsers(String username, String password) throws NoResultFoundException {
        String jpql = "SELECT u from Users u WHERE u.userName =:username AND u.password =:password";
        Query query = em.createQuery(jpql);
        query.setParameter("username", username);
        query.setParameter("password", password);
        try {
            Users u = (Users) query.getSingleResult();
            return u;
        } catch (Exception e) {
            throw new NoResultFoundException("Invalid credentials");
        }
    }

    @Override
    public void updateUsers(Users u) throws NoResultFoundException {
        try {
            em.merge(u);
        } catch (Exception e) {
            throw new NoResultFoundException("Update user failed");
        }
    }

    @Override
    public void followUsers(Users curr1, Users follow1) {

        Users curr = em.find(Users.class, curr1.getId());
        Users follow = em.find(Users.class, follow1.getId());

        if (em.contains(curr)) System.out.println("curr conains");
        if (em.contains(follow)) System.out.println("follow conains");

        List followList = curr.getFollowList();
        followList.add(follow);
        curr.setFollowList(followList);
        System.out.println("curr1 " + curr.getFollowList().size());
        em.merge(curr);
        System.out.println("curr2 " + curr.getFollowList().size());
        
        List followerList = follow.getFollowerList();
        followerList.add(curr);
        follow.setFollowerList(followerList);
        System.out.println("follow1 " + follow.getFollowerList().size());
        em.merge(follow);
        System.out.println("follow2  " + follow.getFollowerList().size());
    }

    @Override
    public void unfollowUsers(Users curr, Users follow){

        List followList = curr.getFollowList();
        followList.remove(follow);
        curr.setFollowList(followList);
        em.merge(curr);

        List followerList = follow.getFollowerList();
        followerList.remove(curr);
        follow.setFollowerList(followerList);
        em.merge(follow);
    }

    @Override
    public List<Users> searchUsers(String name) {

        String jpql = "SELECT u from Users as u WHERE u.userName LIKE :name";
        Query query = em.createQuery(jpql);
        query.setParameter("name", "%" + name + "%");
        List<Users> searchUserList = query.getResultList();

        return searchUserList;
    }

    @Override
    public Users searchUsersById(Long uId) {

        String jpql = "SELECT u from Users as u WHERE u.id =:id";
        Query query = em.createQuery(jpql);
        query.setParameter("id", uId);
        Users u = (Users) query.getSingleResult();
        return u;
    }

    @Override
    public List<Users> getFollowerList(Users curr1) {
        Users curr = em.find(Users.class, curr1.getId());
        return curr.getFollowerList();
    }

    @Override
    public List<Users> getFollowingList(Users curr1) {
        Users curr = em.find(Users.class, curr1.getId());
        return curr.getFollowList();
    }

    @Override
    public byte[] getImageByUser(Users u) {
        String jpql = "SELECT u.profilePic FROM Users as u Where u.id =:id";
        Query query = em.createQuery(jpql);
        query.setParameter("id", u.getId());
        byte[] img = (byte[]) query.getSingleResult();

        return img;
    }
}
