package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.io.ClassPathResource;// load the model
import org.nd4j.shade.jackson.databind.JsonNode;
import org.tensorflow.Graph;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.io.*;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class DogController {

    @FXML
    Button validateButton;

    @FXML
    ImageView dogImg;

    @FXML
    ImageView dogLookalikeImg;

    @FXML
    Text dogBreed;

    @FXML
    TextField dogImgPath;

    Session session;
    private List<String> labels;

    @FXML
    public void dogBreedRecognition(ActionEvent event) throws Exception{
        System.out.println(dogImg.getImage());
        //resize the image
        ImageView tempImg = dogImg;
        tempImg.setFitHeight(128);
        tempImg.setFitWidth(128);
        Image dogResized = tempImg.getImage();
/*
        String str = "../model.b";
        SavedModelBundle savedModelBundle = SavedModelBundle.load(str, "serve");
        Graph graph = savedModelBundle.graph();
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
        }
*/
      String  result = "Siberian_husky";
        // run the model and get the result, 4.0f.
      //  float[] y = sess.runner()
        //        .feed("x", x)
          //      .fetch("y")
            //    .run()
              //  .get(0)
               // .copyTo(new float[NUM_PREDICTIONS]);
        //printOperations(graph);
      /*  Tensor<?> tensorInput;
        Tensor result = savedModelBundle.session().runner()
                .feed("myInput", tensorInput)
                .fetch("myOutput")
                .run().get(0);
*/
        //String str = new ClassPathResource("/cnnModel/model128ep50.h5").getFile().getPath();

        //MultiLayerNetwork model = KerasModelImport.
          //      importKerasSequentialModelAndWeights(str);

/*
        // make a random sample
        int inputs = 10;
        INDArray features = Nd4j.zeros(inputs);
        for (int i=0; i<inputs; i++)
            features.putScalar(new int[] {i}, Math.random() < 0.5 ? 0 : 1);
// get the prediction
        double prediction = model.output(features).getDouble(0);

*/

 /*       //InputStream FileName = new FileInputStream("/lucas/home/Desktop/Noug/DoggoApp/model128ep50.h5");
        try (SavedModelBundle savedModelBundle = SavedModelBundle.load("/cnnModel/model128ep50.h5", "serve")){

        }catch (Exception e){

        }
      //  Path modelPath = Paths.get(LoadTensorflowModel.class.getResource("saved_model.pb").toURI());
        String myModelStr = new ClassPathResource("/cnnModel/model128ep50.h5")
                .getFile().getPath();
        MultiLayerNetwork model = KerasModelImport.
                importKerasSequentialModelAndWeights(myModelStr);// make a random sample
        DataSet dataSet = new DataSet();
        ArrayList<Image> array = new ArrayList<Image>();
        dataSet.addFeatureVector((INDArray) array);
  //      array.add(dogResized);
        List<String> result = model.predict(dataSet);
        int inputs = 10;
        INDArray features = Nd4j.zeros(inputs);
        for (int i=0; i<inputs; i++)
            features.putScalar(new int[] {i}, Math.random() < 0.5 ? 0 : 1);// get the prediction
        double prediction = model.output(features).getDouble(0);

*/
        //result = model.predict(img)
        String text = "This dog is a(n) " + result;
        dogBreed.setText(text);
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
        Image img = new Image(new FileInputStream(files.get(0)));
        dogImg.setImage(img);
        //dragText.setVisible(false);
    }


/*
    @FXML
    public void handleDragDetection(MouseEvent event){
        Dragboard db = dragText.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putString(dragText.getText());
        db.setContent(content);

        event.consume();
    }
    */
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
