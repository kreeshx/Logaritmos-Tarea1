package JUnit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.RTree;


public class RtreeTest {
  int m,M;
  RTree tree;
  
  @Before
  public void settings(){
    m=3;
    M=5;
    tree= new RTree(m,M);
  }
  
  @Test
  public void constructorTestNoNulo(){
    
    assertNotNull("El arbol no debe ser nulo", tree);
    
  }
  
  @Test
  public void constructorTestSetm(){
    assertEquals(m, tree.m);

  }
  
  @Test
  public void constructorTestSetM(){
    assertEquals("M debe ser 5",M,tree.M);
  }
  
  @Test
  public void constructorTestSetRaiz(){
    assertNotNull("la raiz no debe ser nula",tree.root);
    
  }
  
  
  
  
}

  