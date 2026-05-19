package controller;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import model.SessionManager;
import model.User;
import model.UserRepository;
import view.LoginView;

import java.time.LocalDate;

public class LoginController {

    public Scene getScene() {
        StackPane root = new LoginView().build(this);
        return new Scene(root, 800, 600);
    }

    public boolean handleLogin(String username, LocalDate date, double weight) {
        if (username.isEmpty()) return false;
        User user = UserRepository.findByUsername(username);
        if (user == null) return false;
        user.addWeightEntry(date, weight);
        user.setCurrentWeight(weight);
        UserRepository.save();
        SessionManager.login(user);
        UserRepository.setCurrentUser(user);
        SceneManager.switchTo("daytime", null);
        return true;
    }

    public void handleBack() {
        SceneManager.switchTo("main", null);
    }
}
