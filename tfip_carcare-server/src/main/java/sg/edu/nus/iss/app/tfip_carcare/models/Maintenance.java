package sg.edu.nus.iss.app.tfip_carcare.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Table(name="maintenance")
public class Maintenance implements Serializable {
    @NotNull
    private int id;
    private String description;
    // @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    private String status;
    private Double cost=(double) 0;
    private int mileage;
    private Car car;
    private List<ItemListDTO> items;
    // private List<Item> items;

    public Maintenance(){

    }
    

    public Maintenance(String description, LocalDate date, String status, Double cost, int mileage, Car car,
            List<ItemListDTO> items) {
        this.description = description;
        this.date = date;
        this.status = status;
        this.cost = cost;
        this.mileage = mileage;
        this.car = car;
        this.items = items;
    }


    public JsonObject toJson(){
        return Json.createObjectBuilder()
            .add("id",id)
            .add("description",description)
            .add("date",date.toString())
            .add("status",status)
            .add("mileage",mileage)
            .add("cost",cost).build();
    }


}
