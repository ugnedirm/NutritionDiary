package view;

import controller.MainMenuController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class MainMenuView {

    public StackPane build(MainMenuController ctrl) {

        Label titleLabel = new Label("Welcome to Nutrition Diary!");
        titleLabel.setStyle("-fx-font-size: 32px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: black;");

        Label descriptionLabel = new Label("Your daily nutrition companion. " +
                "Log meals, monitor dairy consumption, " +
                "and stay on top of your health goals with " +
                "smart insights and easy tracking.");
        descriptionLabel.setStyle("-fx-font-size: 14px; " +
                "-fx-text-fill: black; " +
                "-fx-font-style: italic;");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setTextAlignment(TextAlignment.CENTER);

        Button loginBtn = new Button("Log in");
        loginBtn.setStyle("-fx-background-color: #A9A9A9; " +
                "-fx-text-fill: black; " +
                "-fx-font-size: 14px; " +
                "-fx-background-radius: 8;");

        Button signUpBtn = new Button("Sign up");
        signUpBtn.setStyle("-fx-background-color: #A9A9A9; " +
                "-fx-text-fill: black; " +
                "-fx-font-size: 14px; " +
                "-fx-background-radius: 8;");

        loginBtn.setOnAction(e -> ctrl.handleLogin());
        signUpBtn.setOnAction(e -> ctrl.handleSignUp());

        VBox content = new VBox(20, titleLabel, descriptionLabel, loginBtn, signUpBtn);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));

        StackPane root = new StackPane();
        root.getChildren().add(content);
        root.setStyle("-fx-background-image: url('/images/background.jpg'); " +
                "-fx-background-size: cover; " +
                "-fx-background-position: center;");

        descriptionLabel.maxWidthProperty().bind(root.widthProperty().multiply(0.5));
        loginBtn.prefWidthProperty().bind(root.widthProperty().multiply(0.25));
        signUpBtn.prefWidthProperty().bind(root.widthProperty().multiply(0.25));

        return root;
    }
}
