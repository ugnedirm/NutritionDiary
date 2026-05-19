package controller;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import view.MainMenuView;

public class MainMenuController {

    public Scene getScene() {
        StackPane root = new MainMenuView().build(this);
        return new Scene(root, 800, 600);
    }

    public void handleLogin() {
        SceneManager.switchTo("login", null);
    }

    public void handleSignUp() {
        SceneManager.switchTo("signup", null);
    }
}
