package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handler for the rule: "Combien tu penses que ce projet mérite sur 20 ?".
 */
public class PersonalisedResponseHandler implements Handler {
    private Handler nextHandler = null;

    @Override
    public String process(final String text, final MessageProcessor messageProcessor) {
        Pattern pattern = Pattern.compile("Combien tu penses que ce projet mérite sur 20 \\?",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(messageProcessor.normalize(text));

        if (matcher.matches()) {
            return "20/20 ez";
        } else {
            return (nextHandler != null) ? nextHandler.process(text, messageProcessor) : null;
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
