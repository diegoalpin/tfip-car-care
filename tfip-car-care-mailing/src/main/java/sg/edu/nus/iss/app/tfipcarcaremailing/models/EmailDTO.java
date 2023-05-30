package sg.edu.nus.iss.app.tfipcarcaremailing.models;

import java.io.Reader;
import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
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
                .add("recipient", recipient)
                .add("subject", subject)
                .add("content", content).build();
    }

    public static EmailDTO create(String payload){
        Reader reader = new StringReader(payload);
        JsonReader jsonReader = Json.createReader(reader);
        JsonObject jsonObject= jsonReader.readObject();
        
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setRecipient(jsonObject.getString("recipient"));
        emailDTO.setSubject(jsonObject.getString("subject"));
        emailDTO.setContent(jsonObject.getString("content"));

        return emailDTO;
    }

}
