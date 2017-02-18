/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.filters.colorquantizationfilter;

import com.imagefilters.filters.ImageFilter;
import java.awt.image.BufferedImage;
/**
 *
 * @author asilkaratas
 */
public class ColorQuantizationFilter extends ImageFilter
{
    private int redPaletteSize;
    private int greenPaletteSize;
    private int bluePaletteSize;
    
    public ColorQuantizationFilter(String name, int redPaletteSize, int greenPaletteSize, int bluePaletteSize)
    {
        super(name);
        
        this.redPaletteSize = redPaletteSize;
        this.greenPaletteSize = greenPaletteSize;
        this.bluePaletteSize = bluePaletteSize;
    }
    
    public void setRedPaletteSize(int redPaletteSize)
    {
        redPaletteSize = redPaletteSize < 2 ? 2 : redPaletteSize;
        redPaletteSize = redPaletteSize > 255 ? 255 : redPaletteSize;
        
        this.redPaletteSize = redPaletteSize;
    }
    
    public int getRedPaletteSize()
    {
        return redPaletteSize;
    }
    
    public void setGreenPaletteSize(int greenPaletteSize)
    {
        greenPaletteSize = greenPaletteSize < 2 ? 2 : greenPaletteSize;
        greenPaletteSize = greenPaletteSize > 255 ? 255 : greenPaletteSize;
        
        this.greenPaletteSize = greenPaletteSize;
    }
    
    public int getGreenPaletteSize()
    {
        return greenPaletteSize;
    }
    
    public void setBluePaletteSize(int bluePaletteSize)
    {
        bluePaletteSize = bluePaletteSize < 2 ? 2 : bluePaletteSize;
        bluePaletteSize = bluePaletteSize > 255 ? 255 : bluePaletteSize;
        
        this.bluePaletteSize = bluePaletteSize;
    }
    
    public int getBluePaletteSize()
    {
        return bluePaletteSize;
    }

    @Override
    public BufferedImage apply(BufferedImage originalImage)
    {
        ColorQuantizationFilterOperation operation = new ColorQuantizationFilterOperation(this, originalImage);
        return operation.apply();
    }

    @Override
    public ImageFilter clone()
    {
        return new ColorQuantizationFilter(getName(), getRedPaletteSize(), getGreenPaletteSize(), getBluePaletteSize());
    }
    
}
