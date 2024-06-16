package hr.algebra.javafxinsurance;

import hr.algebra.javafxinsurance.service.TokenService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        Main.stage=stage;
        try {
            if(!TokenService.INSTANCE.isAuthenticated()){
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 400, 400);
                stage.setTitle("Login");
                stage.setScene(scene);
                stage.show();
            }else{
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                stage.setTitle("Insurance");
                stage.setScene(scene);
                stage.show();
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void showLoginScreen(){
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 400, 400);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            stage.close();
            throw new RuntimeException("Something bad happened");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}