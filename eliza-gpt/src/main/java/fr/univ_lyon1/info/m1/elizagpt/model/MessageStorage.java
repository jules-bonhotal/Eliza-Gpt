package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;


public class MessageStorage {
    private List<MessageObserver> observers = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();

    public void addMessage(String messageId, String messageText, boolean isUser) {
        Message message = new Message(messageId, messageText, isUser);
        messages.add(message);
        notifyObservers("add-message");
    }

    public void removeMessageById(String messageId) {
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

    public void removeMessagesByText(String searchText) {
        Iterator<Message> iterator = messages.iterator();
        boolean wasAMessageRemoved = false;
        while (iterator.hasNext()) {
            Message message = iterator.next();
            if (message.getMessageText().equals(searchText)) {
                iterator.remove();
                wasAMessageRemoved = true;
            }
        }
        if (wasAMessageRemoved) {
            notifyObservers("removed-");
        }
    }

    public List<Message> getMessages() {
        return new ArrayList<>(messages);
    }

    private void notifyObservers(String notification) {
        for (MessageObserver observer : observers) {
            observer.update(notification);
        }
    }

    public void registerObserver(MessageObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(MessageObserver observer) {
        observers.remove(observer);
    }

    public List<MessageObserver> getObservers() {
        return new ArrayList<>(observers);
    }

    // TODO : voire si c'est pas mieux d'extraire cette classe pour limiter les dépendance a messageStorage
    public static class Message {
        private String messageId;
        private String messageText;
        boolean isUser;

        public Message(String messageId, String messageText, boolean isUser) {
            this.messageId = messageId;
            this.messageText = messageText;
            this.isUser = isUser;
        }

        public boolean isUserMessage() { return isUser; }
        public String getMessageId() {
            return messageId;
        }

        public String getMessageText() {
            return messageText;
        }
    }
}
