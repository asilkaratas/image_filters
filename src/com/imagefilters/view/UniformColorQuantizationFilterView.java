/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.view;

import com.imagefilters.filters.ImageFilter;
import com.imagefilters.filters.colorquantizationfilter.ColorQuantizationFilter;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
        

/**
 *
 * @author asilkaratas
 */
public class UniformColorQuantizationFilterView extends ImageFilterView
{
    private TextField redText;
    private TextField greenText;
    private TextField blueText;
    
    public UniformColorQuantizationFilterView(ImageFilter filter)
    {
        super(filter);
        
        redText = new TextField();
        greenText = new TextField();
        blueText = new TextField();
        
        redText.setMaxWidth(50);
        greenText.setMaxWidth(50);
        blueText.setMaxWidth(50);
        
        Label redLabel = new Label("red:");
        Label greenLabel = new Label("green:");
        Label blueLabel = new Label("blue:");
        
        GridPane.setConstraints(redLabel, 0, 0);
        GridPane.setConstraints(redText, 1, 0);
        GridPane.setConstraints(greenLabel, 0, 1);
        GridPane.setConstraints(greenText, 1, 1);
        GridPane.setConstraints(blueLabel, 0, 2);
        GridPane.setConstraints(blueText, 1, 2);
        GridPane.setHalignment(redLabel, HPos.RIGHT);
        GridPane.setHalignment(greenLabel, HPos.RIGHT);
        GridPane.setHalignment(blueLabel, HPos.RIGHT);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(5.0);
        gridPane.setVgap(5.0);
        gridPane.getChildren().addAll(redLabel, redText, 
                                      greenLabel, greenText,
                                      blueLabel, blueText);
        
        BorderedTitledPane paletteSize = new BorderedTitledPane("palette size", gridPane);
        
        getChildren().add(0, paletteSize);
        
        updateUI();
    }
    
    private ColorQuantizationFilter getColorQuantizationFilter()
    {
        return (ColorQuantizationFilter)filter;
    }
    
    @Override
    protected void updateUI()
    {
        redText.setText(String.valueOf(getColorQuantizationFilter().getRedPaletteSize()));
        greenText.setText(String.valueOf(getColorQuantizationFilter().getGreenPaletteSize()));
        blueText.setText(String.valueOf(getColorQuantizationFilter().getBluePaletteSize()));
    }
    
    @Override
    protected void readFromUI()
    {
        ColorQuantizationFilter colorQuantizationFilter = (ColorQuantizationFilter)filter;
        colorQuantizationFilter.setRedPaletteSize(Integer.parseInt(redText.getText()));
        colorQuantizationFilter.setGreenPaletteSize(Integer.parseInt(greenText.getText()));
        colorQuantizationFilter.setBluePaletteSize(Integer.parseInt(blueText.getText()));
    }
}
