/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.view;

import javafx.scene.layout.StackPane;
import javafx.scene.control.SplitPane;

/**
 *
 * @author asilkaratas
 */
public class RootPane extends StackPane
{
    public RootPane()
    {
        ImageViewerView imageViewerView = new ImageViewerView();
        FiltersView filtersView = new FiltersView();
        
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(imageViewerView, filtersView);
        splitPane.setDividerPositions(.5, .5);
        
        getChildren().add(splitPane);
    }
}
