package fr.univ_lyon1.info.m1.elizagpt.model;


import java.util.List;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageStorage;





/**
 * Implementation of the SearchStrategy interface
 * for searching messages using a regular expression.
 */
public class RegexSearchStrategy implements SearchStrategy {
    @Override
    public List<MessageStorage.Message> executeSearch(
            final String searchQuery,
            final MessageStorage messageStorage) {
        // Logique de recherche pour l'option 1
        return messageStorage.findMessagesByRegex(searchQuery);
    }

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
