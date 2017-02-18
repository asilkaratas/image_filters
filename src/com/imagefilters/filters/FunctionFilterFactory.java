/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.filters;

import com.imagefilters.filters.functionfilters.FilterPoint;
import com.imagefilters.filters.functionfilters.FunctionFilter;
import com.imagefilters.model.enums.DragMode;
import java.util.ArrayList;

/**
 *
 * @author asilkaratas
 */
public class FunctionFilterFactory extends FilterFactory
{
    public FunctionFilterFactory()
    {
        super("function filter");
        
        filters = new ArrayList<>();
        filters.add(createNullTransformFilter());
        filters.add(createInversionFilter());
        filters.add(createBrightnessFilter1());
        filters.add(createBrightnessFilter2());
        filters.add(createGammaFilter1());
        filters.add(createGammaFilter2());
        filters.add(createContrastFilter());
    }
    
    private FunctionFilter createNullTransformFilter()
    {
        ArrayList<FilterPoint> filterPoints = new ArrayList<>();
        filterPoints.add(new FilterPoint(0, 0, DragMode.Y));
        filterPoints.add(new FilterPoint(255, 255, DragMode.Y));
        
        FunctionFilter filter = new FunctionFilter("null transform filter", filterPoints);
        return filter;
    }
    
    private FunctionFilter createInversionFilter()
    {
        ArrayList<FilterPoint> filterPoints = new ArrayList<>();
        filterPoints.add(new FilterPoint(0, 255, DragMode.Y));
        filterPoints.add(new FilterPoint(255, 0, DragMode.Y));
        
        FunctionFilter filter = new FunctionFilter("inversion filter", filterPoints);
        return filter;
    }
    
    private FunctionFilter createBrightnessFilter1()
    {
        ArrayList<FilterPoint> filterPoints = new ArrayList<>();
        filterPoints.add(new FilterPoint(0, 50, DragMode.Y));
        filterPoints.add(new FilterPoint(200, 255, DragMode.X_Y));
        filterPoints.add(new FilterPoint(255, 255, DragMode.Y));
        
        FunctionFilter filter = new FunctionFilter("brightness filter 1", filterPoints);
        return filter;
    }
    
    private FunctionFilter createBrightnessFilter2()
    {
        ArrayList<FilterPoint> filterPoints = new ArrayList<>();
        filterPoints.add(new FilterPoint(0, 0, DragMode.Y));
        filterPoints.add(new FilterPoint(50, 0, DragMode.X_Y));
        filterPoints.add(new FilterPoint(255, 200, DragMode.Y));
        
        FunctionFilter filter = new FunctionFilter("brightness filter 2", filterPoints);
        return filter;
    }
    
    private FunctionFilter createGammaFilter1()
    {
        ArrayList<FilterPoint> filterPoints = new ArrayList<>();
        filterPoints.add(new FilterPoint(0, 0, DragMode.Y));
        filterPoints.add(new FilterPoint(150, 75, DragMode.X_Y));
        filterPoints.add(new FilterPoint(255, 255, DragMode.Y));
        
        FunctionFilter filter = new FunctionFilter("gamma filter 1", filterPoints);
        return filter;
    }
    
    private FunctionFilter createGammaFilter2()
    {
        ArrayList<FilterPoint> filterPoints = new ArrayList<>();
        filterPoints.add(new FilterPoint(0, 0, DragMode.Y));
        filterPoints.add(new FilterPoint(75, 150, DragMode.X_Y));
        filterPoints.add(new FilterPoint(255, 255, DragMode.Y));
        
        FunctionFilter filter = new FunctionFilter("gamma filter 2", filterPoints);
        return filter;
    }
    
    private FunctionFilter createContrastFilter()
    {
        ArrayList<FilterPoint> filterPoints = new ArrayList<>();
        filterPoints.add(new FilterPoint(0, 0, DragMode.Y));
        filterPoints.add(new FilterPoint(50, 0, DragMode.X_Y));
        filterPoints.add(new FilterPoint(200, 255, DragMode.X_Y));
        filterPoints.add(new FilterPoint(255, 255, DragMode.Y));
        
        FunctionFilter filter = new FunctionFilter("contrast filter", filterPoints);
        return filter;
    }
}
