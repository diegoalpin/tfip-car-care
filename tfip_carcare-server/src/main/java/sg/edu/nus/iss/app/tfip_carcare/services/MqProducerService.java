package sg.edu.nus.iss.app.tfip_carcare.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.json.JsonObject;
import sg.edu.nus.iss.app.tfip_carcare.models.EmailDTO;

@Service
@Transactional
public class MqProducerService {
    static String QUEUE_NAME = "email";
    private final RabbitTemplate rabbitTemplate;
    
    public MqProducerService(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public Boolean sendEmail(EmailDTO emailDTO){
        try {
            JsonObject jsonEmail = emailDTO.toJson();
            rabbitTemplate.convertAndSend(QUEUE_NAME, jsonEmail.toString());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
