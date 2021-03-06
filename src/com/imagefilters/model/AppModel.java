/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.model;

import java.awt.image.BufferedImage;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author asilkaratas
 */
public class AppModel
{
    public static final String CSS = "assets/css/style.css";
    
    private ObjectProperty<BufferedImage> originalImage;
    private ObjectProperty<BufferedImage> filteredImage;
    
    
    private static AppModel instance;
    public static AppModel getInstance()
    {
        if(instance == null)
        {
            instance = new AppModel();
        }

        return instance;
    }
    
    private AppModel()
    {
        originalImage = new SimpleObjectProperty<>();
        filteredImage = new SimpleObjectProperty<>();
    }
    
    public ObjectProperty<BufferedImage> getOriginalImage()
    {
        return originalImage;
    }
    
    public ObjectProperty<BufferedImage> getFilteredImage()
    {
        return filteredImage;
    }
    
    public void clear()
    {
        originalImage.setValue(null);
        filteredImage.setValue(null);
    }
    
    public Boolean hasImage()
    {
        return originalImage.getValue() != null;
    }
    
}
