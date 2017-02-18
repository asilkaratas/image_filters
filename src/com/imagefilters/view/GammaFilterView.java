/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.view;

import javafx.scene.layout.VBox;
import com.imagefilters.filters.gammafilters.GammaFilter;
import javafx.scene.control.TextField;
/**
 *
 * @author asilkaratas
 */
public class GammaFilterView extends ImageFilterView
{
    private TextField gammaField;
    
    public GammaFilterView(GammaFilter filter)
    {
        super(filter);
        
        gammaField = new TextField();
        
        VBox gammaBox = new VBox();
        gammaBox.getChildren().add(gammaField);
        
        BorderedTitledPane gammaPane = new BorderedTitledPane("gamma", gammaBox);
        
        getChildren().clear();
        getChildren().addAll(gammaPane, applyPane);
        
        updateUI();
    }
    
    private GammaFilter getGammaFilter()
    {
        return (GammaFilter)filter;
    }
    
    @Override
    protected void updateUI()
    {
        gammaField.setText(String.valueOf(getGammaFilter().getGamma()));
    }
    
    @Override
    protected void readFromUI()
    {
        double gamma = Double.parseDouble(gammaField.getText());
        getGammaFilter().setGamma(gamma);
    }
    
}
