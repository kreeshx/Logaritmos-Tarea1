package main;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ExperimentoOficial {
  
  //metodo que escribe en un archivo la cantidad de accesos a disco en la busqueda y el tiempo que se demora por cada query
  private static void generarQueries(int n, RTree arbol1, RTree arbol2, PrintWriter printLinear, PrintWriter printGreene) {
    int[] a = new int[2];
    int alto,ancho;
    Par<Integer, List<Rectangulo>> ans1, ans2;
    float timeLinear = 0;
    float timeGreene = 0;
    long accesosGreene = 0;
    long accesosLinear = 0;
    long timeIni, timeOut;
    long tf;
    for (int i = 0; i < n-1; i++) {
      Random rand = new Random();
      a[0] = rand.nextInt(5000);
      a[1] = rand.nextInt(5000);
      alto = rand.nextInt(100)+1; //se le suma 1 porque queremos que parta de 1
      ancho = rand.nextInt(100)+1; //se le suma 1 porque queremos que parta de 1
      Rectangulo rec = new Rectangulo(a, alto, ancho);
      timeIni = System.nanoTime();
      ans1 = arbol1.buscar(rec);
      timeOut = System.nanoTime();
      tf = timeOut - timeIni;
      timeLinear += tf;
      accesosLinear += ans1.primero; 
      printLinear.write("Accesos a memoria: " + ans1.primero + "   Tiempo de busqueda: " + tf + " nanosegundos");
      printLinear.write(System.lineSeparator());
      timeIni = System.nanoTime();
      ans2 = arbol2.buscar(rec);
      timeOut = System.nanoTime();
      tf = timeOut - timeIni;
      timeGreene += tf;
      accesosGreene += ans2.primero;
      printGreene.write("Accesos a memoria: " + ans2.primero + "   Tiempo de busqueda: " + tf + " nanosegundos");
      printGreene.write(System.lineSeparator());
    }
    timeGreene /= 1000000000.0;
    timeLinear /= 1000000000.0;
    printGreene.write("Tiempo promedio de busqueda total: " + timeGreene/n + "  segundos");
    printGreene.write(System.lineSeparator());
    printGreene.write("Accesos promedio a memoria: " + accesosGreene/n );
    printGreene.write(System.lineSeparator());
    printLinear.write("Tiempo promedio de busqueda total: " + timeLinear/n + "  segundos");
    printLinear.write(System.lineSeparator());
    printLinear.write("Accesos primedio a memoria: " + accesosLinear/n);
    printLinear.write(System.lineSeparator());
  }

  public static void generarRectangulos(int n,RTree arbol1, RTree arbol2, PrintWriter printLinear, PrintWriter printGreene){
    int[] a = new int[2];
    int alto,ancho;
    float timeGreene = 0;
    float timeLinear = 0;
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
    printLinear.write("Tiempo de insercion :" + timeLinear + "  segundos");
    printLinear.write(System.lineSeparator());
    printGreene.write("Tiempo de insercion :" + timeGreene + "  segundos");
    printGreene.write(System.lineSeparator());
  }
  
  
  public static void main(String[] args) {
    //lista de valor con los que probaremos
    String file, file2;
    PrintWriter printLinear = null; 
    PrintWriter printGreene = null;

    List<Integer> indexes = Arrays.asList(100);
    //List<Integer> indexes = Arrays.asList(1000,2500,5000,10000,25000,50000,100000);
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
        file = greene_tree.DIR + "Greene" + each + ".txt";
        file2 = linear_tree.DIR + "Linear" + each + ".txt";
        try {
            printGreene = new PrintWriter(file, "UTF-8");
            printLinear = new PrintWriter(file2, "UTF-8");
            int diff = each - current_lenght;
            generarRectangulos(diff,linear_tree,greene_tree, printLinear, printGreene); //generamos los arboles
            generarQueries(each/10,linear_tree,greene_tree, printLinear, printGreene);
            current_lenght = each;
            printGreene.close();
            printLinear.close();
        }
        catch (Exception e){}
    }
}



}
