package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

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
    void testRetrieveAllEtudiants_WithMultipleStudents() {
        List<Etudiant> etudiants = Arrays.asList(etudiant1, etudiant2);
        when(etudiantRepository.findAll()).thenReturn(etudiants);

        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Doe", result.get(0).getNomEtudiant());
        assertEquals("Smith", result.get(1).getNomEtudiant());
    }

    @Test
    void testRetrieveAllEtudiants_EmptyList() {
        when(etudiantRepository.findAll()).thenReturn(Collections.emptyList());

        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testRetrieveEtudiant_ExistingId() {
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant1));

        Etudiant result = etudiantService.retrieveEtudiant(1L);

        assertNotNull(result);
        assertEquals("Doe", result.getNomEtudiant());
    }

    @Test
    void testRetrieveEtudiant_NonExistingId() {
        when(etudiantRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            etudiantService.retrieveEtudiant(99L);
        });
    }

    @Test
    void testAddEtudiant_ValidData() {
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant1);

        Etudiant result = etudiantService.addEtudiant(etudiant1);

        assertNotNull(result);
        assertEquals(etudiant1.getNomEtudiant(), result.getNomEtudiant());
        verify(etudiantRepository).save(etudiant1);
    }

    @Test
    void testAddEtudiant_NullNom() {
        Etudiant etudiantWithNullNom = new Etudiant();
        etudiantWithNullNom.setPrenomEtudiant("John");
        etudiantWithNullNom.setCinEtudiant(12345678);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            etudiantService.addEtudiant(etudiantWithNullNom);
        });

        assertEquals("Nom etudiant cannot be null or empty", exception.getMessage());
    }

    @Test
    void testAddEtudiant_InvalidCIN() {
        Etudiant etudiantWithInvalidCIN = new Etudiant();
        etudiantWithInvalidCIN.setNomEtudiant("Doe");
        etudiantWithInvalidCIN.setPrenomEtudiant("John");
        etudiantWithInvalidCIN.setCinEtudiant(0); // Invalid CIN

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            etudiantService.addEtudiant(etudiantWithInvalidCIN);
        });

        assertEquals("CIN must be a positive number", exception.getMessage());
    }

    @Test
    void testModifyEtudiant_ExistingEtudiant() {
        Etudiant modifiedEtudiant = new Etudiant();
        modifiedEtudiant.setIdEtudiant(1L);
        modifiedEtudiant.setNomEtudiant("DoeModified");
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(modifiedEtudiant);

        Etudiant result = etudiantService.modifyEtudiant(modifiedEtudiant);

        assertEquals("DoeModified", result.getNomEtudiant());
    }

    @Test
    void testRecupererEtudiantParCin_ExistingCin() {
        when(etudiantRepository.findEtudiantByCinEtudiant(12345678)).thenReturn(etudiant1);

        Etudiant result = etudiantService.recupererEtudiantParCin(12345678);

        assertNotNull(result);
        assertEquals(12345678, result.getCinEtudiant());
    }

    @Test
    void testRecupererEtudiantParCin_NonExistingCin() {
        when(etudiantRepository.findEtudiantByCinEtudiant(99999999)).thenReturn(null);

        Etudiant result = etudiantService.recupererEtudiantParCin(99999999);

        assertNull(result);
    }
}