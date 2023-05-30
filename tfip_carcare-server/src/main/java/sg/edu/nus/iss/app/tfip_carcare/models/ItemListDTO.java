package sg.edu.nus.iss.app.tfip_carcare.models;

import java.io.Serializable;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemListDTO implements Serializable {
    private Item item;
    private int quantity;
    public ItemListDTO(){
        //
    }
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("item",item.toJsonBuilder())
                .add("quantity", quantity).build();
    }
}
