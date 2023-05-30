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
import sg.edu.nus.iss.app.tfip_carcare.models.Item;
import sg.edu.nus.iss.app.tfip_carcare.models.ItemListDTO;
import sg.edu.nus.iss.app.tfip_carcare.services.ItemService;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/item",produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {
    @Autowired
    ItemService itemSvc;

    @GetMapping(path = "/{itemId}")
    public ResponseEntity<String> getItemDetails(@PathVariable int itemId){
        Optional<Item> opt = itemSvc.retrieveItemById(itemId);
        if(opt.isEmpty()){
            return ResponseEntity.badRequest().body("Item with id " + itemId + " not found");
        }
        Item result = opt.get();
        JsonObject resultJson = result.toJson();
        return ResponseEntity.ok().body(resultJson.toString());
    }

    @PostMapping(path="/")
    public ResponseEntity<String> createItem(@RequestBody Item item){
        Item savedItem = itemSvc.createItem(item);
        if(savedItem!=null){
            return ResponseEntity.ok().body("Item details of id " + savedItem.getId() + " has been saved successfully");
        }
        else{
            return ResponseEntity.badRequest().body("Item not saved");
        }
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<String> updateItemDetails(@RequestBody Item item){
        System.out.println("not implemented yet");
        return null; 
    }

    @DeleteMapping(path="/delete/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Integer itemId) {
        Boolean result = itemSvc.deleteItem(itemId);
        if(result==true){
            return ResponseEntity.ok().body("Maintenance of id " + itemId + " has been deleted");
        }
        else{
            return ResponseEntity.badRequest().body("Maintenance not deleted");
        }

    }


    @GetMapping(path = "/all")
    public ResponseEntity<String> getAllItems(){
        List<Item> result = itemSvc.retrieveAllItems();
        if(result.size()==0){
            return ResponseEntity.badRequest().body("No item is found ");
        }
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Item item : result) {
            arrayBuilder.add(item.toJson());
        }
        JsonArray jsonResult = arrayBuilder.build();

        return ResponseEntity.ok().body(jsonResult.toString());
    }
    @GetMapping(path = "/")
    public ResponseEntity<String> getAllItemsByNameLike(@RequestParam(required = true) String name){
        List<Item> result = itemSvc.retrieveItembyNameLike(name);
        if(result.size()==0){
            return ResponseEntity.badRequest().body("No item containing " +name+" in its name is found.");
        }
        else if(result.size()==1){
            JsonObject resultJson = result.get(0).toJson();
            return ResponseEntity.ok().body(resultJson.toString());
        }

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Item item : result) {
            arrayBuilder.add(item.toJson());
        }
        JsonArray jsonResult = arrayBuilder.build();

        return ResponseEntity.ok().body(jsonResult.toString());
    }

    @GetMapping(path="/maintenance")
    public ResponseEntity<String> getAllItemsByMaintId(@RequestParam(required = true) Integer maintId){
        List<ItemListDTO> result = itemSvc.retrieveItemByMaintId(maintId);//id,name,price,quantity
        if(result.size()==0){
            return ResponseEntity.badRequest().body("No item is found in maintId: "+maintId);
        }
        else if(result.size()==1){
            System.out.println(result.get(0).getQuantity());
            System.out.println(result.get(0).getItem().getName());
            JsonObject resultJson = result.get(0).toJson();
            return ResponseEntity.ok().body(resultJson.toString());
        }

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        System.out.println(result.size()+" "+result.get(0).toString());
        for (ItemListDTO itemListDTO : result) {
            
            System.out.println(itemListDTO.getQuantity());
            System.out.println(itemListDTO.getItem().getName());
            arrayBuilder.add(itemListDTO.toJson());
        }
        JsonArray jsonResult = arrayBuilder.build();

        return ResponseEntity.ok().body(jsonResult.toString());
    }


}
