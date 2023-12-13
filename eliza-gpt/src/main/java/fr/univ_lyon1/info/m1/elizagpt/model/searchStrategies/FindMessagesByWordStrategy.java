package fr.univ_lyon1.info.m1.elizagpt.model;


// import fr.univ_lyon1.info.m1.elizagpt.model.Message;
// import fr.univ_lyon1.info.m1.elizagpt.model.MessageStorage;





import java.util.ArrayList;
import java.util.List;
// import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation of the SearchStrategy interface
 * for searching messages using a regular expression.
 */
public class FindMessagesByWordStrategy implements SearchStrategy {
    @Override
    public List<Message> executeSearch(
            final String searchQuery,
            final MessageStorage messageStorage) {
        // Logique de recherche pour l'option 1
        // return messageStorage.findMessagesByWord(searchQuery);

        List<Message> matchingMessages = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\b" + searchQuery + "\\b", Pattern.CASE_INSENSITIVE);

        for (Message message : messageStorage.getMessages()) {
            Matcher matcher = pattern.matcher(message.getMessageText());
            if (matcher.find()) {
                matchingMessages.add(message);
            }
        }

        return matchingMessages;
    }





    // /**
    //  * Finds messages in the storage that contain complete words matching the specified query.
    //  *
    //  * @param word The word to search for.
    //  * @return A list of messages containing complete words matching the specified query.
    //  */
    // public List<Message> findMessagesByWord(final String word) {
    //     List<Message> matchingMessages = new ArrayList<>();
    //     Pattern pattern = Pattern.compile("\\b" + word + "\\b", Pattern.CASE_INSENSITIVE);

    //     for (Message message : messages) {
    //         Matcher matcher = pattern.matcher(message.getMessageText());
    //         if (matcher.find()) {
    //             matchingMessages.add(message);
    //         }
    //     }

    //     return matchingMessages;
    // }



    /**
     * Returns the name of the search option.
     *
     * @return The name of the search option.
     */
    @Override
    public String toString() {
        return "findMessagesByWord";
    }
    
}
