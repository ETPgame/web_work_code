package Dao;

import entity.User;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImplements implements UserDao{
    @Override
    public boolean userLogin(User user) {
        Connection conn= JDBCUtils.getConnection();
        String sql="select * from user where username=? and password=?";
        try {
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1,user.getUsername());
            pstate.setString(2,user.getPassword());
            ResultSet rs=pstate.executeQuery();
            if (rs.next()){
                return true;
            }else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void userRegister(User user) {
        Connection conn=JDBCUtils.getConnection();
        String sql="insert into user(username,password,email) values (?,?,?)";
        try {
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1,user.getUsername());
            pstate.setString(2, user.getPassword());
            pstate.setString(3, user.getEmail());
            pstate.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findUser() {
        List<User> users=new ArrayList<User>();
        Connection connection=JDBCUtils.getConnection();
        String sql="select * from user";
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement= connection.prepareStatement(sql);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user=new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
                users.add(user);

            }
            JDBCUtils.release(connection,preparedStatement,resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;

    }

    @Override
    public User findUserById(int id) {
        Connection connection=JDBCUtils.getConnection();
        PreparedStatement preparedStatement=null;
        User user=null;
        String sql="select * from user where id = ?";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if (resultSet.next()) {
                user=new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
            }
            JDBCUtils.release(connection,preparedStatement,resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public boolean findUserByName(String username) {
        Connection connection=JDBCUtils.getConnection();
        String sql="select * from user where username=?";
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                JDBCUtils.release(connection,preparedStatement,resultSet);
                return true;
            }
            JDBCUtils.release(connection,preparedStatement,resultSet);
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUser(User user) {
        Connection connection=JDBCUtils.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="update user set username=?,password=?,email=? where id=?";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getEmail());
            preparedStatement.setInt(4,user.getId());
            preparedStatement.executeUpdate();
            JDBCUtils.release(connection,preparedStatement,null);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUserById(int id) {
        Connection connection=JDBCUtils.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="delete from user where id=?";
        try{
            preparedStatement= connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            JDBCUtils.release(connection,preparedStatement,null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAllUsersPageable(int pageNow, int pageCount) {
        List<User> users=new ArrayList<>();
        Connection connection= JDBCUtils.getConnection();
        String sql="select * from user limit ?,?";
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,(pageNow-1)*pageCount);
            preparedStatement.setInt(2,pageCount);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user=new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                users.add(user);
            }
            JDBCUtils.release(connection,preparedStatement,resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public int getTotalRows() {
        Connection connection=JDBCUtils.getConnection();
        String sql="select count(*) from user";
        PreparedStatement preparedStatement=null;
        int count=0;
        try {
            preparedStatement=connection.prepareStatement(sql);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                count=resultSet.getInt(1);
            }
            JDBCUtils.release(connection,preparedStatement,resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return count;
    }

    @Override
    public int findIdByUsername(String username) {
        Connection connection=JDBCUtils.getConnection();
        String sql="select id from user where username=?";
        int id=0;
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                id=resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public void updateInformation(User user) {
        Connection  connection=JDBCUtils.getConnection();
        UserDao userDao=new UserDaoImplements();
        String sql="update user set name=?,age=?,sex=?,picture=? where username=?";
        System.out.println("username:"+user.getUsername());
        System.out.println("name:"+user.getName());
        System.out.println("age:"+user.getAge());
        System.out.println("sex:"+user.getSex());
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setInt(2, user.getAge());
            preparedStatement.setString(3, user.getSex());
            preparedStatement.setBytes(4, user.getPicture());
            preparedStatement.setString(5, user.getUsername());
            preparedStatement.executeUpdate();
            JDBCUtils.release(connection,preparedStatement,null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findUserById2(int id) {
        Connection conn=JDBCUtils.getConnection();
        String sql="select * from user where id=?";
        PreparedStatement preparedStatement=null;
        User user=null;
        try {
            preparedStatement= conn.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                user=new User(resultSet.getString(2),
                        resultSet.getString(5),
                        resultSet.getInt(6),
                        resultSet.getString(7),
                        resultSet.getBytes(8));
            }
            JDBCUtils.release(conn,preparedStatement,resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
