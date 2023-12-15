package fr.univ_lyon1.info.m1.elizagpt.model;


// import java.util.List;
// import fr.univ_lyon1.info.m1.elizagpt.model.Message;
// import fr.univ_lyon1.info.m1.elizagpt.model.MessageStorage;



import java.util.ArrayList;
import java.util.List;
// import java.util.Iterator;
// import java.util.regex.Matcher;
// import java.util.regex.Pattern;


/**
 * Implementation of the SearchStrategy interface
 * for searching messages by substring.
 */
public class FindMessagesBySubstringStrategy implements SearchStrategy {
    @Override
    public List<Message> executeSearch(
            final String searchQuery,
            final MessageStorage messageStorage) {
        // Logique de recherche pour l'option 1
        // return messageStorage.findMessagesBySubstring(searchQuery);

        List<Message> matchingMessages = new ArrayList<>();

        for (Message message : messageStorage.getMessages()) {
            if (message.getMessageText().contains(searchQuery)) {
                matchingMessages.add(message);
            }
        }

        return matchingMessages;
    }


    // /**
    //  * Finds messages in the storage that contain the specified substring.
    //  *
    //  * @param substring The substring to search for.
    //  * @return A list of messages containing the specified substring.
    //  */
    // public List<Message> findMessagesBySubstring(final String substring) {
    //     List<Message> matchingMessages = new ArrayList<>();

    //     for (Message message : messages) {
    //         if (message.getMessageText().contains(substring)) {
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
        return "findMessagesBySubstring";
    }
    
}
