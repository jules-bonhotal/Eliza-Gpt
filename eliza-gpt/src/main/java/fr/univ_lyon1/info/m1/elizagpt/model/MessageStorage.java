package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.ArrayList;
import java.util.List;

public class MessageStorage {
    private List<MessageObserver> observers = new ArrayList<>();
    private List<String> messages = new ArrayList<>();

    public void storeMessage(String message) {
        messages.add(message);
        notifyObservers(message);
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    private void notifyObservers(String message) {
        for (MessageObserver observer : observers) {
            observer.update();
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
}