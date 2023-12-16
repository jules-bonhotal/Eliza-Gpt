package fr.univ_lyon1.info.m1.elizagpt.model;


import java.util.Random;

/**
 * Handler for Random responses.
 */
public class RandomResponseHandler implements Handler {
    private Handler nextHandler;
    private Random random = new Random();
    
    /**
     * Constructor of RandomResponseHandler.
     * 
     */
    public RandomResponseHandler(final Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

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
}
