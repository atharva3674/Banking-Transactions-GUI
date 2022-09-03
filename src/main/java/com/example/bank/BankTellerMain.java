package com.example.bank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Bank Teller class that runs the GUI for user interaction.
 * @author Akshat Mehta and Atharva Patil.
 */
public class BankTellerMain extends Application {

    /**
     * Opens up the GUI on the screen.
     * @param stage Stage where GUI window is displayed.
     * @throws IOException exceptions regarding opening the GUI.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankTellerMain.class.getResource("BankTellerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("Bank Teller");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the GUI.
     * @param args arguments for main method.
     */
    public static void main(String[] args) {
        launch();
    }
}