/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mes.functions;

/**
 *
 * @author Wiktor
 */
public class IntegralFunctions {
    

    static double threeNodeIntegralMethod()
    {        
        double out=0.0;     
        double value=0.77;
        double w1=5.0/9.0;
        double w2=8.0/9.0;
        
        out=(inputFunction(-value,-value)+inputFunction(value,-value)+inputFunction(-value,value)+inputFunction(value,value))*w1*w1+(inputFunction(0,-value)+inputFunction(-value,0)+inputFunction(0,value)+inputFunction(value,0))*w1*w2+inputFunction(0,0)*w2*w2;
        return out;
    }
    
     static double twoNodeIntegralMethod()
    {   
        double out=0.0;
        double value=0.57;
        
        out=inputFunction(-value,-value)+inputFunction(value,-value)+inputFunction(value,value)+inputFunction(value,value);
        return out;
    }
    
    private static double inputFunction(double x, double y)
    {
         return -5*Math.pow(x,2)*y+2*x*Math.pow(y,2)+10;
    }
    
}
