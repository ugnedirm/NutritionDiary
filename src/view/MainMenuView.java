package view;

import controller.MainMenuController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class MainMenuView {

    private static final String COLOR_BTN_DEFAULT = "#A9A9A9";
    private static final String COLOR_TEXT = "black";
    private static final int SIZE_TITLE = 32;
    private static final int SIZE_TEXT = 14;
    private static final int SIZE_SMALL = 12;

    public StackPane build(MainMenuController ctrl) {

        Label titleLabel = new Label("Welcome to Nutrition Diary!");
        titleLabel.setStyle(
                "-fx-font-size: " + SIZE_TITLE + "px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_TEXT + ";");

        Label descriptionLabel = new Label("Your daily nutrition companion. " +
                "Log meals, monitor dairy consumption, " +
                "and stay on top of your health goals with " +
                "smart insights and easy tracking.");
        descriptionLabel.setStyle(
                "-fx-font-size: " + SIZE_TEXT + "px; -fx-text-fill: " + COLOR_TEXT + "; -fx-font-style: italic;");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setTextAlignment(TextAlignment.CENTER);

        Button loginBtn = new Button("Log in");
        loginBtn.setStyle("-fx-background-color: " + COLOR_BTN_DEFAULT + "; -fx-text-fill: " + COLOR_TEXT
                + "; -fx-font-size: " + SIZE_TEXT + "px; -fx-background-radius: 8;");

        Button signUpBtn = new Button("Sign up");
        signUpBtn.setStyle("-fx-background-color: " + COLOR_BTN_DEFAULT + "; -fx-text-fill: " + COLOR_TEXT
                + "; -fx-font-size: " + SIZE_TEXT + "px; -fx-background-radius: 8;");

        loginBtn.setOnAction(e -> ctrl.handleLogin());
        signUpBtn.setOnAction(e -> ctrl.handleSignUp());

        Label copyrightLabel = new Label("Ⓒ LU Projects");
        copyrightLabel.setStyle(
                "-fx-font-size: " + SIZE_SMALL + "px; -fx-text-fill: " + COLOR_TEXT + "; -fx-font-style: italic;");

        VBox content = new VBox(20, titleLabel, descriptionLabel, loginBtn, signUpBtn, copyrightLabel);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));

        StackPane root = new StackPane();
        root.getChildren().add(content);
        root.setStyle(
                "-fx-background-image: url('/images/background.jpg'); -fx-background-size: cover; -fx-background-position: center;");

        descriptionLabel.maxWidthProperty().bind(root.widthProperty().multiply(0.5));
        loginBtn.prefWidthProperty().bind(root.widthProperty().multiply(0.25));
        signUpBtn.prefWidthProperty().bind(root.widthProperty().multiply(0.25));

        return root;
    }
}