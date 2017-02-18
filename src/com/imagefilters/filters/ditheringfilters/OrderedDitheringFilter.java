/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.filters.ditheringfilters;

import com.imagefilters.filters.ImageFilter;
import java.awt.image.BufferedImage;

/**
 *
 * @author asilkaratas
 */
public class OrderedDitheringFilter extends ImageFilter
{
    private int[] matrix;
    private int colorPaletteSize;
    
    private boolean colored;
    
    public OrderedDitheringFilter(String name, int[] matrix, int colorPaletteSize)
    {
        super(name);
        this.matrix = matrix;
        this.colorPaletteSize = colorPaletteSize;
    }
    
    public int[] getMatrix()
    {
        return matrix;
    }
    
    public int getColorPaletteSize()
    {
        return colorPaletteSize;
    }
    
    public void setColorPaletteSize(int colorPaletteSize)
    {
        colorPaletteSize = colorPaletteSize < 2 ? 2 : colorPaletteSize;
        colorPaletteSize = colorPaletteSize > 255 ? 255 : colorPaletteSize;
        
        this.colorPaletteSize = colorPaletteSize;
    }
    
    public void setColored(boolean colored)
    {
        this.colored = colored;
    }
    
    public boolean getColored()
    {
        return colored;
    }

    @Override
    public BufferedImage apply(BufferedImage originalImage)
    {
        BufferedImage filteredImage = null;
        
        ColoredOrderedDitheringFilterOperation operation = new ColoredOrderedDitheringFilterOperation(this, originalImage);
        filteredImage = operation.apply(); 
       
        /*
        if(colored)
        {
            ColoredOrderedDitheringFilterOperation operation = new ColoredOrderedDitheringFilterOperation(this, originalImage);
            filteredImage = operation.apply(); 
        }
        else
        {
            OrderedDitheringFilterOperation operation = new OrderedDitheringFilterOperation(this, originalImage);
            filteredImage = operation.apply(); 
        }
         */
        
        return filteredImage;
    }

    @Override
    public ImageFilter clone()
    {
        return new OrderedDitheringFilter(getName(), matrix, colorPaletteSize);
    }
    
}
