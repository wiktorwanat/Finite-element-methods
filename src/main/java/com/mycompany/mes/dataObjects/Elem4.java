/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mes.dataObjects;

import com.mycompany.mes.dataObjects.Global_data;
import com.mycompany.mes.fem_grid.Node;
import com.mycompany.mes.functions.Functions;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wiktor
 */


public class Elem4{
    //4 Node element implementation to calculate Jacobi matrix and more
    public double value;
    //ksi eta values
    public double [] ksi;
    public double [] eta;
    //weights values
    public double [] weights;

    public double matrixDN_Deta [][];
        
    public double matrixDN_Dksi [][];
    
    public double [] HbcWeights;
    public double [] HbcKsi;
    public double [] HbcEta;
    public double [][] Nbc;
    public int nShapeFunctions;
    
        
    public Elem4(){
        this.nShapeFunctions=4;
        
        if(Global_data.iPc==2){
            System.out.println("iPc =2 - 2 POINT METHOD");
            this.value= 1.0/Math.sqrt(3);
            this.ksi=new double[]{-value,value,value,-value};

            this.eta=new double[]{-value,-value,value,value};

            this.weights=new double[]{1.0,1.0,1.0,1.0};

            this.matrixDN_Deta=new double[][]{
                {n1Deta(ksi[0]),n2Deta(ksi[0]),n3Deta(ksi[0]),n4Deta(ksi[0])},
                {n1Deta(ksi[1]),n2Deta(ksi[1]),n3Deta(ksi[1]),n4Deta(ksi[1])},
                {n1Deta(ksi[2]),n2Deta(ksi[2]),n3Deta(ksi[2]),n4Deta(ksi[2])},
                {n1Deta(ksi[3]),n2Deta(ksi[3]),n3Deta(ksi[3]),n4Deta(ksi[3])},
            };

            this.matrixDN_Dksi=new double[][]{
            {n1Dksi(eta[0]),n2Dksi(eta[0]),n3Dksi(eta[0]),n4Dksi(eta[0])},
            {n1Dksi(eta[1]),n2Dksi(eta[1]),n3Dksi(eta[1]),n4Dksi(eta[1])},
            {n1Dksi(eta[2]),n2Dksi(eta[2]),n3Dksi(eta[2]),n4Dksi(eta[2])},
            {n1Dksi(eta[3]),n2Dksi(eta[3]),n3Dksi(eta[3]),n4Dksi(eta[3])},
            };
            
            //calucalting Hbc variables
            
            this.HbcWeights=new double[]{1,1};
            this.HbcKsi=new double[]{-value,value,1.0,1.0,value,-value,-1.0,-1.0};        
            this.HbcEta=new double[]{-1.0,-1.0,-value,value,1.0,1.0,value,-value};
            this.Nbc=new double[4][Global_data.iPc*nShapeFunctions];
            
        }else if(Global_data.iPc==3){
            
            //System.out.println("iPc =3 - 3 POINT METHOD");
            this.value=Math.sqrt(3.0/5.0);
            //this.value=0.7746;
            this.ksi=new double[]{-value,0,value,-value,0,value,-value,0,value};

            this.eta=new double[]{-value,-value,-value,0,0,0,value,value,value};
                
            this.weights=new double[]{5.0/9.0,8.0/9.0,5.0/9.0};

            this.matrixDN_Deta=new double[][]{
                {n1Deta(ksi[0]),n2Deta(ksi[0]),n3Deta(ksi[0]),n4Deta(ksi[0])},
                {n1Deta(ksi[1]),n2Deta(ksi[1]),n3Deta(ksi[1]),n4Deta(ksi[1])},
                {n1Deta(ksi[2]),n2Deta(ksi[2]),n3Deta(ksi[2]),n4Deta(ksi[2])},
                {n1Deta(ksi[3]),n2Deta(ksi[3]),n3Deta(ksi[3]),n4Deta(ksi[3])},
                {n1Deta(ksi[4]),n2Deta(ksi[4]),n3Deta(ksi[4]),n4Deta(ksi[4])},
                {n1Deta(ksi[5]),n2Deta(ksi[5]),n3Deta(ksi[5]),n4Deta(ksi[5])},
                {n1Deta(ksi[6]),n2Deta(ksi[6]),n3Deta(ksi[6]),n4Deta(ksi[6])},
                {n1Deta(ksi[7]),n2Deta(ksi[7]),n3Deta(ksi[7]),n4Deta(ksi[7])},
                {n1Deta(ksi[8]),n2Deta(ksi[8]),n3Deta(ksi[8]),n4Deta(ksi[8])},
            };

            this.matrixDN_Dksi=new double[][]{
                {n1Dksi(eta[0]),n2Dksi(eta[0]),n3Dksi(eta[0]),n4Dksi(eta[0])},
                {n1Dksi(eta[1]),n2Dksi(eta[1]),n3Dksi(eta[1]),n4Dksi(eta[1])},
                {n1Dksi(eta[2]),n2Dksi(eta[2]),n3Dksi(eta[2]),n4Dksi(eta[2])},
                {n1Dksi(eta[3]),n2Dksi(eta[3]),n3Dksi(eta[3]),n4Dksi(eta[3])},
                {n1Dksi(eta[4]),n2Dksi(eta[4]),n3Dksi(eta[4]),n4Dksi(eta[4])},
                {n1Dksi(eta[5]),n2Dksi(eta[5]),n3Dksi(eta[5]),n4Dksi(eta[5])},
                {n1Dksi(eta[6]),n2Dksi(eta[6]),n3Dksi(eta[6]),n4Dksi(eta[6])},
                {n1Dksi(eta[7]),n2Dksi(eta[7]),n3Dksi(eta[7]),n4Dksi(eta[7])},
                {n1Dksi(eta[8]),n2Dksi(eta[8]),n3Dksi(eta[8]),n4Dksi(eta[8])},
            };
            
            this.HbcWeights=new double[]{5.0/9.0, 8.0 / 9.0, 5.0 / 9.0};
            this.HbcKsi=new double[]{-value, 0, value, 1, 1, 1, value, 0, -value, -1, -1, -1 };        
            this.HbcEta=new double[]{-1.0,-1.0,-1.0, -value, 0, value, 1, 1, 1, value, 0, -value};
            this.Nbc=new double[4][Global_data.iPc*nShapeFunctions];
        }else if(Global_data.iPc==4){
            System.out.println("iPc =4 - 4 POINT METHOD");
            //this.value=Math.sqrt(3/7-(2*Math.sqrt(6.0/5.0)/7));
            this.value=0.33981;
            //double value2=Math.sqrt(3/7+(2*Math.sqrt(6.0/5.0)/7));
            double value2=0.86114;
            
            this.ksi=new double[]{-value2,-value,value,value2,-value2,-value,value,value2,-value2,-value,value,value2,-value2,-value,value,value2};
            this.eta=new double[]{-value2,-value2,-value2,-value2,-value,-value,-value,-value,value,value,value,value,value2,value2,value2,value2};
            
            double v1=(18.0-Math.sqrt(30))/36.0;
            double v2=(18.0+Math.sqrt(30))/36.0;
            this.weights=new double[]{v1,v2,v2,v1};

            this.matrixDN_Deta=new double[][]{
                {n1Deta(ksi[0]),n2Deta(ksi[0]),n3Deta(ksi[0]),n4Deta(ksi[0])},
                {n1Deta(ksi[1]),n2Deta(ksi[1]),n3Deta(ksi[1]),n4Deta(ksi[1])},
                {n1Deta(ksi[2]),n2Deta(ksi[2]),n3Deta(ksi[2]),n4Deta(ksi[2])},
                {n1Deta(ksi[3]),n2Deta(ksi[3]),n3Deta(ksi[3]),n4Deta(ksi[3])},
                {n1Deta(ksi[4]),n2Deta(ksi[4]),n3Deta(ksi[4]),n4Deta(ksi[4])},
                {n1Deta(ksi[5]),n2Deta(ksi[5]),n3Deta(ksi[5]),n4Deta(ksi[5])},
                {n1Deta(ksi[6]),n2Deta(ksi[6]),n3Deta(ksi[6]),n4Deta(ksi[6])},
                {n1Deta(ksi[7]),n2Deta(ksi[7]),n3Deta(ksi[7]),n4Deta(ksi[7])},
                {n1Deta(ksi[8]),n2Deta(ksi[8]),n3Deta(ksi[8]),n4Deta(ksi[8])},
                {n1Deta(ksi[9]),n2Deta(ksi[9]),n3Deta(ksi[9]),n4Deta(ksi[9])},
                {n1Deta(ksi[10]),n2Deta(ksi[10]),n3Deta(ksi[10]),n4Deta(ksi[10])},
                {n1Deta(ksi[11]),n2Deta(ksi[11]),n3Deta(ksi[11]),n4Deta(ksi[11])},
                {n1Deta(ksi[12]),n2Deta(ksi[12]),n3Deta(ksi[12]),n4Deta(ksi[12])},
                {n1Deta(ksi[13]),n2Deta(ksi[13]),n3Deta(ksi[13]),n4Deta(ksi[13])},
                {n1Deta(ksi[14]),n2Deta(ksi[14]),n3Deta(ksi[14]),n4Deta(ksi[14])},
                {n1Deta(ksi[15]),n2Deta(ksi[15]),n3Deta(ksi[15]),n4Deta(ksi[15])},
            };

            this.matrixDN_Dksi=new double[][]{
                {n1Dksi(ksi[0]),n2Dksi(ksi[0]),n3Dksi(ksi[0]),n4Dksi(ksi[0])},
                {n1Dksi(ksi[1]),n2Dksi(ksi[1]),n3Dksi(ksi[1]),n4Dksi(ksi[1])},
                {n1Dksi(ksi[2]),n2Dksi(ksi[2]),n3Dksi(ksi[2]),n4Dksi(ksi[2])},
                {n1Dksi(ksi[3]),n2Dksi(ksi[3]),n3Dksi(ksi[3]),n4Dksi(ksi[3])},
                {n1Dksi(ksi[4]),n2Dksi(ksi[4]),n3Dksi(ksi[4]),n4Dksi(ksi[4])},
                {n1Dksi(ksi[5]),n2Dksi(ksi[5]),n3Dksi(ksi[5]),n4Dksi(ksi[5])},
                {n1Dksi(ksi[6]),n2Dksi(ksi[6]),n3Dksi(ksi[6]),n4Dksi(ksi[6])},
                {n1Dksi(ksi[7]),n2Dksi(ksi[7]),n3Dksi(ksi[7]),n4Dksi(ksi[7])},
                {n1Dksi(ksi[8]),n2Dksi(ksi[8]),n3Dksi(ksi[8]),n4Dksi(ksi[8])},
                {n1Dksi(ksi[9]),n2Dksi(ksi[9]),n3Dksi(ksi[9]),n4Dksi(ksi[9])},
                {n1Dksi(ksi[10]),n2Dksi(ksi[10]),n3Dksi(ksi[10]),n4Dksi(ksi[10])},
                {n1Dksi(ksi[11]),n2Dksi(ksi[11]),n3Dksi(ksi[11]),n4Dksi(ksi[11])},
                {n1Dksi(ksi[12]),n2Dksi(ksi[12]),n3Dksi(ksi[12]),n4Dksi(ksi[12])},
                {n1Dksi(ksi[13]),n2Dksi(ksi[13]),n3Dksi(ksi[13]),n4Dksi(ksi[13])},
                {n1Dksi(ksi[14]),n2Dksi(ksi[14]),n3Dksi(ksi[14]),n4Dksi(ksi[14])},
                {n1Dksi(ksi[15]),n2Dksi(ksi[15]),n3Dksi(ksi[15]),n4Dksi(ksi[15])},
            };
            
            double valF = Math.sqrt(3.0 / 7.0 + 2.0 / 7.0 * Math.sqrt(6.0 / 5.0));
            double valS = Math.sqrt(3.0 / 7.0 - 2.0 / 7.0 * Math.sqrt(6.0 / 5.0));
            this.HbcWeights=new double[]{v1,v2,v2,v1};
            this.HbcKsi=new double[]{ -valF, -valS, valS, valF, 1, 1, 1, 1, valF, valS, -valS, -valF, -1, -1, -1, -1 };        
            this.HbcEta=new double[]{ -1, -1, -1, -1, -valF, -valS, valS, valF, 1, 1, 1, 1, valF, valS, -valS, -valF };
            this.Nbc=new double[4][Global_data.iPc*nShapeFunctions];
        }
        /*
        System.out.println("Creating 4 node element");
        System.out.println("Weights: ");
        for(double a:this.weights){
            System.out.print(a+",");
        }
        System.out.println();
        System.out.println("ksi values:");
        for(double a:this.ksi){
            System.out.print(a+",");
        }
        System.out.println();
        System.out.println("eta values:");
        for(double a:this.eta){
            System.out.print(a+",");
        }
        System.out.println();
        System.out.println("With calculated matrixDe");
        MatrixMethod.printWholeMatrix(this.matrixDe);
        System.out.println();
        System.out.println("With calculated matrixDn");
        MatrixMethod.printWholeMatrix(this.matrixDn);
        System.out.println();
        */
        
        calcualteNbc();
    }
    
    public static double n1Dksi(double eta){
        return -0.25*(1-eta);
    }
    public static double n2Dksi(double eta){
        return 0.25*(1-eta);
    }
    public static double n3Dksi(double eta){
        return 0.25*(1+eta);
    }
    public static double n4Dksi(double eta){
        return -0.25*(1+eta);
    }
    
    public static double n1Deta(double ksi){
        return -0.25*(1-ksi);
    }
    public static double n2Deta(double ksi){
        return -0.25*(1+ksi);
    }
    public static double n3Deta(double ksi){
        return 0.25*(1+ksi);
    }
    public static double n4Deta(double ksi){
        return 0.25*(1-ksi);
    }
    
    private void calcualteNbc(){
        for (int k = 0; k < this.nShapeFunctions*Global_data.iPc; k++){
            for (int l = 0; l <4; l++){
                if (l == 0){
                   this.Nbc[l][k] = (0.25) * ((1.0-this.HbcKsi[k])*(1.0-this.HbcEta[k]));
                }
                else if (l == 1){
                    this.Nbc[l][k] = (0.25) * ((1.0 + this.HbcKsi[k]) * (1.0 - this.HbcEta[k]));
                }
                else if (l == 2){
                    this.Nbc[l][k] = (0.25) * ((1.0 + this.HbcKsi[k]) * (1.0 + this.HbcEta[k]));
                }
                else if (l == 3){
                    this.Nbc[l][k] = (0.25) * ((1.0 - this.HbcKsi[k]) * (1.0 + this.HbcEta[k]));
                }
            }
        } 
    }
}
