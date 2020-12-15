/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mes.main;

import com.mycompany.mes.fem_grid.Element;
import com.mycompany.mes.fem_grid.FEM_GRID;
import com.mycompany.mes.dataObjects.Elem4;
import com.mycompany.mes.functions.MatrixMethod;
import com.mycompany.mes.fem_grid.SOE;

/**
 *
 * @author Wiktor
 */

public class Main {
    
     public static void main(String[] args){
        FEM_GRID grid=new FEM_GRID();
        System.out.println("----------------------------------");
        SOE soe=new SOE(grid);
                System.out.println("----------------------------------");
        MatrixMethod.printWholeMatrix(MatrixMethod.N());
        
     }
}     

