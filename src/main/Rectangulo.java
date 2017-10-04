package main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Rectangulo implements Serializable {
  public static final String DIR  
  = RTree.class.getProtectionDomain().getCodeSource()  
  .getLocation().getFile() + File.separator;
  
  public int[] a;
    
  public int alto;
  
  public int ancho;
  
  
  public Rectangulo(int[] a,int alto, int ancho){
    this.a=a;
    this.alto=alto;
    this.ancho=ancho;
  }
  
  //metodo para saber si this rectangulo intersecta con rec2
  public boolean interseccion(Rectangulo rec2){
   
    if(this.a[0] - rec2.ancho <= rec2.a[0] && 
        this.a[0] + this.ancho >= rec2.a[0] && 
        this.a[1] - rec2.alto <= rec2.a[1] &&
        this.a[1]+ this.alto >= rec2.a[1]){
      return true;
    }
    
    return false;
  }

  
  public void writeToDisk(int id) {  
    try {  
      ObjectOutputStream out  
        = new ObjectOutputStream  
        (new FileOutputStream(DIR + "rectangulos" + id + ".rec"));  
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
