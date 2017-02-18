/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.filters.functionfilters;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author asilkaratas
 */
public class FunctionFilter
{
    private final String name;
    private final ArrayList<FilterPoint> filterPoints;
    
    public FunctionFilter(String name, ArrayList<FilterPoint> filterPoints)
    {
        this.name = name;
        this.filterPoints = filterPoints;
    }
    
    public String getName()
    {
        return name;
    }
    
    public ArrayList<FilterPoint> getFilterPoints()
    {
        return filterPoints;
    }

    @Override
    public String toString()
    {
        return getName();
    }
    
    public int[] getPixelTable()
    {
        int[] pixelTable = new int[256];
        
        for(int i = 0; i < 256; ++i)
        {
            int pixelValue = getPixelValue(i);
            pixelTable[i] = pixelValue;
        }
        
        return pixelTable;
    }
    
    private int getPixelValue(int xPos)
    {
        int pixelValue = 0;
        
        FilterPoint leftPoint = getLeftPoint(xPos);
        FilterPoint rightPoint = getRightPoint(xPos);
        
        if(leftPoint != null && rightPoint != null)
        {
           double m = (rightPoint.getY() - leftPoint.getY()) / (rightPoint.getX() - leftPoint.getX());
        
           int y = (int)(m * (xPos - leftPoint.getX()));
        
           pixelValue = y + (int)leftPoint.getY(); 
        }
        else
        {
            System.out.println("here:" + xPos + " leftPoint:" + leftPoint + " rightPoint:" + rightPoint);
        }
        
       // System.out.println("pixelValue:" + pixelValue + " leftPoint:" + leftPoint);
        
        return pixelValue;
    }
    
    private FilterPoint getLeftPoint(int xPos)
    {
        for(int i = filterPoints.size()-1; i >= 0; --i)
        {
            FilterPoint filterPoint = filterPoints.get(i);
            if(filterPoint.x <= xPos)
            {
                return filterPoint;
            }
        }
        
        return null;
    }
    
    private FilterPoint getRightPoint(int xPos)
    {
        for(int i = 0; i < filterPoints.size(); ++i)
        {
            FilterPoint filterPoint = filterPoints.get(i);
            if(filterPoint.x >= xPos)
            {
                return filterPoint;
            }
        }
        
        return null;
    }
    
    public void addPoint(FilterPoint filterPoint)
    {
        FilterPoint leftPoint = getLeftPoint((int)filterPoint.getX());
        int leftPointIndex = filterPoints.indexOf(leftPoint);
        filterPoints.add(leftPointIndex + 1, filterPoint);
    }
    
    public void removePoint(FilterPoint filterPoint)
    {
        filterPoints.remove(filterPoint);
    }
    
    public FunctionFilter clone()
    {
        ArrayList<FilterPoint> newFilterPoints = new ArrayList<FilterPoint>();
        for(FilterPoint filterPoint : filterPoints)
        {
            FilterPoint newFilterPoint = new FilterPoint(filterPoint.x, filterPoint.y, filterPoint.getDragMode());
            newFilterPoints.add(newFilterPoint);
        }
        
        return new FunctionFilter(name, newFilterPoints);
    }
    
    public BufferedImage apply(BufferedImage originalImage)
    {
        FunctionFilterOperation operation = new FunctionFilterOperation(originalImage, this);
        return operation.apply();
    }
}
