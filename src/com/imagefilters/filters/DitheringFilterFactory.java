/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.filters;

import com.imagefilters.filters.ditheringfilters.OrderedDitheringFilter;
import java.util.ArrayList;

/**
 *
 * @author asilkaratas
 */
public class DitheringFilterFactory extends FilterFactory
{
    public DitheringFilterFactory()
    {
        super("dithering filter");
        
        filters = new ArrayList<>();
        filters.add(createOrderedDitheringGroup());
    }
    
    private ImageFilter createOrderedDithering2x2()
    {
        int[] matrix = {
            1, 3,
            4, 2
        };
    
        return new OrderedDitheringFilter("2x2", matrix, 2);
    }
    
    private ImageFilter createOrderedDithering3x3()
    {
        int[] matrix = {
            3, 7, 4,
            6, 1, 9,
            2, 8, 5
        };
    
        return new OrderedDitheringFilter("3x3", matrix, 2);
    }
    
    private ImageFilter createOrderedDithering4x4()
    {
        int[] matrix = {
            1, 9, 3, 11,
            13, 5, 15, 7,
            4, 12, 2, 10,
            16, 8, 14, 6
        };
    
        return new OrderedDitheringFilter("4x4", matrix, 2);
    }
    
    private ImageFilter createOrderedDithering6x6()
    {
        int[] matrix = {
            34, 29, 17, 21, 30, 35,
            28, 14, 9, 16, 20, 31,
            13, 8, 4, 5, 15, 19,
            12, 3, 0, 1, 10, 18,
            27, 7, 2, 6, 23, 24,
            33, 26, 11, 22, 25, 32
        };
    
        return new OrderedDitheringFilter("6x6", matrix, 2);
    }
    
    private ImageFilter createOrderedDithering8x8()
    {
        int[] matrix = {
            1, 49, 13, 61, 4, 52, 16, 64,
            33, 17, 45, 29, 36, 20, 48, 32,
            9, 57, 5, 53, 12, 60, 8, 56,
            41, 25, 37, 21, 44, 28, 40, 24,
            3, 51, 15, 63, 2, 50, 14, 62,
            35, 19, 47, 31, 34, 18, 46, 30,
            11, 59, 7, 55, 10, 58, 6, 54,
            43, 27, 39, 23, 42, 26, 38, 22
        };
    
        return new OrderedDitheringFilter("8x8", matrix, 2);
    }
    
    private ImageFilterGroup createOrderedDitheringGroup()
    {
        ImageFilterGroup group = new ImageFilterGroup("ordered dithering");
        group.add(createOrderedDithering2x2());
        group.add(createOrderedDithering3x3());
        group.add(createOrderedDithering4x4());
        //group.add(createOrderedDithering6x6());
        group.add(createOrderedDithering8x8());
        
        return group;
    }
}
