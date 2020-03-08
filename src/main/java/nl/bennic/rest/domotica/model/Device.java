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


}
