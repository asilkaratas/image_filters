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
public class ColoredOrderedDitheringFilterOperation
{
    private OrderedDitheringFilter filter;
    private BufferedImage originalImage;
    
    private int[] matrix;
    private int matrixSize;
    private double divisor;
    
    public ColoredOrderedDitheringFilterOperation(OrderedDitheringFilter filter, BufferedImage originalImage)
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
        
        ColorProcessor colorProcessor = new ColorProcessor(originalImage);;
        
        if(!filter.getColored())
        {
            ByteProcessor byteProcessor = colorProcessor.convertToByteProcessor();
            colorProcessor= byteProcessor.convertToColorProcessor();
        }
        
        
        int paletteSize = filter.getColorPaletteSize();
        int[] palette = createPalette(paletteSize);
        
        int[] originalPixels = (int[])colorProcessor.getPixels();
        int[] filteredPixels = new int[originalWidth * originalHeight];
        
        int pixelIndex, pixelValue;
        int red, green, blue;
        int x, y;
        double limit;
        int matrixIndex;
        
        for(int i = 0; i < originalHeight; ++i)
        {
            for(int j = 0; j < originalWidth; ++j)
            {
                x = j % matrixSize;
                y = i % matrixSize;
                
                matrixIndex = y * matrixSize + x;
                limit = matrix[matrixIndex]/divisor;
                
                pixelIndex = i * originalWidth + j;
                pixelValue = originalPixels[pixelIndex];
                
                red = (pixelValue&0xff0000)>>16;
		green = (pixelValue&0xff00)>>8;
		blue = pixelValue&0xff;
                
                red = getColor(palette, paletteSize, limit, red);
                green = getColor(palette, paletteSize, limit, green);
                blue = getColor(palette, paletteSize, limit, blue);
                
                pixelValue = (red<<16) + (green<<8) + blue;
                
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
        double threshold = 256.0/(paletteSize - 1);
        System.out.println("threshold:" + threshold);
        for(int i = 0; i < paletteSize; ++i)
        {
            palette[i] = (int)(threshold * i);
            
            System.out.println("palette:" + palette[i] + " i:" + i);
        }
        
        palette[paletteSize-1] = 255;
        
        return palette;
    }
    
    public int getColor(int[] palette, int paletteSize, double limit, int color)
    {
        double intensity = (double)color/255.0;
        intensity = (paletteSize - 1) * intensity;
                
        int col = (int)Math.floor(intensity);
        double re = intensity - col;
                
        if(re >= limit)
        {
             col ++;
        }
            
        return palette[col];
    }
}
