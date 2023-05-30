package sg.edu.nus.iss.app.tfip_carcare.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

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
@Table(name = "customer")
public class Customer {
    // @Id
    // @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    // @GenericGenerator(name = "native",strategy = "native")//let MySQL do the id generation
    @NotNull
    private int id;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pwd;
    private String firstName;
    private String lastName;
    private int phoneNumber;
    private String address;
    private String role;
    
    private List<Car> cars;

    public Customer() {
    //empty constructor
    }
    

    public Customer(String email, String pwd, String firstName, String lastName, int phoneNumber, String address,
            String role, List<Car> cars) {
        this.email = email;
        this.pwd = pwd;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
        this.cars = cars;
    }


    @Override
    public String toString() {
        return "Customer [id=" + id + ", email=" + email + ", pwd=" + pwd + ", role=" + role + "]";
    }


    public JsonObjectBuilder toJsonBuilder() {
        return Json.createObjectBuilder()
                        .add("id",id)
                        .add("email",email)
                        .add("firstName",firstName)
                        .add("lastName",lastName)
                        .add("phoneNumber",phoneNumber)
                        .add("address",address);
    }
    
}
