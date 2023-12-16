package fr.univ_lyon1.info.m1.elizagpt.model;


import java.util.regex.Matcher;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Handler for the rule: "((Je .*)|(J'.*))\\.".
 */
public class JeHandler implements Handler {
    private Handler nextHandler;
    /**
     * Constructor of JeHandler.
     * 
     */
    public JeHandler(final Handler nextHandler) {
        this.nextHandler = nextHandler;
    }


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
}
