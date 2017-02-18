/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.controller.edgemode;

import com.imagefilters.model.AppModel;
import com.imagefilters.model.enums.EdgeMode;

/**
 *
 * @author asilkaratas
 */
public class EdgeFillModeFactory
{
    
    private static EdgeFillModeFactory instance;
    public static EdgeFillModeFactory getInstance()
    {
        if(instance == null)
        {
            instance = new EdgeFillModeFactory();
        }

        return instance;
    }
    
    public EdgeFillMode getEdgeFillMode(EdgeMode edgeMode)
    {
        if(edgeMode == EdgeMode.REPEAT)
        {
            return new RepeatEdgeFillMode();
        }
        else if(edgeMode == EdgeMode.WRAP)
        {
            return new WrapEdgeFillMode();
        }
        
        return null;
    }
}
