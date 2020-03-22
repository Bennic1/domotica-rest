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
    private List<String> deviceList;

    public String addDevice(String deviceId) {
        deviceList.add(deviceId);
        return "Device added to group with id: " + deviceId;
    }

    public String removeDevice(String deviceId) {
        deviceList.remove(deviceId);
        return "Device removed from group with id: " +deviceId;
    }
}

