package nl.bennic.rest.domotica.controller;

import lombok.extern.java.Log;
import nl.bennic.rest.domotica.model.Group;
import nl.bennic.rest.domotica.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log
public class GroupController {

    @Autowired
    private GroupService groupService;

    // POST /////////////////////////////////////////////////////////////////////

    @PostMapping("/addGroup")
    public Group addGroup(@RequestBody Group group) {

        return groupService.saveGroup(group);
    }

    // GET /////////////////////////////////////////////////////////////////////

    @GetMapping("/getAllGroups")
    public List<Group> getAllGroups() {
        System.out.println("REQUEST: getAllGroups");
        return groupService.getAllGroups();
    }

    @GetMapping("/getGroupById/{id}")
    public Group getGroupById(@PathVariable String id) {
        return groupService.getGroupById(id);
    }

    // DELETE ///////////////////////////////////////////////////////////////////

    @DeleteMapping("/deleteGroup/{id}")
    public String deleteGroup(@PathVariable String id) {
        return groupService.deleteGroup(id);
    }

    // PUT //////////////////////////////////////////////////////////////////////

    @PutMapping("/updateGroup/{id}")
    public Group updateGroup(@PathVariable String id) {
//        System.out.println("UpdateGroup");
        return groupService.updateGroup(id);
    }

    @PutMapping("addDeviceToGroup/{groupId}/{deviceId}")
    public Group addDeviceToGroup(@PathVariable String groupId, @PathVariable String deviceId){
        return groupService.addDeviceToGroup(groupId, deviceId);
    }

    @PutMapping("removeDeviceFromGroup/{groupId}/{deviceId}")
    public Group removeDeviceFromGroup(@PathVariable String groupId, @PathVariable String deviceId){
        return groupService.removeDeviceFromGroup(groupId, deviceId);
    }


}
