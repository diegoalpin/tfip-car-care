package sg.edu.nus.iss.app.tfip_carcare.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.app.tfip_carcare.models.Car;
import sg.edu.nus.iss.app.tfip_carcare.models.Customer;
import sg.edu.nus.iss.app.tfip_carcare.repositories.CustomerRepository;
import sg.edu.nus.iss.app.tfip_carcare.services.CarService;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/car", produces = MediaType.APPLICATION_JSON_VALUE)
public class CarController {

    @Autowired
    CarService carSvc;
    @Autowired
    CustomerRepository custRepo;

    @GetMapping(path = "/all")
    public ResponseEntity<String> findAllCars(@RequestParam String email) {
        System.out.println("getting all cars");
        List<Car> result = carSvc.retrieveCarsByEmail(email);
        if (result.size() == 0) {
            return ResponseEntity.badRequest().body("No car found");
        }
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Car car : result) {
            arrayBuilder.add(car.toJson());
        }
        
        JsonArray jsonResult = arrayBuilder.build();
        return ResponseEntity.ok().body(jsonResult.toString());
    }

    @GetMapping(path = "/{carId}")
    public ResponseEntity<String> getCarDetails(@PathVariable Integer carId) {
        Optional<Car> opt = carSvc.retrieveCarById(carId);
        if (opt.isEmpty()) {
            return ResponseEntity.badRequest().body("car with id " + carId + " not found");
        }
        Car result = opt.get();
        JsonObject resultJson = result.toJson();
        return ResponseEntity.ok().body(resultJson.toString());
    }

    @PostMapping(path = "/")
    public ResponseEntity<String> createCar(@RequestBody Car car, @RequestParam String email) {
        // System.out.println("posting a car");
        Car savedCar = carSvc.createCar(car, email);
        if (savedCar != null) {
            return ResponseEntity.ok().body("Car details of id " + savedCar.getId() + " has been saved successfully");
        } else {
            return ResponseEntity.badRequest().body("car not saved");
        }
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<String> updateCarDetails(@RequestBody Car car) {
        if(car.getId()==0){
            return ResponseEntity.badRequest().body("Car does not exist, no car Id");
        }
        Car result = carSvc.editCar(car);
        if(result!=null){
            JsonObject jsonResult = result.toJson();
            return ResponseEntity.ok().body(jsonResult.toString());
        }
        else{
            return ResponseEntity.badRequest().body("car not updated");
        }
    }

    @DeleteMapping(path = "delete/{carId}")
    public ResponseEntity<String> deleteCar(@PathVariable Integer carId) {
        Boolean result = carSvc.deleteCar(carId);
        if(result==true){
            return ResponseEntity.ok().body("Car of id " + carId + " has been deleted");
        }
        else{
            return ResponseEntity.badRequest().body("car not deleted");
        }

    }

    @GetMapping(path = "/soldto")
    public ResponseEntity<String> findCarsSoldTo(@RequestParam int buyerId) {
        List<Car> result = carSvc.retriveCarsSoldTo(buyerId);
        if (result.size() == 0) {
            return ResponseEntity.internalServerError().body("No car found");
        }
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Car car : result) {
            arrayBuilder.add(car.toJson());
        }
        
        JsonArray jsonResult = arrayBuilder.build();
        return ResponseEntity.ok().body(jsonResult.toString());
    }

    @GetMapping(path = "/soldfrom")
    public ResponseEntity<String> findCarsSoldFrom(@RequestParam int sellerId) {
        List<Car> result = carSvc.retriveCarsSoldFrom(sellerId);
        if (result.size() == 0) {
            return ResponseEntity.internalServerError().body("No car found");
        }
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Car car : result) {
            arrayBuilder.add(car.toJson());
        }
        
        JsonArray jsonResult = arrayBuilder.build();
        return ResponseEntity.ok().body(jsonResult.toString());
    }


    @PostMapping(path = "/sale")
    public ResponseEntity<String> putCarOnSale(@RequestParam int carId,@RequestParam int buyerId) {
        Boolean saved = carSvc.createCarSale(carId,buyerId);
        Customer buyer = custRepo.findById(buyerId).get(0);
        String buyerFullName = buyer.getFirstName()+" "+buyer.getLastName();
        if (saved != true) {
            JsonObject jsonResult = Json.createObjectBuilder()
                                            .add("message", "Car of id " + carId + " has been put on Transfer list. Pending Payment from "+buyerFullName).build();
            return ResponseEntity.ok().body(jsonResult.toString());
        } else {
            return ResponseEntity.badRequest().body("Car of id " + carId+ " failed to put in transfer list");
        }
    }
    @PostMapping(path="/transfer")
    public ResponseEntity<String> transferOwnership(@RequestParam String carPlate){
        Boolean transferred = carSvc.transferOwnerShip(carPlate);
        if(transferred==true){
            JsonObject jsonResult = Json.createObjectBuilder()
                                    .add("message","Car of plate "+carPlate+ " has been transferred to you").build();
            return ResponseEntity.ok().body(jsonResult.toString());
        }
        else{
            return ResponseEntity.badRequest().body("Transfer of car with plate "+carPlate+" has failed");
        }
    }
}
