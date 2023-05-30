package sg.edu.nus.iss.app.tfip_carcare.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.app.tfip_carcare.models.EmailDTO;
import sg.edu.nus.iss.app.tfip_carcare.services.MqProducerService;

@RestController
@RequestMapping(path = "/api/mq",produces = MediaType.APPLICATION_JSON_VALUE)
public class MqProducerController {
    @Autowired
    MqProducerService mqProducerService;
    
    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDTO emailDTO )throws JsonException{
        Boolean sent = mqProducerService.sendEmail(emailDTO);
        if(sent==true){
            JsonObject jsonResult = Json.createObjectBuilder()
            .add("message", "Email of Subject "+emailDTO.getSubject()+
            " has been successfully sent to "+emailDTO.getRecipient()).build();
            return ResponseEntity.ok().body(jsonResult.toString()); 
        }
        else{
            return ResponseEntity.badRequest().body("Email of Subject "+emailDTO.getSubject()+
            " has not been sent to "+emailDTO.getRecipient() );
        }
    }
}
