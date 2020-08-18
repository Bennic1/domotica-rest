package nl.bennic.rest.domotica.controller;

import lombok.extern.java.Log;
import nl.bennic.rest.domotica.model.Group;
import nl.bennic.rest.domotica.model.GroupDTO;
import nl.bennic.rest.domotica.service.GroupService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log
public class GroupController {

    @Autowired
    private GroupService groupService;

//    @Autowired
//    ModelMapper modelMapper;

    // POST /////////////////////////////////////////////////////////////////////

    @PostMapping("/addGroup")
    public Group addGroup(@RequestBody Group group) {
//        Group group = modelMapper.map(groupDTO, Group.class);
        return groupService.saveGroup(group);
    }

    // GET /////////////////////////////////////////////////////////////////////

    @GetMapping("/getAllGroups")
    public List<Group> getAllGroups() {
        log.info("REQUEST: getAllGroups");
        return groupService.getAllGroups();
    }

    @GetMapping("/getGroupById/{id}")
    public Group getGroupById(@PathVariable String id) {
        return groupService.getGroupById(id);
    }

    // DELETE ///////////////////////////////////////////////////////////////////

    @DeleteMapping("/deleteGroup")
    public String deleteGroup(@RequestBody Group group) {
//        Group group = modelMapper.map(groupDTO, Group.class);
        return groupService.deleteGroup(group);
    }

    // PUT //////////////////////////////////////////////////////////////////////

    @PutMapping("/updateGroup")
    public Group updateGroup(@RequestBody Group group) {
//        Group group = modelMapper.map(groupDTO, Group.class);
        return groupService.updateGroup(group);
    }
}
