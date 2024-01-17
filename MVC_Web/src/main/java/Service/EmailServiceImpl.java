package Service;

import Dao.EmailDao;
import Dao.EmailDaoImpl;
import entity.Email;
import entity.User;

import java.util.List;

public class EmailServiceImpl implements EmailService{
    private EmailDao emailDao=new EmailDaoImpl();
    @Override
    public List<Email> findEmailByRecipient(String recipient) {
        return emailDao.findEmailByRecipient(recipient);
    }

}
