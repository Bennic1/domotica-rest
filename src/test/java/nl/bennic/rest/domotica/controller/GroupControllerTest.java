package nl.bennic.rest.domotica.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.bennic.rest.domotica.model.Group;
import nl.bennic.rest.domotica.model.Scene;
import nl.bennic.rest.domotica.repository.GroupRepository;
import nl.bennic.rest.domotica.service.GroupService;
import nl.bennic.rest.domotica.service.SceneService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
class GroupControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    @Autowired
//    private GroupService groupService;
//
//    @Mock
//    @Autowired
//    private GroupRepository groupRepository;
//
//    @InjectMocks
//    private GroupController groupController;
//
//    @Before
//    public void setUp() throws Exception {
//        mockMvc = MockMvcBuilders.standaloneSetup(groupController)
//                .build();
//    }
//
//    @Test
//    public void addGroup() throws Exception {
//        Group group = new Group();
//        group.setName("Group1");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String sceneString = objectMapper.writeValueAsString(group);
//
////        when(sceneService.saveScene(any())).thenReturn(scene1);
//
//
//        mockMvc.perform(post("/addGroup")
//                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                .content(sceneString))
////                .andExpect(status().isCreated())
//                .andDo(print())
//                .andExpect(jsonPath("$.name").value("Group1"))
//                .andReturn();
//    }
}