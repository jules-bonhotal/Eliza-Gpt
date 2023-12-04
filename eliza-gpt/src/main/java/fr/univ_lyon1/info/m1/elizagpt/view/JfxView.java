package fr.univ_lyon1.info.m1.elizagpt.view;

// import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageObserver;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageStorage;
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
import javafx.collections.ObservableList;
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
    private final Random random = new Random();
    /**
     * Create the main view of the application.
     */
    public JfxView(final Stage stage,
            final int width,
            final int height,
            final MessageStorage messageStorage) {
        //s'ajoute en temps qu'observer du stockage des message pour etre notifier des changements
        this.messageStorage = messageStorage;
        messageStorage.registerObserver(this);

        processor = new MessageProcessor(messageStorage);

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
        List<MessageStorage.Message> messages = messageStorage.getMessages();

        // Create HBox elements based on the messages
        for (MessageStorage.Message message : messages) {
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

    //factorise la creation des hbox
    private HBox createHBoxWithLabel(final String text, final String style) {
        HBox hBox = new HBox();
        final Label label = new Label(text);
        hBox.getChildren().add(label);
        label.setStyle(style);
        hBox.setAlignment(Pos.BASELINE_LEFT);

        // Attribue un identifiant unique à la HBox pour distingué deux messages avec un 
        // texte identique dans le messageStorage
        String uniqueId = UUID.randomUUID().toString();
        hBox.setId(uniqueId);

        hBox.setOnMouseClicked(e -> {
            dialog.getChildren().remove(hBox);
            messageStorage.removeMessageById(hBox.getId());
        });

        return hBox;
    }

    private HBox createHBoxWithLabel(final String text,
                final String style,
                final String messageId) {
        HBox hBox = new HBox();
        final Label label = new Label(text);
        hBox.getChildren().add(label);
        label.setStyle(style);
        hBox.setAlignment(Pos.BASELINE_LEFT);

        // Set the ID for the HBox
        hBox.setId(messageId);

        hBox.setOnMouseClicked(e -> {
            dialog.getChildren().remove(hBox);
            messageStorage.removeMessageById(messageId);
        });

        return hBox;
    }





    private Pane createSearchWidget() {
        final HBox firstLine = new HBox();
        final HBox secondLine = new HBox();
        firstLine.setAlignment(Pos.BASELINE_LEFT);
        secondLine.setAlignment(Pos.BASELINE_LEFT);

        // Create a ComboBox for search options
        ObservableList<String> searchOptions = FXCollections.observableArrayList(
                "Option 1", "Option 2" // TODO : ajouter des vrai option de recherches
        );
        ComboBox<String> searchOptionsComboBox = new ComboBox<>(searchOptions);
        searchOptionsComboBox.setPromptText("Select Search Option");

        searchText = new TextField();
        searchText.setOnAction(e -> {
            String selectedOption = searchOptionsComboBox.getValue();
            String regexPattern = searchText.getText();

            // Perform different actions based on the selected search option
            switch (selectedOption) {
                case "Option 1":
                    // Perform search logic for Option 1
                    break;
                case "Option 2":
                    // Perform search logic for Option 2
                    break;
                default:
                    break;
            }

            List<MessageStorage.Message> matchingMessages =
                messageStorage.findMessagesByRegex(regexPattern);
            updateSearchResults(matchingMessages);
        });

        firstLine.getChildren().addAll(searchText, searchOptionsComboBox);
        final Button send = new Button("Search");
        send.setOnAction(e -> {
            String selectedOption = searchOptionsComboBox.getValue();
            String regexPattern = searchText.getText();

            // Perform different actions based on the selected search option
            switch (selectedOption) {
                case "Option 1":
                    // Perform search logic 1
                    break;
                case "Option 2":
                    // Perform search logic 2
                    break;
                default:
                    // Default search logic
                    break;
            }

            List<MessageStorage.Message> matchingMessages =
                messageStorage.findMessagesByRegex(regexPattern);
            updateSearchResults(matchingMessages);
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
    private void updateSearchResults(final List<MessageStorage.Message> matchingMessages) {
        dialog.getChildren().clear();

        for (MessageStorage.Message message : matchingMessages) {
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
            processor.sendMessage(text.getText());
            text.setText("");
        });
        final Button send = new Button("Send");
        send.setOnAction(e -> {
            // TODO : voire si il faut pas que ca passe par le contoller 
            processor.sendMessage(text.getText());
            text.setText("");
        });
        input.getChildren().addAll(text, send);
        return input;
    }
}
