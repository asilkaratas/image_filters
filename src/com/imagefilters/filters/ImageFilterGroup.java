/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.filters;

import com.imagefilters.filters.convolutionfilters.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asilkaratas
 */
public class ImageFilterGroup
{
    private ArrayList<ImageFilter> filters;
    private String name;
    
    public ImageFilterGroup(String name)
    {
        this.name = name;
        filters = new ArrayList<>();
    }
    
    public ArrayList<ImageFilter> getFilters()
    {
        return filters;
    }
    
    public void add(ImageFilter imageFilter)
    {
        filters.add(imageFilter);
    }
    
    public ImageFilter get(int index)
    {
        return filters.get(index);
    }
    
    public String getName()
    {
        return name;
    }
    
    @Override
    public String toString()
    {
        return name; 
    }
    
}
