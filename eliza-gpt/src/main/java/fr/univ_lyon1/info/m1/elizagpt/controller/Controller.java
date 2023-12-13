package fr.univ_lyon1.info.m1.elizagpt.controller;

import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;

/**
 * The Controller class acts as an intermediary between the user interface
 * and the underlying message processing logic.
 */
public class Controller {

	private MessageProcessor processor;

    /**
     * Constructs a Controller with the specified MessageProcessor.
     *
     * @param messageProcessor The MessageProcessor used for message processing.
     */
	public Controller(final MessageProcessor messageProcessor) {
		processor = messageProcessor;
	}

    /**
     * Sends a user message to the underlying message processor for processing.
     *
     * @param text The user input message to be sent and processed.
     */
	public void sendMessage(final String text) {
	    processor.sendMessage(text);
	}



}
