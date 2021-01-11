/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mes.fem_grid;

import com.mycompany.mes.dataObjects.Global_data;
import com.mycompany.mes.dataObjects.Elem4;
import com.mycompany.mes.functions.Functions;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wiktor
 */
public class Element {
    private List<Node> nodes=new ArrayList();
    
    private double[][] H;
    
    private double[][] C;
    
    private double[][] Hbc;
    
    //vector
    private double[] P;
    
    private double[][] Hlbc;

    public double[][] getHlbc() {
        return Hlbc;
    }
        
    public double[][] getHbc() {
        return Hbc;
    }
    
    public List<Node> getNodes() {
        return nodes;
    }
    
    public double[][] getH() {
        return H;
    }

    public double[][] getC() {
        return C;
    }

    public double[] getP() {
        return P;
    }

    public void setId(List<Node> nodes) {
        this.nodes = nodes;
    }
    
    public void setHbc(double[][] Hbc) {
        this.Hbc = Hbc;
    }
    
    public void setHlbc(double[][] Hlbc) {
        this.Hlbc = Hlbc;
    }
    
    public void setC(double[][] C) {
        this.C = C;
    }
    
    public void setP(double[] P) {
        this.P = P;
    }
    
    public void setP() {
        //
    }
    
    public void setH() {
        List<Double[][]> elementJacobian=calculateJakobian();
        List<Double> jacobianDet=Functions.detI(elementJacobian);
        double[][] DN_Dx=DN_Dx(Global_data.elem4.matrixDN_Deta, elementJacobian,Global_data.elem4.matrixDN_Dksi,jacobianDet);
        double[][] DN_Dy=DN_Dy(Global_data.elem4.matrixDN_Deta, elementJacobian,Global_data.elem4.matrixDN_Dksi,jacobianDet);
        
        double[][] h=calculateHForElement(DN_Dy,DN_Dx,jacobianDet);
        
        this.H=h;
    }
    
    public void setC(){     
        double[][] N=Functions.N();
        List<Double[][]> elementJacobian=calculateJakobian();
        List<Double> jacobianDet=Functions.detI(elementJacobian);
        double[][] localC=calculateCForElement(N,jacobianDet);
        this.C=localC;
    }
    
    
    
    public Element(Node a,Node b,Node c, Node d){
        super();
        Elem4 elem4=new Elem4();
        this.nodes.add(a);  
        this.nodes.add(b);  
        this.nodes.add(c);  
        this.nodes.add(d);
        setH();
        setC();
        double[][] Hbc=calculateHbc(elem4);
        Functions.printWholeMatrix(Hbc);
        
        double[][] Hlbc=Functions.addMatrix(getH(),Hbc);
        setHlbc(Hlbc);
        double[] pl=calculateP(elem4);
        //Functions.printVector(pl);
        setP(pl);
    }

    
    @Override
    public String toString(){
        String s="Element with nodes:";
        for(Node a:this.nodes){
            s+=a.toString()+" ";
        }
        return s;
    }

    private double[][] calculateHForElement(double[][] matrixDy,double[][] matrixDx,List<Double> detI){
        Elem4 elem4=new Elem4();
        List<double[][]> partialH=new ArrayList();
        double [][] out=new double[4][4];

        if(matrixDy!=null && matrixDy!=null){
                for(int i=0;i<4;i++){
                    for(int j=0;j<4;j++){
                        int aWeight=0;
                        int bWeight=0;
                        for(int row=0;row<(Global_data.iPc*Global_data.iPc);row++){
                                    if(Global_data.iPc==2){
                                        out[i][j]+=Global_data.k*detI.get(i)*(matrixDx[row][i]*matrixDx[row][j]+matrixDy[row][i]*matrixDy[row][j]);
                                    }else if(Global_data.iPc==3){
                                        out[i][j]+=Global_data.k*detI.get(i)*(matrixDx[row][i]*matrixDx[row][j]+matrixDy[row][i]*matrixDy[row][j])*elem4.weights[aWeight]*elem4.weights[bWeight];
                                        aWeight++;
                                        if(aWeight==3){
                                            aWeight=0;
                                            bWeight++;
                                        }
                                    }else if(Global_data.iPc==4){
                                        out[i][j]+=Global_data.k*detI.get(i)*(matrixDx[row][i]*matrixDx[row][j]+matrixDy[row][i]*matrixDy[row][j])*elem4.weights[aWeight]*elem4.weights[bWeight];
                                        aWeight++;
                                        if(aWeight==4){
                                            aWeight=0;
                                            bWeight++;
                                        }
                                    }
                            partialH.add(out);
                        }
                    }
                }
            if(Global_data.iPc==2){
                if(partialH.size()==4){
                     out=Functions.addMatrix(Functions.addMatrix(partialH.get(0), partialH.get(1)), Functions.addMatrix(partialH.get(2), partialH.get(3)));
                }
            }else if(Global_data.iPc==3){
                if(partialH.size()==9){
                    double[][] h_1=Functions.addMatrix(Functions.addMatrix(partialH.get(0), partialH.get(1)), Functions.addMatrix(partialH.get(2), partialH.get(3)));
                    double[][] h_2=Functions.addMatrix(Functions.addMatrix(partialH.get(4), partialH.get(5)), Functions.addMatrix(partialH.get(6), partialH.get(7)));
                    
                    double[][] h_11=Functions.addMatrix(partialH.get(8),h_1);
                    
                     out=Functions.addMatrix(Functions.addMatrix(partialH.get(2), partialH.get(3)), h_11);
                }
            }else if(Global_data.iPc==4){
                if(partialH.size()==16){
                double[][] H12 = Functions.addMatrix(Functions.addMatrix(partialH.get(0), partialH.get(1)), Functions.addMatrix(partialH.get(2), partialH.get(3)));
                double[][] H34 = Functions.addMatrix(Functions.addMatrix(partialH.get(4), partialH.get(5)), Functions.addMatrix(partialH.get(6), partialH.get(7)));
                double[][] H56 = Functions.addMatrix(Functions.addMatrix(partialH.get(8), partialH.get(9)), Functions.addMatrix(partialH.get(10), partialH.get(11)));
                double[][] H78 = Functions.addMatrix(Functions.addMatrix(partialH.get(12), partialH.get(13)), Functions.addMatrix(partialH.get(14), partialH.get(15)));
                

                out=Functions.addMatrix(Functions.addMatrix(H12,H34),Functions.addMatrix(H56,H78));
                }
            }
         }else{
             System.out.println("one of given matrix is nullable");
         }
        return out;
    }
    
    public List<Double[][]> calculateJakobian(){
         List<Double[][]> listOfJacobians=new ArrayList();
         int iterate=0;
         
         for(int v=0;v<(Global_data.iPc*Global_data.iPc);v++){
            if(Global_data.elem4!=null){
               Double[][] out = {{0.0,0.0},{0.0,0.0}};
               for(Node n:this.getNodes()){
                   out[0][0]+=n.getX()*Global_data.elem4.matrixDN_Dksi[v][iterate];
                   iterate++;
               }
               iterate=0;
               for(Node n:this.getNodes()){
                   out[0][1]+=n.getY()*Global_data.elem4.matrixDN_Dksi[v][iterate];
                   iterate++;
               }
               iterate=0;
               for(Node n:this.getNodes()){
                   out[1][0]+=n.getX()*Global_data.elem4.matrixDN_Deta[v][iterate];
                   iterate++;
               }
               iterate=0;
               for(Node n:this.getNodes()){
                   out[1][1]+=n.getY()*Global_data.elem4.matrixDN_Deta[v][iterate];
                   iterate++;
               }
               iterate=0;
               listOfJacobians.add(out);
               out=new Double[][]{{0.0,0.0},{0.0,0.0}};
            }else{
                     System.out.println("one of given matrix is nullable");
            }
        }
        return listOfJacobians;
    }
    
    public static double[][] DN_Dx(double matrixDN_Dksi[][],List<Double[][]> jacobian,double matrixDN_Deta[][],List<Double> detI){
        double [][] out=null;
        
        if(Global_data.iPc==2){
            out=new double[4][4];
        }else if(Global_data.iPc==3){
            out=new double[9][4];
        }else if(Global_data.iPc==4){
            out=new double[16][4];
        }
            if(matrixDN_Dksi!=null && jacobian!=null && matrixDN_Deta!=null){
                    for (int i=0;i<out.length;i++){
                        for(int j=0;j<4;j++){ 
                                out[i][j]=(1/detI.get(i))*(matrixDN_Dksi[i][j]*jacobian.get(i)[1][1]+matrixDN_Deta[i][j]*(-jacobian.get(i)[0][1]));
                        }
                    }
            }else{
                 System.out.println("one of given matrix is nullable");
            }
        return out;
    }

    public static double[][] DN_Dy(double[][] matrixDN_Dksi,List<Double[][]> jacobian,double[][] matrixDN_Deta,List<Double> detI){
        double [][] out=null;
        
        if(Global_data.iPc==2){
            out=new double[4][4];
        }else if(Global_data.iPc==3){
            out=new double[9][4];
        }else if(Global_data.iPc==4){
            out=new double[16][4];
        }
        if(matrixDN_Dksi!=null && jacobian!=null && matrixDN_Deta!=null){
                for (int i=0;i<out.length;i++){
                    for(int j=0;j<4;j++)
                            out[i][j]=(1/detI.get(i))*(matrixDN_Dksi[i][j]*(-jacobian.get(i)[1][0])+matrixDN_Deta[i][j]*(-jacobian.get(i)[0][0]));
                }
        }else{
             System.out.println("one of given matrix is nullable");
        }
        return out;
    }
    
    /*----------------------------------------------------C--------------------------------------*/
    
    public double[][] calculateCForElement(double[][] N,List<Double> detI){
        Elem4 elem4=new Elem4();
        List<double[][]> partialH=new ArrayList();
        
        double [][] out=new double[4][4];
        
        if(N!=null){
                for(int i=0;i<4;i++){
                    int w=0;
                    for(int j=0;j<4;j++){
                        int aWeight=0;
                        int bWeight=0;
                        for(int row=0;row<(Global_data.iPc*Global_data.iPc);row++){
                                    if(Global_data.iPc==2){
                                        out[i][j]+=Global_data.c*Global_data.ro*detI.get(j)*N[row][i]*N[row][w];
                                    }else if(Global_data.iPc==3){
                                        out[i][j]+=Global_data.c*Global_data.ro*detI.get(j)*N[row][i]*N[row][w]*elem4.weights[aWeight]*elem4.weights[bWeight];
                                        aWeight++;
                                        if(aWeight==3){
                                            aWeight=0;
                                            bWeight++;
                                        }
                                    }else if(Global_data.iPc==4){
                                        out[i][j]+=Global_data.c*Global_data.ro*detI.get(j)*N[row][i]*N[row][w]*elem4.weights[aWeight]*elem4.weights[bWeight];
                                        aWeight++;
                                        if(aWeight==4){
                                            aWeight=0;
                                            bWeight++;
                                        }
                                    }
                        }
                               w++;
                   }
                }
            partialH.add(out);
            
            if(Global_data.iPc==2){
                if(partialH.size()==4){
                     out=Functions.addMatrix(Functions.addMatrix(partialH.get(0), partialH.get(1)), Functions.addMatrix(partialH.get(2), partialH.get(3)));
                }
            }else if(Global_data.iPc==3){
                if(partialH.size()==9){
                    double[][] h_1=Functions.addMatrix(Functions.addMatrix(partialH.get(0), partialH.get(1)), Functions.addMatrix(partialH.get(2), partialH.get(3)));
                    double[][] h_2=Functions.addMatrix(Functions.addMatrix(partialH.get(4), partialH.get(5)), Functions.addMatrix(partialH.get(6), partialH.get(7)));
                    
                    double[][] h_11=Functions.addMatrix(partialH.get(8),h_1);
                    
                     out=Functions.addMatrix(Functions.addMatrix(partialH.get(2), partialH.get(3)), h_11);
                }
            }else if(Global_data.iPc==4){
                if(partialH.size()==16){
                double[][] H12 = Functions.addMatrix(Functions.addMatrix(partialH.get(0), partialH.get(1)), Functions.addMatrix(partialH.get(2), partialH.get(3)));
                double[][] H34 = Functions.addMatrix(Functions.addMatrix(partialH.get(4), partialH.get(5)), Functions.addMatrix(partialH.get(6), partialH.get(7)));
                double[][] H56 = Functions.addMatrix(Functions.addMatrix(partialH.get(8), partialH.get(9)), Functions.addMatrix(partialH.get(10), partialH.get(11)));
                double[][] H78 = Functions.addMatrix(Functions.addMatrix(partialH.get(12), partialH.get(13)), Functions.addMatrix(partialH.get(14), partialH.get(15)));
                

                out=Functions.addMatrix(Functions.addMatrix(H12,H34),Functions.addMatrix(H56,H78));
                }
            }
         }else{
             System.out.println("one of given matrix is nullable");
         }
        return out;  
    }
    
    private double[][] calculateHbc(Elem4 elem4){
        double[][] Hbc = new double[4][4];
        double[][][] hbc_partial = new double[4][4][4];
 
        //
        int iP = 0;
        for (int i = 0; i < elem4.nShapeFunctions; i++){
            int i2 = i + 1;
            if (i2 == 4){
                i2 = 0;
            }
            if (this.nodes.get(i).getBC() == 1 && this.nodes.get(i2).getBC() == 1) {
                for (int j = 0; j < Global_data.iPc; j++){
                    for (int k = 0; k < 4; k++){
                        for (int l = 0; l < 4; l++){
                            hbc_partial[i][k][l]+=(elem4.Nbc[k][iP]*elem4.Nbc[l][iP])*elem4.HbcWeights[j]*Global_data.alfa*calculateL(this.nodes.get(i),this.nodes.get(i2)) / 2;
                        }
                    }
                    iP++;
                }
            }else{
                iP += Global_data.iPc;
            }
        }
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                for (int k = 0; k < 4; k++){
                    Hbc[i][j] += hbc_partial[k][i][j];
                }
            }
        }
        return Hbc;
    }
    
    private double calculateL(Node p1, Node p2) {
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(),2) + Math.pow(p2.getY() - p1.getY(),2));
    }
    
    public double[] calculateP(Elem4 elem4){
        double[] P = new double[elem4.nShapeFunctions];
        double[][] P_partial=new double [elem4.nShapeFunctions][elem4.nShapeFunctions];
        //
        int iP = 0;
        for (int i = 0; i < elem4.nShapeFunctions; i++){
            int i2 = i + 1;
            if (i2 == 4){
                i2 = 0;
            }
            if (this.nodes.get(i).getBC() == 1 && this.nodes.get(i2).getBC() == 1) {
                for (int j = 0; j<Global_data.iPc; j++){
                    for (int k = 0; k < 4; k++){
                            P_partial[i][k]+= -1*(elem4.Nbc[k][iP] * elem4.weights[j] *  Global_data.alfa * Global_data.Talfa * (calculateL(this.nodes.get(i),this.nodes.get(i2)) / 2));
                    }
                    iP++;
                }
            }
            else{
                iP += Global_data.iPc;
            }
        }
        for (int i = 0; i < elem4.nShapeFunctions; i++){
                    for (int j = 0; j < elem4.nShapeFunctions; j++){
                        P[i] += P_partial[j][i];
                    }
                }
        return P;
    }
          
}
