/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesTeste.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Danilo
 */
public class ManipuladorArquivos {
  
  public String diretorioExecucao=System.getProperty("user.dir")+File.separator;
  
  public void extrairRecursoJar(String caminhoOrigemArquivo, String nomeArquivo){
  extrairRecursoJar(caminhoOrigemArquivo, nomeArquivo, "");
  }
   
  public void extrairRecursoJar(String caminhoOrigemArquivo, String nomeArquivo, String caminhoDestinoArquivo){
    FileOutputStream saidaRecurso = null;
    try {
      InputStream recurso = ManipuladorArquivos.class.getResourceAsStream(caminhoOrigemArquivo+nomeArquivo);
      //caminhoDestinoArquivo.replace("\", """)
      File arquivo = new File(diretorioExecucao+caminhoDestinoArquivo+nomeArquivo);
      saidaRecurso = new FileOutputStream(arquivo);
      
      byte[] bytes = new byte[2048];
      int read = recurso.read(bytes);
      while(read != -1) {
        saidaRecurso.write(bytes, 0, read);
        read = recurso.read(bytes);
      }
      if(saidaRecurso !=null){
      saidaRecurso.close();
      }
    }
    catch (IOException e){
      JOptionPane.showMessageDialog(null,"Problema ao capturar arquivo na tela de load, " + e.getMessage(),"[ERRO AO GERAR ARQUIVO]",0);
      System.exit(0);
    }
  }
  
  public boolean verificaDiretorioExiste(String caminhoDiretorio){
    File f = new File(diretorioExecucao+caminhoDiretorio);
    if(f.exists() && f.isDirectory()){
      return true;
    }
    return false;
  }
  
  public boolean verificaArquivoExiste(String arquivo){
    File f = new File(diretorioExecucao+arquivo);
    if(f.exists() && !f.isDirectory()){
      return true;
    }
    return false;
  }
  
  public void criarDiretorio(String diretorio){
    File f = new File(diretorioExecucao+diretorio);
    boolean sucesso = f.mkdir();
    if (sucesso){
      System.out.println("Diretorio: "+ diretorio + " foi criado");
    }
    else{System.out.println("Falha ao criar diretorio: "+ diretorio);}
  }
  
 public void excluirArquivo(String arquivo){
    File f = new File(diretorioExecucao+arquivo);
     while(f.exists()){
       f.delete();
       //System.out.println("deletando arquivo...");
       try {
        Thread.sleep(60);
       }catch (InterruptedException ex) {
          Logger.getLogger(ManipuladorArquivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 }
 
 public void excluirArquivo(File arquivo){
    while(arquivo.exists()){
        arquivo.delete();
        //System.out.println("deletando arquivo...");
        try {
          Thread.sleep(60);
        }catch (InterruptedException ex) {
          Logger.getLogger(ManipuladorArquivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 }
 
 public boolean verificarEexcluirArquivo(String arquivo) throws IOException{
   File f = new File(diretorioExecucao+arquivo);
   boolean result =Files.deleteIfExists(f.toPath());
   return result;
 }
 
 public void abrirArquivo(String caminhoArquivo){
  Process pro;
  ProcessBuilder p = new ProcessBuilder();
   File arquivo = new File(caminhoArquivo);
   try
  {
      Desktop.getDesktop().open(arquivo);
 }
  catch (IOException e){e.printStackTrace();}
 // catch (InterruptedException e){e.printStackTrace();}

 
 }
  
}
