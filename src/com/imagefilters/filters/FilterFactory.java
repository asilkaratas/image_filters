/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.filters;

import java.util.ArrayList;

/**
 *
 * @author asilkaratas
 */
public class FilterFactory
{
    private static ArrayList<FilterFactory> factories;
    public static ArrayList<FilterFactory> getFactories()
    {
        if(factories == null)
        {
            factories = new ArrayList<>();
            factories.add(new FunctionFilterFactory());
            //factories.add(new ConvolutionFilterFactory());
            factories.add(new GammaFiltersFactory());
            factories.add(new ThresholdFiltersFactory());
            factories.add(new DitheringFilterFactory());
            factories.add(new ColorQuantizationFilterFactory());
        }
        return factories;
    }
    
    protected String name;
    protected ArrayList<Object> filters;
    
    protected FilterFactory(String name)
    {
        this.name = name;
        filters = new ArrayList<>();
    }
    
    public String getName()
    {
        return name;
    }
    
    public ArrayList<Object> getFilters()
    {
        return filters;
    }
    
    @Override
    public String toString()
    {
        return name;
    }
}
