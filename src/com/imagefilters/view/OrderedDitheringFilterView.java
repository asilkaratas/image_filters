/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.view;

import com.imagefilters.filters.ImageFilter;
import com.imagefilters.filters.colorquantizationfilter.ColorQuantizationFilter;
import com.imagefilters.filters.ditheringfilters.OrderedDitheringFilter;
import javafx.geometry.HPos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author asilkaratas
 */
public class OrderedDitheringFilterView extends ImageFilterView
{
    private TextField paletteSizeText;
    private CheckBox coloredCheckBox;
    
    public OrderedDitheringFilterView(ImageFilter filter)
    {
        super(filter);
        
        Label paletSizeLabel = new Label("palette size:");
        
        paletteSizeText = new TextField();
        paletteSizeText.setMaxWidth(50);
        
        coloredCheckBox = new CheckBox("colored");
        
        
        
        GridPane.setConstraints(paletSizeLabel, 0, 0);
        GridPane.setConstraints(paletteSizeText, 1, 0);
        GridPane.setConstraints(coloredCheckBox, 1, 1);
        GridPane.setHalignment(paletSizeLabel, HPos.RIGHT);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(5.0);
        gridPane.setVgap(5.0);
        gridPane.getChildren().addAll(paletSizeLabel, paletteSizeText, coloredCheckBox);
        
        BorderedTitledPane paletteSizePane = new BorderedTitledPane("palette size", gridPane);
        
        getChildren().add(0, paletteSizePane);
        
        updateUI();
    }
    
    @Override
    protected void updateUI()
    {
        paletteSizeText.setText(String.valueOf(getOrderedDitheringFilter().getColorPaletteSize()));
        coloredCheckBox.setSelected(getOrderedDitheringFilter().getColored());
    }
    
    @Override
    protected void readFromUI()
    {
        getOrderedDitheringFilter().setColorPaletteSize(Integer.parseInt(paletteSizeText.getText()));
        getOrderedDitheringFilter().setColored(coloredCheckBox.isSelected());
    }
    
    public OrderedDitheringFilter getOrderedDitheringFilter()
    {
        return (OrderedDitheringFilter)filter;
    }
    
}
