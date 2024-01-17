package Dao;

import entity.User;

import java.util.List;

public interface UserDao {
    public boolean userLogin(User user);
    public void userRegister(User user);
    List<User> findUser();
    User findUserById(int id);
    boolean findUserByName(String username);
    void updateUser(User user);
    void deleteUserById(int id);
    List<User> findAllUsersPageable(int pageNow,int pageCount);
    int getTotalRows();
    int findIdByUsername(String username);
    void updateInformation(User user);
    User findUserById2(int id);
}
