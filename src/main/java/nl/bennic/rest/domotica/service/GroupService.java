package nl.bennic.rest.domotica.service;

import nl.bennic.rest.domotica.model.Group;
import nl.bennic.rest.domotica.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

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

    // UPDATE ///////////////////////////////////////////////////////////

    public Group updateGroup(Group group) {
        Group existingGroup = groupRepository.findById(group.getId()).orElse(null);
        existingGroup.setName(group.getName());
        existingGroup.setState(group.getState());
        existingGroup.setDeviceList(group.getDeviceList());
        return groupRepository.save(existingGroup);
    }

//    public Group updateGroupDeviceList(Group group){
//        Group existingGroup = groupRepository.findById(group.getId()).orElse(null);
//
//
//    }


}
