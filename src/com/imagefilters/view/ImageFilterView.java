/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.view;

import com.imagefilters.filters.ImageFilter;
import com.imagefilters.model.AppModel;
import java.awt.image.BufferedImage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 *
 * @author asilkaratas
 */
public class ImageFilterView extends VBox
{
    protected ImageFilter originalFilter;
    protected ImageFilter filter;
   
    protected BorderedTitledPane applyPane;
    
    public ImageFilterView(ImageFilter filter)
    {
        originalFilter = filter;
        this.filter = filter.clone();
        
        Button applyButton = new Button("apply");
        applyButton.setOnAction(new ApplyButtonHandler());
        
        Button resetButton = new Button("reset");
        resetButton.setOnAction(new ResetButtonHandler());
        
        HBox applyBox = new HBox();
        applyBox.setSpacing(5.0);
        applyBox.getChildren().addAll(applyButton, resetButton);
        
        applyPane = new BorderedTitledPane("apply", applyBox);
        
        getChildren().addAll(applyPane);
    }
    
    
    protected void updateUI()
    {
        
    }
    
    protected void readFromUI()
    {
        
    }
    
    protected void apply()
    {
        readFromUI();
        
        BufferedImage originalImage = AppModel.getInstance().getOriginalImage().getValue();
        BufferedImage filteredImage = filter.apply(originalImage);
        
        AppModel.getInstance().getFilteredImage().setValue(filteredImage);
    }
    
    protected void reset()
    {
        filter = originalFilter.clone();
        updateUI();
        apply();
    }
    
    private class ApplyButtonHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            apply();
        }
    }
    
    private class ResetButtonHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            reset();
        }
    }
    
    
}
