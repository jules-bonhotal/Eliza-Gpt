package fr.univ_lyon1.info.m1.elizagpt.model.modifytext;

import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVReader;

/**
 * Classe pour lire les verbes à partir d'un fichier CSV.
 */
public class VerbDAO {
    /** Chemin du fichier CSV contenant les verbes. */
    private final String filePath;

    /**
     * Constructeur de la classe VerbDAO.
     *
     * @param filePath Chemin du fichier CSV contenant les verbes.
     */
    public VerbDAO(final String filePath) {
        this.filePath = filePath;
    }

    /**
     * Charge la liste des verbes depuis le fichier CSV.
     *
     * @return Liste des verbes chargée depuis le fichier CSV.
     * @throws CsvValidationException Si une erreur de validation CSV survient.
     */
    public List<Verb> loadVerbs() throws CsvValidationException {
        List<Verb> verbs = new ArrayList<>();
        try (Reader reader = new FileReader(filePath);
             CSVReader csvReader = new CSVReader(reader)) {

            String[] tabVerbs;
            while ((tabVerbs = csvReader.readNext()) != null) {
                if (tabVerbs.length > 9) {
                    verbs.add(new Verb(tabVerbs[5], tabVerbs[9]));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier CSV: " + filePath, e);
        }
        return verbs;
    }
}
