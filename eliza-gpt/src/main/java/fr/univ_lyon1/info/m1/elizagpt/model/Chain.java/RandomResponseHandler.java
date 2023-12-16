package fr.univ_lyon1.info.m1.elizagpt.model;


import java.util.Random;

/**
 * Handler for Random responses.
 */
public class RandomResponseHandler implements Handler {
    private Handler nextHandler;
    private Random random = new Random();
    


    @Override
    public String process(final String text, final MessageProcessor messageProcessor) {
        // Nothing clever to say, answer randomly
        if (random.nextBoolean()) {
            return "Il fait beau aujourd'hui, vous ne trouvez pas ?";
        }
        if (random.nextBoolean()) {
            return "Je ne comprends pas.";
        }
        if (random.nextBoolean()) {
            return "Hmmm, hmm ...";
        }

        // Default answer
        if (messageProcessor.getName() != null) {
            return "Qu'est-ce qui vous fait dire cela, " + messageProcessor.getName() + " ?";
        } else {
            return "Qu'est-ce qui vous fait dire cela ?";
        }
    }

    /**
     * Sets the next handler in the chain.
     *
     * @param next The next handler to be set in the chain.
     */
    public void setNextHandler(final Handler next) {
        nextHandler = next;
    }

    /**
     * Gets the next handler in the chain.
     *
     * @return The next handler in the chain.
     */
    public Handler getNextHandler() {
        return nextHandler;
    }
}
