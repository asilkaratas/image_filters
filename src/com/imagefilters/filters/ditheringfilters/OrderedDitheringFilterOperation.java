/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.filters.ditheringfilters;

import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.FloatProcessor;
import java.awt.image.BufferedImage;

/**
 *
 * @author asilkaratas
 */
public class OrderedDitheringFilterOperation
{
    private OrderedDitheringFilter filter;
    private BufferedImage originalImage;
    
    private int[] matrix;
    private int matrixSize;
    private double divisor;
    
    public OrderedDitheringFilterOperation(OrderedDitheringFilter filter, BufferedImage originalImage)
    {
       this.filter = filter;
       this.originalImage = originalImage;
       
       matrix = filter.getMatrix();
       matrixSize = (int)Math.sqrt(matrix.length);
       divisor = matrix.length + 1;
    }
    
    public BufferedImage apply()
    {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        
        
        ColorProcessor colorProcessor = new ColorProcessor(originalImage);
        ByteProcessor byteProcessor = colorProcessor.convertToByteProcessor();
        
        
        int paletteSize = filter.getColorPaletteSize();
        int[] palette = createPalette(paletteSize);
        
        byte[] originalPixels = (byte[])byteProcessor.getPixels();
        byte[] filteredPixels = new byte[originalWidth * originalHeight];
        
        //double gamma = filter.getGamma();
        int pixelIndex, pixelValue;
        double intensity;
        int red, green, blue;
        int x, y;
        int col;
        double re;
        double limit;
        int matrixIndex;
        
        for(int i = 0; i < originalHeight; ++i)
        {
            for(int j = 0; j < originalWidth; ++j)
            {
                pixelIndex = i * originalWidth + j;
                pixelValue = originalPixels[pixelIndex] & 0xff;
                
                intensity = (double)pixelValue/255.0;
                intensity = (paletteSize - 1) * intensity;
                
                col = (int)Math.floor(intensity);
                re = intensity - col;
                
                x = j % matrixSize;
                y = i % matrixSize;
                
                matrixIndex = y * matrixSize + x;
                limit = matrix[matrixIndex]/divisor;
                
                if(re >= limit)
                {
                    col ++;
                }
                
                
                pixelValue = palette[col];
                
                
                filteredPixels[pixelIndex] = (byte)pixelValue;
            }
        }
        
        ByteProcessor filteredProcessor = new ByteProcessor(originalWidth, originalHeight, filteredPixels);
        BufferedImage filteredImage = filteredProcessor.getBufferedImage();
        
        return filteredImage;
    }
    
    private int findClosest(int x, int y, int c)
    {
        int index = y * matrixSize + x;
        
        int limit = (int)(((matrix[index] + 1.0) / divisor) * 255.0);
        
        
        if(c < limit)
            return 0;
        
        return 255;
    }
    
    private int[] createPalette(int paletteSize)
    {
        int[] palette = new int[paletteSize];
        int threshold = 255/paletteSize;
        for(int i = 1; i < paletteSize-1; ++i)
        {
            palette[i] = threshold * i + threshold/2;
        }
        
        palette[0] = 0;
        palette[paletteSize-1] = 255;
        
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
