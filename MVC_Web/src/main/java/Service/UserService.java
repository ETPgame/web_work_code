package Service;

import entity.User;

import java.util.List;

public interface UserService {

    boolean login(User user);
    void register(User user);
    List<User> findAllUsers();
    User findUserById(int id);
    void updateUser(User user);
    boolean findUserByName(String username);
    void deleteUserById(int id);
    List<User> findAllUsersPageable(int pageNow,int pageCount);
    int getTotalRows();
    int findIdByUsername(String username);

    void updateInformation(User user);
    User findUserById2(int id);
}
