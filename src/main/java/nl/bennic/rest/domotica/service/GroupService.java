package nl.bennic.rest.domotica.service;

import lombok.extern.java.Log;
import nl.bennic.rest.domotica.Exception.ApiRequestException;
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
        System.out.println("Deleting Group: " + group + "..");
        groupRepository.delete(group);
        if (groupRepository.existsById(group.getId())) {
            System.out.println("Error: Group not deleted: " + group.getId());
            return "Error: Group not deleted: " + group.getId();
        } else {
            System.out.println("Group deleted: " + group.getId());
            return "Group  deleted: " + group.getId();
        }
    }

    // PUT ///////////////////////////////////////////////////////////////
    public Group updateGroup(Group group) {
        try {
            Group existingGroup = groupRepository.findById(group.getId()).orElse(null);

            System.out.println("Updating Group with ID: " + group.getId());

            if (!(existingGroup.getName().equals(group.getName()))) {
                System.out.println("Name from: '" + existingGroup.getName() + "' to: '" + group.getName() + "'");
            }

            if (existingGroup.getDevices().size() != (group.getDevices().size())) {
                System.out.println("Total devices from: " + existingGroup.getDevices().size() + "' to: '" + group.getDevices().size());
            }

            // als de status van de groep in de DB verschilt met de status van de groep die wordt meegegeven..
            if (Boolean.compare(existingGroup.getState(), group.getState()) != 0) {
                System.out.println("State from: " + existingGroup.getState() + " to: " + group.getState());
                // als er Devices in de Group zitten..
                List<Device> devicesInGroup = new ArrayList<>(group.getDevices());
                DeviceService deviceService = new DeviceService();
                if (devicesInGroup.size() > 0) {
                    // update de devices..
                    for (Device device : devicesInGroup) {
                        deviceService.updateDevice(device);
                    }
                } else {
                    System.out.println("No Devices in Group to switch.");
                }
            }
//             update het object in de DB
            return groupRepository.save(group);
        } catch (Exception e) {
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
//                System.out.println("Device " + i + ":" + device + "\n");
//                deviceList.add(device);
//            }
//
//        System.out.println("DONE!...");
//
//
//
//            System.out.println("Update Group: " + group.getName() + ", with ID: " + group.getId() + ", devices in group: " + group.getDevices());
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
//        System.out.println("GroupService - updateGroup: " + id);
//        Group existingGroup = groupRepository.findById(id).orElse(null);
//        System.out.println(existingGroup.toString());
//        List<Group> groups = existingGroup.getGroups();
//        System.out.println(groups.toString());
//
//        Optional<Group> optional = groupRepository.findById(groups.get(1).getId());
//
//        optional.ifPresent(group -> {
//            System.out.println("Group name = " + group.getName());
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
