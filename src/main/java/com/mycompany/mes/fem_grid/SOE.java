/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mes.fem_grid;

import com.mycompany.mes.dataObjects.Elem4;
import com.mycompany.mes.fem_grid.Element;
import com.mycompany.mes.fem_grid.FEM_GRID;
import com.mycompany.mes.functions.Functions;
import java.util.ArrayList;
import java.util.List;

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
        Functions.printWholeMatrix(getHG());
        setCG(calculateCG(grid));
        System.out.println("CG");
        System.out.println("----------------------------------");
        Functions.printWholeMatrix(getCG());
        setHLbc(calculateHlbc(grid));
        System.out.println("HLbc");
        System.out.println("----------------------------------");
        Functions.printWholeMatrix(getHLbc());
        setPG(calculatePG(grid));
        System.out.println("----------------------------------");
        Functions.printVector(getPG());
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
       double[][] hg=new double[16][16];
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
       System.out.println("CG");
       double[][] cg=new double[16][16];
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
       System.out.println("Hlbg");
       double[][] hglbc=new double[16][16];
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
       System.out.println("PG");
       double[] pg=new double[16];
       for(Element e:grid.getElementTab()){
            for (int i = 0; i < 4; i++) {
                System.out.println(e.getP()[i]);
                    pg[e.getNodes().get(i).getId()]+=e.getP()[i];
            }
        }
        return pg;
    }
}
