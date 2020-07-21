package nl.bennic.rest.domotica.service;

import lombok.extern.java.Log;
import nl.bennic.rest.domotica.Exception.ApiRequestException;
import nl.bennic.rest.domotica.controller.DeviceController;
import nl.bennic.rest.domotica.model.Device;
import nl.bennic.rest.domotica.model.Group;
import nl.bennic.rest.domotica.repository.DeviceRepository;
import nl.bennic.rest.domotica.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log
@Service
public class GroupService {
    private static final String TAG = "Bennic GroupService";


    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DeviceService deviceService;


    // POST ////////////////////////////////////////////////////////////

    // één group opslaan
    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    // GET  /////////////////////////////////////////////////////////////

    // group ophalen met een 'id'
    public Group getGroupById(String id) {
        return groupRepository.findById(id).orElse(null);
    }

    // lijst met alle groepen ophalen
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }


    // DELETE ///////////////////////////////////////////////////////////

    public String deleteGroup(Group group) {
        log.info("Deleting Group: " + group + "..");
        groupRepository.delete(group);
        if (groupRepository.existsById(group.getId())) {
            log.info("Error: Group not deleted: " + group.getId());
            return "Error: Group not deleted: " + group.getId();
        } else {
            log.info("Group deleted: " + group.getId());
            return "Group  deleted: " + group.getId();
        }
    }

    // PUT ///////////////////////////////////////////////////////////////
    public Group updateGroup(Group group) {
        try {
            Group existingGroup = groupRepository.findById(group.getId()).orElse(null);

            log.info("\u001b[32;1m============================= Update Goup =============================\u001b[0m");

            log.info("From: \t" + existingGroup);
            log.info("To: \t" + group);

            if (!(existingGroup.getName().equals(group.getName()))) {
                log.info("Name from: '" + existingGroup.getName() + "' to: '" + group.getName() + "'");
            }

            if (existingGroup.getDevices().size() != (group.getDevices().size())) {
                log.info("Total devices from: " + existingGroup.getDevices().size() + "' to: '" + group.getDevices().size());
            }

            // als de status van de groep in de DB verschilt met de status van de groep die wordt meegegeven..
            if (Boolean.compare(existingGroup.getState(), group.getState()) != 0) {
                log.info("Group state from: " + existingGroup.getState() + " to: " + group.getState());
                // als er Devices in de Group zitten..
                List<Device> devicesInGroup = new ArrayList<>(group.getDevices());

                log.info("Devices in group to update: " + devicesInGroup.size());

                if (devicesInGroup.size() > 0) {
                    // update de devices..
                    int i = 1;
                    for (Device device : devicesInGroup) {
                        log.info("\u001b[32;1mUpdate Device \u001b[0m" + i + ": " + device.getName());
                        device.setState(group.getState());
                        deviceService.updateDevice(device);
                        i++;
                    }
                } else {
                    log.info("No Devices in Group to switch.");
                }
            }
            log.info("\u001b[32;1m======================================================================\u001b[0m");

//             update het object in de DB
            return groupRepository.save(group);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException("Cannot update group with id " + group.getId() + ". Exception: " + e);
        }
    }


//        if (group.getDevices().size() == )
//
//            List<Device> deviceList = new ArrayList<>();
//
//            for (int i = 0; i < group.getDevices().size(); i++) {
//                Device device = new Device();
//                device = (group.getDevices().get(i));
//                log.info("Device " + i + ":" + device + "\n");
//                deviceList.add(device);
//            }
//
//        log.info("DONE!...");
//
//
//
//            log.info("Update Group: " + group.getName() + ", with ID: " + group.getId() + ", devices in group: " + group.getDevices());
//            Group existingGroup = groupRepository.findById(group.getId()).orElse(null);
//
//
//            // als de status van de groep veranderd is.. (off -> on)
//            existingGroup.setDevices(group.getDevices());
//            existingGroup.setName(group.getName());
//            existingGroup.setState(group.getState());
//            return groupRepository.save(existingGroup);
//        } catch (Exception e) {
//            throw new ApiRequestException("Cannot update group with id " + group.getId() + ". Exception: " + e);
//        }
//    }
//
//
//        log.info("GroupService - updateGroup: " + id);
//        Group existingGroup = groupRepository.findById(id).orElse(null);
//        log.info(existingGroup.toString());
//        List<Group> groups = existingGroup.getGroups();
//        log.info(groups.toString());
//
//        Optional<Group> optional = groupRepository.findById(groups.get(1).getId());
//
//        optional.ifPresent(group -> {
//            log.info("Group name = " + group.getName());
//        });
//
//        Group group = groupRepository.findById(id);
//
//        return existingGroup;
//        return null;
//    }

    public Group addDeviceToGroup(Group group, Device device) {
        try {
            Optional<Group> groupOptional = groupRepository.findById(group.getId());
            Optional<Device> deviceOptional = deviceRepository.findById(device.getId());
            groupOptional.get().addDevice(deviceOptional.get());
            return groupRepository.save(groupOptional.get());
        } catch (ApiRequestException e) {
            throw new ApiRequestException("Cannot add device with id " + device.getId() + " to group with id: " + group.getId());
        }
    }


    public Group removeDeviceFromGroup(String groupId, String deviceId) {
        try {
            Optional<Group> group = groupRepository.findById(groupId);
            group.get().getDevices().remove(deviceId);
            return groupRepository.save(group.get());
        } catch (Exception e) {
            throw new ApiRequestException("lalalalala");
        }
    }

//    public Group removeDeviceFromGroup(String groupId, String deviceId) {
//        Optional<Group> group = groupRepository.findById(groupId);
//        if (group.isPresent()) {
//            if (group.get().getDevices().contains(deviceId)) {
//                group.get().getDevices().remove(deviceId);
//                return groupRepository.save(group.get());
//            } else {
//                throw new ApiRequestException("Cannot find device with id: " + deviceId);
//            }
//        } else {
//            throw new ApiRequestException("Cannot find group with id: " + groupId);
//        }
//    }
}
