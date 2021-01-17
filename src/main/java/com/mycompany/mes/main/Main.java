/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mes.main;

import com.mycompany.mes.fem_grid.FEM_GRID;
import com.mycompany.mes.dataObjects.Global_data;
import com.mycompany.mes.fem_grid.SOE;
import com.mycompany.mes.functions.Functions;
import static java.lang.Thread.sleep;

/**
 *
 * @author Wiktor
 */

public class Main {
    
     public static void main(String[] args){
        //  try{
            FEM_GRID grid=new FEM_GRID();
            System.out.println("----------------------------------");
            SOE soe=new SOE(grid);

            //sleep(5);
            System.out.println("----------------------------------");


            double[] to=new double[Global_data.nN];
            for(int a=0;a<to.length;a++){
                to[a]=Global_data.initialTemperature;
            }

            int iteration=Global_data.simulationTime/Global_data.simulationStepTime;
            double[][] Hz=soe.Hz(Global_data.simulationStepTime);
            System.out.println("----------------------------------");
            Functions.printWholeMatrix(Hz);
            System.out.println("----------------------------------");
            double[] Pz=soe.Pz(Global_data.simulationStepTime, to);
            Functions.printVector(Pz);
            //double[][] Hz=soe.Hz(Global_data.simulationStepTime);
            System.out.println("----------------------------------");
            
            for(int k=0;k<iteration;k++){
                System.out.println("Iteration nr :"+k);
                Pz=soe.Pz(Global_data.simulationStepTime, to);
                
                to=Functions.gaussMethod(soe.Hz(Global_data.simulationStepTime), soe.Pz(Global_data.simulationStepTime, to));
                

                double maxTemp=Functions.findMaxTemp(to);
                double minTemp=Functions.findMinTemp(to);

                System.out.println("Max temp="+maxTemp);
                System.out.println("Min temp="+minTemp);
                System.out.println("--------------------");
            }
            
         //}catch(Exception e){
          //  System.out.println(e.getMessage());
        // }
     }
}     

