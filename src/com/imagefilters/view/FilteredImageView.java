/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.view;

import com.imagefilters.model.AppModel;
import java.awt.image.BufferedImage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;

/**
 *
 * @author asilkaratas
 */
public class FilteredImageView extends Stage
{
    private ImageView imageView;
    
    
    public static boolean IsOpened = false;
    
    public FilteredImageView()
    {
        super(StageStyle.UTILITY);
        
        imageView = new ImageView();
        
        StackPane pane = new StackPane(imageView);
        
        ScrollPane scrollPane = new ScrollPane(pane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        
        StackPane root = new StackPane();
        root.getChildren().add(scrollPane);
        
        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().add(AppModel.CSS);
        
        AppModel.getInstance().getFilteredImage().addListener(new FilteredImageChangeHandler());

        setTitle("filtered image");
        setScene(scene);
        
        setOnCloseRequest(new OnCloseRequestHandler());
        
        updateUI();
        
        IsOpened = true;
    }
    
    private void updateUI()
    {
        handleFilteredImage();
    }
    
    private void handleFilteredImage()
    {
        BufferedImage bufferedImage = AppModel.getInstance().getFilteredImage().getValue();
        if(bufferedImage != null)
        {
            WritableImage fxImage = new WritableImage(bufferedImage.getWidth(), bufferedImage.getHeight());
            SwingFXUtils.toFXImage(bufferedImage, fxImage);
        
            imageView.setImage(fxImage); 
        }
        else
        {
           imageView.setImage(null);
           handleClose();
        }
    }
    
    private void handleClose()
    {
        IsOpened = false;
        close();
    }
    
    private class FilteredImageChangeHandler implements ChangeListener<BufferedImage>
    {
        @Override
        public void changed(ObservableValue<? extends BufferedImage> observable, BufferedImage oldValue, BufferedImage newValue)
        {
            handleFilteredImage();
        }   
    }
    
    private class OnCloseRequestHandler implements EventHandler<WindowEvent>
    {
        @Override
        public void handle(WindowEvent event)
        {
            handleClose();
        }
    }
}
