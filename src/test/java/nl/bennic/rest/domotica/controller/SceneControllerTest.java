package nl.bennic.rest.domotica.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.bennic.rest.domotica.model.Device;
import nl.bennic.rest.domotica.model.Scene;
import nl.bennic.rest.domotica.service.SceneService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebMvcTest(SceneController.class)
public class SceneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SceneService sceneService;


    @Test
    public void addScene() throws Exception {
        Scene scene1 = new Scene();
        scene1.setName("Scene1");
        ObjectMapper objectMapper = new ObjectMapper();
        String sceneString = objectMapper.writeValueAsString(scene1);

        when(sceneService.saveScene(any())).thenReturn(scene1);

        mockMvc.perform(post("/addScene")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sceneString))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Scene1"))
                .andReturn();
    }

    @Test
    public void getAllScenes() throws Exception {
        List<Device> deviceList1 = new ArrayList<>();
        deviceList1.add(new Device("aaa", "Device1", "192.168.1.1", false, 0));
        deviceList1.add(new Device("bbb", "Device2", "192.168.1.2", false, 0));

        List<Device> deviceList2 = new ArrayList<>();
        deviceList2.add(new Device("ccc", "Device3", "192.168.1.3", false, 0));
        deviceList2.add(new Device("ddd", "Device4", "192.168.1.4", false, 0));

        List<Scene> sceneList = new ArrayList<>();
        Scene scene1 = new Scene("s111", "Scene1", deviceList1);
        Scene scene2 = new Scene("s222", "Scene2", deviceList2);
        sceneList.add(scene1);
        sceneList.add(scene2);

        ObjectMapper objectMapper = new ObjectMapper();
        String sceneListString = objectMapper.writeValueAsString(sceneList);

        when(sceneService.getAllScenes()).thenReturn(Stream.of(scene1, scene2).collect(Collectors.toList()));

        mockMvc
                .perform(get("/getAllScenes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sceneListString))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2))) //2 scenes in List
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", Matchers.containsInAnyOrder("Scene1", "Scene2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].devices[*].name", Matchers.containsInAnyOrder("Device1", "Device2", "Device3", "Device4")))
                .andReturn();
    }

//    @Test
//    public void getSceneById() {
//    }
//
//    @Test
//    public void deleteScene() {
//    }
//
    @Test
    public void updateScene() throws Exception {

        List<Device> deviceList1 = new ArrayList<>();
        deviceList1.add(new Device("aaa", "Device1", "192.168.1.1", false, 0));
        deviceList1.add(new Device("bbb", "Device2", "192.168.1.2", false, 0));

        Scene scene1 = new Scene("s111", "Scene1", deviceList1);

        ObjectMapper objectMapper = new ObjectMapper();
        String sceneString = objectMapper.writeValueAsString(scene1);

        when(sceneService.updateScene(any())).thenReturn(scene1);

        mockMvc
                .perform(put("/updateScene")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sceneString))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Scene1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.devices[*].name", Matchers.containsInAnyOrder("Device1", "Device2")))
                .andReturn();
    }


//    @Test
//    public void runScene() {
//    }
}