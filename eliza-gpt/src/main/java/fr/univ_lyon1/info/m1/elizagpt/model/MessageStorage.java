package fr.univ_lyon1.info.m1.elizagpt.model;

// import fr.univ_lyon1.info.m1.elizagpt.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Manages a collection of messages and notifies observers of changes.
 */
public class MessageStorage {
    private List<MessageObserver> observers = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();

    /**
     * Constructs a new MessageStorage and initializes it with a default message.
     */
    public MessageStorage() {
        addMessage("0", "Bonjour!", false);
        notifyObservers("add-message");

    }

    /**
     * Adds a message to the storage and notifies observers.
     *
     * @param messageId   The unique identifier for the message.
     * @param messageText The content of the message.
     * @param isUser      Indicates whether the message is from a user.
     */
    public void addMessage(final String messageId,
                final String messageText,
                final boolean isUser) {
        Message message = new Message(messageId, messageText, isUser);
        messages.add(message);
        notifyObservers("add-message");
    }

    /**
     * Removes a message by its unique identifier and notifies observers.
     *
     * @param messageId The unique identifier of the message to be removed.
     */
    public void removeMessageById(final String messageId) {
        Iterator<Message> iterator = messages.iterator();
        while (iterator.hasNext()) {
            Message message = iterator.next();
            if (message.getMessageId().equals(messageId)) {
                iterator.remove();
                notifyObservers("removed-one-message");
                return;
            }
        }
    }


    /**
     * Removes messages that match the specified regular expression pattern and notifies observers.
     *
     * @param regexPattern The regular expression pattern to match against message texts.
     */
    public void removeMessagesByText(final String regexPattern) {
        Iterator<Message> iterator = messages.iterator();
        boolean wasAMessageRemoved = false;
        Pattern pattern = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);

        while (iterator.hasNext()) {
            Message message = iterator.next();
            Matcher matcher = pattern.matcher(message.getMessageText());

            if (matcher.find()) {
                iterator.remove();
                wasAMessageRemoved = true;
            }
        }

        if (wasAMessageRemoved) {
            notifyObservers("removed-");
        }
    }


    // TODO : voir si on peut factoriser du code pour les differents strategies 
    // mais ca a sans doute a voir avec le fait de passer le pattern strategie 
    // dans le model

    // /**
    //  * Find messages in the storage that match the given regular expression.
    //  *
    //  * @param regexPattern The regular expression pattern to match.
    //  * @return A list of messages that match the given regular expression.
    //  */
    // public List<Message> findMessagesByRegex(final String regexPattern) {
    //     List<Message> matchingMessages = new ArrayList<>();
    //     Pattern pattern = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);

    //     for (Message message : messages) {
    //         Matcher matcher = pattern.matcher(message.getMessageText());
    //         if (matcher.find()) {
    //             matchingMessages.add(message);
    //         }
    //     }

    //     return matchingMessages;
    // }


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
     * Finds messages in the storage that contain complete words matching the specified query.
     *
     * @param word The word to search for.
     * @return A list of messages containing complete words matching the specified query.
     */
    public List<Message> findMessagesByWord(final String word) {
        List<Message> matchingMessages = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\b" + word + "\\b", Pattern.CASE_INSENSITIVE);

        for (Message message : messages) {
            Matcher matcher = pattern.matcher(message.getMessageText());
            if (matcher.find()) {
                matchingMessages.add(message);
            }
        }

        return matchingMessages;
    }

    /**
     * Retrieves a copy of the list of messages stored in the MessageStorage.
     *
     * @return A list of Message objects.
     */
    public List<Message> getMessages() {
        return new ArrayList<>(messages);
    }

    /**
     * Notifies all registered observers with the given notification.
     *
     * @param notification The message notification to be sent to observers.
     */
    private void notifyObservers(final String notification) {
        for (MessageObserver observer : observers) {
            observer.update(notification);
        }
    }

    /**
     * Registers a new observer to receive notifications.
     *
     * @param observer The observer to be registered.
     */
    public void registerObserver(final MessageObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes the specified observer from the list of registered observers.
     *
     * @param observer The observer to be removed.
     */
    public void removeObserver(final MessageObserver observer) {
        observers.remove(observer);
    }

    /**
     * Retrieves a copy of the list of currently registered observers.
     *
     * @return A list of MessageObserver objects.
     */
    public List<MessageObserver> getObservers() {
        return new ArrayList<>(observers);
    }


}
