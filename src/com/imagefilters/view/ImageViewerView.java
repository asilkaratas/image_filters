/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.view;

import com.imagefilters.model.AppModel;
import com.imagefilters.model.ImageExtensions;
import ij.ImagePlus;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 *
 * @author asilkaratas
 */
public class ImageViewerView extends StackPane
{
    public static double WIDTH = 316;
    public static double HEIGHT = 316;
    
    private Button loadImageButton;
    private Button clearButton;
    private ImageView originalImageView;
    private ImageView filteredImageView;
    
    public ImageViewerView()
    {
        loadImageButton = new Button("load image");
        loadImageButton.setPrefWidth(90.0);
        loadImageButton.setOnAction(new LoadImageButtonHandler());
        
        clearButton = new Button("clear");
        clearButton.setPrefWidth(90.0);
        clearButton.setOnAction(new ClearButtonHandler());
        
        VBox loadImageVBox = new VBox();
        loadImageVBox.setSpacing(5.0);
        loadImageVBox.setPrefHeight(100.0);
        loadImageVBox.getChildren().addAll(loadImageButton, clearButton);
        BorderedTitledPane loadImagePane = new BorderedTitledPane("load image", loadImageVBox);
        
        originalImageView = new ImageView();
        originalImageView.setFitWidth(WIDTH);
        originalImageView.setFitHeight(HEIGHT);
        originalImageView.setPreserveRatio(true);
        VBox originalImageVBox = new VBox();
        originalImageVBox.setPrefSize(WIDTH, HEIGHT);
        originalImageVBox.setAlignment(Pos.CENTER);
        originalImageVBox.getChildren().add(originalImageView);
        BorderedTitledPane originalImagePane = new BorderedTitledPane("original image", originalImageVBox);
        
        filteredImageView = new ImageView();
        filteredImageView.setFitWidth(WIDTH);
        filteredImageView.setFitHeight(HEIGHT);
        filteredImageView.setPreserveRatio(true);
        filteredImageView.setOnMouseClicked(new FilteredImageViewClickHandler());
        VBox filteredImageVBox = new VBox();
        filteredImageVBox.setPrefSize(WIDTH, HEIGHT);
        filteredImageVBox.setAlignment(Pos.CENTER);
        filteredImageVBox.getChildren().add(filteredImageView);
        BorderedTitledPane filteredImagePane = new BorderedTitledPane("filtered image", filteredImageVBox);
        
        VBox vBox = new VBox();
        vBox.getChildren().addAll(originalImagePane, filteredImagePane);
        
        HBox imageViewerHBox = new HBox();
        imageViewerHBox.getChildren().addAll(loadImagePane, vBox);
        
        BorderedTitledPane imageViewerPane = new BorderedTitledPane("image viewer", imageViewerHBox);
        getChildren().add(imageViewerPane);
        
        AppModel.getInstance().getOriginalImage().addListener(new OriginalImageChangeHandler());
        AppModel.getInstance().getFilteredImage().addListener(new FilteredImageChangeHandler());
        
        updateUI();
    }
    
    
    private void loadImage()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(ImageExtensions.getImageFilter());

        File file = fileChooser.showOpenDialog(getScene().getWindow());

        if(file != null)
        {
            ImagePlus imagePlus = new ImagePlus(file.getPath());
            ImageProcessor imageProcessor = imagePlus.getProcessor();
            
            ByteProcessor byteProcessor = imageProcessor.convertToByteProcessor();
            BufferedImage bufferedImage = byteProcessor.getBufferedImage();
            
            //ColorProcessor colorProcessor = imageProcessor.convertToColorProcessor();
            //BufferedImage bufferedImage = colorProcessor.getBufferedImage();
    
            AppModel.getInstance().getOriginalImage().setValue(bufferedImage);
            AppModel.getInstance().getFilteredImage().setValue(null);
        }
    }
    
    private void handleClear()
    {
        AppModel.getInstance().clear();
    }
    
    private void handleOriginalImage()
    {
        BufferedImage bufferedImage = AppModel.getInstance().getOriginalImage().getValue();
        if(bufferedImage != null)
        {
            WritableImage fxImage = new WritableImage(bufferedImage.getWidth(), bufferedImage.getHeight());
            SwingFXUtils.toFXImage(bufferedImage, fxImage);
        
            originalImageView.setImage(fxImage);
        }
        else
        {
          originalImageView.setImage(null);
          
        }
        
        updateUI();
    }
    
    private void updateUI()
    {
        Boolean hasImage = AppModel.getInstance().getOriginalImage().getValue() != null;
        clearButton.setVisible(hasImage);
    }
    
    private void handleFilteredImage()
    {
        BufferedImage bufferedImage = AppModel.getInstance().getFilteredImage().getValue();
        if(bufferedImage != null)
        {
            WritableImage fxImage = new WritableImage(bufferedImage.getWidth(), bufferedImage.getHeight());
            SwingFXUtils.toFXImage(bufferedImage, fxImage);
        
            filteredImageView.setImage(fxImage); 
        }
        else
        {
           filteredImageView.setImage(null);
        }
    }
    
    private void handleExlargeFilteredImage()
    {
        if(!FilteredImageView.IsOpened)
        {
            FilteredImageView view = new FilteredImageView();
            view.show();
        }
        
    }
    
    private class LoadImageButtonHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            loadImage();
        }
    }
    
    private class ClearButtonHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            handleClear();
        }
    }
    
    private class FilteredImageViewClickHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            handleExlargeFilteredImage();
        }
    }
    
    private class ImageFileFilter implements FileFilter
    {
        @Override
        public boolean accept(File file)
        {
            final String[] extensions = ImageExtensions.getExtensions();

            for (String extension : extensions)
            {
                if (file.getName().toLowerCase().endsWith(extension))
                {
                    return true;
                }
            }
            return false;
        }

    }
    
    private class FilteredImageChangeHandler implements ChangeListener<BufferedImage>
    {
        @Override
        public void changed(ObservableValue<? extends BufferedImage> observable, BufferedImage oldValue, BufferedImage newValue)
        {
            handleFilteredImage();
        }   
    }
    
    private class OriginalImageChangeHandler implements ChangeListener<BufferedImage>
    {
        @Override
        public void changed(ObservableValue<? extends BufferedImage> observable, BufferedImage oldValue, BufferedImage newValue)
        {
            handleOriginalImage();
        }   
    }
}
