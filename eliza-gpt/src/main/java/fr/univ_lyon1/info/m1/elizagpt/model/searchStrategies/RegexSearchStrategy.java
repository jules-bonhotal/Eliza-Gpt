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
public class RegexSearchStrategy implements SearchStrategy {
    @Override
    public List<Message> executeSearch(
            final String searchQuery,
            final MessageStorage messageStorage) {
        // Logique de recherche pour l'option 1
        // return messageStorage.findMessagesByRegex(searchQuery);


        List<Message> matchingMessages = new ArrayList<>();
        Pattern pattern = Pattern.compile(searchQuery, Pattern.CASE_INSENSITIVE);

        for (Message message : messageStorage.getMessages()) {
            Matcher matcher = pattern.matcher(message.getMessageText());
            if (matcher.find()) {
                matchingMessages.add(message);
            }
        }

        return matchingMessages;
    }

    //     /**
    //  * Find messages in the storage that match the given regular expression.
    //  *
    //  * @param regexPattern The regular expression pattern to match.
    //  * @return A list of messages that match the given regular expression.
    //  */
    // public List<Message> findMessagesByRegex(final String regexPattern) {

    // }

    /**
     * Returns the name of the search option.
     *
     * @return The name of the search option.
     */
    @Override
    public String toString() {
        return "RegexSearch";
    }
    
}
