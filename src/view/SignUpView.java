package view;

import controller.LoginController;
import controller.SignUpController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SignUpView {

    public StackPane build(SignUpController ctrl) {

        Label subtitleLabel = new Label("Enter your personal data to continue. All fields are necessary");
        subtitleLabel.setStyle("-fx-font-size: 14px; " +
                "-fx-text-fill: black; " +
                "-fx-font-style: italic;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Your username");
        usernameField.setStyle("-fx-background-radius: 8; " +
                "-fx-font-size: 14px;");

        TextField ageField = new TextField();
        ageField.setPromptText("Your age");
        ageField.setStyle("-fx-background-radius: 8; " +
                "-fx-font-size: 14px;");

        TextField heightField = new TextField();
        heightField.setPromptText("Your height (cm)");
        heightField.setStyle("-fx-background-radius: 8; " +
                "-fx-font-size: 14px;");

        TextField currentWeightField = new TextField();
        currentWeightField.setPromptText("Your current weight (kg)");
        currentWeightField.setStyle("-fx-background-radius: 8; " +
                "-fx-font-size: 14px;");

        TextField goalWeightField = new TextField();
        goalWeightField.setPromptText("Your goal weight (kg)");
        goalWeightField.setStyle("-fx-background-radius: 8; " +
                "-fx-font-size: 14px;");

        TextField activityField = new TextField();
        activityField.setPromptText("Activity level (1-5, 1 being sedentary and 5 very intense)");
        activityField.setStyle("-fx-background-radius: 8; " +
                "-fx-font-size: 14px;");

        Label genderLabel = new Label("Your gender");
        genderLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");

        ToggleGroup genderGroup = new ToggleGroup();

        ToggleButton femaleBtn = new ToggleButton("♀ Female");
        ToggleButton maleBtn   = new ToggleButton("♂ Male");

        femaleBtn.setToggleGroup(genderGroup);
        maleBtn.setToggleGroup(genderGroup);
        femaleBtn.setSelected(true);

        String toggleBase = "-fx-font-size: 14px; -fx-background-radius: 20; -fx-padding: 6 20; -fx-cursor: hand;";
        femaleBtn.setStyle(toggleBase + "-fx-background-color: #e91e8c; -fx-text-fill: white;");
        maleBtn.setStyle(toggleBase + "-fx-background-color: #cccccc; -fx-text-fill: black;");

        genderGroup.selectedToggleProperty().addListener((obs, old, selected) -> {
            femaleBtn.setStyle(toggleBase + (femaleBtn.isSelected()
                    ? "-fx-background-color: #e91e8c; -fx-text-fill: white;"
                    : "-fx-background-color: #cccccc; -fx-text-fill: black;"));
            maleBtn.setStyle(toggleBase + (maleBtn.isSelected()
                    ? "-fx-background-color: #4a90d9; -fx-text-fill: white;"
                    : "-fx-background-color: #cccccc; -fx-text-fill: black;"));
        });

        HBox genderBox = new HBox(10, femaleBtn, maleBtn);
        genderBox.setAlignment(Pos.CENTER);

        //TODO
        //error vienas tik pranesimas
        Label errorLabel = new Label("Data is invalid. Please try again.");
        errorLabel.setStyle("-fx-text-fill: #ff4444; " +
                "-fx-font-size: 12px;");
        errorLabel.setVisible(false);

        Button signUpBtn = new Button("Sign up");
        signUpBtn.setStyle("-fx-background-color: #A9A9A9; " +
                "-fx-text-fill: black; " +
                "-fx-font-size: 14px; " +
                "-fx-background-radius: 8;");

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: #A9A9A9; " +
                "-fx-text-fill: black; " +
                "-fx-font-size: 14px; " +
                "-fx-background-radius: 8;");

        signUpBtn.setOnAction(e -> {
            boolean success = ctrl.handleSignUp(
                    usernameField.getText(),
                    ageField.getText(),
                    heightField.getText(),
                    currentWeightField.getText(),
                    goalWeightField.getText(),
                    activityField.getText(),
                    ((ToggleButton) genderGroup.getSelectedToggle()).getText().contains("Female") ? "female" : "male"
            );
            errorLabel.setVisible(!success);
        });
        backBtn.setOnAction(e -> ctrl.handleBack());

        VBox content = new VBox(15, subtitleLabel, usernameField, ageField, heightField,
                currentWeightField, goalWeightField, activityField, genderLabel, genderBox, errorLabel, signUpBtn, backBtn);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));

        StackPane root = new StackPane();
        root.getChildren().add(content);
        root.setStyle("-fx-background-image: url('/images/background.jpg'); " +
                "-fx-background-size: cover; " +
                "-fx-background-position: center;");

        usernameField.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        ageField.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        heightField.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        currentWeightField.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        goalWeightField.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        activityField.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        signUpBtn.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        backBtn.prefWidthProperty().bind(root.widthProperty().multiply(0.3));

        return root;
    }
}

