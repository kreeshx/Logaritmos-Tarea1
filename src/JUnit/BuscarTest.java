package JUnit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.RTree;
import main.Rectangulo;

public class BuscarTest {
  RTree tree;
  Rectangulo rec; 
		  
  @Before
  public void setter(){
	rec = new Rectangulo((0,0),3, 4);
    tree= new RTree(2,5,rec);
    
  }
  
  @Test
  public void test() {
    fail("Not yet implemented");
  }

}
