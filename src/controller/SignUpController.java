package controller;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import model.SessionManager;
import model.User;
import model.UserRepository;
import view.SignUpView;

public class SignUpController {

    public Scene getScene() {
        StackPane root = new SignUpView().build(this);
        return new Scene(root, 800, 600);
    }

    public boolean handleSignUp(String username, String age, String height,
                                String currentWeight, String goalWeight, String activityLevel, String gender) {
        if (username.isEmpty() || age.isEmpty() || height.isEmpty() ||
                currentWeight.isEmpty() || goalWeight.isEmpty() || activityLevel.isEmpty() || gender.isEmpty()) {
            return false;
        }

        if (UserRepository.exists(username)) return false;

        try {
            User user = new User(
                    username,
                    Integer.parseInt(age),
                    Double.parseDouble(height),
                    Double.parseDouble(currentWeight),
                    Double.parseDouble(goalWeight),
                    Integer.parseInt(activityLevel),
                    gender
            );
            UserRepository.addUser(user);
            SessionManager.login(user);
            UserRepository.setCurrentUser(user);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }

        SceneManager.switchTo("daytime", null);
        return true;
    }

    public void handleBack() {
        SceneManager.switchTo("main", null);
    }
}
