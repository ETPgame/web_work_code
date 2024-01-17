package Service;

import Dao.UserDao;
import Dao.UserDaoImplements;
import entity.User;

import java.util.List;

public class UserServiceImplements implements UserService{
    private UserDao userDao=new UserDaoImplements();
    @Override
    public boolean login(User user) {
        return userDao.userLogin(user);
    }

    @Override
    public void register(User user) {
        userDao.userRegister(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findUser();
    }

    @Override
    public User findUserById(int id) {
        return userDao.findUserById(id);
    }

    @Override
    public boolean findUserByName(String username) {
        return userDao.findUserByName(username);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void deleteUserById(int id) {
        userDao.deleteUserById(id);
    }

    @Override
    public List<User> findAllUsersPageable(int pageNow, int pageCount) {
        return userDao.findAllUsersPageable(pageNow,pageCount);
    }

    @Override
    public int getTotalRows() {
        return userDao.getTotalRows();
    }

    public int findIdByUsername(String username){
            return userDao.findIdByUsername(username);
    }

    @Override
    public void updateInformation(User user) {
        userDao.updateInformation(user);
    }
    @Override
    public User findUserById2(int id) {
        return userDao.findUserById2(id);
    }
}
