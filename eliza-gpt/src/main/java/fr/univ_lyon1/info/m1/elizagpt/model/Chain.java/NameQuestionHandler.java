package fr.univ_lyon1.info.m1.elizagpt.model;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Handler for the rule: "Quel est mon nom \\?.".
 */
public class NameQuestionHandler implements Handler {
    private Handler nextHandler = null;
    


    @Override
    public String process(final String text, final MessageProcessor messageProcessor) {
        Pattern pattern = Pattern.compile("Quel est mon nom \\?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(messageProcessor.normalize(text));

        if (matcher.matches()) {
            if (messageProcessor.getName() != null) {
                return "Votre nom est " + messageProcessor.getName() + ".";
            } else {
                return "Je ne connais pas votre nom.";
            }
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
