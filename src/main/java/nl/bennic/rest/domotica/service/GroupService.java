package nl.bennic.rest.domotica.service;

import lombok.extern.java.Log;
import nl.bennic.rest.domotica.Exception.ApiRequestException;
import nl.bennic.rest.domotica.model.Device;
import nl.bennic.rest.domotica.model.Group;
import nl.bennic.rest.domotica.repository.DeviceRepository;
import nl.bennic.rest.domotica.repository.GroupRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public String deleteGroup(String id) {
        groupRepository.deleteById(id);
        return "Group " + id + " is deleted";
    }

    // PUT ///////////////////////////////////////////////////////////////
    public Group updateGroup(String id) {
//        System.out.println("GroupService - updateGroup: " + id);
        Group existingGroup = groupRepository.findById(id).orElse(null);
//        System.out.println(existingGroup.toString());
//        List<Device> deviceList = existingGroup.getDeviceList();
//        System.out.println(deviceList.toString());

//        Optional<Device> optional = deviceRepository.findById(deviceList.get(1).getId());
//
//        optional.ifPresent(device -> {
//            System.out.println("Device name = " + device.getName());
//        });

//        Device device = deviceRepository.findById(id);

        return existingGroup;
    }

    public Group addDeviceToGroup(String groupId, String deviceId) {
        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isPresent()) {
            Optional<Device> device = deviceRepository.findById(deviceId);
            if (device.isPresent()) {
                if (!group.get().getDeviceList().contains(deviceId)) {
                    group.get().getDeviceList().add(deviceId);
                    return groupRepository.save(group.get());
                } else {
                    throw new ApiRequestException("Device with id " + deviceId + " already exists!");
                }
            } else {
                throw new ApiRequestException("Cannot find device with id: " + deviceId);
            }
        } else {
            throw new ApiRequestException("Cannot find group with id: " + groupId);
        }
    }

    public Group removeDeviceFromGroup(String groupId, String deviceId) {
        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isPresent()) {
            if (group.get().getDeviceList().contains(deviceId)) {
                group.get().getDeviceList().remove(deviceId);
                return groupRepository.save(group.get());
            } else {
                throw new ApiRequestException("Cannot find device with id: " + deviceId);
            }
        } else {
            throw new ApiRequestException("Cannot find group with id: " + groupId);
        }
    }
}
