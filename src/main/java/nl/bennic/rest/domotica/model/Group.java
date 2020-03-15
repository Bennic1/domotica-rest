package nl.bennic.rest.domotica.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Group {

    @Id
    private String id;
    private String name;
    private Boolean state;
    private List<Device> deviceList;

    public Device addDevice(Device device) {
        deviceList.add(device);
        return device;
    }

    public Device removeDevice(Device device) {
        deviceList.remove(device);
        return device;
    }
}

