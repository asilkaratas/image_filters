/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.filters;

import com.imagefilters.filters.thresholdfilters.ThresholdFilter;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author asilkaratas
 */
public class ThresholdFiltersFactory extends FilterFactory
{   
    public ThresholdFiltersFactory()
    {
        super("threshold filter");
        
        filters = new ArrayList<>();
        filters.add(new ThresholdFilter("threshold filter", 128));
    }
    
}
