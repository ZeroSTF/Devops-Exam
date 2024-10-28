package tn.esprit.tpfoyer.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.service.IEtudiantService;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.*;

@WebMvcTest(EtudiantRestController.class)
public class EtudiantRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEtudiantService etudiantService;

    @Autowired
    private ObjectMapper objectMapper;

    private Etudiant etudiant1;
    private Etudiant etudiant2;

    @BeforeEach
    void setUp() {
        etudiant1 = new Etudiant();
        etudiant1.setIdEtudiant(1L);
        etudiant1.setNomEtudiant("Doe");
        etudiant1.setPrenomEtudiant("John");
        etudiant1.setCinEtudiant(12345678);
        etudiant1.setDateNaissance(new Date());

        etudiant2 = new Etudiant();
        etudiant2.setIdEtudiant(2L);
        etudiant2.setNomEtudiant("Smith");
        etudiant2.setPrenomEtudiant("Jane");
        etudiant2.setCinEtudiant(87654321);
        etudiant2.setDateNaissance(new Date());
    }

    @Test
    void testGetEtudiants_MultipleStudents() throws Exception {
        when(etudiantService.retrieveAllEtudiants())
                .thenReturn(Arrays.asList(etudiant1, etudiant2));

        mockMvc.perform(get("/etudiant/retrieve-all-etudiants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomEtudiant").value("Doe"))
                .andExpect(jsonPath("$[1].nomEtudiant").value("Smith"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetEtudiants_EmptyList() throws Exception {
        when(etudiantService.retrieveAllEtudiants())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/etudiant/retrieve-all-etudiants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testRetrieveEtudiantParCin_ExistingCin() throws Exception {
        when(etudiantService.recupererEtudiantParCin(12345678))
                .thenReturn(etudiant1);

        mockMvc.perform(get("/etudiant/retrieve-etudiant-cin/{cin}", 12345678))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant").value("Doe"))
                .andExpect(jsonPath("$.cinEtudiant").value(12345678));
    }

    @Test
    void testRetrieveEtudiantParCin_NonExistingCin() throws Exception {
        when(etudiantService.recupererEtudiantParCin(99999999))
                .thenReturn(null);

        mockMvc.perform(get("/etudiant/retrieve-etudiant-cin/{cin}", 99999999))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void testAddEtudiant_ValidData() throws Exception {
        when(etudiantService.addEtudiant(any(Etudiant.class)))
                .thenReturn(etudiant1);

        mockMvc.perform(post("/etudiant/add-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(etudiant1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant").value("Doe"))
                .andExpect(jsonPath("$.prenomEtudiant").value("John"));
    }

    @Test
    void testAddEtudiant_InvalidData() throws Exception {
        Etudiant invalidEtudiant = new Etudiant();
        // Empty student with no data

        mockMvc.perform(post("/etudiant/add-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEtudiant)))
                .andExpect(status().isOk()); // Or .andExpect(status().isBadRequest()) if you add validation
    }

    @Test
    void testModifyEtudiant_ExistingEtudiant() throws Exception {
        when(etudiantService.modifyEtudiant(any(Etudiant.class)))
                .thenReturn(etudiant1);

        mockMvc.perform(put("/etudiant/modify-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(etudiant1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant").value("Doe"));
    }

    @Test
    void testRemoveEtudiant_ExistingId() throws Exception {
        doNothing().when(etudiantService).removeEtudiant(1L);

        mockMvc.perform(delete("/etudiant/remove-etudiant/{etudiant-id}", 1L))
                .andExpect(status().isOk());

        verify(etudiantService, times(1)).removeEtudiant(1L);
    }
}