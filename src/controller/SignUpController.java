
package controller;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import model.SessionManager;
import model.User;
import model.UserRepository;
import view.SignUpView;
import java.util.HashSet;
import java.util.Set;

public class SignUpController {

    public Scene getScene() {
        StackPane root = new SignUpView().build(this);
        return new Scene(root, 800, 600);
    }

    public Set<String> handleSignUp(String username, String age, String height,
                                    String currentWeight, String goalWeight, String activityLevel, String gender) {
        Set<String> errors = new HashSet<>();

        if (username.isEmpty())
            errors.add("username");
        if (UserRepository.exists(username))
            errors.add("username_taken");

        try {
            int v = Integer.parseInt(age);
            if (v <= 0 || v > 120)
                errors.add("age");
        } catch (NumberFormatException e) {
            errors.add("age");
        }

        try {
            double v = Double.parseDouble(height);
            if (v <= 0 || v > 300)
                errors.add("height");
        } catch (NumberFormatException e) {
            errors.add("height");
        }

        try {
            double v = Double.parseDouble(currentWeight);
            if (v <= 0 || v > 500)
                errors.add("currentWeight");
        } catch (NumberFormatException e) {
            errors.add("currentWeight");
        }

        try {
            double v = Double.parseDouble(goalWeight);
            if (v <= 0 || v > 500)
                errors.add("goalWeight");
        } catch (NumberFormatException e) {
            errors.add("goalWeight");
        }

        try {
            int v = Integer.parseInt(activityLevel);
            if (v <= 0 || v > 5)
                errors.add("activityLevel");
        } catch (NumberFormatException e) {
            errors.add("activityLevel");
        }

        if (!errors.isEmpty())
            return errors;

        User user = new User(
                username,
                Integer.parseInt(age),
                Double.parseDouble(height),
                Double.parseDouble(currentWeight),
                Double.parseDouble(goalWeight),
                Integer.parseInt(activityLevel),
                gender);
        UserRepository.addUser(user);
        SessionManager.login(user);
        UserRepository.setCurrentUser(user);
        SceneManager.switchTo("daytime", null);
        return errors;
    }

    public void handleBack() {
        SceneManager.switchTo("main", null);
    }
}