package ClassesTeste;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.ucanaccess.jdbc.UcanaccessConnection;
import net.ucanaccess.jdbc.UcanaccessDriver;
	
	
public class ConectaBanco{
  
  private Connection conexao;
  private String nomeBanco = "BancoJProvas.mdb";
  private String diretorioExecucao=System.getProperty("user.dir")+File.separator+"DATABASE"+File.separator;
        
  public ConectaBanco(){
    conectarBanco();
  }
  
  public ConectaBanco(String bancoName){
    nomeBanco = bancoName;
    conectarBanco();
  }

  public void conectarBanco(){
    try{
      /* Busca Classes do Driver */
      Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
    }
    catch (ClassNotFoundException e){
      JOptionPane.showMessageDialog(null,"Classes do driver não encontradas, " + e.getMessage(),"[ERRO AO CONECTAR NO BANCO]",0);
      System.exit(0);
    }
	
    try{
      String urlBanco= UcanaccessDriver.URL_PREFIX+diretorioExecucao+nomeBanco+";newDatabaseVersion=V2003";
      //JOptionPane.showMessageDialog(null,"URL DO BANCO DE DADOS:"+urlBanco);
      this.conexao= DriverManager.getConnection(urlBanco, "sa", "");
    }
    catch (SQLException e){
      JOptionPane.showMessageDialog(null,"Caminho/nome do banco está errado ou arquivo de banco não existe, " + e.getMessage(),"[ERRO AO CONECTAR NO BANCO]",0);
      System.exit(0);
    }
  }
          
          
  public void executaSQLsemRetorno(String comandoX){
    try{
      Statement st =this.conexao.createStatement();
      st.execute(comandoX);
      st.execute("SET FILES DEFRAG 100");
      if (st != null)st.close();      
    }
    catch (SQLException e){
      JOptionPane.showMessageDialog(null,"Erro no comando executaSQLsemRetorno da classe ConectaBanco: \n" + e.getMessage(),"ERRO DE SQL",0);
    }
  }
  
  public void executaSQLInsertImagem(String comandoX, File arquivoImg){
    try{
    PreparedStatement psmnt = this.conexao.prepareStatement(comandoX);
    FileInputStream fis = new FileInputStream(arquivoImg);
    psmnt.setBinaryStream(1, (InputStream)fis, (int)(arquivoImg.length()));
    psmnt.executeUpdate();
    }
   
    catch (SQLException e){
      JOptionPane.showMessageDialog(null,"Erro no comando executaSQLInsertImagem da classe ConectaBanco: \n" + e.getMessage(),"ERRO DE SQL",0);
    } catch (FileNotFoundException ex) {
      Logger.getLogger(ConectaBanco.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
	
   public void executaSQLInsertImagem(String comandoX, Blob arquivoImg){
    try{
     PreparedStatement psmnt = this.conexao.prepareStatement(comandoX);
     psmnt.setBlob(1, arquivoImg);
     psmnt.executeUpdate();
    }
    catch (SQLException e){
      JOptionPane.showMessageDialog(null,"Erro no comando executaSQLInsertImagem(comandoX,Blob) da classe ConectaBanco: \n" + e.getMessage(),"ERRO DE SQL",0);
    }
   }
  
  public ResultSet executaSQLcomRetorno(String retornoY){
    Statement st = null;
    ResultSet rs = null;
    try {
      st =this.conexao.createStatement();
      rs=st.executeQuery(retornoY);
      if (st != null)st.close();
    }
    catch (SQLException e) {
      JOptionPane.showMessageDialog(null,"Erro no comando executaSQLcomRetorno da classe ConectaBanco: \n" + e.getMessage(),"ERRO DE SQL",0);
    }	  
    finally {
      return rs;
    }
  }

  public void desconectaBanco(){
    try {
     //http://sourceforge.net/p/ucanaccess/discussion/help/thread/4539a56c/
      ((UcanaccessConnection) conexao).unloadDB();
    } catch (SQLException ex) {
      Logger.getLogger(ConectaBanco.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
//fim da classe	
}
