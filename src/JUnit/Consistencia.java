package JUnit;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;

import org.junit.Test;

import main.Par;
import main.RTree;
import main.Rectangulo;

public class Consistencia {
  @Test
  public void consistence(){
    Random rand = new Random();
    int[] a = new int[2];
    int alto,ancho;
    a[0] = rand.nextInt(5000);
    a[1] = rand.nextInt(5000);
    alto = rand.nextInt(100)+1; //se le suma 1 porque queremos que parta de 1
    ancho = rand.nextInt(100)+1; //se le suma 1 porque queremos que parta de 1
    Rectangulo rec = new Rectangulo(a, alto, ancho);
    
    //creamos el arbol con el nuevo rectangulo
    RTree tree1 = new RTree(3, 5, rec);

    RTree tree2 = new RTree(3, 5, rec);

      
      Random rnd = new Random();
      int coorMax = 50;
      int lenghtMax = 10;
      for (int i = 0; i < 100; i++) {
          ancho = rnd.nextInt(lenghtMax);
          alto = rnd.nextInt(lenghtMax);
          int[] pto = new int[2];
          pto[0] = rnd.nextInt(coorMax-ancho);
          pto[1] = rnd.nextInt(coorMax-alto);
          tree1.insertar(new Rectangulo(pto,ancho,alto), 1);
          tree2.insertar(new Rectangulo(pto,ancho,alto),2);
      }

      for (int i = 0; i < 100; i++) {
          ancho = rnd.nextInt(lenghtMax);
          alto = rnd.nextInt(lenghtMax);
          int[] pto = new int[2];
          pto [0] = rnd.nextInt(coorMax-ancho);
          pto[1]= rnd.nextInt(coorMax-alto);
          Par<Integer,List<Rectangulo>> q1 = tree1.buscar(new Rectangulo(pto,ancho,alto));
          Par<Integer,List<Rectangulo>> q2 = tree1.buscar(new Rectangulo(pto,ancho,alto));
          assertEquals(q1.segundo.size(), q2.segundo.size());
      }

}
}
