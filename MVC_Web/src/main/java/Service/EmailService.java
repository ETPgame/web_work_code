package Service;

import entity.Email;
import entity.User;

import java.util.List;

public interface EmailService {
    List<Email> findEmailByRecipient(String recipient);
}
