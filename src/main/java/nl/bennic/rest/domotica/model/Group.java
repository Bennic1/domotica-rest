package nl.bennic.rest.domotica.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Group {

    @Id
    private String id;
    private String name;
    private Boolean state;

    private List<Device> devices;// aanpassen naar Set en Device obj

    public Boolean addDevice(Device device) {
        return devices.add(device);
    }

    public Boolean removeDevice(Device device) {
        return devices.remove(device);


    }
}

