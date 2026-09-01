package pdd.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import pdd.PDD;

/**
 * Controller for {@code MainWindow.fxml}: the chat window's scrollable
 * dialog history, input field, and send button. Delegates all command
 * handling to the {@link PDD} instance injected via {@link #setPdd(PDD)},
 * displaying its response as a new dialog box each time.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private PDD pdd;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private final Image pddImage = new Image(this.getClass().getResourceAsStream("/images/Pdd.png"));

    /** Binds the dialog scroll pane to auto-scroll to the newest message. */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the chatbot backend and shows its greeting as the first message. */
    public void setPdd(PDD pdd) {
        this.pdd = pdd;
        dialogContainer.getChildren().add(DialogBox.getPddDialog(pdd.getGreeting(), pddImage));
    }

    /**
     * Sends the text field's contents to {@link PDD#getResponse(String)}, shows both the
     * user's message and PDD's response as dialog boxes, clears the field, and closes the
     * window if that command was an exit command (e.g. {@code bye}).
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = pdd.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getPddDialog(response, pddImage));
        userInput.clear();
        if (pdd.isExit()) {
            Platform.exit();
        }
    }
}
