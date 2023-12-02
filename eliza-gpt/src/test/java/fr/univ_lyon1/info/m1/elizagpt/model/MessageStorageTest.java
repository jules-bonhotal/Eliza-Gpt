import fr.univ_lyon1.info.m1.elizagpt.model.MessageObserver;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageStorage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageStorageTest implements MessageObserver {

    private boolean notified = false;

    @Test
    void testAddMessage() {
        MessageStorage messageStorage = new MessageStorage();
        messageStorage.registerObserver(this);

        // Check if the "Bonjour" message is already there 
        assertEquals(1, messageStorage.getMessages().size());
        // Add a message
        messageStorage.addMessage("1", "Hello", true);

        // Check if the message is added
        assertEquals(2, messageStorage.getMessages().size());

        // Check if the observer is notified
        assertTrue(notified);
    }

    @Test
    void testRemoveMessageById() {
        MessageStorage messageStorage = new MessageStorage();
        messageStorage.registerObserver(this);

        // Add a message
        messageStorage.addMessage("1", "Hello", true);

        // Remove the message by ID
        messageStorage.removeMessageById("1");

        // Check if the message is removed
        assertEquals(1, messageStorage.getMessages().size());


        // Remove the "Bonjour" message by ID
        messageStorage.removeMessageById("0");

        // Check if the message is removed
        assertEquals(0, messageStorage.getMessages().size());

        // Check if the observer is notified
        assertTrue(notified);
    }

    @Test
    void testRemoveMessagesByText() {
        MessageStorage messageStorage = new MessageStorage();
        messageStorage.registerObserver(this);

        // Add two messages with the same text
        messageStorage.addMessage("1", "Hello", true);
        messageStorage.addMessage("2", "Hello", true);

        // Remove messages by text
        messageStorage.removeMessagesByText("Hello");

        // Check if the messages are removed
        assertEquals(1, messageStorage.getMessages().size());


        // Remove the "Bonjour" message by ID
        messageStorage.removeMessageById("0");

        // Check if the message is removed
        assertEquals(0, messageStorage.getMessages().size());


        // Check if the observer is notified
        assertTrue(notified);
    }

    @Override
    public void update(final String notification) {
        notified = true;
    }
}
