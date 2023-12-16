package fr.univ_lyon1.info.m1.elizagpt.model;


import java.util.regex.Matcher;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Handler for the rule: "((Je .*)|(J'.*))\\.".
 */
public class JeHandler implements Handler {
    private Handler nextHandler;



    @Override
    public String process(final String text, final MessageProcessor messageProcessor) {
        Pattern pattern = Pattern.compile("((Je .*)|(J'.*))\\.", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(messageProcessor.normalize(text));
        Random random = new Random();

        if (matcher.matches()) {
            final String[] options = {
                "Pourquoi dites-vous que ",
                "Pourquoi pensez-vous que ",
                "Êtes-vous sûr que ",
            };
            final String startQuestion =  options[random.nextInt(options.length)];
            return startQuestion 
                    + messageProcessor.firstToSecondPerson(matcher.group(1))
                    + "?";
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
