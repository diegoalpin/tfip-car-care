package sg.edu.nus.iss.app.tfipcarcaremailing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sg.edu.nus.iss.app.tfipcarcaremailing.models.EmailDTO;
import sg.edu.nus.iss.app.tfipcarcaremailing.services.EmailService;


@Component
public class MessageListener {

    @Autowired
    EmailService emailService;

    public void onMessageReceived(String message) {
        System.out.println("Received [" + message + "]");
        EmailDTO emailDTO = EmailDTO.create(message);
        emailService.sendEmail(emailDTO);
    }
}
