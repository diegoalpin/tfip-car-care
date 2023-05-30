package sg.edu.nus.iss.app.tfip_carcare.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api")
public class MainController {
    
    @GetMapping(path = "/welcome")
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok().body("Hello to welcome");
    }
    
    @GetMapping(path="/contactUs")
    public ResponseEntity<String> getContactUsForm(){
        return ResponseEntity.ok().body("Hello to Contact Us Form");
    }
    
}
