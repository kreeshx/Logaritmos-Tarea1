package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class RTreeNode implements Serializable{
  
  //elementos de un arbol
  public java.util.ArrayList<Integer> childID; //lista de ids de los hijos
  public int id; //id de este nodo
  public int m; //se almacenan al menos m hijos
  public int M; //se almacenan a lo mas M hijos
  int father; //id del padre
  Rectangulo mbr;
 
  //funcion constructor
  //especifica si es una hoja o no
  public RTreeNode(boolean leaf, int m , int M){
    this.m=m;
    this.M=M;
   
    this.id=IdGenerator.nextId(); //crear el id de este nodo
    if(!leaf){
      this.childID=new java.util.ArrayList<Integer>(this.M); //solo si no somos hoja podemos tener hijos
    }    
  }
  
  
  public RTreeNode(boolean b) {
    // TODO Auto-generated constructor stub
  }


  //getter para el ID
  public Integer getId() {
    
    return this.id;
  }
  
  public boolean isLeaf(){
    if(this.childID==null){
      return true;
    }
    return false;
  }
  
  public static RTreeNode readFromDisk(int id) {  
    try {  
      ObjectInputStream in  
        = new ObjectInputStream  
        (new FileInputStream(RTree.DIR + "b" + id + ".node"));  
      return (RTreeNode)(in.readObject());  
    } catch (Exception e) {  
      e.printStackTrace();  
      System.exit(1);  
      return null;  
    }  
  }  
  
  //escribir el nodo en disco 
  public void writeToDisk() {  
    try {  
      ObjectOutputStream out  
        = new ObjectOutputStream  
        (new FileOutputStream(RTree.DIR + "r" + id + ".node"));  
      out.writeObject(this);  
      out.close();  
    } catch (Exception e) {  
      e.printStackTrace();  
      System.exit(1);  
    }  
  }

  public int crecimiento(Rectangulo child, Rectangulo insercion){
    int ancho = 0;
    int alto = 0;
    //calcular ancho
    if (insercion.a[0] < child.a[0]){
      ancho = (child.ancho + (child.a[0] - insercion.a[0]));
    }
    else if (((insercion.a[0] - child.a[0]) + (insercion.ancho - child.a[0])) < child.ancho){
      ancho = child.ancho;
    }
    else if (((child.a[0] - insercion.a[0]) + (child.ancho - insercion.a[0])) < insercion.ancho){
      ancho = insercion.ancho;
    }
    else if (insercion.a[0] > (child.a[0] + child.ancho)){
      ancho = (child.ancho + (insercion.a[0] + insercion.ancho));
    }
    //calcular alto
    if (insercion.a[1] < child.a[1]){
      alto = (child.alto + (child.a[1] - insercion.a[1]));
    }
    else if (((insercion.a[1] - child.a[1]) + (insercion.alto - child.a[1])) < child.alto){
      alto = child.alto;
    }
    else if (((child.a[1] - insercion.a[1]) + (child.alto - insercion.a[1])) < insercion.alto){
      alto = insercion.alto;
    }
    else if (insercion.a[1] > (child.a[1] + child.alto)){
      alto = (child.alto + (insercion.a[1] + insercion.alto));
    }
    return (ancho*alto) - (child.ancho*child.alto);
  }

  
}
