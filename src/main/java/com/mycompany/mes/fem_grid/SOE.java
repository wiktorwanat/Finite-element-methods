/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mes.fem_grid;

import com.mycompany.mes.dataObjects.Global_data;
import com.mycompany.mes.functions.Functions;

/**
 *
 * @author Wiktor
 */
public class SOE {
    private static double[][] HG;
    private static double[][] CG;
    private static double[][] HLbc;
    private static double[] PG;
    

    
    public SOE(FEM_GRID grid){
        this.HG=new double[16][16];
        this.CG=new double[16][16];
        
        setHG(calculateHG(grid));
        System.out.println("HG");
        System.out.println("----------------------------------");
        //Functions.printWholeMatrix(getHG());
        setCG(calculateCG(grid));
        System.out.println("CG");
        System.out.println("----------------------------------");
        //Functions.printWholeMatrix(getCG());
        setHLbc(calculateHlbc(grid));
        System.out.println("HLbc");
        System.out.println("----------------------------------");
        //Functions.printWholeMatrix(getHLbc());
        setPG(calculatePG(grid));
        System.out.println("----------------------------------");
        //Functions.printVector(getPG());
    }

    public static double[][] getHG() {
        return HG;
    }

    public static void setHG(double[][] HG) {
        SOE.HG = HG;
    }

    public static double[][] getCG() {
        return CG;
    }

    public static void setCG(double[][] CG) {
        SOE.CG = CG;
    }

    public static double[][] getHLbc() {
        return HLbc;
    }

    public static void setHLbc(double[][] HLbc) {
        SOE.HLbc = HLbc;
    }

    public static double[] getPG() {
        return PG;
    }

    public static void setPG(double[] PG) {
        SOE.PG = PG;
    }
    
    
    
    public static double[][] calculateHG(FEM_GRID grid){
       double[][] hg=new double[Global_data.nN][Global_data.nN];
       for(Element e:grid.getElementTab()){
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    hg[e.getNodes().get(i).getId()][e.getNodes().get(j).getId()]+=e.getH()[i][j];
                }
            }
        }
        return hg;
    }
    
    public static double[][] calculateCG(FEM_GRID grid){
       double[][] cg=new double[Global_data.nN][Global_data.nN];
       for(Element e:grid.getElementTab()){
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    cg[e.getNodes().get(i).getId()][e.getNodes().get(j).getId()]+=e.getC()[i][j];
                }
            }
        }
        return cg;
    }
    
        public static double[][] calculateHlbc(FEM_GRID grid){
       double[][] hglbc=new double[Global_data.nN][Global_data.nN];
       for(Element e:grid.getElementTab()){
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    hglbc[e.getNodes().get(i).getId()][e.getNodes().get(j).getId()]+=e.getHlbc()[i][j];
                }
            }
        }
        return hglbc;
    }
        
    public static double[] calculatePG(FEM_GRID grid){
       double[] pg=new double[Global_data.nN];
       for(Element e:grid.getElementTab()){
            for (int i = 0; i < 4; i++) {
                    pg[e.getNodes().get(i).getId()]+=e.getP()[i];
            }
        }
        return pg;
    }
    
    
    public double[] Pz(double iteration,double[] t){
    double []Pz=new double[Global_data.nN];
    double []Ct=new double[Global_data.nN];
     for (int i=0;i<Global_data.nN;i++)
        for (int j=0;j<Global_data.nN;j++)
            Ct[i]+=t[j]*(CG[i][j]/iteration);
        for (int i=0;i<Global_data.nN;i++) {
            Pz[i] = Math.abs(PG[i]) + Ct[i];
        }
    return Pz;
    }
    
    public double[][] Hz(double iteration){
        System.out.println(Global_data.nN);
        double [][] Hz=new double[Global_data.nN][Global_data.nN];
        for (int k=0;k<Global_data.nN;k++){
            for (int l=0;l<Global_data.nN;l++){
                Hz[k][l]+=HLbc[k][l]+(CG[k][l]/iteration);
            }
        }
        return Hz;
        

    }
}
