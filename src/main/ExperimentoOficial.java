package main;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ExperimentoOficial {
  
  //metodo que escribe en un archivo la cantidad de accesos a disco en la busqueda y el tiempo que se demora por cada query
  private static void generarQueries(int i) {
    
    return null;
  }

  public static void generarRectangulos(int n,RTree arbol1, RTree arbol2){
    int[] a = new int[2];
    int alto,ancho;
    long timeGreene = 0;
    long timeLinear = 0;
    long timeIni, timeOut;
    for (int i = 0; i < n-1; i++) {
      Random rand = new Random();
      a[0] = rand.nextInt(5000);
      a[1] = rand.nextInt(5000);
      alto = rand.nextInt(100)+1; //se le suma 1 porque queremos que parta de 1
      ancho = rand.nextInt(100)+1; //se le suma 1 porque queremos que parta de 1
      Rectangulo rec = new Rectangulo(a, alto, ancho);
      timeIni = System.currentTimeMillis();
      arbol1.insertar(rec,1);
      timeOut = System.nanoTime();
      timeLinear += timeOut-timeIni;
      timeIni = System.currentTimeMillis();
      arbol2.insertar(rec,2);
      timeOut = System.nanoTime();
      timeGreene += timeOut-timeIni;
    }
    timeGreene /= 1000000000.0;
    timeLinear /= 1000000000.0;
    //TODO falta escribir en disco, ahi esta calculado el tiempo de insercion de cada arbol --> Experimento 1
  }
  
  
  public static void main(String[] args) {
    //lista de valor con los que probaremos
    List<Integer> indexes = Arrays.asList(1000,2500,5000,10000,25000,50000,100000);
    Random rand = new Random();
    int[] a = new int[2];
    int alto,ancho;
    a[0] = rand.nextInt(5000);
    a[1] = rand.nextInt(5000);
    alto = rand.nextInt(100)+1; //se le suma 1 porque queremos que parta de 1
    ancho = rand.nextInt(100)+1; //se le suma 1 porque queremos que parta de 1
    Rectangulo rec = new Rectangulo(a, alto, ancho);
    int M = 100;
    int m = (int)0.4*M;
    RTree linear_tree = new RTree(m, M,rec);
    RTree greene_tree = new RTree(m, M,rec);
    int current_lenght = 0;
    for (Integer each : indexes) {
        int diff = each - current_lenght;
        generarRectangulos(diff,linear_tree,greene_tree); //generamos los arboles
        List<Rectangulo> queries = generarQueries(each/10,linear_tree,greene_tree);
        current_lenght = each;
    }
}



}
