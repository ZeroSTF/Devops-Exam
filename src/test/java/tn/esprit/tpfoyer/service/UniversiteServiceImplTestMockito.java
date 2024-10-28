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
class UniversiteServiceImplTestMockito {

    @Mock
    private UniversiteRepository universiteRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    private Universite universite;

    @BeforeEach
    void setUp() {
        universite = new Universite();
        universite.setIdUniversite(1L);
        universite.setNomUniversite("ESPRIT");
        universite.setAdresse("Tunis");
    }

    @Test
    void retrieveAllUniversites() {
        // Mocking the repository to return a list of universities
        when(universiteRepository.findAll()).thenReturn(Arrays.asList(universite));

        // Calling the service method
        List<Universite> universites = universiteService.retrieveAllUniversites();

        // Verifying the result
        assertNotNull(universites);
        assertEquals(1, universites.size());
        assertEquals("ESPRIT", universites.get(0).getNomUniversite());

        // Verifying interactions
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    void retrieveUniversite() {
        // Mocking the repository to return an Optional of Universite
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        // Calling the service method
        Universite foundUniversite = universiteService.retrieveUniversite(1L);

        // Verifying the result
        assertNotNull(foundUniversite);
        assertEquals(1L, foundUniversite.getIdUniversite());
        assertEquals("ESPRIT", foundUniversite.getNomUniversite());

        // Verifying interactions
        verify(universiteRepository, times(1)).findById(1L);
    }

    @Test
    void addUniversite() {
        // Mocking the repository to save the Universite entity
        when(universiteRepository.save(universite)).thenReturn(universite);

        // Calling the service method
        Universite savedUniversite = universiteService.addUniversite(universite);

        // Verifying the result
        assertNotNull(savedUniversite);
        assertEquals("ESPRIT", savedUniversite.getNomUniversite());

        // Verifying interactions
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void modifyUniversite() {
        // Mocking the repository to save the modified Universite
        when(universiteRepository.save(universite)).thenReturn(universite);

        // Calling the service method
        Universite modifiedUniversite = universiteService.modifyUniversite(universite);

        // Verifying the result
        assertNotNull(modifiedUniversite);
        assertEquals("ESPRIT", modifiedUniversite.getNomUniversite());

        // Verifying interactions
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void removeUniversite() {
        // Calling the service method to delete a Universite by ID
        universiteService.removeUniversite(1L);

        // Verifying the repository interaction
        verify(universiteRepository, times(1)).deleteById(1L);
    }
}
