/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.filters.gammafilters;

import java.awt.image.BufferedImage;

import com.imagefilters.filters.gammafilters.GammaFilter;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
/**
 *
 * @author asilkaratas
 */
public class GammaFilterOperation
{
    private BufferedImage originalImage;
    private GammaFilter filter;
    public GammaFilterOperation(BufferedImage originalImage, GammaFilter filter)
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
        
        double gamma = filter.getGamma();
        int pixelIndex, pixelValue;
        int red, green, blue;
        double redIntensity, greenIntensity, blueIntensity;
        
        for(int i = 0; i < originalHeight; ++i)
        {
            for(int j = 0; j < originalWidth; ++j)
            {
                pixelIndex = i * originalWidth + j;
                pixelValue = originalPixels[pixelIndex];
                
                red = (pixelValue&0xff0000)>>16;
		green = (pixelValue&0xff00)>>8;
		blue = pixelValue&0xff;
                
                redIntensity = (double)red / 255.0;
                greenIntensity = (double)green / 255.0;
                blueIntensity = (double)blue / 255.0;
                
                redIntensity = Math.pow(redIntensity, gamma);
                red = (int)(redIntensity * 255.0);
                red = red < 0 ? 0 : red;
                red = red > 255 ? 255 : red;
                
                greenIntensity = Math.pow(greenIntensity, gamma);
                green = (int)(greenIntensity * 255.0);
                green = green < 0 ? 0 : green;
                green = green > 255 ? 255 : green;
                
                blueIntensity = Math.pow(blueIntensity, gamma);
                blue = (int)(blueIntensity * 255.0);
                blue = blue < 0 ? 0 : blue;
                blue = blue > 255 ? 255 : blue;
                
                pixelValue = (red<<16)+(green<<8)+blue;
                filteredPixels[pixelIndex] = pixelValue;
            }
        }
        
        ColorProcessor filteredProcessor = new ColorProcessor(originalWidth, originalHeight, filteredPixels);
        BufferedImage filteredImage = filteredProcessor.getBufferedImage();
        
        return filteredImage;
    }
}
