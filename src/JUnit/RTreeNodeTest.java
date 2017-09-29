package JUnit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.RTreeNode;

public class RTreeNodeTest {
   int m,M,cero;
   RTreeNode nodoHoja,nodoNoHoja;

  @Before
  public void setup() {
    m = 3;
    M = 5;
    cero = 0;

    // nodo hoja
    nodoHoja = new RTreeNode(true, m, M);
    nodoNoHoja = new RTreeNode(false, m, M);
  }

  @Test
  public void constructorTest() {

    assertNotNull("Se creo el arbol", nodoHoja);
    assertNotNull("Se creo el arbol", nodoNoHoja);

    assertNotNull("se creo m ", nodoHoja.m);
    assertNotNull("se creo m ", nodoHoja.M);

    assertEquals("m debería ser 3", m, nodoHoja.m);
    assertEquals("M debería ser 5", M, nodoHoja.M);

    assertNotNull("el id se creo", nodoHoja.getId());
    assertEquals("el id es 0 por ser el primero", cero, nodoHoja.id);



  }

}
