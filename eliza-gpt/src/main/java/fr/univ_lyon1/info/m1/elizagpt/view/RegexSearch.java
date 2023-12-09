package fr.univ_lyon1.info.m1.elizagpt.view;

// import java.util.ArrayList;
import java.util.List;
// import java.util.UUID;
// import java.util.Collections;


// import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
// import fr.univ_lyon1.info.m1.elizagpt.model.MessageObserver;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageStorage;
// import fr.univ_lyon1.info.m1.elizagpt.model.processUserInput;
// import javafx.geometry.Pos;
// import javafx.scene.Node;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.control.TextField;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Pane;
// import javafx.scene.layout.VBox;
// import javafx.stage.Stage;
// import java.util.regex.Matcher;
// import java.util.regex.Pattern;
// import java.util.Random;
// import javafx.scene.control.ChoiceBox;
// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
// import javafx.scene.control.ComboBox;




/**
 * Implementation of the SearchStrategy interface
 * for searching messages using a regular expression.
 */
public class RegexSearch implements SearchStrategy {
    @Override
    public List<MessageStorage.Message> executeSearch(
            final String searchQuery,
            final MessageStorage messageStorage) {
        // Logique de recherche pour l'option 1
        return messageStorage.findMessagesByRegex(searchQuery);
    }

    /**
     * Returns the name of the search option.
     *
     * @return The name of the search option.
     */
    @Override
    public String toString() {
        return "RegexSearch";
    }
    
}
