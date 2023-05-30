package sg.edu.nus.iss.app.tfip_carcare.models;

import java.io.Serializable;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Table(name = "item")
public class Item implements Serializable{
    @NotNull
    private int id;
    private String name;
    private Float price;

    public Item() {
        // empty
    }

    public Item(String name, Float price) {
        this.name = name;
        this.price = price;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("name", name)
                .add("price", price).build();
    }

    public JsonObjectBuilder toJsonBuilder() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("name", name)
                .add("price", price);
    }

}
