package sg.edu.nus.iss.app.tfip_carcare.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailDTO {
    private String recipient;
    private String subject;
    private String content;
    
    public EmailDTO() {
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                        .add("recipient",recipient)
                        .add("subject",subject)
                        .add("content",content).build();
    }
    
}
