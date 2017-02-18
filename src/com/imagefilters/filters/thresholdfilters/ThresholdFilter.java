/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.filters.thresholdfilters;

import java.awt.image.BufferedImage;
import com.imagefilters.filters.ImageFilter;

/**
 *
 * @author asilkaratas
 */
public class ThresholdFilter extends ImageFilter
{
    private int threshold;
    
    public ThresholdFilter(String name, int threshold)
    {
        super(name);
        this.threshold = threshold;
    }

    public void setThreshold(int threshold)
    {
        this.threshold = threshold;
    }
    
    public int getThreshold()
    {
        return threshold;
    }
    
    public BufferedImage apply(BufferedImage originalImage)
    {
        ThresholdFilterOperation operation = new ThresholdFilterOperation(originalImage, this);
        return operation.apply();
    }

    @Override
    public ImageFilter clone()
    {
        return new ThresholdFilter(getName(), threshold);
    }
    
}
