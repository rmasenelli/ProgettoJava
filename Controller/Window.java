
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Window extends Application {
    
    Stage window;
    Scene scene1, scene2;
    
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        
        window = stage;
        window.setTitle("Prova");

        Label label1 = new Label("Pannello Login");
        Button button1 = new Button("Login");
        button1.setOnAction(e -> window.setScene(scene2));

        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, button1);
        scene = new Scene(layout1, 700, 400);

        Label label2 = new Label("Pannello Utente");
        Button button2 = new Button("Logout");
        button.setOnAction(e -> window.setScene(scene1));

        window.setScene(scene1);
        window.show();
        
    }
}