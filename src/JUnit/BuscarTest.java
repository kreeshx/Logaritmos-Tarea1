package JUnit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.RTree;

public class BuscarTest {
  RTree tree;
  
  @Before
  public void setter(){
    tree= new RTree(2,5);
    
  }
  
  @Test
  public void test() {
    fail("Not yet implemented");
  }

}
