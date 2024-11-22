package tn.esprit.tpfoyer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer.control.EtudiantRestController;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.service.IEtudiantService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EtudiantRestController.class)
class EtudiantRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEtudiantService etudiantService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetEtudiants() throws Exception {
        // Arrange
        Etudiant etudiant1 = new Etudiant(1L, "John", "Doe", 12345678L, new Date(), null);
        Etudiant etudiant2 = new Etudiant(2L, "Jane", "Smith", 87654321L, new Date(), null);
        List<Etudiant> etudiants = Arrays.asList(etudiant1, etudiant2);

        when(etudiantService.retrieveAllEtudiants()).thenReturn(etudiants);

        // Act & Assert
        mockMvc.perform(get("/etudiant/retrieve-all-etudiants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nomEtudiant", is("John")))
                .andExpect(jsonPath("$[1].prenomEtudiant", is("Smith")));
    }

    @Test
    void testRetrieveEtudiant() throws Exception {
        // Arrange
        Etudiant etudiant = new Etudiant(1L, "John", "Doe", 12345678L, new Date(), null);
        when(etudiantService.retrieveEtudiant(1L)).thenReturn(etudiant);

        // Act & Assert
        mockMvc.perform(get("/etudiant/retrieve-etudiant/{etudiant-id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant", is("John")));
    }

    @Test
    void testAddEtudiant() throws Exception  {
        // Arrange
        Etudiant etudiant = new Etudiant(0L, "John", "Doe", 12345678L, new Date(), null);
        when(etudiantService.addEtudiant(any(Etudiant.class))).thenReturn(etudiant);

        // Act & Assert
        mockMvc.perform(post("/etudiant/add-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(etudiant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant", is("John")));
    }

    @Test
    void testModifyEtudiant() throws Exception {
        // Arrange
        Etudiant etudiant = new Etudiant(1L, "John", "Doe", 12345678L, new Date(), null);
        etudiant.setNomEtudiant("Updated John");
        when(etudiantService.modifyEtudiant(any(Etudiant.class))).thenReturn(etudiant);

        // Act & Assert
        mockMvc.perform(put("/etudiant/modify-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(etudiant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant", is("Updated John")));
    }

    @Test
    void testRemoveEtudiant() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/etudiant/remove-etudiant/{etudiant-id}", 1L))
                .andExpect(status().isOk());

        verify(etudiantService, times(1)).removeEtudiant(1L);
    }
}
