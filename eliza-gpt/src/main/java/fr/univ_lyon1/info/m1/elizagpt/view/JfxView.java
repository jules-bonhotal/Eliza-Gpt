package fr.univ_lyon1.info.m1.elizagpt.view;

import java.util.ArrayList;
import java.util.UUID;

import javafx.geometry.Insets; // Ajout de cette importation
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;

import fr.univ_lyon1.info.m1.elizagpt.controller.Controller;
import fr.univ_lyon1.info.m1.elizagpt.model.Message;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageObserver;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageStorage;
import fr.univ_lyon1.info.m1.elizagpt.model.SearchStrategy;
import fr.univ_lyon1.info.m1.elizagpt.model.SearchStrategyList;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

/**
 * Main class of the View (GUI) of the application.
 */
public class JfxView implements MessageObserver {
    private final VBox dialog;
    private TextField text = null;
    private TextField searchText = null;
    private Label searchTextLabel = null;
    private Controller controller;
    private final Random random = new Random();
    private boolean isDarkMode = false;
    private VBox root;

    /**
     * Constructs a new instance of {@code JfxView}.
     *
     * @param stage          The primary stage for the JavaFX application.
     * @param width          The width of the scene.
     * @param height         The height of the scene.
     * @param messageStorage The message storage to be observed.
     * @param controller     The controller for handling user interactions.
     */
    public JfxView(final Stage stage,
                   final int width,
                   final int height,
                   final MessageStorage messageStorage,
                   final Controller controller) {
        messageStorage.registerObserver(this);
        this.controller = controller;

        root = new VBox(10);

        stage.setTitle("Eliza GPT");

        final Pane search = createSearchWidget();
        root.getChildren().add(search);

        ScrollPane dialogScroll = new ScrollPane();
        dialog = new VBox(10);
        dialogScroll.setContent(dialog);
        dialogScroll.vvalueProperty().bind(dialog.heightProperty());
        root.getChildren().add(dialogScroll);
        dialogScroll.setFitToWidth(true);

        final Pane input = createInputWidget();
        root.getChildren().add(input);

        updateTheme();

        Button themeToggleButton = new Button("Toggle Theme");
        themeToggleButton.setOnAction(e -> toggleTheme());
        HBox.setHgrow(themeToggleButton, Priority.ALWAYS);
        HBox.setMargin(themeToggleButton, new Insets(10, 10, 10, 10));
        HBox bottomRight = new HBox(themeToggleButton);
        bottomRight.setAlignment(Pos.BOTTOM_RIGHT);
        root.getChildren().add(bottomRight);

        final Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        text.requestFocus();
        stage.show();
        update("start");
    }

    private void updateTheme() {
        String backgroundColor = isDarkMode ? "#333333" : "#FFFFFF";
        String otherColor = isDarkMode ? "#666666" : "#DDDDDD";
        String textColor = isDarkMode ? "#FFFFFF" : "#000000"; // Couleur du texte

        // Mise à jour du style du root
        root.setStyle("-fx-background-color: " + backgroundColor + "; -fx-text-fill: " + textColor);

        // Mise à jour du style des champs de texte
        if (text != null) {
            text.setStyle("-fx-background-color: " + otherColor + "; -fx-text-fill: " + textColor);
        }

        if (searchText != null) {
            searchText.setStyle("-fx-background-color: " 
                + otherColor + "; -fx-text-fill: " + textColor);
        }

        // Mise à jour du style de la boîte de dialogue
        dialog.setStyle("-fx-background-color: " + otherColor); 
    }

    private void toggleTheme() {
        isDarkMode = !isDarkMode;
        updateTheme();
    }

    /**
     * Updates the view based on the provided notification.
     * Clears the existing dialog content and populates it with messages from the controller.
     *
     * @param notification A string indicating the type of notification.
     */
    public void update(final String notification) {
        dialog.getChildren().clear();
        List<Message> messages = controller.getMessages();

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
    static final String USER_STYLE = "-fx-background-color:  #94DB96; " + BASE_STYLE;
    static final String ELIZA_STYLE = "-fx-background-color: #73C2FB; " + BASE_STYLE;
    static final String BUTTON_STYLE = "-fx-background-color: #4CAF50; -fx-text-fill: white;";
    static final String DELETE_BUTTON_STYLE = 
                                        "-fx-background-color: #FF6666; -fx-text-fill: white;";
    static final String UNDO_BUTTON_STYLE = "-fx-background-color: #FF6666; -fx-text-fill: white;";

    private HBox createHBoxWithLabel(final String text,
                                  final String style,
                                  final String messageId) {
        HBox outerHBox = new HBox();
        HBox innerHBox = new HBox();
        final Label label = new Label(" " + text + " ");
        final Button deleteButton = new Button("x");
        setButtonStyle(deleteButton, DELETE_BUTTON_STYLE);

        if (style.equals(USER_STYLE)) {
            innerHBox.getChildren().addAll(label, deleteButton);
            outerHBox.setAlignment(Pos.BASELINE_RIGHT);
        } else {
            innerHBox.getChildren().addAll(label, deleteButton);
            outerHBox.setAlignment(Pos.BASELINE_LEFT);
        }

        innerHBox.setStyle(style);

        // Centrer le texte dans innerHBox
        innerHBox.setAlignment(Pos.CENTER);

        outerHBox.getChildren().addAll(innerHBox, new Pane());
        outerHBox.setSpacing(10);

        outerHBox.setStyle("-fx-padding: 0 10 0 10;");

        outerHBox.setId(messageId);

        deleteButton.setOnAction(e -> {
            dialog.getChildren().remove(outerHBox);
            controller.removeMessageById(messageId);
        });

        return outerHBox;
    }

    private HBox createHBoxWithLabel(final String text, final String style) {
        String uniqueId = UUID.randomUUID().toString();
        return createHBoxWithLabel(text, style, uniqueId);
    }

    /**
     * Creates the search widget.
     *
     * @return the created search widget
     */
    private Pane createSearchWidget() {
        final HBox firstLine = new HBox();
        final HBox secondLine = new HBox();
        firstLine.setAlignment(Pos.BASELINE_LEFT);
        secondLine.setAlignment(Pos.BASELINE_LEFT);

        SearchStrategyList searchStrategyList = new SearchStrategyList();
        ArrayList<SearchStrategy> searchOptions = searchStrategyList.getList();

        ComboBox<SearchStrategy> searchOptionsComboBox =
                new ComboBox<>(FXCollections.observableArrayList(searchOptions));
        searchOptionsComboBox.setPromptText("Select Search Option");

        searchText = new TextField();
        searchText.setOnAction(e -> {
            SearchStrategy selectedOption = searchOptionsComboBox.getValue();
            String regexPattern = searchText.getText();

            if (selectedOption != null) {
                List<Message> matchingMessages = controller.executeSearchController(
                        selectedOption,
                        regexPattern);
                updateSearchResults(matchingMessages);
            }
        });

        firstLine.getChildren().addAll(searchText, searchOptionsComboBox);
        final Button send = new Button("Search");
        setButtonStyle(send, BUTTON_STYLE);
        send.setOnAction(e -> {
            controller.sendMessage(text.getText());
            text.setText("");
        });

        searchTextLabel = new Label();
        final Button undo = new Button("Undo search");
        setButtonStyle(undo, UNDO_BUTTON_STYLE);
        undo.setOnAction(e -> {
            searchText.clear();
            update("start");
        });
        secondLine.getChildren().addAll(send, searchTextLabel, undo);
        secondLine.setSpacing(10);

        final VBox input = new VBox();
        input.getChildren().addAll(firstLine, secondLine);
        return input;
    }

    /**
     * Updates the search results.
     *
     * @param matchingMessages the matching messages
     */
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

        String currentSearchText = searchText.getText();
        if (currentSearchText == null || currentSearchText.isEmpty()) {
            searchTextLabel.setText("No active search");
        } else {
            searchTextLabel.setText("Searching for: " + currentSearchText);
        }
    }

    private Pane createInputWidget() {
        final Pane input = new HBox();
        text = new TextField();
        text.setOnAction(e -> {
            controller.sendMessage(text.getText());
            text.setText("");
        });
        final Button send = new Button("Send");
        setButtonStyle(send, BUTTON_STYLE);
        send.setOnAction(e -> {
            controller.sendMessage(text.getText());
            text.setText("");
        });
        input.getChildren().addAll(text, send);
        ((HBox) input).setSpacing(10); 
        return input;
    }

    private void setButtonStyle(final Button button, final String style) {
        button.setStyle(style);
    }
}
