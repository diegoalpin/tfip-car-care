package sg.edu.nus.iss.app.tfip_carcare.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Table(name = "car")
public class Car {

    // @Id
    // @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    // @GenericGenerator(name = "native", strategy = "native") // let MySQL do the id generation
    private int id;
    private String carPlate;
    private String brand;
    private String model;
    private int yearManufactured;
    private Customer customer;

    public Car() {
        // Empty constructor
    }

    public Car(String carPlate, String brand, String model, int yearManufactured, Customer customer) {
        this.carPlate = carPlate;
        this.brand = brand;
        this.model = model;
        this.yearManufactured = yearManufactured;
        this.customer = customer;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("carPlate", carPlate)
                .add("brand", brand)
                .add("model", model)
                .add("yearManufactured", yearManufactured)
                .build();
    }
    


}
