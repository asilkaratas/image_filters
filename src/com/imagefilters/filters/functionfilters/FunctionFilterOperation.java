/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.filters.functionfilters;

import com.imagefilters.filters.functionfilters.FunctionFilter;
import ij.process.ColorProcessor;
import java.awt.image.BufferedImage;

/**
 *
 * @author asilkaratas
 */
public class FunctionFilterOperation
{
    private BufferedImage originalImage;
    private FunctionFilter filter;
    public FunctionFilterOperation(BufferedImage originalImage, FunctionFilter filter)
    {
        this.originalImage = originalImage;
        this.filter = filter;
    }
    
    public BufferedImage apply()
    {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        
        int[] pixelTable = filter.getPixelTable();
        
        ColorProcessor colorProcessor = new ColorProcessor(originalImage);
        int[] originalPixels = (int[])colorProcessor.getPixels();
        int[] filteredPixels = new int[originalWidth * originalHeight];
        
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
                
                red = pixelTable[red];
                green = pixelTable[green];
                blue = pixelTable[blue];
                
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
                
                if(pixelValue < 0 || pixelValue > 255)
                {
                    System.out.println("INVALID PIXEL VALUE");
                }
                
                int filteredPixelValue = pixelTable[pixelValue];
                
                filteredPixels[pixelIndex] = (byte)filteredPixelValue;
            }
        }
        
        
        ByteProcessor filteredProcessor = new ByteProcessor(originalWidth, originalHeight, filteredPixels);
        BufferedImage filteredImage = filteredProcessor.getBufferedImage();
        */
        return filteredImage;
    }
}
