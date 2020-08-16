package nl.bennic.rest.domotica.model;

import java.io.Serializable;
import java.util.List;

public class GroupDTO implements Serializable {

    private String id;
    private String name;
    private Boolean state;
    private List<DeviceDTO> devices;

     public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public List<DeviceDTO> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceDTO> devices) {
        this.devices = devices;
    }
}

