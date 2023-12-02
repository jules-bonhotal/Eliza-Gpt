package fr.univ_lyon1.info.m1.elizagpt.model;

/**
 * An interface for observers that listen for updates from a subject.
 */

public interface MessageObserver {

     /**
     * Notifies the observer about changes or updates in the MessageStorage class.
     *
     * @param notification A string indicating the type or nature of the update.
     */
    void update(String notification);
}
