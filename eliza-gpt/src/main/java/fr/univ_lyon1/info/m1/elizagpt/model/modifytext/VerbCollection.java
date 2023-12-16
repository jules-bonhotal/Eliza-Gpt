package fr.univ_lyon1.info.m1.elizagpt.model.modifytext;

import com.opencsv.exceptions.CsvValidationException;

import java.util.List;

/**
 * Classe utilitaire pour gérer la collection de verbes.
 */
public final class VerbCollection {

    /** Liste de verbes chargée depuis le fichier CSV. */
    private static final List<Verb> VERBS;

    /** Constructeur privé pour une classe utilitaire. */
    private VerbCollection() {
    }

    static {
        String filePath = "src/main/resources/verbs.csv";
        VerbDAO verbDAO = new VerbDAO(filePath);
        try {
            VERBS = verbDAO.loadVerbs();
        } catch (CsvValidationException e) {
            throw new RuntimeException("Erreur lors du chargement des verbes", e);
        }
    }

    /**
     * Récupère la liste des verbes.
     *
     * @return Liste de verbes.
     */
    public static List<Verb> getVerbs() {
        return VERBS;
    }
}
