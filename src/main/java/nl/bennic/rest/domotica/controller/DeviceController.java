package nl.bennic.rest.domotica.controller;


import nl.bennic.rest.domotica.Exception.ApiRequestException;
import nl.bennic.rest.domotica.model.Device;
import nl.bennic.rest.domotica.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    // POST /////////////////////////////////////////////////////////////////////

    @PostMapping("/addDevice")
    public Device addDevice(@RequestBody Device device) {
        System.out.println("REQUEST: addDevice " + device.toString());
        return deviceService.saveDevice(device);
    }

    @PostMapping("/addDevices")
    public List<Device> addDevices(@RequestBody List<Device> devices) {
        return deviceService.saveDevices(devices);
    }

    // GET /////////////////////////////////////////////////////////////////////

    @GetMapping("/getAllDevices")
    public List<Device> getAllDevices() {
        System.out.println("REQUEST: getAllDevices");
        return deviceService.getAllDevices();
    }

    @GetMapping("/getDeviceById/{id}")
    public Device getDeviceById(@PathVariable String id) {
        throw new ApiRequestException("Cannot find a device with ID: " + id);
//        System.out.println("getDeviceById: "+ id);
//        return deviceService.getDeviceById(id);
    }

    @GetMapping("/getAllDeviceByName/{name}")
    public List<Device> getAllDeviceByName(@PathVariable String name) {
        return deviceService.getAllDevicesByName(name);
    }

    // DELETE ///////////////////////////////////////////////////////////////////

    @DeleteMapping("/deleteDevice/{id}")
    public String deleteDevice(@PathVariable String id) {
        return deviceService.deleteDevice(id);
    }

    // PUT //////////////////////////////////////////////////////////////////////

    @PutMapping("/updateDevice")
    public Device updateDevice(@RequestBody Device device) {
        return deviceService.updateDevice(device);
    }


    //    Aangepast naar updateDevice
//    @PutMapping("/switch/{id}/{state}")
//    public Device switchDevice(@PathVariable String id, @PathVariable Boolean state) {
//        return deviceService.switchDevice(id, state);
//    }
//    Aangepast naar updateDevice
//    @PutMapping("/switchDevice")
//    public Device switchDevice(@RequestBody Device device) {
//        return deviceService.switchDevice(device);
//    }

}
