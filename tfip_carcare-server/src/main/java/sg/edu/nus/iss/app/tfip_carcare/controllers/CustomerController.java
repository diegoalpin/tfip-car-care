package sg.edu.nus.iss.app.tfip_carcare.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.app.tfip_carcare.models.Customer;
import sg.edu.nus.iss.app.tfip_carcare.repositories.CustomerRepository;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {
    @Autowired
    CustomerRepository custRepo;

    @GetMapping(path = "/other")
    public ResponseEntity<String> getCustomersExcept(@RequestParam int custId){
        List<Customer> result = custRepo.findAllExceptById(custId);
        
        if (result.size() == 0) {
            return ResponseEntity.badRequest().body("No car found");
        }
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Customer customer : result) {
            arrayBuilder.add(customer.toJsonBuilder());
        }
        
        JsonArray jsonResult = arrayBuilder.build();
        return ResponseEntity.ok().body(jsonResult.toString());
        
    }
}
