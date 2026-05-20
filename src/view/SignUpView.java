package view;

import controller.SignUpController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.util.Set;

public class SignUpView {

    private static final String COLOR_BTN_DEFAULT = "#A9A9A9";
    private static final String COLOR_BTN_FEMALE = "#e91e8c";
    private static final String COLOR_BTN_MALE = "#4a90d9";
    private static final String COLOR_BTN_INACTIVE = "#cccccc";
    private static final String COLOR_TEXT = "black";
    private static final String COLOR_TEXT_WHITE = "white";
    private static final String COLOR_ERROR = "#ff4444";
    private static final int SIZE_TEXT = 14;
    private static final int SIZE_ERROR = 12;

    private static final String FIELD_NORMAL = "-fx-background-radius: 8; -fx-font-size: " + SIZE_TEXT + "px;";
    private static final String FIELD_ERROR = "-fx-background-radius: 8; -fx-font-size: " + SIZE_TEXT
            + "px; -fx-border-color: " + COLOR_ERROR + "; -fx-border-radius: 8;";
    private static final String TOGGLE_BASE = "-fx-font-size: " + SIZE_TEXT
            + "px; -fx-background-radius: 20; -fx-padding: 6 20; -fx-cursor: hand;";

    public StackPane build(SignUpController ctrl) {

        Label subtitleLabel = new Label("Enter your personal data to continue. All fields are necessary");
        subtitleLabel.setStyle(
                "-fx-font-size: " + SIZE_TEXT + "px; -fx-text-fill: " + COLOR_TEXT_WHITE + "; -fx-font-style: italic;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Your username");
        usernameField.setStyle(FIELD_NORMAL);

        TextField ageField = new TextField();
        ageField.setPromptText("Your age");
        ageField.setStyle(FIELD_NORMAL);

        TextField heightField = new TextField();
        heightField.setPromptText("Your height (cm)");
        heightField.setStyle(FIELD_NORMAL);

        TextField currentWeightField = new TextField();
        currentWeightField.setPromptText("Your current weight (kg)");
        currentWeightField.setStyle(FIELD_NORMAL);

        TextField goalWeightField = new TextField();
        goalWeightField.setPromptText("Your goal weight (kg)");
        goalWeightField.setStyle(FIELD_NORMAL);

        TextField activityField = new TextField();
        activityField.setPromptText("Activity level (1-5, 1 being sedentary and 5 very intense)");
        activityField.setStyle(FIELD_NORMAL);

        Label genderLabel = new Label("Your gender");
        genderLabel.setStyle("-fx-font-size: " + SIZE_TEXT + "px; -fx-text-fill: " + COLOR_TEXT_WHITE + ";");

        ToggleGroup genderGroup = new ToggleGroup();
        ToggleButton femaleBtn = new ToggleButton("♀ Female");
        ToggleButton maleBtn = new ToggleButton("♂ Male");
        femaleBtn.setToggleGroup(genderGroup);
        maleBtn.setToggleGroup(genderGroup);
        femaleBtn.setSelected(true);

        femaleBtn.setStyle(TOGGLE_BASE + "-fx-background-color: " + COLOR_BTN_FEMALE + "; -fx-text-fill: "
                + COLOR_TEXT_WHITE + ";");
        maleBtn.setStyle(
                TOGGLE_BASE + "-fx-background-color: " + COLOR_BTN_INACTIVE + "; -fx-text-fill: " + COLOR_TEXT + ";");

        genderGroup.selectedToggleProperty().addListener((obs, old, selected) -> {
            femaleBtn.setStyle(TOGGLE_BASE + (femaleBtn.isSelected()
                    ? "-fx-background-color: " + COLOR_BTN_FEMALE + "; -fx-text-fill: " + COLOR_TEXT_WHITE + ";"
                    : "-fx-background-color: " + COLOR_BTN_INACTIVE + "; -fx-text-fill: " + COLOR_TEXT + ";"));
            maleBtn.setStyle(TOGGLE_BASE + (maleBtn.isSelected()
                    ? "-fx-background-color: " + COLOR_BTN_MALE + "; -fx-text-fill: " + COLOR_TEXT_WHITE + ";"
                    : "-fx-background-color: " + COLOR_BTN_INACTIVE + "; -fx-text-fill: " + COLOR_TEXT + ";"));
        });

        HBox genderBox = new HBox(10, femaleBtn, maleBtn);
        genderBox.setAlignment(Pos.CENTER);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: " + COLOR_ERROR + "; -fx-font-size: " + SIZE_ERROR + "px;");
        errorLabel.setVisible(false);

        Button signUpBtn = new Button("Sign up");
        signUpBtn.setStyle("-fx-background-color: " + COLOR_BTN_DEFAULT + "; -fx-text-fill: " + COLOR_TEXT
                + "; -fx-font-size: " + SIZE_TEXT + "px; -fx-background-radius: 8;");

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: " + COLOR_BTN_DEFAULT + "; -fx-text-fill: " + COLOR_TEXT
                + "; -fx-font-size: " + SIZE_TEXT + "px; -fx-background-radius: 8;");

        signUpBtn.setOnAction(e -> {
            usernameField.setStyle(FIELD_NORMAL);
            ageField.setStyle(FIELD_NORMAL);
            heightField.setStyle(FIELD_NORMAL);
            currentWeightField.setStyle(FIELD_NORMAL);
            goalWeightField.setStyle(FIELD_NORMAL);
            activityField.setStyle(FIELD_NORMAL);
            errorLabel.setVisible(false);

            Set<String> errors = ctrl.handleSignUp(
                    usernameField.getText(),
                    ageField.getText(),
                    heightField.getText(),
                    currentWeightField.getText(),
                    goalWeightField.getText(),
                    activityField.getText(),
                    ((ToggleButton) genderGroup.getSelectedToggle()).getText().contains("Female") ? "female" : "male");

            if (!errors.isEmpty()) {
                if (errors.contains("username_taken")) {
                    errorLabel.setText("Username already exists. Please try a different one.");
                    usernameField.setStyle(FIELD_ERROR);
                } else {
                    errorLabel.setText("Data is invalid. Please try again.");
                }
                errorLabel.setVisible(true);
                if (errors.contains("username")) {
                    usernameField.setStyle(FIELD_ERROR);
                }
                if (errors.contains("age")) {
                    ageField.setStyle(FIELD_ERROR);
                }
                if (errors.contains("height")) {
                    heightField.setStyle(FIELD_ERROR);
                }
                if (errors.contains("currentWeight")) {
                    currentWeightField.setStyle(FIELD_ERROR);
                }
                if (errors.contains("goalWeight")) {
                    goalWeightField.setStyle(FIELD_ERROR);
                }
                if (errors.contains("activityLevel")) {
                    activityField.setStyle(FIELD_ERROR);
                }
            }
        });

        backBtn.setOnAction(e -> ctrl.handleBack());

        VBox content = new VBox(15, subtitleLabel, usernameField, ageField, heightField,
                currentWeightField, goalWeightField, activityField, genderLabel, genderBox,
                errorLabel, signUpBtn, backBtn);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));

        StackPane root = new StackPane();
        root.getChildren().add(content);
        root.setStyle(
                "-fx-background-image: url('/images/background.jpg'); -fx-background-size: cover; -fx-background-position: center;");

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