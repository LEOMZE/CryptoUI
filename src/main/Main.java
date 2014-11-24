package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/main/cryptoui.fxml"));
        primaryStage.setTitle("CAST-128 CRYPTO APP v0.1");
        primaryStage.setWidth(600);
        primaryStage.setHeight(550);
        primaryStage.setResizable(false);
        root.getStylesheets().add("\\main\\chart_style.css");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }


    public static void main(String[] args) {
                launch(args);


    }
}
