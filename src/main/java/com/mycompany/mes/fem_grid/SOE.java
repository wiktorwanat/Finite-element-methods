/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mes.fem_grid;

import com.mycompany.mes.dataObjects.Elem4;
import com.mycompany.mes.fem_grid.Element;
import com.mycompany.mes.fem_grid.FEM_GRID;
import com.mycompany.mes.functions.MatrixMethod;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wiktor
 */
public class SOE {
    public static double[][] HG;
    public static double[][] CG;
    public static double[][] HLbc;
    public static double[] PG;

    
    public SOE(FEM_GRID grid){
        this.HG=new double[16][16];
        this.CG=new double[16][16];
        
        this.HG=calculateHG(grid);
        this.CG=calculateCG(grid);
    }
    
    public static double[][] calculateHG(FEM_GRID grid){
       System.out.println("HG");
       double[][] hg=new double[16][16];
       for(Element e:grid.getElementTab()){
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    hg[e.getNodes().get(i).getId()][e.getNodes().get(j).getId()]+=e.getH()[i][j];
                }
            }
        }
        MatrixMethod.printWholeMatrix(hg);
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
        MatrixMethod.printWholeMatrix(cg);
        return cg;
    }
    
}
