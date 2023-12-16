package fr.univ_lyon1.info.m1.elizagpt.model;


import java.util.regex.Matcher;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Handler for the rule: "(.*)\\?".
 */
public class QuestionHandler implements Handler {
    private Handler nextHandler;
    
    /**
     * Constructor of QuestionHandler.
     * 
     */
    public QuestionHandler(final Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public String process(final String text, final MessageProcessor messageProcessor) {
        Pattern pattern = Pattern.compile("(.*)\\?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(messageProcessor.normalize(text));
        Random random = new Random();

        if (matcher.matches()) {
            final String[] options = {
                    "Ici, c'est moi qui pose les questions. ",
                    "Je vous renvoie la question. ",
            };
            return options[random.nextInt(options.length)];
        } else {
            return (nextHandler != null) ? nextHandler.process(text, messageProcessor) : null;
        }
    }
}
