/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.controller;

import com.imagefilters.filters.convolutionfilters.ConvolutionFilter;
import com.imagefilters.view.ConvolutionFilterView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 *
 * @author asilkaratas
 */
public class ResizeController
{
    private ConvolutionFilterView view;
    private Button minusButton;
    private Button plusButton;
    private ConvolutionFilter filter;
    
    private int minSize;
    private int maxSize;
    
    public ResizeController(ConvolutionFilterView view, Button minusButton, Button plusButton, ConvolutionFilter filter, int minSize, int maxSize)
    {
        this.view = view;
        this.minusButton = minusButton;
        this.plusButton = plusButton;
        this.filter = filter;
        this.minSize = minSize;
        this.maxSize = maxSize;
        
        minusButton.setOnAction(new MinusButtonHandler());
        plusButton.setOnAction(new PlusButtonHandler());
        
        updateButtons();
    }
    
    public void setFilter(ConvolutionFilter filter)
    {
        this.filter = filter;
        updateButtons();
    }
    
    private void increaseSize()
    {
        filter.setSize(filter.getSize() + 2);
        updateView();
    }
    
    private void decreaseSize()
    {
        filter.setSize(filter.getSize() - 2);
        updateView();
    }
    
    private void updateButtons()
    {
        minusButton.setDisable(filter.getSize() == minSize);
        plusButton.setDisable(filter.getSize() == maxSize);
    }
    
    private void updateView()
    {
        updateButtons();
        view.updateUI();
    }
    
    private class MinusButtonHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            decreaseSize();
        }
    }
    
    private class PlusButtonHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            increaseSize();
        }
    }
}
