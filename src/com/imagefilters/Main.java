/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.imagefilters.model.AppModel;
import com.imagefilters.view.RootPane;
import ij.process.ByteProcessor;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author asilkaratas
 */
public class Main extends Application 
{
    @Override
    public void start(Stage primaryStage) 
    {
        RootPane root = new RootPane();
        
        Scene scene = new Scene(root, 1085, 820);
        scene.getStylesheets().add(AppModel.CSS);
        
        primaryStage.setTitle("Image Filters");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
