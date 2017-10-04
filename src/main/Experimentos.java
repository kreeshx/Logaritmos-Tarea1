package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;

public class Experimentos implements Serializable{
  public static final String DIR  
  = RTree.class.getProtectionDomain().getCodeSource()  
  .getLocation().getFile() + File.separator;
  
  
  public static RTree arbol;
  public static void generarRectangulos(int n, int tipo ){
    int[] a = new int[2];
    int alto,ancho;

    System.out.println("Entra a generar rectangulos");
    for (int i = 0; i < n-1; i++) {
      Random rand = new Random();
      a[0] = rand.nextInt(5000);
      a[1] = rand.nextInt(5000);
      alto = rand.nextInt(100)+1; //se le suma 1 porque queremos que parta de 1
      ancho = rand.nextInt(100)+1; //se le suma 1 porque queremos que parta de 1
      Rectangulo rec = new Rectangulo(a, alto, ancho);

      System.out.println("Insertar rectangulo");
      arbol.insertar(rec, tipo);

      System.out.println("Ya inserto rectangulo");
    }
  }
  
  public static void main(String[] args) {
    System.out.println("aaaa");
    //leemos las variables de la consola
    Scanner sc = new Scanner(System.in);
    System.out.println("ingrese un n ");
    int n = sc.nextInt();
    System.out.println("ingrese m ");
    int m = sc.nextInt();
    System.out.println("ingrese M ");
    int M = sc.nextInt();
    System.out.println("ingrese un tipo, 1 si es linear, 2 si es greene ");
    int tipo = sc.nextInt();
    
    //generamos el primer rectangulo para inicializar el arbol
    Random rand = new Random();
    int[] a = new int[2];
    int alto,ancho;
    a[0] = rand.nextInt(5000);
    a[1] = rand.nextInt(5000);
    alto = rand.nextInt(100)+1; //se le suma 1 porque queremos que parta de 1
    ancho = rand.nextInt(100)+1; //se le suma 1 porque queremos que parta de 1
    Rectangulo rec = new Rectangulo(a, alto, ancho);
    System.out.println("Se creo el primer rectangulo");
    //creamos el arbol con el nuevo rectangulo
    arbol = new RTree(m, M, rec);

    System.out.println("Se creo el arbol");
    //generamos un arbol con n nodos insertando con el tipo ingresado. 
    generarRectangulos(n, tipo);
  }

}
