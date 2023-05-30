package sg.edu.nus.iss.app.tfip_carcare.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.app.tfip_carcare.models.Customer;
import sg.edu.nus.iss.app.tfip_carcare.repositories.CustomerRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private CustomerRepository custRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        Customer savedCustomer;
        ResponseEntity<String> response = null;
        List<Customer> checkCustomers = custRepo.findByEmail(customer.getEmail());
        if(checkCustomers.size()>0){
            JsonObject result = Json.createObjectBuilder()
                        .add("message","email "+customer.getEmail()+" already exists!").build();
            return ResponseEntity.internalServerError().body(result.toString());
        }
        try {
            String hashPwd = passwordEncoder.encode((customer.getPwd()));
            System.out.println("hashpwd is "+ hashPwd);
            customer.setPwd(hashPwd);
            savedCustomer = custRepo.save(customer);

            if (savedCustomer.getId() > 0) {
                JsonObject resultJson = Json.createObjectBuilder()
                    .add("message","User details are registered").build();
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(resultJson.toString());
            }

        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("exception occured" + ex.getMessage());
        }
        return response;
    }

    @RequestMapping("/login")
    public Customer getUserDetailsAfterLogin(Authentication authentication){
        List<Customer> customers = custRepo.findByEmail(authentication.getName());
        if (customers.size() > 0) {
            return customers.get(0);
        } else {
            return null;
        }
        
    }

}
