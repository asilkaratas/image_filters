/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.filters.convolutionfilters;

import com.imagefilters.controller.edgemode.EdgeFillMode;
import com.imagefilters.controller.edgemode.EdgeFillModeFactory;
import com.imagefilters.filters.convolutionfilters.ConvolutionFilter;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author asilkaratas
 */
public class SobelConvolutionFilterOperation 
{
    private BufferedImage originalImage;
    private ConvolutionFilter filter;
    
    public SobelConvolutionFilterOperation(BufferedImage originalImage, ConvolutionFilter filter)
    {
        this.originalImage = originalImage;
        this.filter = filter;
    }
    
    public BufferedImage apply()
    {
        int d = filter.getD();
        int kernelSize = filter.getSize() - 1;
        int kernelWidth = filter.getKernelWidth();
        int kernelHeight = filter.getKernelHeight();
        
        Point pivot = filter.getPivot();
        
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        
        int enlargedWidth = originalWidth + kernelSize;
        int enlargedHeight = originalHeight + kernelSize;
        
        //ColorProcessor colorProcessor = new ColorProcessor(originalImage);
        ByteProcessor byteProcessor = new ByteProcessor(originalImage);
        //ByteProcessor byteProcessor = colorProcessor.convertToByteProcessor();
        byte[] originalPixels = (byte[])byteProcessor.getPixels();
        
        byte[] enlargedPixels = getEnlargedPixels(originalPixels, originalWidth, originalHeight, enlargedWidth, enlargedHeight, pivot);
        
        EdgeFillMode edgeFillMode = EdgeFillModeFactory.getInstance().getEdgeFillMode(filter.getEdgeMode());
        if(edgeFillMode != null)
        {   
            edgeFillMode.fill(enlargedPixels, originalPixels, originalWidth, originalHeight, enlargedWidth, enlargedHeight, pivot, kernelWidth-1, kernelHeight-1);
        }
        
        
        byte[] filteredPixels = new byte[originalWidth * originalHeight];
        
        int[] kernel = filter.getKernel();
        
        for(int i = 0; i < originalHeight; ++i)
        {
            for(int j = 0; j < originalWidth; ++j)
            {
                byte pixelValue = applyFilter(i, j, kernel, kernelWidth, kernelHeight, enlargedPixels, enlargedWidth, enlargedHeight, pivot, d);
                
                int index = i * originalWidth + j;
                filteredPixels[index] = pixelValue;
            }
        }
        
        ByteProcessor filteredProcessor = new ByteProcessor(originalWidth, originalHeight, filteredPixels);
        BufferedImage filteredImage = filteredProcessor.getBufferedImage();
        
        return filteredImage;
    }
    
    private byte applyFilter(int row, int column, int[] kernel, int kernelWidth, int kernelHeight, byte[] pixels, int pixelWidth, int pixelHeight, Point pivot, int d)
    {
        int total = 0;
        for(int i = 0; i < kernelHeight; ++i)
        {
            for(int j = 0; j < kernelWidth; ++j)
            {
                int kernelIndex = i * kernelWidth + j;
                int kernelValue = kernel[kernelIndex];
                
                int pixelRow = row + i;
                int pixelColumn = column + j;
                int pixelIndex = pixelRow * pixelWidth + pixelColumn;
                int pixelValue = pixels[pixelIndex] & 0xff;
                
                total += (pixelValue * kernelValue);
            }
        }
        
        int value = total/d;
        
        value += filter.getOffset();
        
        value = value < 0 ? 0 : value;
        value = value > 255 ? 255 : value;
        
        byte byteValue = (byte)value;
        
        return byteValue;
    }
    
    private byte[] getEnlargedPixels(byte[] originalPixels, int originalWidth, int originalHeight, int enlargedWidth, int enlargedHeight, Point pivot)
    {
        byte[] enlargedPixels = new byte[enlargedWidth*enlargedHeight];
        
        int pivotX = pivot.x;
        int pivotY = pivot.y;
        
        for(int i = 0; i < originalHeight; ++i)
        {
            for(int j = 0; j < originalWidth; ++j)
            {
                int originalIndex = i * originalWidth + j;
                int enlargedIndex = (i + pivotY) * enlargedWidth + (j + pivotX);
                
                byte pixelValue = originalPixels[originalIndex];
                enlargedPixels[enlargedIndex] = pixelValue;
            }
        }
        
        return enlargedPixels;
    }
    
}
