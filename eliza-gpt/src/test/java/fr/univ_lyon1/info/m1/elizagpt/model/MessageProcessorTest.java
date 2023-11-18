package fr.univ_lyon1.info.m1.elizagpt.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests for MessageProcessor.
 */
public class MessageProcessorTest {
    @Test
    void testFirstToSecondPerson() {
        // Given
        MessageProcessor p = new MessageProcessor();

        // Then
        assertThat(p.firstToSecondPerson("Je pense à mon chien."),
                is("vous pensez à votre chien."));

        assertThat(p.firstToSecondPerson("Je suis heureux."),
                is("vous êtes heureux."));

        assertThat(p.firstToSecondPerson("Je dis bonjour."),
                is("vous dites bonjour."));

        assertThat(p.firstToSecondPerson("Je vais à la mer."),
                is("vous allez à la mer."));

        assertThat(p.firstToSecondPerson("Je finis mon travail."),
                is("vous finissez votre travail."));
    }

    /**
     * Not so relevant test, but here to give an example of non-trivial
     * hamcrest assertion.
     */
    @Test
    void testVerbList() {
        assertThat(MessageProcessor.VERBS, hasItem(
                allOf(
                        hasProperty("firstSingular", is("suis")),
                        hasProperty("secondPlural", is("êtes")))));
    }


    @Test
    void testProcessUserInput() {
        MessageProcessor processor = new MessageProcessor();


        assertThat(processor.processUserInput("Qui est le plus intelligent ?"),
                is("Le plus intelligent est bien sûr votre enseignant de MIF01 !"));

        // Test random responses
        String randomResponse = processor.processUserInput("Random input");
        assertThat(randomResponse, anyOf(
                is("Il faut beau aujourd'hui, vous ne trouvez pas ?"),
                is("Je ne comprends pas."),
                is("Hmmm, hmm ..."),
                is("Qu'est-ce qui vous fait dire cela ?")));

        // Add more tests to cover other scenarios

        // Test firstToSecondPerson method
        assertThat(processor.firstToSecondPerson("Je suis content."),
                is("vous êtes contents."));
    }


    @Test
    void testProcessUserInputRememberingName() {
        assertThat(processor.processUserInput("Quel est mon nom ?"),
                is("Je ne connais pas votre nom."));

        assertThat(processor.processUserInput("Je m'appelle Alice."),
                is("Bonjour Alice."));

        assertThat(processor.processUserInput("Quel est mon nom ?"),
                is("Votre nom est Alice."));
    }

}
