package fr.univ_lyon1.info.m1.elizagpt.view;

import java.util.ArrayList;
import java.util.List;

import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
// import fr.univ_lyon1.info.m1.elizagpt.model.processUserInput;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Random;


/**
 * Main class of the View (GUI) of the application.
 */
public class JfxView {
    private final VBox dialog;
    private TextField text = null;
    private TextField searchText = null;
    private Label searchTextLabel = null;
    private MessageProcessor processor = new MessageProcessor();
    private final Random random = new Random();
    /**
     * Create the main view of the application.
     */
        // TODO: style error in the following line. Check that checkstyle finds it, and then fix it.
        public JfxView(final Stage stage, final int width, final int height) {
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
        replyToUser("Bonjour");

        // Everything's ready: add it to the scene and display it
        final Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        text.requestFocus();
        stage.show();
    }

    static final String BASE_STYLE = "-fx-padding: 8px; "
            + "-fx-margin: 5px; "
            + "-fx-background-radius: 5px;";
    static final String USER_STYLE = "-fx-background-color: #A0E0A0; " + BASE_STYLE;
    static final String ELIZA_STYLE = "-fx-background-color: #A0A0E0; " + BASE_STYLE;

    private void replyToUser(final String text) {
        HBox hBox = new HBox();
        final Label label = new Label(text);
        hBox.getChildren().add(label);
        label.setStyle(ELIZA_STYLE);
        hBox.setAlignment(Pos.BASELINE_LEFT);
        dialog.getChildren().add(hBox);
        // click on this hbox deletes the message.
        hBox.setOnMouseClicked(e -> {
            dialog.getChildren().remove(hBox);
        });
    }
    
    /*la logic de reonpse ne devrais pas etre dans la meme fonction et devrais etre en le model*/
    private void sendMessage(final String text) {
        HBox hBox = new HBox();
        final Label label = new Label(text);
        hBox.getChildren().add(label);
        label.setStyle(USER_STYLE);
        hBox.setAlignment(Pos.BASELINE_RIGHT);
        dialog.getChildren().add(hBox);
        hBox.setOnMouseClicked(e -> {
            dialog.getChildren().remove(hBox);
        });
    
        String reply = processor.processUserInput(text);
        replyToUser(reply);
    }

    /**
    * Extract the name of the user from the dialog.
    * TODO: this totally breaks the MVC pattern, never, ever, EVER do that.
    * @return
    */
    private String getName() {
        for (Node hBox : dialog.getChildren()) {
            for (Node label : ((HBox) hBox).getChildren()) {
                if (((Label) label).getStyle().equals("-fx-background-color: #A0E0A0;")) {
                    String text = ((Label) label).getText();
                    Pattern pattern = Pattern.compile("Je m'appelle (.*)\\.",
                                                      Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.matches()) {
                        return matcher.group(1);
                    }
                }
            }
        }
        return null;
    }

    private Pane createSearchWidget() {
        final HBox firstLine = new HBox();
        final HBox secondLine = new HBox();
        firstLine.setAlignment(Pos.BASELINE_LEFT);
        secondLine.setAlignment(Pos.BASELINE_LEFT);
        searchText = new TextField();
        searchText.setOnAction(e -> {
            searchText(searchText);
        });
        firstLine.getChildren().add(searchText);
        final Button send = new Button("Search");
        send.setOnAction(e -> {
            searchText(searchText);
        });
        searchTextLabel = new Label();
        final Button undo = new Button("Undo search");
        undo.setOnAction(e -> {
            throw new UnsupportedOperationException("TODO: implement undo for search");
        });
        secondLine.getChildren().addAll(send, searchTextLabel, undo);
        final VBox input = new VBox();
        input.getChildren().addAll(firstLine, secondLine);
        return input;
    }



    private void searchText(final TextField text) {
        //TODO: voire si il faut pas ecahper les chaaracter qui vont passer pour des regex, 
                // genr si on chereche * ca cherche pas etoile mais tout les texte
	    //TODO passer la recherche dans le model
        //TODO: !!!!! faire en sorte que le controller et model ait aucune mention des 
                //hBox et tout, le stockage devrait marcher de manière différente
        String normalizedText;		      
		      
        Pattern pattern = Pattern.compile(text.getText(), Pattern.CASE_INSENSITIVE);
        Matcher matcher;
    	
    	
        String currentSearchText = text.getText();
        if (currentSearchText == null) {
            searchTextLabel.setText("No active search");
        } else {
            searchTextLabel.setText("Searching for: " + currentSearchText);
        }
        List<HBox> toDelete = new ArrayList<>();
        for (Node hBox : dialog.getChildren()) {
            for (Node label : ((HBox) hBox).getChildren()) {
                String t = ((Label) label).getText();
                normalizedText = processor.normalize(t);
        		    matcher = pattern.matcher(normalizedText);
                if (!matcher.find()) {                  
                    toDelete.add((HBox) hBox);
                }
/*                if (!t.contains(text.getText())) {
                    // Can delete it right now, we're iterating over the list.
                    toDelete.add((HBox) hBox);
                }*/
            }
        }
        dialog.getChildren().removeAll(toDelete);
        text.setText("");
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
            sendMessage(text.getText());
            text.setText("");
        });
        final Button send = new Button("Send");
        send.setOnAction(e -> {
            sendMessage(text.getText());
            text.setText("");
        });
        input.getChildren().addAll(text, send);
        return input;
    }
}
