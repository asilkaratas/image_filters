/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.view;

import com.imagefilters.filters.thresholdfilters.ThresholdFilter;
import com.imagefilters.model.AppModel;
import java.awt.image.BufferedImage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 *
 * @author asilkaratas
 */
public class ThresholdFilterView extends ImageFilterView
{
    private Slider slider;
    
    ThresholdFilterView(ThresholdFilter filter)
    {
        super(filter);
        
        slider = new Slider(0, 255, filter.getThreshold());
        slider.setPrefWidth(255);
        
        VBox gammaBox = new VBox();
        gammaBox.getChildren().add(slider);
        
        BorderedTitledPane gammaPane = new BorderedTitledPane("threshold", gammaBox);
        
        getChildren().clear();
        getChildren().addAll(gammaPane, applyPane);
    }
    
    public ThresholdFilter getThresholdFilter()
    {
        return (ThresholdFilter)filter;
    }
    
    @Override
    protected void updateUI()
    {
        slider.setValue(getThresholdFilter().getThreshold());
    }
    
    @Override
    protected void readFromUI()
    {
        getThresholdFilter().setThreshold((int)slider.getValue());
    }
    
}
