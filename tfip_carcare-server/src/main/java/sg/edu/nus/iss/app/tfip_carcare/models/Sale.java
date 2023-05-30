package sg.edu.nus.iss.app.tfip_carcare.models;

import java.io.Reader;
import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Sale {
    private int carId;
    private int buyerId;
    private int sellerId;

    public Sale(){}

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                    .add("carId", buyerId)
                    .add("buyerId",buyerId)
                    .add("sellerId",sellerId).build();
    }

    public static Sale create(String payload){
        Reader reader = new StringReader(payload);
		JsonReader jsonReader = Json.createReader(reader);
		JsonObject jsonResult = jsonReader.readObject();
        return new Sale(jsonResult.getInt("carId"),
                        jsonResult.getInt("buyerId"),
                        jsonResult.getInt("sellerId"));

    }
    
}
