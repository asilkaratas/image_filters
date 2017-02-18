/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.filters;

import java.util.ArrayList;

import com.imagefilters.filters.gammafilters.GammaFilter;

/**
 *
 * @author asilkaratas
 */
public class GammaFiltersFactory extends FilterFactory
{
    public GammaFiltersFactory()
    {
        super("gamma filter");
        
        filters = new ArrayList<>();
        filters.add(new GammaFilter("gamma filter", 1.5));
    }
}
