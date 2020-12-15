/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mes.fem_grid;

/**
 *
 * @author Wiktor
 */
public class Node {
    private int id;
    private double x;
    private double y;
    
    private int BC=0;
    
    private int t;
    
    public Node(int id,double x,double y){
        this.id=id;
        this.x=x;
        this.y=y;
    }
    
    public Node(double x,double y){
        this.x=x;
        this.y=y;
    }
        
    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }
    
        /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    public int getBC() {
        return BC;
    }

    public void setBC(int BC) {
        this.BC = BC;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }
    
    
    
    @Override
    public String toString() {
        return "Node id="+id+"{" + "x=" + x + ", y=" + y + '}';
    }
}
