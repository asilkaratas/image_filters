/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.filters.functionfilters;

import com.imagefilters.model.enums.DragMode;
import java.awt.Point;


/**
 *
 * @author asilkaratas
 */
public class FilterPoint extends Point
{
    private DragMode dragMode;
    public FilterPoint(int x, int y, DragMode dragMode)
    {
        super(x, y);
        this.dragMode = dragMode;
    }
    

    public void move(double newCenterX, double newCenterY)
    {
        super.move((int)newCenterX, (int)newCenterY);
    }

    @Override
    public String toString()
    {
        return "[FilterPoint x:" + x + " y:"+ y + " dragMode:" + dragMode + "]";
    }

    public DragMode getDragMode()
    {
        return dragMode;
    }
    
    public Boolean canMoveOnX()
    {
        return dragMode == DragMode.X || dragMode == DragMode.X_Y;
    }
    
    public Boolean canMoveOnY()
    {
        return dragMode == DragMode.Y || dragMode == DragMode.X_Y;
    }
    
    
}
