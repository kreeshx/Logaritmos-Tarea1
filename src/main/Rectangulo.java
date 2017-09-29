package main;

public class Rectangulo {

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

 
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
