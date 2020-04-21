package javaFiles.Controller;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends Application implements Initializable {
    private Stage stage;
    private Scene scene1, scene2;

    public void main() throws FileNotFoundException, IOException, Exception {

        launch();
    }

    @Override
    public void start(Stage astage) throws Exception {

        this.stage = astage;

        AnchorPane pane = FXMLLoader.load(getClass().getResource("/resources/View/MainScreen.fxml"));
        this.scene1 = new Scene(pane);


        this.stage.setScene(this.scene1);
        this.stage.setTitle("DoggoLookalike App");
        this.stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (stage != null) {
          //  if (scene1 != null) existingTournamentButton.setOnAction(e -> stage.setScene(scene2));
        }

    }


    @FXML
    public void handleDogButtonClick(ActionEvent event)throws Exception{
        Stage vite = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AnchorPane pane2 = FXMLLoader.load(getClass().getResource("/resources/View/DogScreen.fxml"));
        this.scene2 = new Scene(pane2);

        System.out.println("in handle dog");

        vite.setScene(this.scene2);
        /*System.out.println(scene1 +" "+ scene2+ " "+stage);
        System.out.println(existingTournamentButton);
        existingTournamentButton.
        stage.setScene(scene2);
        */
    }


    @FXML
    public void handleHumanButtonClick(ActionEvent event)throws Exception{
       Stage vite = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AnchorPane pane2 = FXMLLoader.load(getClass().getResource("/resources/View/HumanScreen.fxml"));
        this.scene2 = new Scene(pane2);

        System.out.println("in handle human");

        vite.setScene(this.scene2);
        /*System.out.println(scene1 +" "+ scene2+ " "+stage);
        System.out.println(existingTournamentButton);
        existingTournamentButton.
        stage.setScene(scene2);
        */
    }

}

