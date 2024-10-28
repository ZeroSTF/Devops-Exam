package tn.esprit.tpfoyer.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class EtudiantServiceImpl implements IEtudiantService {


    EtudiantRepository etudiantRepository;

    public List<Etudiant> retrieveAllEtudiants() {
        return etudiantRepository.findAll();
    }
    public Etudiant retrieveEtudiant(Long etudiantId) {
        return etudiantRepository.findById(etudiantId).get();
    }
    public Etudiant addEtudiant(Etudiant c) {
        if (c == null) {
            throw new IllegalArgumentException("Etudiant cannot be null");
        }

        if (c.getNomEtudiant() == null || c.getNomEtudiant().trim().isEmpty()) {
            throw new IllegalArgumentException("Nom etudiant cannot be null or empty");
        }

        if (c.getPrenomEtudiant() == null || c.getPrenomEtudiant().trim().isEmpty()) {
            throw new IllegalArgumentException("Prenom etudiant cannot be null or empty");
        }

        if (c.getCinEtudiant() <= 0) {
            throw new IllegalArgumentException("CIN must be a positive number");
        }

        return etudiantRepository.save(c);
    }
    public Etudiant modifyEtudiant(Etudiant c) {
        return etudiantRepository.save(c);
    }
    public void removeEtudiant(Long etudiantId) {
        etudiantRepository.deleteById(etudiantId);
    }
    public Etudiant recupererEtudiantParCin(long cin)
    {
        return etudiantRepository.findEtudiantByCinEtudiant(cin);
    }



}
