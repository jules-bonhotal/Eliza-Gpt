package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.List;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageStorage;



/**
 * Interface defining a search strategy for message retrieval.
 * Implementations of this interface define specific search logic
 * and provide a way to identify the search option.
 */
public interface SearchStrategy {


    /**
     * Executes the search based on the given search query and
     * the message storage.
     *
     * @param searchQuery The query string for the search.
     * @param messageStorage The storage containing messages.
     * @return A list of messages matching the search criteria.
     */
    List<MessageStorage.Message> executeSearch(
                String searchQuery,
                MessageStorage messageStorage);


    // /**
    //  * Returns the name of the search option.
    //  * Used to avoid using magic numbers in UI components.
    //  *
    //  * @return The name of the search option.
    //  */
    // String getOptionName();

    /**
     * Returns a string representation of the search option.
     * By default, it is the same as the option name.
     *
     * @return A string representation of the search option.
     */
    @Override
    String toString();
}


