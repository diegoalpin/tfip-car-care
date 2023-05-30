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
import sg.edu.nus.iss.app.tfip_carcare.models.ItemListDTO;
import sg.edu.nus.iss.app.tfip_carcare.models.Maintenance;
import sg.edu.nus.iss.app.tfip_carcare.services.MaintenanceService;

@CrossOrigin("*")
@RestController
@RequestMapping(path="/api/maintenance",produces = MediaType.APPLICATION_JSON_VALUE)
public class MaintenanceController {
    @Autowired
    MaintenanceService maintSvc;

    @GetMapping("/all")
    public ResponseEntity<String> getAllMaintenancesByUser(@RequestParam String email){
        List<Maintenance> result =  maintSvc.retrieveMaintenancesByEmail(email);    
        if(result.size()==0){
            ResponseEntity.badRequest().body("No Maintenance is found for email "+email);
        }
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Maintenance maintenance : result) {
            arrayBuilder.add(maintenance.toJson());
        }
        JsonArray jsonResult = arrayBuilder.build();

        return ResponseEntity.ok().body(jsonResult.toString());
    }

    @GetMapping
    public ResponseEntity<String> getAllMaintenancesByCarId(@RequestParam int car_id){
        List<Maintenance> result = maintSvc.retrieveMaintenancesByCarId(car_id);
        if(result.size()==0){
            ResponseEntity.badRequest().body("No Maintenance is found for car id"+ car_id);
        }
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Maintenance maintenance : result) {
            arrayBuilder.add(maintenance.toJson());
        }
        JsonArray jsonResult = arrayBuilder.build();

        return ResponseEntity.ok().body(jsonResult.toString());
    }

    @GetMapping(path="/{maintId}")
    public ResponseEntity<String> getMaintenanceDetails(@PathVariable Integer maintId){
        Optional<Maintenance> opt =  maintSvc.retrieveMaintenanceById(maintId);

        if (opt.isEmpty()) {
            return ResponseEntity.badRequest().body("Maintenance with id " + maintId + " not found");
        }
        Maintenance result = opt.get();
        JsonObject resultJson = result.toJson();
        return ResponseEntity.ok().body(resultJson.toString());
    }
    @GetMapping(path = "/getCarId")
    public ResponseEntity<String> getCarIdByMaintId(@RequestParam Integer maintId){
        Integer result = maintSvc.retrieveCarIdByMaintId(maintId);
        return ResponseEntity.ok().body(result.toString());
    }

    @PostMapping(path = "/")
    public ResponseEntity<String> createMaintenance(@RequestBody Maintenance maintenance, @RequestParam int car_id) {
        System.out.println(maintenance.toString());
        for (ItemListDTO itemListDTO : maintenance.getItems()) {
            System.out.println(itemListDTO.getItem().getName()+ " quantity is "+itemListDTO.getQuantity());
        } 
        
        Maintenance savedMaintenance = maintSvc.createMaintenance(maintenance, car_id);
        if (savedMaintenance != null) {
            JsonObject jsonResult = Json.createObjectBuilder()
                        .add("message","Maintenance details of id " + savedMaintenance.getId() + " has been saved successfully").build();
            return ResponseEntity.ok().body(jsonResult.toString());
        } else {
            return ResponseEntity.badRequest().body("Maintenance not saved");
        }
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<String> updateMaintenanceDetails(@RequestBody Maintenance maintenance){
        Maintenance result = maintSvc.editMaintenance(maintenance);
        if(result!= null){
            JsonObject jsonResult = result.toJson();
            return ResponseEntity.ok().body(jsonResult.toString());
        }
        else{
            return ResponseEntity.badRequest().
                body("Maintenance of id "+ maintenance.getId()+" not updated");
        }
    }

    @DeleteMapping(path = "delete/{maintenanceId}")
    public ResponseEntity<String> deleteMaintenance(@PathVariable Integer maintenanceId) {
        Boolean result = maintSvc.deleteMaintenance(maintenanceId);
        if(result==true){
            JsonObject jsonResult = Json.createObjectBuilder()
                        .add("message","Maintenance of id " + maintenanceId + " has been deleted").build();
            return ResponseEntity.ok().body(jsonResult.toString());
        }
        else{
            return ResponseEntity.badRequest().body("Maintenance not deleted");
        }

    }

}
