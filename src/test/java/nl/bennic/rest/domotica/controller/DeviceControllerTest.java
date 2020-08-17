package nl.bennic.rest.domotica.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import nl.bennic.rest.domotica.model.Device;
import nl.bennic.rest.domotica.service.DeviceService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DeviceController.class)
class DeviceControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private DeviceService deviceService;
//
//    @Test
//    public void addDevice() throws Exception {
//
//        String name = "test_device";
//        String ip =  "192.168.2.201";
//        boolean state = false;
//        int delay = 0;
//
//        //Voer de test uit met een Device object
//        Device device = new Device(null, name, ip, state, delay);
//        when(deviceService.saveDevice(any())).thenReturn(device);
//
//        //Voer de test uit met JSON, zodat ook het parsen getest wordt
//        this.mockMvc
//                .perform(post("/addDevice")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"name\":\"test_device\",\"ip\":\"192.168.2.201\",\"state\":\"false\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value(name))
//                .andExpect(jsonPath("$.ip").value(ip))
//                .andExpect(jsonPath("$.state").value(state));
//
//        //Vergelijk de uitkomst
//        ArgumentCaptor<Device> deviceArgumentCaptor = ArgumentCaptor.forClass(Device.class);
//        verify(deviceService).saveDevice(deviceArgumentCaptor.capture());
//        assertThat(deviceArgumentCaptor.getValue().getName()).isEqualTo(name);
//        assertThat(deviceArgumentCaptor.getValue().getIp()).isEqualTo(ip);
//        assertThat(deviceArgumentCaptor.getValue().getState()).isEqualTo(state);
//    }
}