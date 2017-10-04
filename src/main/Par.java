package main;

public class Par<T1, T2> {
  
  public T1 primero;
  public T2 segundo;
  
  public Par(T1 t1,T2 t2) {
    this.primero=t1;
    this.segundo=t2;
  }
  
  @Override
  public String toString(){
    return "{" + primero.toString() + "," + segundo.toString() + "}";
  }
}
