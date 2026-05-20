package view;

import controller.LogInController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.time.LocalDate;

public class LogInView {

    private static final String COLOR_BTN_DEFAULT = "#A9A9A9";
    private static final String COLOR_TEXT = "black";
    private static final String COLOR_ERROR = "#ff4444";
    private static final int SIZE_TEXT = 14;
    private static final int SIZE_ERROR = 12;

    private static final String FIELD_NORMAL = "-fx-background-radius: 8; -fx-font-size: " + SIZE_TEXT + "px;";
    private static final String FIELD_ERROR = "-fx-background-radius: 8; -fx-font-size: " + SIZE_TEXT
            + "px; -fx-border-color: " + COLOR_ERROR + "; -fx-border-radius: 8;";

    public StackPane build(LogInController ctrl) {

        Label subtitleLabel = new Label("Enter your username to continue");
        subtitleLabel.setStyle(
                "-fx-font-size: " + SIZE_TEXT + "px; -fx-text-fill: " + COLOR_TEXT + "; -fx-font-style: italic;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle(FIELD_NORMAL);

        Label dateLabel = new Label("Select today's date:");
        dateLabel.setStyle("-fx-font-size: " + SIZE_TEXT + "px; -fx-text-fill: " + COLOR_TEXT + ";");

        DatePicker datePicker = new DatePicker(LocalDate.now());
        datePicker.setStyle(FIELD_NORMAL);

        Label weightLabel = new Label("Today's weight (kg):");
        weightLabel.setStyle("-fx-font-size: " + SIZE_TEXT + "px; -fx-text-fill: " + COLOR_TEXT + ";");

        Spinner<Double> weightSpinner = new Spinner<>(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(30, 250, 70, 0.1));
        weightSpinner.setEditable(true);
        weightSpinner.setStyle(FIELD_NORMAL);

        Label errorLabel = new Label("User not found. Please try again.");
        errorLabel.setStyle("-fx-text-fill: " + COLOR_ERROR + "; -fx-font-size: " + SIZE_ERROR + "px;");
        errorLabel.setVisible(false);

        Button loginBtn = new Button("Log in");
        loginBtn.setStyle("-fx-background-color: " + COLOR_BTN_DEFAULT + "; -fx-text-fill: " + COLOR_TEXT
                + "; -fx-font-size: " + SIZE_TEXT + "px; -fx-background-radius: 8;");

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: " + COLOR_BTN_DEFAULT + "; -fx-text-fill: " + COLOR_TEXT
                + "; -fx-font-size: " + SIZE_TEXT + "px; -fx-background-radius: 8;");

        loginBtn.setOnAction(e -> {
            usernameField.setStyle(FIELD_NORMAL);
            errorLabel.setVisible(false);

            boolean found = ctrl.handleLogin(
                    usernameField.getText(),
                    datePicker.getValue(),
                    weightSpinner.getValue());
            if (!found) {
                errorLabel.setVisible(true);
                usernameField.setStyle(FIELD_ERROR);
            }
        });

        backBtn.setOnAction(e -> ctrl.handleBack());

        VBox content = new VBox(15, subtitleLabel, usernameField, dateLabel, datePicker,
                weightLabel, weightSpinner, errorLabel, loginBtn, backBtn);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));

        StackPane root = new StackPane();
        root.getChildren().add(content);
        root.setStyle(
                "-fx-background-image: url('/images/background.jpg'); -fx-background-size: cover; -fx-background-position: center;");

        usernameField.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        loginBtn.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        backBtn.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        datePicker.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        weightSpinner.prefWidthProperty().bind(root.widthProperty().multiply(0.3));

        return root;
    }
}