package sg.edu.nus.iss.app.tfip_carcare.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class AccountController {
    @GetMapping(path = "/myAccount")
    public ResponseEntity<String> getAccountDetails(){
        return ResponseEntity.ok().body("this is my account controller");
        //accountdetailsbyEmail
    }
}
