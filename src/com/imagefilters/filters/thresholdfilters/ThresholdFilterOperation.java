/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.filters.thresholdfilters;

import java.awt.image.BufferedImage;

import com.imagefilters.filters.thresholdfilters.ThresholdFilter;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
/**
 *
 * @author asilkaratas
 */
public class ThresholdFilterOperation
{
    private BufferedImage originalImage;
    private ThresholdFilter filter;
    public ThresholdFilterOperation(BufferedImage originalImage, ThresholdFilter filter)
    {
        this.originalImage = originalImage;
        this.filter = filter;
    }
    
    public BufferedImage apply()
    {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        
        
        ColorProcessor colorProcessor = new ColorProcessor(originalImage);
        int[] originalPixels = (int[])colorProcessor.getPixels();
        int[] filteredPixels = new int[originalWidth * originalHeight];
        
        int threshold = filter.getThreshold();
        int pixelIndex, pixelValue;
        int red, green, blue;
        for(int i = 0; i < originalHeight; ++i)
        {
            for(int j = 0; j < originalWidth; ++j)
            {
                pixelIndex = i * originalWidth + j;
                pixelValue = originalPixels[pixelIndex];
                
                red = (pixelValue&0xff0000)>>16;
		green = (pixelValue&0xff00)>>8;
		blue = pixelValue&0xff;
                
                red = red < threshold ? 0 : 255;
                green = green < threshold ? 0 : 255;
                blue = blue < threshold ? 0 : 255;
                
                pixelValue = (red<<16)+(green<<8)+blue;
                filteredPixels[pixelIndex] = pixelValue;
            }
        }
        
        ColorProcessor filteredProcessor = new ColorProcessor(originalWidth, originalHeight, filteredPixels);
        BufferedImage filteredImage = filteredProcessor.getBufferedImage();
        
        
        /*
        ByteProcessor byteProcessor = new ByteProcessor(originalImage);
        byte[] originalPixels = (byte[])byteProcessor.getPixels();
        byte[] filteredPixels = new byte[originalWidth * originalHeight];
        
        for(int i = 0; i < originalHeight; ++i)
        {
            for(int j = 0; j < originalWidth; ++j)
            {
                int pixelIndex = i * originalWidth + j;
                int pixelValue = originalPixels[pixelIndex] & 0xff;
                
                pixelValue = pixelValue < filter.getThreshold() ? 0 : 255;
                
                filteredPixels[pixelIndex] = (byte)pixelValue;
            }
        }
        
        
        ByteProcessor filteredProcessor = new ByteProcessor(originalWidth, originalHeight, filteredPixels);
        BufferedImage filteredImage = filteredProcessor.getBufferedImage();
        */
        
        return filteredImage;
    }
}
