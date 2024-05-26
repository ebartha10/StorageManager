package com.example.tp_tema3_fx;

import com.example.tp_tema3_fx.backend.database.impl.QueryClient;
import com.example.tp_tema3_fx.backend.database.impl.QueryProduct;
import com.example.tp_tema3_fx.backend.singleton.ConnectionFactorySingleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SceneProduct.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("Storage Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        QueryProduct queryClient=new QueryProduct();
        System.out.println(queryClient.findByName());
        System.out.println(queryClient.getFindAll());
        System.out.println(queryClient.getFindById());
        System.out.println(queryClient.getInsert());
        System.out.println(queryClient.getUpdate());
        System.out.println(queryClient.getDelete());
        launch();
        Runtime.getRuntime().addShutdownHook(new Thread(ConnectionFactorySingleton.getConnectionFactoryService()::closeConnection));
    }
}