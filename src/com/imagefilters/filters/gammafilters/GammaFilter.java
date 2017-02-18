/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.filters.gammafilters;

import java.awt.image.BufferedImage;
import com.imagefilters.filters.ImageFilter;

/**
 *
 * @author asilkaratas
 */
public class GammaFilter extends ImageFilter
{
    private double gamma;
    
    public GammaFilter(String name, double gamma)
    {
        super(name);
        this.gamma = gamma;
    }
    
    public double getGamma()
    {
        return gamma;
    }
    
    public void setGamma(double gamma)
    {
        this.gamma = gamma;
    }
    
    public BufferedImage apply(BufferedImage originalImage)
    {
        GammaFilterOperation operation = new GammaFilterOperation(originalImage, this);
        return operation.apply();
    }

    @Override
    public ImageFilter clone()
    {
        return new GammaFilter(getName(), gamma);
    }
}
