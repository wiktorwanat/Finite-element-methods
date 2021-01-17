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
    
    private static final double EPSILON = 1e-10;
        
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
        
        public static double findMaxTemp(double[] arr){
            double max=0;
            if(arr!=null){
                max=arr[0];
                for(double m:arr){
                    if(max<m){
                        max=m;
                    }
                }
            }
            return max;
        }
        
        public static double findMinTemp(double[] arr){
            double min=0.0;
            if(arr!=null){
                min=arr[0];
                for(double m:arr){
                    if(min>m){
                        min=m;
                    }
                }
            }
            return min;
        }
        
        public static double[] gaussMethod(double[][] Hz,double[] Pz){
            int n = Pz.length;

            for (int p = 0; p < n; p++) {

                // find pivot row and swap
                int max = p;
                for (int i = p + 1; i < n; i++) {
                    if (Math.abs(Hz[i][p]) > Math.abs(Hz[max][p])) {
                        max = i;
                    }
                }
                double[] temp = Hz[p]; Hz[p] = Hz[max]; Hz[max] = temp;
                double   t    = Pz[p]; Pz[p] = Pz[max]; Pz[max] = t;

                // singular or nearly singular
                if (Math.abs(Hz[p][p]) <= EPSILON) {
                    throw new ArithmeticException("Matrix is singular or nearly singular");
                }

                // pivot within A and b
                for (int i = p + 1; i < n; i++) {
                    double alpha = Hz[i][p] / Hz[p][p];
                    Pz[i] -= alpha * Pz[p];
                    for (int j = p; j < n; j++) {
                        Hz[i][j] -= alpha * Hz[p][j];
                    }
                }
            }

            // back substitution
            double[] x = new double[n];
            for (int i = n - 1; i >= 0; i--) {
                double sum = 0.0;
                for (int j = i + 1; j < n; j++) {
                    sum += Hz[i][j] * x[j];
                }
                x[i] = (Pz[i] - sum) / Hz[i][i];
            }
            return x;
        }
        
}
