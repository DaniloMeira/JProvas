package ClassesTeste;
import Telas.TelaLoading;

/**
 * @author Danilo Meira e Silva
 */

public class Main {
  
  public static void main(String[] args){
    new Thread(new TelaLoading()).start();
  }
  
}
