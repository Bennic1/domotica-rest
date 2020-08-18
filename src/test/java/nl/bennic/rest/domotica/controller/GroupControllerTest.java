package nl.bennic.rest.domotica.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.bennic.rest.domotica.model.Group;
import nl.bennic.rest.domotica.service.GroupService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupController.class)
class GroupControllerTest {

    @MockBean
    GroupService groupService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addGroup() throws Exception {
        Group group = new Group();
        group.setName("Group1");

        when(groupService.saveGroup(any())).thenReturn(group);

        mockMvc.perform(post("/addGroup")
                .content(objectMapper.writeValueAsString(group))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name").value("Group1"))
                .andReturn();
    }
}