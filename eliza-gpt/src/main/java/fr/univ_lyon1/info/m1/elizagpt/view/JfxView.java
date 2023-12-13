package fr.univ_lyon1.info.m1.elizagpt.view;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
// import java.util.Collections;


import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageObserver;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageStorage;
import fr.univ_lyon1.info.m1.elizagpt.model.Message;
import fr.univ_lyon1.info.m1.elizagpt.model.SearchStrategyList;
import fr.univ_lyon1.info.m1.elizagpt.model.SearchStrategy;

import fr.univ_lyon1.info.m1.elizagpt.controller.Controller;

// import fr.univ_lyon1.info.m1.elizagpt.model.MessageStorage;
// import fr.univ_lyon1.info.m1.elizagpt.model.processUserInput;
import javafx.geometry.Pos;
// import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
// import java.util.regex.Matcher;
// import java.util.regex.Pattern;
import java.util.Random;
// import javafx.scene.control.ChoiceBox;
import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * Main class of the View (GUI) of the application.
 */
public class JfxView implements MessageObserver {
    private final VBox dialog;
    private TextField text = null;
    private TextField searchText = null;
    private Label searchTextLabel = null;
    private MessageProcessor processor;
    private MessageStorage messageStorage;
    private Controller controller;
    private final Random random = new Random();
    /**
     * Create the main view of the application.
     */
    public JfxView(final Stage stage,
            final int width,
            final int height,
            final MessageStorage messageStorage,
            final MessageProcessor messageProcessor,
            final Controller controller) {
        //s'ajoute en temps qu'observer du stockage des message pour etre notifier des changements
        this.messageStorage = messageStorage;
        messageStorage.registerObserver(this);

        processor = messageProcessor;
        this.controller = controller;

        stage.setTitle("Eliza GPT");

        final VBox root = new VBox(10);

        final Pane search = createSearchWidget();
        root.getChildren().add(search);

        ScrollPane dialogScroll = new ScrollPane();
        dialog = new VBox(10);
        dialogScroll.setContent(dialog);
        // scroll to bottom by default:
        dialogScroll.vvalueProperty().bind(dialog.heightProperty());
        root.getChildren().add(dialogScroll);
        dialogScroll.setFitToWidth(true);

        final Pane input = createInputWidget();
        root.getChildren().add(input);

        // Everything's ready: add it to the scene and display it
        final Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        text.requestFocus();
        stage.show();
        update("start");
    }


    /**
     * Updates the graphical interface based on the notification received.
     *
     * @param notification The notification string indicating the type of update.
     */

    public void update(final String notification) {
        // Clear existing HBox elements
        dialog.getChildren().clear();

        // Get the messages from the MessageStorage
        List<Message> messages = messageStorage.getMessages();

        // Create HBox elements based on the messages
        for (Message message : messages) {
            HBox hBox;
            if (message.isUserMessage()) {
                hBox = createHBoxWithLabel(message.getMessageText(),
                            USER_STYLE,
                            message.getMessageId());
            } else {
                hBox = createHBoxWithLabel(message.getMessageText(),
                            ELIZA_STYLE,
                            message.getMessageId());
            }
            dialog.getChildren().add(hBox);
        }
    }




    static final String BASE_STYLE = "-fx-padding: 8px; "
            + "-fx-margin: 5px; "
            + "-fx-background-radius: 5px;";
    static final String USER_STYLE = "-fx-background-color: #A0E0A0; " + BASE_STYLE;
    static final String ELIZA_STYLE = "-fx-background-color: #A0A0E0; " + BASE_STYLE;

    // Factorize the creation of HBox with label and button
    private HBox createHBoxWithLabel(final String text,
            final String style,
            final String messageId) {
        HBox outerHBox = new HBox();
        outerHBox.setAlignment(Pos.BASELINE_LEFT);

        HBox innerHBox = new HBox();
        final Label label = new Label(text);
        final Button deleteButton = new Button("x");
        innerHBox.getChildren().addAll(label, deleteButton);
        innerHBox.setAlignment(Pos.BASELINE_LEFT);
        innerHBox.setStyle(style);

        outerHBox.getChildren().add(innerHBox);

        outerHBox.setId(messageId);

        deleteButton.setOnAction(e -> {
            dialog.getChildren().remove(outerHBox);
            messageStorage.removeMessageById(messageId);
        });

        return outerHBox;
    }

    private HBox createHBoxWithLabel(final String text, final String style) {
        String uniqueId = UUID.randomUUID().toString();
        return createHBoxWithLabel(text, style, uniqueId);
    }






    private Pane createSearchWidget() {
        final HBox firstLine = new HBox();
        final HBox secondLine = new HBox();
        firstLine.setAlignment(Pos.BASELINE_LEFT);
        secondLine.setAlignment(Pos.BASELINE_LEFT);


        // Create a ComboBox for search options
        SearchStrategyList searchStrategyList = new SearchStrategyList();
        ArrayList<SearchStrategy> searchOptions = searchStrategyList.getList();

        ComboBox<SearchStrategy> searchOptionsComboBox = 
                new ComboBox<>(FXCollections.observableArrayList(searchOptions));
        searchOptionsComboBox.setPromptText("Select Search Option");

        searchText = new TextField();
        searchText.setOnAction(e -> {
            SearchStrategy selectedOption = searchOptionsComboBox.getValue();
            String regexPattern = searchText.getText();

            // Utilisation de la stratégie sélectionnée pour la recherche
            if (selectedOption != null) {
                List<Message> matchingMessages = 
                        selectedOption.executeSearch(regexPattern, messageStorage);
                updateSearchResults(matchingMessages);
            }
        });

        firstLine.getChildren().addAll(searchText, searchOptionsComboBox);
        final Button send = new Button("Search");
        send.setOnAction(e -> {
            SearchStrategy selectedOption = searchOptionsComboBox.getValue();
            String regexPattern = searchText.getText();

            // Utilisation de la stratégie sélectionnée pour la recherche
            if (selectedOption != null) {
                List<Message> matchingMessages = 
                        selectedOption.executeSearch(regexPattern, messageStorage);
                updateSearchResults(matchingMessages);
            }
        });

        searchTextLabel = new Label();
        final Button undo = new Button("Undo search");
        // undo.setOnAction(e -> {
        //     // TODO: implement undo for search
        // });
        secondLine.getChildren().addAll(send, searchTextLabel); //, undo);
        final VBox input = new VBox();
        input.getChildren().addAll(firstLine, secondLine);
        return input;
    }



    // update the search results in the View
    private void updateSearchResults(final List<Message> matchingMessages) {
        dialog.getChildren().clear();

        for (Message message : matchingMessages) {
            HBox hBox;
            if (message.isUserMessage()) {
                hBox = createHBoxWithLabel(message.getMessageText(),
                        USER_STYLE,
                        message.getMessageId());
            } else {
                hBox = createHBoxWithLabel(message.getMessageText(),
                        ELIZA_STYLE,
                        message.getMessageId());
            }
            dialog.getChildren().add(hBox);
        }

        // Update the search label
        String currentSearchText = searchText.getText();
        if (currentSearchText == null || currentSearchText.isEmpty()) {
            searchTextLabel.setText("No active search");
        } else {
            searchTextLabel.setText("Searching for: " + currentSearchText);
        }
    }






/**
 * Crée un widget d'entrée pour l'utilisateur.
 *
 * Ce widget permet à l'utilisateur de saisir du texte dans un champ de texte
 * et d'envoyer ce texte en appuyant sur la touche "Enter" ou en cliquant sur
 * le bouton "Send". Lorsque l'utilisateur envoie un message, le texte saisi est
 * transmis à la fonction sendMessage, et le champ de texte est effacé pour
 * permettre la saisie d'un nouveau message.
 *
 * @return Un conteneur d'interface utilisateur (HBox) comprenant un champ de texte
 *         et un bouton "Send" pour l'envoi de messages.
 */

    private Pane createInputWidget() {
        final Pane input = new HBox();
        text = new TextField();
        text.setOnAction(e -> {
            // TODO : voire si il faut pas que ca passe par le contoller 
            // processor.sendMessage(text.getText());
            controller.sendMessage(text.getText());
            text.setText("");
        });
        final Button send = new Button("Send");
        send.setOnAction(e -> {
            // TODO : voire si il faut pas que ca passe par le contoller 
            // processor.sendMessage(text.getText());
            controller.sendMessage(text.getText());
            text.setText("");
        });
        input.getChildren().addAll(text, send);
        return input;
    }

    // //  utility class for managing strategies 
    // // TODO temporary solution, there is most likly a better solution
    // public class SearchStrategyUtil {
    //     public static List<SearchStrategy> getAllStrategiesList() {
    //         List<SearchStrategy> strategies = new ArrayList<>();
    //         strategies.add(new RegexSearch());
    //         strategies.add(new Option2Search());
    //         return strategies;
    //     }
    // }
}
