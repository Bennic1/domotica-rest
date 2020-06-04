package nl.bennic.rest.domotica.service;

import lombok.extern.java.Log;
import nl.bennic.rest.domotica.Exception.ApiRequestException;
import nl.bennic.rest.domotica.model.Device;
import nl.bennic.rest.domotica.model.Group;
import nl.bennic.rest.domotica.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Log
public class DeviceService {

    //private WebClient webClient;

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
        System.out.println("Deleting Device: " + device.getId() + "..");
        deviceRepository.delete(device);
        if (deviceRepository.existsById(device.getId())) {
            System.out.println("Error: Device not deleted: " + device.getId());
            return "Error: Device not deleted: " + device.getId();
        } else {
            System.out.println("Device deleted: " + device.getId());
            return "Device deleted: " + device.getId();
        }
    }


    // PUT //////////////////////////////////////////////////////////////
    //Tasmota on: http://192.168.2.201/cm?cmnd=Power%20on
    //Tasmota off: http://192.168.2.201/cm?cmnd=Power%20off


    public Device updateDevice(Device device) {
        try {
            System.out.println("Update Device: " + device.getId() + ", State: " + device.getState());
            String path = "/cm";
            String command = "cmnd";
            String state;
            if (device.getState()) state = "Power on";
            else state = "Power off";
            System.out.println(state);

            WebClient webClient = WebClient.create("http://" + device.getIp());
            Mono<String> result = webClient.put()
                    .uri(uriBuilder -> uriBuilder
                            .path(path)
                            .queryParam(command, state)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class);

            System.out.println("Result" + result.block());

            return deviceRepository.save(device);
        } catch (Exception e) {
            throw new ApiRequestException("Cannot update device with id " + device.getId() + ". Device not found!");
        }
    }
//Aangepast naar updateDevice
//    public Device switchDevice(Device device) {
//        Device existingDevice = deviceRepository.findById(device.getId()).orElse(null);
//        try {
//            existingDevice.setState(device.getState());
//            return deviceRepository.save(existingDevice);
//        } catch (Exception e) {
//            throw new ApiRequestException("Cannot switch device with id " + device.getId() + ". Device not found!");
//        }
//    }
//
//
//
//        if (existingDevice != null) {
//            existingDevice.setState(device.getState());
//            return deviceRepository.save(existingDevice);
//        }
//        if (deviceRepository.findAll().contains(device)){
//            device.getState()
//        }
//
//        Device existingDevice = deviceRepository.findById(id).orElse(null);
//        existingDevice.setState(state);
//        String ip = existingDevice.getIp();
////        String cmd;
//
////        if (state) {
////            cmd = "lampaan";
////        } else {
////            cmd = "lampuit";
////        }
//
//        final String uri = "http://" + ip + "/cm?cmnd=power" + state;
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject(uri, String.class);
//        System.out.println(result);
//
//        return deviceRepository.save(existingDevice);
//    }
//    public Device switchDevice(String id, Boolean state) {
//
//        Device existingDevice = deviceRepository.findById(id).orElse(null);
//        existingDevice.setState(state);
//        String ip = existingDevice.getIp();
////        String cmd;
//
////        if (state) {
////            cmd = "lampaan";
////        } else {
////            cmd = "lampuit";
////        }
//
//        final String uri = "http://" + ip + "/cm?cmnd=power" + state;
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject(uri, String.class);
//        System.out.println(result);
//
//        return deviceRepository.save(existingDevice);
//    }
}
