package nl.bennic.rest.domotica.service;

import lombok.extern.java.Log;
import nl.bennic.rest.domotica.exception.ApiRequestException;
import nl.bennic.rest.domotica.model.Device;
import nl.bennic.rest.domotica.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Log
@RequestMapping
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

    public String deleteDevice(Device device) {
        log.info("Deleting Device: " + device.getId() + "..");
        deviceRepository.delete(device);
        if (deviceRepository.existsById(device.getId())) {
            log.info("Error: Device not deleted: " + device.getId());
            return "Error: Device not deleted: " + device.getId();
        } else {
            log.info("Device deleted: " + device.getId());
            return "Device deleted: " + device.getId();
        }
    }


    // PUT //////////////////////////////////////////////////////////////
    //Tasmota on: http://192.168.2.201/cm?cmnd=Power%20on
    //Tasmota off: http://192.168.2.201/cm?cmnd=Power%20off


    public Device updateDevice(Device device) {
        try {
            Device existingDevice = deviceRepository.findById(device.getId()).orElse(null);

            log.info("\u001b[34;1m============================Update Device ============================\u001b[0m");
            log.info("From: \t" + existingDevice);
            log.info("To: \t" + device);

            final String PATH = "/cm";
            final String COMMAND = "cmnd";
            String state;
            if (Boolean.TRUE.equals(device.getState())) state = "Power on";
            else state = "Power off";

            WebClient webClient = WebClient.create("http://" + device.getIp());
            Mono<String> result = webClient.put()
                    .uri(uriBuilder -> uriBuilder
                            .path(PATH)
                            .queryParam(COMMAND, state)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class);

            log.info("Result: " + result.block());
            log.info("\u001b[34;1m======================================================================\u001b[0m");
            return deviceRepository.save(device);

        } catch (Exception e) {
            log.warning("Device Exception! Device: " + device);
            log.warning(e.getMessage());
            throw new ApiRequestException("Cannot update device with id " + device.getId() + ". Device not found!");
        }
    }

}
