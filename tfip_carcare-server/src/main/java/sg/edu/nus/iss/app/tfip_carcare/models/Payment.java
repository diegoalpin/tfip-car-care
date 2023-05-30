package sg.edu.nus.iss.app.tfip_carcare.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Payment {
    private String name;
    private String currency;
    private String successUrl;
    private String cancelUrl;
    private long amount;
    private long quantity;

    public Payment(){
        //empty constructor
    }
    
}
