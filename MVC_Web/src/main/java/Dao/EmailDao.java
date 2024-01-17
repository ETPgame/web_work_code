package Dao;

import entity.Email;
import entity.User;

import java.util.List;

public interface EmailDao {
    List<Email> findEmailByRecipient(String recipient);
}
