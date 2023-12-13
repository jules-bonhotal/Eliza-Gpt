package fr.univ_lyon1.info.m1.elizagpt.model;


/**
 * Represents a message with a unique identifier, message text, and user indication.
 */
// TODO : voire si c'est pas mieux d'extraire cette classe pour limiter
// les dépendance a messageStorage
public class Message {
    private String messageId;
    private String messageText;
    private boolean isUser;


    /**
     * Constructs a Message object with the specified attributes.
     *
     * @param messageId   The unique identifier of the message.
     * @param messageText The text content of the message.
     * @param isUser      True if the message is from a user, otherwise false.
     */
    public Message(final String messageId, final String messageText, final boolean isUser) {
        this.messageId = messageId;
        this.messageText = messageText;
        this.isUser = isUser;
    }

    /**
     * Checks if the message is from a user.
     *
     * @return True if the message is from a user, otherwise false.
     */
    public boolean isUserMessage() {
        return isUser;
    }

    /**
     * Gets the unique identifier of the message.
     *
     * @return The message identifier.
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Gets the text content of the message.
     *
     * @return The message text.
     */
    public String getMessageText() {
        return messageText;
    }
}
