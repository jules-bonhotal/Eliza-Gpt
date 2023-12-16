package fr.univ_lyon1.info.m1.elizagpt.model;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handler for the rule: "Qui est le plus (.*) \\?".
 */
public class QuiEstLePlusHandler implements Handler {
    private Handler nextHandler;
    
    /**
     * Constructor of QuiEstLePlusHandler.
     * 
     */
    public QuiEstLePlusHandler(final Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public String process(final String text, final MessageProcessor messageProcessor) {
        Pattern pattern = Pattern.compile("Qui est le plus (.*) \\?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(messageProcessor.normalize(text));

        if (matcher.matches()) {
            return "Le plus " + matcher.group(1) + " est bien sûr votre enseignant de MIF01 !";
        } else {
            return (nextHandler != null) ? nextHandler.process(text, messageProcessor) : null;
        }
    }
}
