package fr.univ_lyon1.info.m1.elizagpt.model.modifytext;

import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;


/**
 * Tests for VerbDAO, Verb, and TextProcessor.
 */
public class ModifyTextTest {

    @Test
    void testVerbConjugation() {
        Verb verb = new Verb("parle", "parlez");

        assertThat(verb.getFirstSingular(), is("parle"));
        assertThat(verb.getSecondPlural(), is("parlez"));
    }

    @Test
    void testModifyPronouns() {
        String inputText = "j'ai faim.";
        String modifiedText = TextProcessor.modifyPronouns(inputText);

        assertThat(modifiedText, is("vous ai faim."));
    }

    @Test
    void testModifyVerbs() {
        String inputText = "Je parle français.";
        String modifiedText = TextProcessor.modifyVerbs(inputText);

        assertThat(modifiedText, is("Je parlez français."));
    }

    @Test
    void testModifyDeterminers() {
        String inputText = "Mon ami est gentil.";
        String modifiedText = TextProcessor.modifyDeterminers(inputText);

        assertThat(modifiedText, is("votre ami est gentil."));
    }

    @Test
    void testGetVerbs() {
        List<Verb> verbs = VerbCollection.getVerbs();

        assertThat(verbs, notNullValue());
    }

    @Test
    void testLoadVerbs() {
        String filePath = "src/main/resources/verbs.csv";

        VerbDAO verbDAO = new VerbDAO(filePath);
        List<Verb> verbs;
        try {
            verbs = verbDAO.loadVerbs();
        } catch (CsvValidationException e) {
            throw new RuntimeException("Error loading verbs from CSV", e);
        }

        assertThat(verbs, notNullValue());
    }
}
