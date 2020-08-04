package nl.bennic.rest.domotica.service;

import lombok.extern.java.Log;
import nl.bennic.rest.domotica.exception.ApiRequestException;
import nl.bennic.rest.domotica.model.Device;
import nl.bennic.rest.domotica.model.Group;
import nl.bennic.rest.domotica.repository.DeviceRepository;
import nl.bennic.rest.domotica.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log
@Service
public class GroupService {

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
            if (existingGroup != null) {

                log.info("\u001b[32;1m============================= Update Goup =============================\u001b[0m");
                log.info("From: \t" + existingGroup);
                log.info("To: \t" + group);
                log.info("Total devices from: " + existingGroup.getDevices().size() + "' to: '" + group.getDevices().size());

                // als de status van de groep in de DB verschilt met de status van de groep die wordt meegegeven..
                if (Boolean.compare(existingGroup.getState(), group.getState()) != 0) {
                    log.info("Group state from: " + existingGroup.getState() + " to: " + group.getState());
                    // als er Devices in de Group zitten..
                    List<Device> devicesInGroup = new ArrayList<>(group.getDevices());
                    log.info("Devices in group to update: " + devicesInGroup.size());
                    if (!devicesInGroup.isEmpty()) {
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
            } else return null;
        } catch (Exception e) {
            log.warning(e.getMessage());
            throw new ApiRequestException("Cannot update group with id " + group.getId() + ". Exception: " + e);
        }
    }
}
