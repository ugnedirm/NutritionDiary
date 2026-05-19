package view;

import controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import java.time.LocalDate;

import java.time.LocalDate;

public class LoginView {

    public StackPane build(LoginController ctrl) {

        Label subtitleLabel = new Label("Enter your username to continue");
        subtitleLabel.setStyle("-fx-font-size: 14px; " +
                "-fx-text-fill: black; " +
                "-fx-font-style: italic;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-background-radius: 8; " +
                "-fx-font-size: 14px;");

        // Date picker
        Label dateLabel = new Label("Select today's date:");
        dateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");

        DatePicker datePicker = new DatePicker(LocalDate.now());
        datePicker.setStyle("-fx-background-radius: 8; -fx-font-size: 14px;");

        // Weight spinner
        Label weightLabel = new Label("Today's weight (kg):");
        weightLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");

        Spinner<Double> weightSpinner = new Spinner<>(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(30, 250, 70, 0.1)
        );
        weightSpinner.setEditable(true);
        weightSpinner.setStyle("-fx-background-radius: 8; -fx-font-size: 14px;");

        Label errorLabel = new Label("User not found. Please try again.");
        errorLabel.setStyle("-fx-text-fill: #ff4444; " +
                "-fx-font-size: 12px;");
        errorLabel.setVisible(false);

        Button loginBtn = new Button("Log in");
        loginBtn.setStyle("-fx-background-color: #A9A9A9; " +
                "-fx-text-fill: black; " +
                "-fx-font-size: 14px; " +
                "-fx-background-radius: 8;");

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: #A9A9A9; " +
                "-fx-text-fill: black; " +
                "-fx-font-size: 14px; " +
                "-fx-background-radius: 8;");

        loginBtn.setOnAction(e -> {
            boolean found = ctrl.handleLogin(
                    usernameField.getText(),
                    datePicker.getValue(),
                    weightSpinner.getValue()
            );
            errorLabel.setVisible(!found);
        });
        backBtn.setOnAction(e -> ctrl.handleBack());

        VBox content = new VBox(15, subtitleLabel, usernameField, dateLabel, datePicker,
                weightLabel, weightSpinner, errorLabel, loginBtn, backBtn);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));

        StackPane root = new StackPane();
        root.getChildren().add(content);
        root.setStyle("-fx-background-image: url('/images/background.jpg'); " +
                "-fx-background-size: cover; " +
                "-fx-background-position: center;");

        usernameField.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        loginBtn.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        backBtn.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        datePicker.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        weightSpinner.prefWidthProperty().bind(root.widthProperty().multiply(0.3));

        return root;
    }
}
