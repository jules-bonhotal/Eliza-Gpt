package fr.univ_lyon1.info.m1.elizagpt.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests for MessageProcessor.
 */
public class MessageProcessorTest {

    @Test
    void testFirstToSecondPerson() {
        // Given
        MessageStorage messageStorage = new MessageStorage();
        MessageProcessor p = new MessageProcessor(messageStorage);


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

    @Test
    void testProcessUserInput() {
        MessageStorage messageStorage = new MessageStorage();
        MessageProcessor processor = new MessageProcessor(messageStorage);

        assertThat(processor.processUserInput("Qui est le plus intelligent ?"),
                is("Le plus intelligent est bien sûr votre enseignant de MIF01 !"));

        // Test random responses
        String randomResponse = processor.processUserInput("Random input");
        assertThat(randomResponse, anyOf(
                is("Il fait beau aujourd'hui, vous ne trouvez pas ?"),
                is("Je ne comprends pas."),
                is("Hmmm, hmm ..."),
                is("Qu'est-ce qui vous fait dire cela ?")));
    }



    @Test
    void testProcessUserInputRememberingName() {
        MessageStorage messageStorage = new MessageStorage();
        MessageProcessor processor = new MessageProcessor(messageStorage);


        
        assertThat(processor.processUserInput("Quel est mon nom ?"),
                is("Je ne connais pas votre nom."));

        assertThat(processor.processUserInput("Je m'appelle Alice."),
                is("Bonjour Alice."));
        
        // la fonction preocessUserInput ne rajoute pas les message dans le storage en lui meme
        messageStorage.addMessage("1", "Je m'appelle Alice.", true);

        assertThat(processor.getName(), is("Alice"));

        assertThat(processor.processUserInput("Quel est mon nom ?"),
                is("Votre nom est Alice."));
    }


    @Test
    void testProcessUserInputRememberingNameLongerSentence() {
        MessageStorage messageStorage = new MessageStorage();
        MessageProcessor processor = new MessageProcessor(messageStorage);

        assertThat(processor.processUserInput("Quel est mon nom ?"),
                is("Je ne connais pas votre nom."));

        assertThat(processor.processUserInput("Tu sais Je m'appelle Alice."),
                is("Bonjour Alice."));

        // la fonction preocessUserInput ne rajoute pas les message dans le storage en lui meme
        messageStorage.addMessage("1", "Je m'appelle Alice.", true);


        assertThat(processor.getName(), is("Alice"));

        assertThat(processor.processUserInput("Quel est mon nom ?"),
                is("Votre nom est Alice."));
    }


    @Test
    void testGetName() {
        MessageStorage messageStorage = new MessageStorage();
        MessageProcessor processor = new MessageProcessor(messageStorage);

        // Test case: No messages in the storage
        assertThat(processor.getName(), is(nullValue()));

        // Test case: Name found in a single message
        messageStorage.addMessage("1", "Je m'appelle Alice.", true);
        assertThat(processor.getName(), is("Alice"));

        // Test case: Name found in multiple messages, return the first one
        messageStorage.addMessage("2", "Je m'appelle Bob.", true);
        messageStorage.addMessage("3", "Je m'appelle Carol.", true);
        assertThat(processor.getName(), is("Alice"));

        // Test case: Name not found
        messageStorage.removeMessagesByText(".*Je m'appelle(.)*");
        assertThat(processor.getName(), is(nullValue()));

        // Test case: Case-insensitive matching
        messageStorage.addMessage("4", "Je M'appelle Dave.", true);
        assertThat(processor.getName(), is("Dave"));
    }


    @Test
    void testGoodbyeHandler() {
        // Given
        MessageStorage messageStorage = new MessageStorage();
        MessageProcessor processor = new MessageProcessor(messageStorage);

        // Test "Au revoir" without knowing the name
        assertThat(processor.processUserInput("Au revoir."),
                anyOf(is("Oh non, c'est trop triste de se quitter !"), is("Au revoir !")));

        // Test "Au revoir" with knowing the name
        messageStorage.addMessage("1", "Je m'appelle Alice.", true);
        assertThat(processor.processUserInput("Au revoir."),
                anyOf(is("Oh non, c'est trop triste de se quitter Alice !"),
                      is("Au revoir Alice !")));
    }



    @Test
    void testProjectRatingHandler() {
        // Given
        MessageStorage messageStorage = new MessageStorage();
        MessageProcessor processor = new MessageProcessor(messageStorage);

        assertThat(processor.processUserInput("Combien tu penses que ce projet mérite sur 20 ?"),
        anyOf(is("20/20 ez "),
        is("Est-ce que l'on a réellement besoin de répondre à cette question ?  (⌐▨_▨) ")));

        assertThat(processor.processUserInput("Combien tu penses que ce projet mérite sur 20?"),
        anyOf(is(not("20/20 ez ")),
        is(not("Est-ce que l'on a réellement besoin de répondre à cette question ?  (⌐▨_▨) "))));

        assertThat(processor.processUserInput("Combien tu penses que ce projet mérite sur vingt ?"),
        anyOf(is(not("20/20 ez ")),
        is(not("Est-ce que l'on a réellement besoin de répondre à cette question ?  (⌐▨_▨) "))));
    }
}
