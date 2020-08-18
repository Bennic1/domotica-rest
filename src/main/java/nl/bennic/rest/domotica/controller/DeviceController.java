package nl.bennic.rest.domotica.controller;

import lombok.extern.java.Log;
import nl.bennic.rest.domotica.exception.ApiRequestException;
import nl.bennic.rest.domotica.model.Device;
import nl.bennic.rest.domotica.model.DeviceDTO;
import nl.bennic.rest.domotica.service.DeviceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Log
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

//    @Autowired
//    ModelMapper modelMapper;

   // POST /////////////////////////////////////////////////////////////////////

    @PostMapping("/addDevice")
    public Device addDevice(@RequestBody Device device) {
//        Device device = modelMapper.map(deviceDTO, Device.class);
        log.info("REQUEST: addDevice " + device.toString());
        return deviceService.saveDevice(device);
    }

    @PostMapping("/addDevices")
    public List<Device> addDevices(@RequestBody List<Device> devices) {
        return deviceService.saveDevices(devices);
    }

    // GET /////////////////////////////////////////////////////////////////////

    @GetMapping("/getAllDevices")
    public List<Device> getAllDevices() {
        log.info("REQUEST: getAllDevices");
        return deviceService.getAllDevices();
    }

    @GetMapping("/getDeviceById/{id}")
    public Device getDeviceById(@PathVariable String id) {
        throw new ApiRequestException("Cannot find a device with ID: " + id);

    }

    @GetMapping("/getAllDeviceByName/{name}")
    public List<Device> getAllDeviceByName(@PathVariable String name) {
        return deviceService.getAllDevicesByName(name);
    }

    // DELETE ///////////////////////////////////////////////////////////////////

    @DeleteMapping("/deleteDevice")
    public String deleteDevice(@RequestBody Device device) {
//        Device device = modelMapper.map(deviceDTO, Device.class);
        return deviceService.deleteDevice(device);
    }

    // PUT //////////////////////////////////////////////////////////////////////

    @PutMapping("/updateDevice")
    public Device updateDevice(@RequestBody Device device) {
//        Device device = modelMapper.map(deviceDTO, Device.class);
        return deviceService.updateDevice(device);
    }
}
