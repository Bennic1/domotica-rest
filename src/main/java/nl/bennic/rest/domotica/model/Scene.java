package nl.bennic.rest.domotica.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Scene {

    @Id
    private String id;
    private String name;
    private List<Device> devices;

    @Override
    public String toString() {
        StringBuilder devicelistText = new StringBuilder();

        if (!isEmpty(devices)) {
            for (Device device : devices) {
                devicelistText.append("\t\t\t").append(device.toString()).append("\n");
            }
        }
        return "\u001b[32;1mGroup id: \u001b[0m" + id + ", \u001b[32;1mName: \u001b[0m" + name + ", \u001b[32;1mItem count: \u001b[0m" + devices.size() +
                ", \u001b[32;1mDevice List: \n\u001b[0m" + devicelistText;
    }
}