package Dao;

import entity.Email;
import entity.User;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmailDaoImpl implements EmailDao{
    @Override
    public List<Email> findEmailByRecipient(String recipient) {
        List<Email> emails=new ArrayList<>();
        Connection connection= JDBCUtils.getConnection();
        String sql="select * from email where recipient=? or sender=?";
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,recipient);
            preparedStatement.setString(2,recipient);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                Email email=new Email(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5));
                emails.add(email);
            }
            JDBCUtils.release(connection,preparedStatement,resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return emails;
    }


}
