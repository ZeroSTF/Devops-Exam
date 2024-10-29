package tn.esprit.tpfoyer.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.UniversiteRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
 class UniversiteServiceImplTest {

    @Mock
    private UniversiteRepository universiteRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    private Universite universite1;
    private Universite universite2;

    @BeforeEach
    public void setUp() {
        universite1 = new Universite();
        universite1.setIdUniversite(1L);
        universite1.setNomUniversite("Universite A");

        universite2 = new Universite();
        universite2.setIdUniversite(2L);
        universite2.setNomUniversite("Universite B");
    }

    @Test
     void testRetrieveAllUniversites() {
        // Arrange
        List<Universite> universites = Arrays.asList(universite1, universite2);
        Mockito.when(universiteRepository.findAll()).thenReturn(universites);

        // Act
        List<Universite> result = universiteService.retrieveAllUniversites();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Universite A", result.get(0).getNomUniversite());
    }

    @Test
     void testRetrieveUniversite() {
        // Arrange
        Mockito.when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite1));

        // Act
        Universite result = universiteService.retrieveUniversite(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Universite A", result.getNomUniversite());
    }

    @Test
     void testAddUniversite() {
        // Arrange
        Mockito.when(universiteRepository.save(any(Universite.class))).thenReturn(universite1);

        // Act
        Universite result = universiteService.addUniversite(universite1);

        // Assert
        assertNotNull(result);
        assertEquals("Universite A", result.getNomUniversite());
    }

    @Test
     void testModifyUniversite() {
        // Arrange
        universite1.setNomUniversite("Universite A Updated");
        Mockito.when(universiteRepository.save(any(Universite.class))).thenReturn(universite1);

        // Act
        Universite result = universiteService.modifyUniversite(universite1);

        // Assert
        assertNotNull(result);
        assertEquals("Universite A Updated", result.getNomUniversite());
    }

    @Test
     void testRemoveUniversite() {
        // Act
        universiteService.removeUniversite(1L);

        // Assert
        Mockito.verify(universiteRepository, Mockito.times(1)).deleteById(1L);
    }
}
