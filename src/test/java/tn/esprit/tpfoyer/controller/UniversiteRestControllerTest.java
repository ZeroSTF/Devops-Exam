package tn.esprit.tpfoyer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer.control.UniversiteRestController;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.service.IUniversiteService;

import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UniversiteRestController.class)
@ActiveProfiles("test")
class UniversiteRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUniversiteService universiteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetUniversites() throws Exception {
        // Arrange
        Universite universite1 = new Universite();
        universite1.setIdUniversite(1L);
        universite1.setNomUniversite("Universite A");

        Universite universite2 = new Universite();
        universite2.setIdUniversite(2L);
        universite2.setNomUniversite("Universite B");

        List<Universite> universites = Arrays.asList(universite1, universite2);

        when(universiteService.retrieveAllUniversites()).thenReturn(universites);

        // Act & Assert
        mockMvc.perform(get("/universite/retrieve-all-universites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nomUniversite", is("Universite A")))
                .andExpect(jsonPath("$[1].nomUniversite", is("Universite B")));
    }

    @Test
    void testRetrieveUniversite() throws Exception {
        // Arrange
        Universite universite = new Universite();
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Universite A");

        when(universiteService.retrieveUniversite(1L)).thenReturn(universite);

        // Act & Assert
        mockMvc.perform(get("/universite/retrieve-universite/{universite-id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomUniversite", is("Universite A")));
    }

    @Test
    void testAddUniversite() throws Exception {
        // Arrange
        Universite universite = new Universite();
        universite.setNomUniversite("Universite A");

        when(universiteService.addUniversite(any(Universite.class))).thenReturn(universite);

        // Act & Assert
        mockMvc.perform(post("/universite/add-universite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(universite)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomUniversite", is("Universite A")));
    }

    @Test
    void testModifyUniversite() throws Exception {
        // Arrange
        Universite universite = new Universite();
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Universite A Updated");

        when(universiteService.modifyUniversite(any(Universite.class))).thenReturn(universite);

        // Act & Assert
        mockMvc.perform(put("/universite/modify-universite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(universite)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomUniversite", is("Universite A Updated")));
    }

    @Test
    void testRemoveUniversite() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/universite/remove-universite/{universite-id}", 1L))
                .andExpect(status().isOk());

        // Verify that the service method was called
        verify(universiteService, times(1)).removeUniversite(1L);
    }
}