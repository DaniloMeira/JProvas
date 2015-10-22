package ClassesTeste.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class ManipuladorImagem{
  public File imagem;
  public ImageIcon thumbnail = null;
  public String diretorioImagens=System.getProperty("user.dir")+"/HTML/Imagens/";

  public ManipuladorImagem(File arquivoImg,int tamanhoH,int tamanhoV){  
	imagem= arquivoImg;
	dimensionaImagem(tamanhoH, tamanhoV);
        
  }

  public ManipuladorImagem(){  
        
  }


 public void dimensionaImagem(int tamanhoH,int tamanhoV){
    
	ImageIcon tmpIcon = new ImageIcon(imagem.getPath());
        if (tmpIcon != null) {
           /* if (tmpIcon.getIconWidth() > tamanhoH && tmpIcon.getIconHeight() > tamanhoV) {
                thumbnail = new ImageIcon(tmpIcon.getImage().
                                          //getScaledInstance(tamanhoH, -1,
                                            getScaledInstance(tamanhoH, tamanhoV,
                                                      Image.SCALE_DEFAULT));
            } 
            else*/ if (tmpIcon.getIconWidth() > tamanhoH) {
                thumbnail = new ImageIcon(tmpIcon.getImage().
                                          getScaledInstance(tamanhoH, -1,
                                                      Image.SCALE_DEFAULT));
            
              if(thumbnail.getIconHeight() > tamanhoV){
                   thumbnail = new ImageIcon(thumbnail.getImage().
                                         getScaledInstance(-1,tamanhoV,
                                                      Image.SCALE_DEFAULT));
              }
            }  
           /* else if (tmpIcon.getIconHeight() > tamanhoV) {
                thumbnail = new ImageIcon(tmpIcon.getImage().
                                          getScaledInstance(-1,tamanhoV,
                                                      Image.SCALE_DEFAULT));
            } */
			 else { //no need to miniaturize
                thumbnail = tmpIcon;
            }
        }
 atualizaImageFile();
 }
 
 public void dimensionaQuadroEditor(int tamanhoH,int tamanhoV){
   	ImageIcon tmpIcon = new ImageIcon(imagem.getPath());
        if (tmpIcon != null) {
           if (tmpIcon.getIconWidth() > tamanhoH && tmpIcon.getIconHeight() > tamanhoV) {
                thumbnail = new ImageIcon(tmpIcon.getImage().
                                          //getScaledInstance(tamanhoH, -1,
                                            getScaledInstance(tamanhoH, tamanhoV,
                                                      Image.SCALE_DEFAULT));
            } 
            else if (tmpIcon.getIconWidth() > tamanhoH) {
                thumbnail = new ImageIcon(tmpIcon.getImage().
                                          getScaledInstance(tamanhoH, -1,
                                                      Image.SCALE_DEFAULT));
            }  
            else if (tmpIcon.getIconHeight() > tamanhoV) {
                thumbnail = new ImageIcon(tmpIcon.getImage().
                                          getScaledInstance(-1,tamanhoV,
                                                      Image.SCALE_DEFAULT));
            }
			 else { //no need to miniaturize
                thumbnail = tmpIcon;
            }
        }
       // atualizaImageFile();
 }
 
 
 public void gravarImagem(String nomeArquivo, String diretorioImagem){
  
   FileInputStream in = null;
   FileOutputStream out = null;
   try {
//    RenderedImage rendered = (RenderedImage)imagem; 
    /*Image novaImagem = thumbnail.getImage();
    if (novaImagem instanceof RenderedImage){  
    rendered = (RenderedImage)novaImagem;
    } 
    else{  
     BufferedImage buffered = new BufferedImage(  
        thumbnail.getIconWidth(),  
        thumbnail.getIconHeight(),  
        BufferedImage.TYPE_INT_RGB  
     );  
     Graphics2D g = buffered.createGraphics();  
     g.drawImage(novaImagem, 0, 0, null);  
     g.dispose();  
     rendered = buffered;  
    }*/
    
         in = new FileInputStream(imagem);
         out = new FileOutputStream(new File(diretorioImagem+nomeArquivo));
         
         int c;
         while ((c = in.read()) != -1) {
            out.write(c);
         }
         if (in != null) {
            in.close();
         }
         if (out != null) {
            out.close();
         }
      }
      
catch (IOException e){  };
    
   // ImageIO.write(rendered, "JPEG", new File(diretorioImagem+nomeArquivo));
    
    //ImageIO.write(rendered, "JPEG", imagem);
System.out.println("gravando imagemmm linha 134 classe manipuladora");
}
   
 


//http://www.javatpoint.com/compressing-and-uncompressing-file
public void comprimeImagem(){

File imagemComprimida=new File(diretorioImagens+"comprimida");
try{  

FileInputStream fin=new FileInputStream(imagem);  
  
FileOutputStream fout=new FileOutputStream(imagemComprimida);  
DeflaterOutputStream out=new DeflaterOutputStream(fout);  
  
int i;  
while((i=fin.read())!=-1){  
out.write((byte)i);  
out.flush();  
}  
  
fin.close();  
out.close();  
  
}catch(Exception e){System.out.println(e);}  

imagem=imagemComprimida;

 }

 
 public void descomprimeImagem(File imagemComprimida, String pathArquivo){
 File imagemDescomprimida=new File(pathArquivo);
 try{  
FileInputStream fin=new FileInputStream(imagemComprimida);  
InflaterInputStream in=new InflaterInputStream(fin);  
  
FileOutputStream fout=new FileOutputStream(imagemDescomprimida);  
  
int i;  
while((i=in.read())!=-1){  
fout.write((byte)i);  
fout.flush();  
}  
  
fin.close();  
fout.close();  
in.close();  

}catch(Exception e){System.out.println(e);} 
 
 imagem=imagemDescomprimida;
 }
 
 public void atualizaImageFile(){
  try {
    RenderedImage rendered = null; 
    Image novaImagem = thumbnail.getImage();
    if (novaImagem instanceof RenderedImage){  
    rendered = (RenderedImage)novaImagem;
    } 
    else{  
     BufferedImage buffered = new BufferedImage(  
        thumbnail.getIconWidth(),  
        thumbnail.getIconHeight(),  
        BufferedImage.TYPE_INT_RGB  
     );  
     Graphics2D g = buffered.createGraphics();  
     g.drawImage(novaImagem, 0, 0, null);  
     g.dispose();  
     rendered = buffered;  
    }
    ImageIO.write(rendered, "JPEG", imagem);
  } 
  catch (IOException e){

  }
 
 
 }
 
}