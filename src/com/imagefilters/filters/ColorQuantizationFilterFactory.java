/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.filters;

import com.imagefilters.filters.colorquantizationfilter.ColorQuantizationFilter;
import java.util.ArrayList;

/**
 *
 * @author asilkaratas
 */
public class ColorQuantizationFilterFactory extends FilterFactory
{
    public ColorQuantizationFilterFactory()
    {
        super("color quantization filter");
        
        filters = new ArrayList<>();
        filters.add(new ColorQuantizationFilter("uniform", 5, 4, 3));
    }
    
}
