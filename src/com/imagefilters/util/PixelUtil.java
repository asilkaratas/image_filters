/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.util;

import java.awt.Rectangle;

/**
 *
 * @author asilkaratas
 */
public class PixelUtil
{
    public static byte[] copyPixelsFromRect(byte[] sourcePixels, int sourceWidth, int sourceHeight, Rectangle rectangle)
    {
        byte[] pixels = new byte[rectangle.width * rectangle.height];
        
        for(int i = 0; i < rectangle.height; ++i)
        {
            for(int j = 0; j < rectangle.width; ++j)
            {
                int sourceIndex = (rectangle.y + i) * sourceWidth + (rectangle.x + j);
                int index = i * rectangle.width + j;
                
                byte pixelValue = sourcePixels[sourceIndex];
                pixels[index] = pixelValue;
            }
        }
        
        return pixels;
    }
    
    public static void pastePixelsToRect(byte[] pixels, int pixelsWidth, int pixelsHeight,  byte[] destinationPixels, int destinationWidth, int destinationHeight, Rectangle destinationRectangle)
    {
        for(int i = 0; i < pixelsHeight; ++i)
        {
            for(int j = 0; j < pixelsWidth; ++j)
            {
                int destinationInex = (destinationRectangle.y + i) * destinationWidth + (destinationRectangle.x + j);
                int index = i * pixelsWidth + j;
                
                byte pixelValue = pixels[index];
                destinationPixels[destinationInex] = pixelValue;
            }
        }
    }
}
