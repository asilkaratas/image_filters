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
public class RowColumnResizeController
{
    private ConvolutionFilterView view;
    private ConvolutionFilter filter;
    private Button addRowButton;
    private Button removeRowButton;
    private Button addColumnButton;
    private Button removeColumnButton;
    
    private int minValue = 3;
    private int maxValue = 9;
    
    public RowColumnResizeController(ConvolutionFilterView view, ConvolutionFilter filter, Button addRowButton, Button removeRowButton, Button addColumnButton, Button removeColumnButton)
    {
        this.view = view;
        this.filter =filter;
        this.addRowButton = addRowButton;
        this.removeRowButton = removeRowButton;
        this.addColumnButton = addColumnButton;
        this.removeColumnButton = removeColumnButton;
        
        addRowButton.setOnAction(new AddRowButtonHandler());
        removeRowButton.setOnAction(new RemoveRowButtonHandler());
        addColumnButton.setOnAction(new AddColumnButtonHandler());
        removeColumnButton.setOnAction(new RemoveColumnButtonHandler());
    }
    
    private class AddRowButtonHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            if(filter.getKernelHeight() < maxValue)
            {
                filter.setKernelHeight(filter.getKernelHeight() + 1);
                view.updateUI();
            }
        }
        
    }
    
    private class RemoveRowButtonHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            if(filter.getKernelHeight() > minValue)
            {
                filter.setKernelHeight(filter.getKernelHeight() - 1);
                view.updateUI();
            }
        }
    }
    
    private class AddColumnButtonHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            if(filter.getKernelWidth()< maxValue)
            {
                filter.setKernelWidth(filter.getKernelWidth()+ 1);
                view.updateUI();
            }
        }
    }
    
    private class RemoveColumnButtonHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            if(filter.getKernelWidth() > minValue)
            {
                filter.setKernelWidth(filter.getKernelWidth()- 1);
                view.updateUI();
            }
        }
    }
}
