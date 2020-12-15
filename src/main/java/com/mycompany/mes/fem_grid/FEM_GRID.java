/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mes.fem_grid;

import com.mycompany.mes.dataObjects.Global_data;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wiktor
 */
public class FEM_GRID extends Global_data{
    private List<Node> nodeTab;
    private List<Element> elementTab;
    
    public FEM_GRID(){
        this.nodeTab=new ArrayList();
        this.elementTab=new ArrayList();
        createGrid();
        BCCondition();
    }


        /**
     * @return the nodeTab
     */
    public List<Node> getNodeTab() {
        return nodeTab;
    }

    /**
     * @param nodeTab the nodeTab to set
     */
    public void setNodeTab(List<Node> nodeTab) {
        this.nodeTab = nodeTab;
    }

    /**
     * @return the elementTab
     */
    public List<Element> getElementTab() {
        return elementTab;
    }

    /**
     * @param elementTab the elementTab to set
     */
    public void setElementTab(List<Element> elementTab) {
        this.elementTab = elementTab;
    }
    
    public void createGrid(){
        System.out.println("Creating grid");
        double dX=Global_data.H/(Global_data.nH-1);
        double dY=Global_data.W/(Global_data.nW-1);
        
        System.out.println("Delta x = "+dX);
        System.out.println("Delta y = "+dY);
        int nodeNumber=0;
        double xValue=0.0;
        double yValue=0.0;
        for(int i=0;i<Global_data.nH;i++){
            yValue=0.0;
            for(int j=0;j<Global_data.nW;j++){
                Node newNode=new Node(nodeNumber,i*dX,j*dY);
                this.nodeTab.add(nodeNumber,newNode);
                nodeNumber++;
                yValue+=dY;
            }
            xValue+=dX;
        }
        
        
        int elementNumber=0;
        int nextColumn=0;
        for(int i=0;i<Global_data.nW-1;i++){
            for(int j=0;j<Global_data.nH-1;j++){
                int value1=elementNumber+1+nextColumn;
                Node n1=this.nodeTab.get(value1-1);
                Node n2=this.nodeTab.get(value1+Global_data.nH-1);
                Node n3=this.nodeTab.get(value1+Global_data.nH+1-1);
                Node n4=this.nodeTab.get(value1+1-1);
                Element e=new Element(n1,n2,n3,n4);
                System.out.println(e.toString());
                this.elementTab.add(elementNumber, e);
                elementNumber++;
            }
            nextColumn++;
        }
    }
    
    public void printElement(int elementID){
        System.out.println("Element number "+elementID);
        Element e=getElementTab().get(elementID);
        for(Node n:e.getNodes()){
             System.out.println(" "+n.toString());
        }
    }
    
    public void printGrid(){
        System.out.println("Printing whole grid");
        System.out.println("number of elements: "+getElementTab().size());
        for(int i=0;i<getElementTab().size();i++){
            printElement(i+1);
        }
    }
    
    public void BCCondition(){
        if(this.nodeTab!=null){
            for(Node n:this.nodeTab){
                if(n.getX()==0.0 || n.getY()==0.0 || n.getX()==Global_data.W || n.getY()==Global_data.H){
                    n.setBC(1);
                }
            }
        }
    }
}
