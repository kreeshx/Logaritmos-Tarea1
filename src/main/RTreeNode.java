package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

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
  public RTreeNode(boolean leaf, int m , int M,Rectangulo mbr){
    this.m=m;
    this.M=M;
    this.mbr=mbr;
   //TODO: Ver como se setea el padre
    this.id=IdGenerator.nextId(); //crear el id de este nodo
    if(!leaf){
      this.childID=new java.util.ArrayList<Integer>(this.M); //solo si no somos hoja podemos tener hijos
    }    
  }

  //metodo que avisa si sus hijos son hojas. 
  //se realiza una lectura a disco. 
  public boolean hijoEsHoja(){
    RTreeNode hijo = RTreeNode.readFromDisk(this.childID.get(0)); //primer hijo
    if(hijo.childID==null){
      return true;
    }
    else 
      return false;
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

  public static int crecimiento(Rectangulo child, Rectangulo insercion){
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

  //calcula cual de los hijos crece menos dado un rectangulo
  public int menorCrecimiento(Rectangulo insercion){
    int minimo  = Integer.MAX_VALUE;
    int id_minimo = 0;
    int areaMinimo = 0;
    for (int i = 0; i < this.childID.size(); i++){
      Rectangulo rec = RTreeNode.readFromDisk(this.childID.get(i)).mbr;
      int crecimiento = crecimiento(rec,insercion);
      if(crecimiento < minimo){ //si el crecimiento es menor al minimo encontrado
        minimo = crecimiento;
        areaMinimo= rec.alto*rec.alto;
        id_minimo = this.childID.get(i);
      }
      else if(crecimiento==minimo){ //si el crecimiento es igual al minimo encontrado
        if(rec.alto*rec.ancho<areaMinimo){ //area menor
          minimo = crecimiento;
          areaMinimo= rec.alto*rec.alto;
          id_minimo = this.childID.get(i);
        }
        else if (rec.alto*rec.ancho==areaMinimo){ //si tienen la misma area
          int r = (int)(Math.random()*2); /////esto podria estar MAAALOOOOO!!!!!!!!
          if(r==1){
            minimo = crecimiento;
            areaMinimo= rec.alto*rec.alto;
            id_minimo = this.childID.get(i);
          }
        }
      }
    }
    return id_minimo;
  }

  
  //metodo que redirige a que tipo de heuristica haremos 
  public void arreglar(int tipo) {
    if (tipo == 1){
      this.lineal();
    }
    else {
      this.greene();
    }
  }

  private void greene() {
    // TODO Auto-generated method stub
    
  }

  private void lineal() {
    Par<int[],Rectangulo[]> par = parLejano(this.childID); //devuelve el par de rectangulos y sus ids
    Rectangulo recMenor = par.segundo[0];
    Rectangulo recMayor = par.segundo[1];
    int id1 = par.primero[0];
    int id2 = par.primero[1];
    //crear dos nodos internos que sean mbr de los nodos que escogimos cmo mas lejanos

    RTreeNode nuevoNodoPadre1 = new RTreeNode(false, this.m, this.M, recMenor); //nodo con el mbr inicial
    RTreeNode nuevoNodoPadre2 = new RTreeNode(false, this.m, this.M, recMayor);//nodo con el mbr inicial
    
    RTreeNode padre = RTreeNode.readFromDisk(this.father); //traemos el padre
    //le agregamos llos nuevos nodos padre al abuelo
    padre.childID.add(nuevoNodoPadre1.id);
    padre.childID.add(nuevoNodoPadre2.id);
   
    nuevoNodoPadre1.childID.add(id1);//reciclAje de id!!!
    nuevoNodoPadre2.childID.add(id2);
    //eliminar los rec q elegimos de los hijoss
    this.eliminarHijo(id1);
    this.eliminarHijo(id2);
    
   //TODO: Falta agregarle a los nodos un padre
    for(int i = 0 ; i<this.childID.size();i++){
      RTreeNode tempNodo = RTreeNode.readFromDisk(this.childID.get(i));
      Rectangulo tempRec = tempNodo.mbr;
      int grown1 = crecimiento(nuevoNodoPadre1.mbr, tempRec);
      int grown2 = crecimiento(nuevoNodoPadre2.mbr, tempRec);
      if (grown1 > grown2){
        nuevoNodoPadre1.childID.add(this.childID.get(i));
      } else {
        nuevoNodoPadre2.childID.add(this.childID.get(i));
      }
    }
    
    nuevoNodoPadre1.writeToDisk();
    nuevoNodoPadre2.writeToDisk();
    padre.writeToDisk(); 
    
    
  }

  
  public void eliminarHijo(int id1) {
    this.childID.remove(id1);
    
  }

  //metodo que calcula el par de rectangulos mas lejanos
  public static Par<int[], Rectangulo[]> parLejano(ArrayList<Integer> IDChilds) {
    int[] lados = new int[8];
    int[] ids = new int[8];
    Rectangulo[] recs = new Rectangulo[2];
    Par<int[],Rectangulo[]> ans = new Par(new int[2], new Rectangulo[2]);
    
    lados[0] = Integer.MAX_VALUE; //rectangulo que tiene la derecha mas a la izquierda
    lados[1] = Integer.MIN_VALUE; //rectangulo que tiene la izquierda mas a la derecha
    lados[2] = Integer.MAX_VALUE; //rectangulo que tiene la izquierda mas a la izquierda
    lados[3] = Integer.MIN_VALUE; //rectangulo que tiene la derecha mas a la derecha
    lados[4] = Integer.MAX_VALUE; //rectangulo que tiene el arriba mas a el abajo
    lados[5] = Integer.MIN_VALUE; //rectangulo que tiene el abajo mas a el arriba
    lados[6] = Integer.MAX_VALUE; //rectangulo que tiene el abajo mas a el abajo
    lados[7] = Integer.MIN_VALUE; //rectangulo que tiene el arriba mas a el arriba
    for (int i = 0; i < IDChilds.size(); i++) {
      RTreeNode nodo = RTreeNode.readFromDisk(IDChilds.get(i));
      Rectangulo rec = nodo.mbr;
      //rectangulo que tiene la derecha mas a la izquierda
      if (rec.a[0] + rec.ancho < lados[0]){
        ids[0] = nodo.id;
        recs[0] = rec;
        lados[0] = rec.a[0] + rec.ancho;
      }
      //rectangulo que tiene la izquierda mas a la derecha
      if (rec.a[0] > lados[1]){
        ids[1] =  nodo.id;
        recs[1] = rec;
        lados[1] = rec.a[0];
      }
      //rectangulo que tiene la izquierda mas a la izquierda
      if (rec.a[0] < lados[2]){
        ids[2] =  nodo.id;
        recs[2] = rec;
        lados[2] = rec.a[0];
      }
      //rectangulo que tiene la derecha mas a la derecha
      if (rec.a[0] + rec.ancho > lados[3]){
        ids[3] =  nodo.id;
        recs[3] = rec;
        lados[3] = rec.a[0] + rec.ancho;
      }
      //rectangulo que tiene el arriba mas a el abajo
      if (rec.a[1] + rec.alto < lados[4]){
        ids[4] =  nodo.id;
        recs[4] = rec;
        lados[4] = rec.a[1] + rec.alto;
      }
      //rectangulo que tiene el abajo mas a el arriba
      if (rec.a[1] > lados[5]){
        ids[5] =  nodo.id;
        recs[5] = rec;
        lados[5] = rec.a[1];
      }
      //rectangulo que tiene el abajo mas a el abajo
      if (rec.a[1] < lados[6]){
        ids[6] =  nodo.id;
        recs[6] = rec;
        lados[6] = rec.a[1];
      }
      //rectangulo que tiene el arriba mas a el arriba
      if (rec.a[1] + rec.alto > lados[7]){
        ids[7] =  nodo.id;
        recs[7] = rec;
        lados[7] = rec.a[1] + rec.alto;
      }
    }
    //retorna la id de los rectangulos elegidos
    int[] indicesElegidos = parElegido(lados);
    ans.primero[0] = ids[indicesElegidos[0]];
    ans.primero[1] = ids[indicesElegidos[1]];
    ans.segundo[0] = recs[indicesElegidos[1]];
    ans.segundo[1] = recs[indicesElegidos[1]];
    return ans;
    
  }

  private static int[] parElegido(int[] lados) {
    int[] respuestas = new int[2];
    int horizontalChico = lados[1]-lados[0];
    int horizontalGrande = lados[3]-lados[2];
    int verticalChico = lados[5]-lados[4];
    int verticalGrande = lados[7]-lados[6];
    int horizontal = horizontalChico/horizontalGrande;
    int vertical = verticalChico/verticalGrande;
    if (horizontal > vertical){
      respuestas[0] = 0;
      respuestas[1] = 1;
    }
    else {
      respuestas[0] = 5;
      respuestas[1] = 6;
    }
    return respuestas;
  }
}
