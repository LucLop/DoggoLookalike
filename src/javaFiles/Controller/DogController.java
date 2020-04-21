package javaFiles.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class DogController {

    @FXML
    Button validateButton;

    @FXML
    ImageView dogImg;

    @FXML
    ImageView dogLookalikeImg;

    @FXML
    Label dogBreed;

    @FXML
    public void dogBreedRecognition(ActionEvent event) throws Exception{
        System.out.println("in breed recognition");
        dogBreed.setText("This dog is a ");
    }

    @FXML
    public void returnMenu(ActionEvent event){
        Stage vite = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AnchorPane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("/resources/View/MainScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(pane);

        vite.setScene(scene);

    }
}
