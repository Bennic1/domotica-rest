package nl.bennic.rest.domotica.service;

import lombok.extern.java.Log;
import nl.bennic.rest.domotica.exception.ApiRequestException;
import nl.bennic.rest.domotica.model.Device;
import nl.bennic.rest.domotica.model.Scene;
import nl.bennic.rest.domotica.repository.SceneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log
@Service
public class SceneService {

    @Autowired
    private SceneRepository sceneRepository;

    @Autowired
    private DeviceService deviceService;

    public Scene saveScene(Scene scene) {
        return sceneRepository.save(scene);
    }

    public Scene getSceneById(String id) {
        return sceneRepository.findById(id).orElse(null);
    }

    public List<Scene> getAllScenes() {
        return sceneRepository.findAll();
    }

    public String deleteScene(Scene scene) {
        log.info("Deleting Scene: " + scene + "..");
        sceneRepository.delete(scene);
        if (sceneRepository.existsById(scene.getId())) {
            log.info("Error: Scene not deleted: " + scene.getId());
            return "Error: Scene not deleted: " + scene.getId();
        } else {
            log.info("Scene deleted: " + scene.getId());
            return "Scene  deleted: " + scene.getId();
        }
    }

    public Scene updateScene(Scene scene) {
        try {
            Scene existingScene = sceneRepository.findById(scene.getId()).orElse(null);
            log.info("Updating Scene: " + scene.toString());
            return sceneRepository.save(scene);
        } catch (
                Exception e) {
            log.warning(e.getMessage());
            throw new ApiRequestException("Cannot update scene with id " + scene.getId() + ". Exception: " + e);
        }
    }

    public Scene runScene(Scene scene) {
        try {
            Scene existingScene = sceneRepository.findById(scene.getId()).orElse(null);
            log.info("\u001b[32;1m============================= Run Scene =============================\u001b[0m");
            List<Device> devicesInScene = new ArrayList<>(scene.getDevices());
            for (Device device : devicesInScene) {
                log.info("\u001b[32;1mRun Device: \u001b[0m" + device.getName());
                log.info("Delay: " + device.getDelay() + " seconds");
                for (int i = device.getDelay(); i > 0; i--) {
                    Thread.sleep(1000);
                    log.info((i - 1) + "..");
                }
                deviceService.updateDevice(device);
            }
            log.info("\u001b[32;1m============================= Scene end =============================\u001b[0m");
            return scene;
        } catch (
                Exception e) {
            log.warning(e.getMessage());
            throw new ApiRequestException("Cannot update scene with id " + scene.getId() + ". Exception: " + e);
        }
    }
}