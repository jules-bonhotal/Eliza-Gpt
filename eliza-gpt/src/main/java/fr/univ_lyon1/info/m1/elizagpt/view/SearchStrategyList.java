package fr.univ_lyon1.info.m1.elizagpt.view;

import java.util.ArrayList;
// import java.util.List;
// import java.util.UUID;
// import java.util.Collections;


// import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
// import fr.univ_lyon1.info.m1.elizagpt.model.MessageObserver;
// import fr.univ_lyon1.info.m1.elizagpt.model.MessageStorage;
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
 * A utility class representing a list of search strategies.
 * Is used to avoid having to modify list of Strategies in 
 * use, espeacialy if there were multiple class that needed
 * a list of all the strategies.
 */
public class SearchStrategyList {
    private ArrayList<SearchStrategy> searchOptions;

    /**
     * Initializes the list of search strategies with all Strategies available.
     */
    public SearchStrategyList() {
        searchOptions = new ArrayList<>();
        searchOptions.add(new RegexSearch());
        searchOptions.add(new Option2Search());
    }

    /**
     * Gets the list of search strategies.
     *
     * @return The list of search strategies.
     */
    public ArrayList<SearchStrategy> getList() {
        return searchOptions;
    }
}

