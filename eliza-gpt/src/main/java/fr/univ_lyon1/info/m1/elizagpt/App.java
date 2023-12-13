package fr.univ_lyon1.info.m1.elizagpt;

import fr.univ_lyon1.info.m1.elizagpt.view.JfxView;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageStorage;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
import fr.univ_lyon1.info.m1.elizagpt.controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for the application (structure imposed by JavaFX).
 */
public class App extends Application {

    /**
     * With javafx, start() is called when the application is launched.
     */
    @Override
    public void start(final Stage stage) throws Exception {
        MessageStorage messageStorage = new MessageStorage();
        MessageProcessor processor = new MessageProcessor(messageStorage);
        Controller controller = new Controller(processor);

        new JfxView(stage, 600, 600, messageStorage, processor, controller);
        // Second view (uncomment to activate)
        new JfxView(new Stage(), 900, 900, messageStorage, processor, controller);
        
    }


    /**
     * A main method in case the user launches the application using
     * App as the main class.
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }
}
