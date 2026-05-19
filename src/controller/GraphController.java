package controller;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import model.User;
import view.GraphView;

public class GraphController {

    private User user;

    public GraphController(User user) { this.user = user; }

    public Scene getScene() {
        StackPane root = new GraphView().build(this);
        return new Scene(root, 800, 600);
    }

    public User getUser() { return user; }

    public void handleBack() {
        SceneManager.switchTo("result", user);
    }
}
