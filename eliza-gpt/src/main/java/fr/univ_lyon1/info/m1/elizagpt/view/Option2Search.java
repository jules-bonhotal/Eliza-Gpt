package fr.univ_lyon1.info.m1.elizagpt.view;

import java.util.ArrayList;
import java.util.List;
// import java.util.UUID;
// import java.util.Collections;


// import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
// import fr.univ_lyon1.info.m1.elizagpt.model.MessageObserver;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageStorage;
// import fr.univ_lyon1.info.m1.elizagpt.model.processUserInput;
// import javafx.geometry.Pos;
// // import javafx.scene.Node;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.control.TextField;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Pane;
// import javafx.scene.layout.VBox;
// import javafx.stage.Stage;
// // import java.util.regex.Matcher;
// // import java.util.regex.Pattern;
// import java.util.Random;
// // import javafx.scene.control.ChoiceBox;
// import javafx.collections.FXCollections;
// // import javafx.collections.ObservableList;
// import javafx.scene.control.ComboBox;






/**
 * Implementation of the {@link SearchStrategy} interface
 * for a placeholder/search strategy (Option 2).
 */
public class Option2Search implements SearchStrategy {
    /**
     * Executes a placeholder search strategy.
     * This implementation returns an empty list.
     *
     * @param searchQuery The search query (not used).
     * @param messageStorage The storage containing messages (not used).
     * @return An empty list, indicating no messages found.
     */
    @Override
    public List<MessageStorage.Message> executeSearch(
                final String searchQuery,
                final MessageStorage messageStorage) {
        return new ArrayList<>(); // temporary
    }

    /**
     * Returns the name of the search option.
     *
     * @return The name of the search option.
     */
    @Override
    public String toString() {
        return "Option 2";
    }
}
