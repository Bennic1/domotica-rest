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
    public Group updateGroup(Group group) {
        try {

            System.out.println("Update group: " + group.getId() + ", State: " + group.getState());
            String path = "/cm";
            String command = "cmnd";
            String state;
            if (group.getState()) state = "Power on";
            else state = "Power off";
            System.out.println(state);

            WebClient webClient = WebClient.create("http://192.168.2.201");
            Mono<String> result = webClient.put()
                    .uri(uriBuilder -> uriBuilder
                            .path(path)
                            .queryParam(command, state)
                            .build())
//                    .uri(command+state).
                    .retrieve()
                    .bodyToMono(String.class);


            System.out.println("Result" + result.block());

//            RestTemplate restTemplate = new RestTemplate();
//            String result = restTemplate.getForObject(uri, String.class);

//            System.out.println(result);

            Group existingGroup = groupRepository.findById(group.getId()).orElse(null);
//            existingGroup.setIp(group.getIp());
            existingGroup.setName(group.getName());
            existingGroup.setState(group.getState());
            return groupRepository.save(existingGroup);
        } catch (Exception e) {
            throw new ApiRequestException("Cannot update group with id " + group.getId() + ". Group not found!");
        }
    }


//        System.out.println("GroupService - updateGroup: " + id);
//        Group existingGroup = groupRepository.findById(id).orElse(null);
//        System.out.println(existingGroup.toString());
//        List<Group> groups = existingGroup.getGroups();
//        System.out.println(groups.toString());

//        Optional<Group> optional = groupRepository.findById(groups.get(1).getId());
//
//        optional.ifPresent(group -> {
//            System.out.println("Group name = " + group.getName());
//        });

//        Group group = groupRepository.findById(id);

//        return existingGroup;
//    }

    public Group addDeviceToGroup(Group group, Device device) /*throws ApiRequestException*/ {
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
