/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mes.dataObjects;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Wiktor
 */
public abstract class Global_data {
    public static double H;
    public static double W;
    public static int nH;
    public static int nW;
    public static int nN;
    public static int nE;
    public static int iPc;
    public static int k=25;
    
    public static int alfa;
    public static int Talfa;
    
    public static int ro;
    public static int c;
    
    public static Elem4 elem4;

    public static int getK() {
        return k;
    }

    public static void setK(int k) {
        Global_data.k = k;
    }

    public static int getAlfa() {
        return alfa;
    }

    public static void setAlfa(int alfa) {
        Global_data.alfa = alfa;
    }

    
    public static int getRo() {
        return ro;
    }

    public static void setRo(int ro) {
        Global_data.ro = ro;
    }

    public static int getC() {
        return c;
    }

    public static void setC(int c) {
        Global_data.c = c;
    }

    public static int getTalfa() {
        return Talfa;
    }

    public static void setTalfa(int Talfa) {
        Global_data.Talfa = Talfa;
    }
    
    

    /**
     * @return the H
     */
    public double getH() {
        return H;
    }

    /**
     * @param H the H to set
     */
    public void setH(double H) {
        this.H = H;
    }

    /**
     * @return the W
     */
    public double getW() {
        return W;
    }

    /**
     * @param W the W to set
     */
    public void setW(double W) {
        this.W = W;
    }

    /**
     * @return the nH
     */
    public int getnH() {
        return nH;
    }

    /**
     * @param nH the nH to set
     */
    public void setnH(int nH) {
        this.nH = nH;
    }

    /**
     * @return the nW
     */
    public int getnW() {
        return nW;
    }

    /**
     * @param nW the nW to set
     */
    public void setnW(int nW) {
        this.nW = nW;
    }
    
    /**
     * @return the nN
     */
    public int getnN() {
        return nN;
    }

    /**
     * @param nN the nN to set
     */
    public void setnN(int nN) {
        this.nN = nN;
    }

    /**
     * @return the nE
     */
    public int getnE() {
        return nE;
    }

    /**
     * @param nE the nE to set
     */
    public void setnE(int nE) {
        this.nE = nE;
    }

    public static int getiPc() {
        return iPc;
    }

    public static void setiPc(int iPc) {
        Global_data.iPc = iPc;
    }
    
    
    public Global_data(){
        readDataFromFile();
        int nN=getnH()*getnW();
        int nE=(getnH()-1)*(getnW()-1);
        setnE(nE);
        setnN(nN);
        System.out.println("H="+getH());
        System.out.println("W="+getW());
        System.out.println("nH="+getnH());
        System.out.println("nW="+getnW());
        System.out.println("iPc="+getiPc());
        System.out.println("nodes number nN="+getnN());
        System.out.println("elements number nE="+getnE());
        System.out.println("ro = "+getRo());
        System.out.println("C = "+getC());
        System.out.println("Alfe = "+getAlfa());
        System.out.println("TAlfa = "+getTalfa());
        
        this.elem4=new Elem4();
    }
    
    private void readDataFromFile(){
        try{
            System.out.println("Reading h and w from file");
            File f=new File("C:\\Users\\Wiktor\\Documents\\NetBeansProjects\\MES\\global_data_input");
            Scanner s=new Scanner(f);
            List<String> values=new ArrayList();
            while(s.hasNextLine()){
                String ss=s.nextLine();
                System.out.println(ss);
                values.add(ss);
            }
            s.close();
            if(values.size()==9){
                setH(Double.parseDouble(values.get(0)));
                
                setW(Double.parseDouble(values.get(1)));

                setnH(Integer.parseInt(values.get(2)));
                
                setnW(Integer.parseInt(values.get(3)));
                
                setiPc(Integer.parseInt(values.get(4)));
                
                setRo(Integer.parseInt(values.get(5)));
                
                setC(Integer.parseInt(values.get(6)));
                
                setAlfa(Integer.parseInt(values.get(7)));
                
                setTalfa(Integer.parseInt(values.get(8)));
            }
        }catch(FileNotFoundException exc){
                System.out.println(exc.getMessage());
                setH(0.0);
                setW(0.0);
                setnH(0);
                setnW(0);
                setiPc(2);
                setRo(0);
                setC(0);
                setAlfa(0);
        }catch(NullPointerException exc){
            System.out.println(exc.getMessage());
        }
    }
}
