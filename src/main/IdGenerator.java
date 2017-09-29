package main;
import java.io.*;  
  
/** Generates unique id numbers, even across multiple sessions. */  
public class IdGenerator {  
  
  /** File in which the next available id is stored. */  
  public static final File FILE = new File(RTree.DIR + "id");  
  
    /** Return the next available id number. */  
  public static int nextId() {  
    try {  
      int result;  
      if (FILE.exists()) {  
        ObjectInputStream in  
          = new ObjectInputStream(new FileInputStream(FILE));  
        result = in.readInt();  
      } else {  
        result = 0;  
      }  
      ObjectOutputStream out  
        = new ObjectOutputStream(new FileOutputStream(FILE));  
      out.writeInt(result + 1);  
      out.close();  
      return result;  
    } catch (IOException e) {  
      e.printStackTrace();  
      System.exit(1);  
      return 0;  
    }  
  }  
  
  public static void main(String[] args) {  
    System.out.println(nextId());  
  }  
}  