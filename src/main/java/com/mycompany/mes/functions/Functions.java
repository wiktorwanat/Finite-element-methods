/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mes.functions;

import com.mycompany.mes.dataObjects.Global_data;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wiktor
 */
public class Functions {
        
    public static void printWholeMatrix(double [][] tab)
    {
        for (int i = 0; i < tab.length; i++) {
            System.out.print("[");
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j]+",");
            }
            System.out.println("]");
        }
    }
    
    public static double[][] addMatrix(double[][] A,double[][] B) {
        double[][] out=new double[A.length][A[0].length];
        if (B.length==A.length && B[0].length==A[0].length) {
            for (int i=0;i<B.length;i++) {
                for (int j=0;j<B[i].length;j++) {
                    out[i][j]=A[i][j]+B[i][j];
                }
            }
        } else {
            out=null;
        }
        return out;
    }
    
    public static void printVector(double[] tab) {
        System.out.print("[");
        for (int i = 0; i < tab.length; i++) {
             System.out.print(tab[i]+",");
        }
        System.out.println("]");

    }
    
    public static List detI(List<Double[][]> matrix){
        List listofDetI=new ArrayList();
        if(matrix!=null){
            double detI=0.0;
            for(Double[][] m:matrix){
                detI=m[0][0]*m[1][1]-m[0][1]*m[1][0];
                listofDetI.add(detI);
            }
        }else{
            System.out.println("given matrix is nullable");
        }
        return listofDetI;
    }
    
        public static double[][] N() {
            double[][] out=null;

            if(Global_data.iPc==2){
                out=new double[4][4];
            }else if(Global_data.iPc==3){
                out=new double[9][4];
            }else if(Global_data.iPc==4){
                out=new double[16][4];
            }

            for (int i = 0; i <out.length; i++) {
                    out[i][0] = (1 - Global_data.elem4.ksi[i]) * (1 - Global_data.elem4.eta[i]) / 4;
                    out[i][1] = (1 - Global_data.elem4.ksi[i]) * (1 + Global_data.elem4.eta[i]) / 4;
                    out[i][2] = (1 + Global_data.elem4.ksi[i]) * (1 + Global_data.elem4.eta[i]) / 4;
                    out[i][3] = (1 + Global_data.elem4.ksi[i]) * (1 - Global_data.elem4.eta[i]) / 4;
            }
            return out;
        }
        
        
}
