package nl.bennic.rest.domotica.service;

import nl.bennic.rest.domotica.model.Device;
import nl.bennic.rest.domotica.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    // POST ////////////////////////////////////////////////////////////

    // één device opslaan
    public Device saveDevice(Device device) {
        return deviceRepository.save(device); // save methode komt van: DeviceRepository -> JpaRepository -> PagingAndSortingRepository -> CrudRepository
    }

    // meerdere devices opslaan
    public List<Device> saveDevices(List<Device> devices) {
        return deviceRepository.saveAll(devices);
    }

    // GET  /////////////////////////////////////////////////////////////

    // één device ophalen met een 'id'
    public Device getDeviceById(String id) {
        return deviceRepository.findById(id).orElse(null);
    }

      // lijst met alle devices ophalen
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    // lijst met alle devices met een zoekterm 'name'
    public List<Device> getAllDevicesByName(String name) {
        return deviceRepository.findAllByName(name);
    }

    // DELETE ///////////////////////////////////////////////////////////

    public String deleteDevice(String id) {
        deviceRepository.deleteById(id);
        return "Device " + id + " is deleted";
    }

    // UPDATE ///////////////////////////////////////////////////////////

    public Device updateDevice(Device device) {
        Device existingDevice = deviceRepository.findById(device.getId()).orElse(null);
        existingDevice.setIp(device.getIp());
        existingDevice.setName(device.getName());
        existingDevice.setState(device.getState());
        return deviceRepository.save(existingDevice);
    }
}
