package nl.bennic.rest.domotica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data //maakt getters en setters
@AllArgsConstructor // maakt constructor met alle argumenten
@NoArgsConstructor // maakt constructor zonder argumenten
@Document
public class Device {

    @Id // maakt van 'id' een uniek id in de database
    private String id;
    private String name;
    private String ip;
    private Boolean state;
    private int delay;

    @Override
    public String toString() {
        return "\u001b[34;1mDevice id: \u001b[0m" + id + ", \u001b[34;1mName: \u001b[0m" + name + ", \u001b[34;1mIP: \u001b[0m" + ip + ", \u001b[34;1mState: \u001b[0m" + state
                + ", \u001b[34;1mDelay: \u001b[0m" + delay;
    }
}
