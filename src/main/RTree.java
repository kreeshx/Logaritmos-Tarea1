package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class RTree implements Serializable {
  public static final String DIR  
  = RTree.class.getProtectionDomain().getCodeSource()  
  .getLocation().getFile() + File.separator;
  
  public int rootID; //id del nodo raiz

  public int m;
  public int M;

  public RTreeNode root;
   
   //constructor del RTree
   public RTree(int m, int M, Rectangulo rec) {
     RTreeNode root = new RTreeNode(false,m,M,rec);
     this.root=root;
     this.m=m;
     this.M=M;
     this.rootID = root.getId();
     root.writeToDisk();
     writeToDisk();
   }
   
   //metodo buscar que hace un dfs sobre el arbol para buscar a los rectangulos que intersectan con C
   public Par<Integer,List<Rectangulo>> buscar(Rectangulo C){
     
     Stack<Integer> stack = new Stack(); //stack donde se almacenaran los nodos posibles
     stack.add(this.rootID); //agrego el primer nodo a evaluar en el stack
     Par<Integer,List<Rectangulo>> ans = new Par(0,new ArrayList<>()); //par solucion
     while(!stack.isEmpty()){ //mientras pueda seguir buscando
       RTreeNode nodo = RTreeNode.readFromDisk(stack.pop()); //saco el primer elemento del stack y abro su nodo
       ans.primero++;
       
       if(nodo.isLeaf()){ //si es una hoja e intersecta a C, lo agrego a la solucion
         if(C.interseccion(nodo.mbr)){
           ans.segundo.add(nodo.mbr); 
          }        
       }
       //no es una hoja
       else{
         //agregar al stack todos los hijos de este nodo que intersectan
         stack.addAll(nodo.childID.stream().filter(a->{
           //leo un hijo
           RTreeNode hijo= RTreeNode.readFromDisk(a);
           //agrego 1 porque lei del disco
           ans.primero++;
           //si intersecta lo agrego al stack
           if(C.interseccion(hijo.mbr)){
             return true;
           }
           return false;
         }).collect(Collectors.toList()));
       }
     }     
     return null;
   }
   
   

   
   //m�todo que inserta un rectangulo al arbol y la heuristica a usar y retorna la cantidad de accesos a disco 
   //linear -> tipo= 1
   //greene -> tipo = 2
   int insertar(Rectangulo rec, int tipo){
     int accesos = 0;
     RTreeNode tempRoot = this.root;
     RTreeNode nodoNuevo = new RTreeNode(true, this.m, this.M ,rec);
     
     while(!tempRoot.hijoEsHoja()){ //mientras no lleguemos al penultimo
       accesos++;
       int id = tempRoot.menorCrecimiento(rec); //buscamos a que hijo irnos
       tempRoot = RTreeNode.readFromDisk(id);
       
     } //llegamos a la hoja    
       
      //se inserta el nodo nuevo a la lista de hijos del tempRoot
     tempRoot.childID.add(nodoNuevo.id);
     tempRoot.writeToDisk();
     //se arreglan los invariantes
     while(!tempRoot.isRoot) {
       tempRoot.arreglar(tipo);
       tempRoot = RTreeNode.readFromDisk(tempRoot.father);
     }
     return accesos;
   }
   
   
   //m�todo que escribe un arbol en disco  
   public void writeToDisk() {  
     try {  
       ObjectOutputStream out  
         = new ObjectOutputStream  
         (new FileOutputStream(DIR + "rtree"));  
       out.writeObject(this);  
       out.close();  
     } catch (Exception e) {  
       e.printStackTrace();  
       System.exit(1);  
     }  
   }    
  
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
