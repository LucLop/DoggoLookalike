package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.tensorflow.Graph;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HumanController {

    @FXML
    ImageView humanImg;

    @FXML
    Button validateButton;

    @FXML
    Text breedLabel;

    Session session;
    private List<String> labels;

    @FXML
    public void humanDogLookalikeRecognition(ActionEvent event) throws Exception {

        System.out.println(humanImg.getImage());
        //resize the image
        ImageView tempImg = humanImg;
        tempImg.setFitHeight(128);
        tempImg.setFitWidth(128);
        javafx.scene.image.Image dogResized = tempImg.getImage();
/*
        // create the session from the Bundle
        Path modelPath =  Paths.get(this.getClass().getResource("../model.b/saved_model.pb").toURI());
        Path labelsPath = Paths.get(this.getClass().getResource("../model.b").toURI());
        byte[] graphData = Files.readAllBytes(modelPath);
        labels = Files.readAllLines(labelsPath);
        Graph modelGraph = new Graph();
        modelGraph.importGraphDef(graphData);
        session = new Session(modelGraph);
        Tensor imageTensor = Tensor.create(dogResized, Float.class);
        float[][] prediction = predict(imageTensor);
        List<String> predictions = findPredictedLabel(prediction);

        String result= "";
        for (String string : predictions){
            result = result + " "+ string;
        }*/
        String result = "cocker_spaniel";
        String text = "Your dog lookalike is a(n)"+result;
        breedLabel.setText(text);

    }

    private float[][] predict(Tensor imageTensor) {
        Tensor result = session.runner()
                .feed("input", imageTensor)
                .fetch("output").run().get(0);
        int batchSize = (int)result.shape()[0];
        //create prediction buffer
        float[][] prediction = new float[batchSize][1008];
        result.copyTo(prediction);
        return prediction;
    }

    private List<String> findPredictedLabel(float[][] prediction) {
        List<String> result = new ArrayList<>();
        int batchSize = prediction.length;
        for (int i = 0; i < batchSize; i++) {
            //Finding maximum value for each predicted image
            int maxValueIndex = 0;
            for (int j = 1; j < prediction[i].length; j++) {
                if (prediction[i][maxValueIndex] < prediction[i][j]) {
                    maxValueIndex = j;
                }
            }
            result.add(labels.get(maxValueIndex) + ": " + (prediction[i][maxValueIndex] * 100) + "%");
        }
        return result;
    }
    @FXML
    public void handleImageDragOver(DragEvent event){
        if(event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
        }else if(event.getDragboard().hasImage()){
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    public void handleImageDrop(DragEvent event) throws FileNotFoundException {
        List<File> files = event.getDragboard().getFiles();
        javafx.scene.image.Image img = new  javafx.scene.image.Image(new FileInputStream(files.get(0)));
        humanImg.setImage(img);
        //dragText.setVisible(false);
    }

    @FXML
    public void returnMenu(ActionEvent event){
        Stage vite = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AnchorPane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("/MainScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(pane);

        vite.setScene(scene);

    }
}
