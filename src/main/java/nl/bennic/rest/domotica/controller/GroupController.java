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

    @DeleteMapping("/deleteGroup")
    public String deleteGroup(@RequestBody Group group) {
        return groupService.deleteGroup(group);
    }

    // PUT //////////////////////////////////////////////////////////////////////

    @PutMapping("/updateGroup")
    public Group updateGroup(@RequestBody Group group) {
        return groupService.updateGroup(group);
    }
}





//    @PutMapping("addDeviceToGroup/")
//    public Group addDeviceToGroup(@RequestBody Group group, @RequestBody Device device){
//        return groupService.addDeviceToGroup(group, device);
//    }
//
//    @PutMapping("removeDeviceFromGroup/{groupId}/{deviceId}")
//    public Group removeDeviceFromGroup(@PathVariable String groupId, @PathVariable String deviceId){
//        return groupService.removeDeviceFromGroup(groupId, deviceId);
//    }
//}
