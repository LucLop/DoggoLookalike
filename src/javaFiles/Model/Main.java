package javaFiles.Model;

import javaFiles.Controller.MainController;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {

        MainController start = new MainController();
        start.main();
    }

}
