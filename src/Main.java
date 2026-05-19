import controller.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;
import model.UserRepository;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        UserRepository.load();
        SceneManager.init(stage);
        SceneManager.switchTo("main", null);
        stage.setTitle("Nutrition Diary");
        stage.setOnCloseRequest(e -> UserRepository.save());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}