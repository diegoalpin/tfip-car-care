package sg.edu.nus.iss.app.tfipcarcaremailing.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sg.edu.nus.iss.app.tfipcarcaremailing.models.EmailDTO;

@Service
@RequiredArgsConstructor
public class EmailService {
    
    @Autowired
    private final JavaMailSender javaMailSender;

    @Async
    public void sendEmail(EmailDTO emailDTO){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailDTO.getRecipient());
        mailMessage.setSubject(emailDTO.getSubject());
        mailMessage.setCc("tfipcarcare.2023@gmail.com");
        mailMessage.setFrom("tfipcarcare.2023@gmail.com");
        mailMessage.setText(emailDTO.getContent());
        javaMailSender.send(mailMessage);
    }
}
