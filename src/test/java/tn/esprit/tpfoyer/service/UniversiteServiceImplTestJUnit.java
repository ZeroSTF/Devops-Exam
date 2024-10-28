package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.UniversiteRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UniversiteServiceImplTestJUnit {

    @Mock
    private UniversiteRepository universiteRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    private Universite universite;

    @BeforeEach
    void setUp() {
        // Initialize a Universite instance for use in all tests
        universite = new Universite();
        universite.setIdUniversite(1L);
        universite.setNomUniversite("ESPRIT");
        universite.setAdresse("Tunis");
    }

    @Test
    void retrieveAllUniversites() {
        // Mock the repository to return a list containing the Universite instance
        when(universiteRepository.findAll()).thenReturn(Arrays.asList(universite));

        // Call the service method
        List<Universite> universites = universiteService.retrieveAllUniversites();

        // Verify the results
        assertNotNull(universites, "The list of universities should not be null");
        assertEquals(1, universites.size(), "The list size should be 1");
        assertEquals("ESPRIT", universites.get(0).getNomUniversite(), "The university name should be 'ESPRIT'");

        // Verify the repository interaction
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    void retrieveUniversite() {
        // Mock the repository to return an Optional containing the Universite instance
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        // Call the service method
        Universite foundUniversite = universiteService.retrieveUniversite(1L);

        // Verify the results
        assertNotNull(foundUniversite, "The retrieved university should not be null");
        assertEquals(1L, foundUniversite.getIdUniversite(), "The ID should match");
        assertEquals("ESPRIT", foundUniversite.getNomUniversite(), "The university name should match");

        // Verify the repository interaction
        verify(universiteRepository, times(1)).findById(1L);
    }

    @Test
    void addUniversite() {
        // Mock the repository to save the Universite instance
        when(universiteRepository.save(universite)).thenReturn(universite);

        // Call the service method
        Universite savedUniversite = universiteService.addUniversite(universite);

        // Verify the results
        assertNotNull(savedUniversite, "The saved university should not be null");
        assertEquals("ESPRIT", savedUniversite.getNomUniversite(), "The university name should match");

        // Verify the repository interaction
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void modifyUniversite() {
        // Mock the repository to save the Universite instance
        when(universiteRepository.save(universite)).thenReturn(universite);

        // Call the service method
        Universite modifiedUniversite = universiteService.modifyUniversite(universite);

        // Verify the results
        assertNotNull(modifiedUniversite, "The modified university should not be null");
        assertEquals("ESPRIT", modifiedUniversite.getNomUniversite(), "The university name should match");

        // Verify the repository interaction
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void removeUniversite() {
        // Call the service method to remove the Universite by ID
        universiteService.removeUniversite(1L);

        // Verify the repository interaction
        verify(universiteRepository, times(1)).deleteById(1L);
    }
}
