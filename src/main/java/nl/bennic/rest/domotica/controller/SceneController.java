package nl.bennic.rest.domotica.controller;

import lombok.extern.java.Log;
import nl.bennic.rest.domotica.model.Scene;
import nl.bennic.rest.domotica.service.SceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@Log
@RequestMapping
public class SceneController {

    @Autowired
    private SceneService sceneService;

    // POST /////////////////////////////////////////////////////////////////////

    @PostMapping("/addScene")
    public Scene addScene(@RequestBody Scene scene) {
        return sceneService.saveScene(scene);
    }

    // GET /////////////////////////////////////////////////////////////////////

    @GetMapping("/getAllScenes")
    public List<Scene> getAllScenes() {
        log.info("REQUEST: getAllScenes");
        return sceneService.getAllScenes();
    }

    @GetMapping("/getSceneById/{id}")
    public Scene getSceneById(@PathVariable String id) {
        return sceneService.getSceneById(id);
    }

    // DELETE ///////////////////////////////////////////////////////////////////

    @DeleteMapping("/deleteScene")
    public String deleteScene(@RequestBody Scene scene) {
        return sceneService.deleteScene(scene);
    }

    // PUT //////////////////////////////////////////////////////////////////////

    @PutMapping("/updateScene")
    public Scene updateScene(@RequestBody Scene scene) {
        return sceneService.updateScene(scene);
    }

    @PutMapping("/runScene")
    public Scene runScene(@RequestBody Scene scene) {
        return sceneService.runScene(scene);
    }

}
