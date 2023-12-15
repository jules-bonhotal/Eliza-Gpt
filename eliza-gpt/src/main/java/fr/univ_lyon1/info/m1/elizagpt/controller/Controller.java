package fr.univ_lyon1.info.m1.elizagpt.controller;

import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageStorage;
import fr.univ_lyon1.info.m1.elizagpt.model.Message;
import fr.univ_lyon1.info.m1.elizagpt.model.SearchStrategy;
import java.util.List;

/**
 * The Controller class acts as an intermediary between the user interface
 * and the underlying message processing logic.
 */
public class Controller {

    private MessageProcessor processor;
	private MessageStorage storage;

    /**
     * Constructs a Controller with the specified MessageProcessor.
     *
     * @param messageProcessor The MessageProcessor used for message processing.
     */
	public Controller(final MessageProcessor messageProcessor,
                      final MessageStorage messageStorage) {
		processor = messageProcessor;
        storage = messageStorage;
	}

    /**
     * Sends a user message to the underlying message processor for processing.
     *
     * @param text The user input message to be sent and processed.
     */
	public void sendMessage(final String text) {
	    processor.sendMessage(text);
	}


    /**
     * Removes a message by its unique identifier and notifies observers.
     *
     * @param messageId The unique identifier of the message to be removed.
     */
    public void removeMessageById(final String messageId) {
        storage.removeMessageById(messageId);
    }

    /**
     * Retrieves a copy of the list of messages stored in the MessageStorage.
     *
     * @return A list of Message objects.
     */
    public List<Message> getMessages() {
        return storage.getMessages();
    }


    /**
     * Executes a search using the provided search strategy.
     * Serves to avoid storing an instance of messageStorage in the view
     *
     * @param selectedOption The selected search strategy.
     * @param searchQuery   The regular expression pattern for the search.
     * @return A list of messages matching the search criteria.
     */
    public List<Message> executeSearchController(final SearchStrategy selectedOption,
                                                 final String searchQuery) {
        return selectedOption.executeSearch(searchQuery, storage);
    }

}
