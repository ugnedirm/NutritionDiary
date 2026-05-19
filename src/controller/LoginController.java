package controller;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import model.SessionManager;
import model.User;
import model.UserRepository;
import view.LoginView;

public class LoginController {

    public Scene getScene() {
        StackPane root = new LoginView().build(this);
        return new Scene(root, 800, 600);
    }

    public boolean handleLogin(String username) {
        if (username.isEmpty()) return false;
        User user = UserRepository.findByUsername(username);
        if (user == null) return false;
        SessionManager.login(user);
        SceneManager.switchTo("daytime", null);
        return true;
    }

    public void handleBack() {
        SceneManager.switchTo("main", null);
    }
}
