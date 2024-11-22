package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    private Etudiant etudiant1;
    private Etudiant etudiant2;

    @BeforeEach
    void setUp() {
        etudiant1 = new Etudiant(1L, "John", "Doe", 12345678L, new Date(), null);
        etudiant2 = new Etudiant(2L, "Jane", "Smith", 87654321L, new Date(), null);
    }

    @Test
    void testRetrieveAllEtudiants() {
        // Arrange
        List<Etudiant> etudiants = Arrays.asList(etudiant1, etudiant2);
        Mockito.when(etudiantRepository.findAll()).thenReturn(etudiants);

        // Act
        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        // Assert
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getNomEtudiant());
    }

    @Test
    void testRetrieveEtudiant() {
        // Arrange
        Mockito.when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant1));

        // Act
        Etudiant result = etudiantService.retrieveEtudiant(1L);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getNomEtudiant());
    }

    @Test
    void testAddEtudiant() {
        // Arrange
        Mockito.when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant1);

        // Act
        Etudiant result = etudiantService.addEtudiant(etudiant1);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getNomEtudiant());
    }

    @Test
    void testModifyEtudiant() {
        // Arrange
        etudiant1.setNomEtudiant("Updated John");
        Mockito.when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant1);

        // Act
        Etudiant result = etudiantService.modifyEtudiant(etudiant1);

        // Assert
        assertNotNull(result);
        assertEquals("Updated John", result.getNomEtudiant());
    }

    @Test
    void testRemoveEtudiant() {
        // Act
        etudiantService.removeEtudiant(1L);

        // Assert
        Mockito.verify(etudiantRepository, Mockito.times(1)).deleteById(1L);
    }
}
