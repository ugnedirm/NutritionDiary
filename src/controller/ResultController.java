package controller;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import model.MealRepository;
import model.User;
import model.UserRepository;
import view.ResultView;

public class ResultController {
    private User user;

    public ResultController(User user) {
        this.user = user;
    }

    public Scene getScene() {
        StackPane root = new ResultView().build(this);
        return new Scene(root, 800, 600);
    }

    public User getUser() {
        return user;
    }

    public void handleGraph() {
        SceneManager.switchTo("graph", UserRepository.getCurrentUser());
    }

    public void handleEnterAgain() {
        SceneManager.switchTo("daytime", null);
    }
}
