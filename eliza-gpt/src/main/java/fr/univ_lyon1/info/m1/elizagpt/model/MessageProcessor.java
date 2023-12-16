package fr.univ_lyon1.info.m1.elizagpt.model;

import fr.univ_lyon1.info.m1.elizagpt.model.modifytext.TextProcessor;

import java.util.List;
import java.util.UUID;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Logic to process a message (and probably reply to it).
 */
public class MessageProcessor implements MessageObserver {
    private final MessageStorage messageStorage;
    private final Random random = new Random();
    //private final VerbCollection verbCollection;

    @Override
    public void update(final String norification) {
        //TODO     
    }

    /**
     * Constructs a MessageProcessor by copying a specified MessageStorage.
     * 
     * @param messageStorage The MessageStorage to be copied with the MessageProcessor.
     */
    public MessageProcessor(final MessageStorage messageStorage) {
        this.messageStorage = messageStorage;
        this.messageStorage.registerObserver(this);
        //this.verbCollection = verbCollection;
    }

    /**
     * Normlize the text: remove extra spaces, add a final dot if missing.
     * @param text
     * @return normalized text.
     */
    public String normalize(final String text) {
        return text.replaceAll("\\s+", " ")
                .replaceAll("^\\s+", "")
                .replaceAll("\\s+$", "")
                .replaceAll("[^\\.!?:]$", "$0.");
    }

    /**
     * Extract the name of the user from the dialog.
     * @return
     */
    protected String getName() {
        List<Message> messages = messageStorage.getMessages();

        for (Message message : messages) {
            String messageText = message.getMessageText();
            Pattern pattern = Pattern.compile(".*Je m'appelle (.*)\\.", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(messageText);

            if (matcher.matches()) {
                return matcher.group(1);
            }
        }

        return null;
    }

    /**
     * Processes user input and generates a response based on predefined patterns.
     *
     * @param text The user input to be processed.
     * @return A generated response based on the analyzed patterns in the user input.
     */

// TODO : a revoir pour que ce soit pas une suite de if else et implementer un pattern
    public String processUserInput(final String text) {
        String normalizedText = normalize(text);
    
        Pattern pattern;
        Matcher matcher;
    
        // First, try to answer specifically to what the user said
        pattern = Pattern.compile(".*Je m'appelle (.*)\\.", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(normalizedText);
        if (matcher.matches()) {
            return "Bonjour " + matcher.group(1) + ".";
        }

        pattern = Pattern.compile("Quel est mon nom \\?", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(normalizedText);
        if (matcher.matches()) {
             if (getName() != null) {
                 return "Votre nom est " + getName() + ".";
             } else {
                return "Je ne connais pas votre nom.";
             }
        }
        pattern = Pattern.compile("Qui est le plus (.*) \\?", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(normalizedText);
        if (matcher.matches()) {
            return "Le plus " + matcher.group(1) + " est bien sûr votre enseignant de MIF01 !";
        }
        pattern = Pattern.compile("((Je .*)|(J'.*))\\.", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(normalizedText);
        if (matcher.matches()) {
            final String startQuestion = pickRandom(new String[] {
                "Pourquoi dites-vous que ",
                "Pourquoi pensez-vous que ",
                "Êtes-vous sûr que ",
            });
            return startQuestion + firstToSecondPerson(matcher.group(1)) + " ?";
        }
        pattern = Pattern.compile("(.*)\\?", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(normalizedText);
        if (matcher.matches()) {
            final String reponse = pickRandom(new String[] {//TODO VOIR SI CA DEVRAIT ETRE LA
                    "Ici, c'est moi qui pose les questions. ",
                    "Je vous renvoie la question. ",
            });
            return reponse;
        }

        // Nothing clever to say, answer randomly
        if (random.nextBoolean()) {
            return "Il faut beau aujourd'hui, vous ne trouvez pas ?";
        }
        if (random.nextBoolean()) {
            return "Je ne comprends pas.";
        }
        if (random.nextBoolean()) {
            return "Hmmm, hmm ...";
        }
        // Default answer
        if (getName() != null) {
            return "Qu'est-ce qui vous fait dire cela, " + getName() + " ?";
        } else {
            return "Qu'est-ce qui vous fait dire cela ?";
        }
    }

    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    private void replyToUser(final String text) {
        String uniqueId = generateUniqueId();
        messageStorage.addMessage(uniqueId, text, false);
    }
        
    /**
     * Sends a user message to the message storage.
     *
     * @param text The user input message to be sent and processed.
     */
    // TODO : voire si c'est vraimment bien de le mettre en public ou si c'est un truc a 
    // réglé en passant par le controller
    public void sendMessage(final String text) {
        String uniqueId = generateUniqueId();
        messageStorage.addMessage(uniqueId, text, true);

        String reply = processUserInput(text);
        replyToUser(reply);
    }

    /**
     * Turn a 1st-person sentence (Je ...) into a plural 2nd person (Vous ...).
     * The result is not capitalized to allow forming a new sentence.
     *
     * TODO: does not deal with all 3rd group verbs.
     *
     * @param text
     * @return The 2nd-person sentence.
     */
    public String firstToSecondPerson(final String text) {
        String processedText = TextProcessor.modifyPronouns(text);
        processedText = TextProcessor.modifyVerbs(processedText);
        processedText = TextProcessor.modifyDeterminers(processedText);
        return processedText;
    }

    /** Pick an element randomly in the array. */
    public <T> T pickRandom(final T[] array) {
        return array[random.nextInt(array.length)];
    }
}
