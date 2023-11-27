package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageStorageTest {

    @Test
    void testStoreMessage() {
        // Given
        MessageStorage messageStorage = new MessageStorage();

        // When
        messageStorage.storeMessage("Test message");

        // Then
        assertThat(messageStorage.getMessages(), contains("Test message"));
    }

    @Test
    void testAddMessage() {
        // Given
        MessageStorage messageStorage = new MessageStorage();

        // When
        messageStorage.addMessage("Test message");

        // Then
        assertThat(messageStorage.getMessages(), contains("Test message"));
    }

    @Test
    void testGetMessages() {
        // Given
        MessageStorage messageStorage = new MessageStorage();
        messageStorage.addMessage("Message 1");
        messageStorage.addMessage("Message 2");

        // When
        List<String> messages = messageStorage.getMessages();

        // Then
        assertThat(messages, contains("Message 1", "Message 2"));
    }

    @Test
    void testRegisterObserver() {
        // Given
        MessageStorage messageStorage = new MessageStorage();
        MessageObserver observer = new TestMessageObserver();

        // When
        messageStorage.registerObserver(observer);

        // Then
        assertThat(messageStorage.getObservers(), contains(observer));
    }

    @Test
    void testRemoveObserver() {
        // Given
        MessageStorage messageStorage = new MessageStorage();
        MessageObserver observer = new TestMessageObserver();
        messageStorage.registerObserver(observer);

        // When
        messageStorage.removeObserver(observer);

        // Then
        assertThat(messageStorage.getObservers(), not(contains(observer)));
    }


    //class de test
    private static class TestMessageObserver implements MessageObserver {
        @Override
        public void update() {
            // Do nothing in this test observer
        }
    }
}
