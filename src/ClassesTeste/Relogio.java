
import java.awt.Font;
import java.awt.Color;
import java.awt.Point;
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class Relogio extends JWindow{
  //Video Aula - Como criar uma tela de Splash : http://www.youtube.com/watch?v=R7WOQ0Yn3jg
  int tempo;
   public Relogio(final int tempo) throws InterruptedException{
      this.tempo = tempo;
      int w = this.getToolkit().getDefaultToolkit().getScreenSize().width;
      int h = this.getToolkit().getDefaultToolkit().getScreenSize().height;
      int z = 2;
      int x = (w - 523)/z;
      int y = (h - 533)/z;
	
	final JLabel contador = new JLabel();
	//contador.setForeground(Color.green);//Definir cor da label
	contador.setFont(new Font("Dialog", Font.PLAIN, 18)); 
	contador.setForeground(new Color(50,160,74));//Definir cor da label usando o formato RGB;
	contador.setLocation(new Point(10,1));
	contador.setSize(102,52);
	contador.setText("asdfasdf");

		
	this.setLayout(null);
	this.add(contador);
	this.setLocation(new Point(x,y));
	this.setSize(120,70);
	this.setVisible(true);
	
	
	new Thread(){
     public void run(){
	  for(int progress = 1;progress <= tempo;progress++){
	   try{contador.setText("00:00:" +progress); sleep(1000);}
	   catch(InterruptedException e1){e1.printStackTrace();}
	  }
     }
	}.start();
	
	Thread.sleep(tempo * 1010); //tempo em que a tela fica visível
	
	//Fazer o contador piscar após o termino do tempo
	contador.setForeground(Color.red);
	Thread.sleep(600);
	contador.setForeground(Color.yellow);
	Thread.sleep(600);
	contador.setForeground(Color.red);
	Thread.sleep(600);
	contador.setForeground(Color.yellow);
	Thread.sleep(600);
	contador.setForeground(Color.red);
	Thread.sleep(600);
	contador.setForeground(Color.yellow);
	Thread.sleep(600);
	contador.setForeground(Color.red);
	Thread.sleep(1000);
	contador.setText("<html><center>TEMPO<br/>ESGOTADO!</center></html>");
	Thread.sleep(3500);
	
	//encerrar o processo
	dispose();
  }
  

 /* public static void main (String []a) throws InterruptedException{
    Relogio r = new Relogio(10);
    
    
  }*/
}