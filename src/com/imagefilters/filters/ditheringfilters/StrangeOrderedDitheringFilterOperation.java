/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.filters.ditheringfilters;

import ij.process.ColorProcessor;
import ij.process.FloatProcessor;
import java.awt.image.BufferedImage;

/**
 *
 * @author asilkaratas
 */
public class StrangeOrderedDitheringFilterOperation
{
    private OrderedDitheringFilter filter;
    private BufferedImage originalImage;
    
    private int[] matrix;
    private int size;
    private double divisor;
    
    public StrangeOrderedDitheringFilterOperation(OrderedDitheringFilter filter, BufferedImage originalImage)
    {
       this.filter = filter;
       this.originalImage = originalImage;
       
       matrix = filter.getMatrix();
       size = (int)Math.sqrt(matrix.length);
       divisor = matrix.length + 1;
    }
    
    public BufferedImage apply()
    {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        
        
        ColorProcessor colorProcessor = new ColorProcessor(originalImage);
        
        
        int[] originalPixels = (int[])colorProcessor.getPixels();
        int[] filteredPixels = new int[originalWidth * originalHeight];
        
        //double gamma = filter.getGamma();
        int pixelIndex, pixelValue;
        int red, green, blue;
        int x, y;
        
        for(int i = 0; i < originalHeight; ++i)
        {
            for(int j = 0; j < originalWidth; ++j)
            {
                pixelIndex = i * originalWidth + j;
                pixelValue = originalPixels[pixelIndex];
                
                red = (pixelValue&0xff0000)>>16;
		green = (pixelValue&0xff00)>>8;
		blue = pixelValue&0xff;
                
                x = j%size;
                y = i%size;
                
                red = findClosest(x, y, red);
                green = findClosest(x, y, green);
                blue = findClosest(x, y, blue);
                
                
                pixelValue = (red<<16)+(green<<8)+blue;
                filteredPixels[pixelIndex] = pixelValue;
            }
        }
        
        ColorProcessor filteredProcessor = new ColorProcessor(originalWidth, originalHeight, filteredPixels);
        BufferedImage filteredImage = filteredProcessor.getBufferedImage();
        
        return filteredImage;
    }
    
    private int findClosest(int x, int y, int c)
    {
        int index = y * size + x;
        int limit = (int)(((matrix[index] + 1.0) / divisor) * 255.0);
        
        
        if(c < limit)
            return 0;
        
        return 255;
    }
}
