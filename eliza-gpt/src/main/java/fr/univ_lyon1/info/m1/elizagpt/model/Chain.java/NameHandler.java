package fr.univ_lyon1.info.m1.elizagpt.model;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handler for the rule: "".*Je m'appelle (.*)\\."".
 */
public class NameHandler implements Handler {
    private Handler nextHandler = null;
    
    /**
     * Constructor of NameHandler.
     * 
     */
    public NameHandler(final Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public String process(final String text, final MessageProcessor messageProcessor) {
        Pattern pattern = Pattern.compile(".*Je m'appelle (.*)\\.", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(messageProcessor.normalize(text));

        if (matcher.matches()) {
            String userName = matcher.group(1);
            // Capitalize the first letter of the name
            String capitalizedUserName = userName.substring(0, 1).toUpperCase() 
                    + userName.substring(1);
            return "Bonjour " + capitalizedUserName + ".";
        } else {
            return (nextHandler != null) ? nextHandler.process(text, messageProcessor) : null;
        }
    }
}
