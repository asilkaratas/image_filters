/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.filters.colorquantizationfilter;

import ij.process.ColorProcessor;
import java.awt.image.BufferedImage;

/**
 *
 * @author asilkaratas
 */
public class ColorQuantizationFilterOperation
{
    private ColorQuantizationFilter filter;
    private BufferedImage originalImage;
    
    public ColorQuantizationFilterOperation(ColorQuantizationFilter filter, BufferedImage originalImage)
    {
        this.filter = filter;
        this.originalImage = originalImage;
    }
    
    public BufferedImage apply()
    {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        
        ColorProcessor colorProcessor = new ColorProcessor(originalImage);
        
        int[] originalPixels = (int[])colorProcessor.getPixels();
        int[] filteredPixels = new int[originalWidth * originalHeight];
        
        int redPaletteSize = filter.getRedPaletteSize();
        int greenPaletteSize = filter.getGreenPaletteSize();
        int bluePaletteSize = filter.getBluePaletteSize();
        
        int[] redPalette = createPalette(redPaletteSize);
        int[] greenPalette = createPalette(greenPaletteSize);
        int[] bluePalette = createPalette(bluePaletteSize);
        
        int pixelIndex, pixelValue;
        int red, green, blue;
        
        for(int i = 0; i < originalHeight; ++i)
        {
            for(int j = 0; j < originalWidth; ++j)
            {
                pixelIndex = i * originalWidth + j;
                pixelValue = originalPixels[pixelIndex];
                
                red = (pixelValue & 0xff0000) >> 16;
		green = (pixelValue & 0xff00) >> 8;
		blue = pixelValue & 0xff;
                
                red = getColor(redPalette, redPaletteSize, red);
                green = getColor(greenPalette, greenPaletteSize, green);
                blue = getColor(bluePalette, bluePaletteSize, blue);
                
                pixelValue = (red << 16) + (green << 8) + blue;
                filteredPixels[pixelIndex] = pixelValue;
            }
        }
        
        ColorProcessor filteredProcessor = new ColorProcessor(originalWidth, originalHeight, filteredPixels);
        BufferedImage filteredImage = filteredProcessor.getBufferedImage();
        
        return filteredImage;
    }
    
    private int[] createPalette(int paletteSize)
    {
        int[] palette = new int[paletteSize];
        int threshold = 255/paletteSize;
        for(int i = 0; i < paletteSize; ++i)
        {
            palette[i] = threshold * i + threshold/2;
        }
        
        return palette;
    }
    
    public int getColor(int[] palette, int paletteSize, int color)
    {
        int index = (int)(Math.floor(((double)color/255.0) * paletteSize));
        if(index == paletteSize)
            index --;
        return palette[index];
    }
}
