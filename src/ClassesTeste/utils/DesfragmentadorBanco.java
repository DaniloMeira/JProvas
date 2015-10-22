package ClassesTeste.utils;
import ClassesTeste.ConectaBanco;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class DesfragmentadorBanco {

boolean sistemaWindows=false;
public ConectaBanco banco1;
File arquivoMDB;
File bancoDesfragmentado;
int tamanhoArquivoMDBatual=0;
ResultSet resultSet1;
ManipuladorArquivos mp = new ManipuladorArquivos();
private final String diretorioExecucao=System.getProperty("user.dir")+File.separator;
public boolean exibeMsgFinal;

  


public DesfragmentadorBanco(){
    String OS = System.getProperty("os.name");
    if(OS.startsWith("Windows")){sistemaWindows=true;}
    /*System.out.println("Sistema: "+OS);
     System.out.println("Diretorio de execucao: "+diretorioExecucao);
    System.out.println("sistema operacional windows? :"+sistemaWindows);*/
  arquivoMDB  = new File(diretorioExecucao+"DATABASE"+File.separator+"BancoJProvas.mdb");
  bancoDesfragmentado = new File(diretorioExecucao+"DATABASE"+File.separator+"BancoVazio.mdb");
  tamanhoArquivoMDBatual= (int) arquivoMDB.length();arquivoMDB  = new File(diretorioExecucao+"DATABASE"+File.separator+"BancoJProvas.mdb");
  bancoDesfragmentado = new File(diretorioExecucao+"DATABASE"+File.separator+"BancoVazio.mdb");
}
        

public void verificaFragmentacaoMDB(){
  int deletesRealizados=0;
  int tamanhoArquivoMDB=0;
 // int tamanhoArquivoMDBatual=0;

    
  try{
    resultSet1= banco1.executaSQLcomRetorno("SELECT valor_parametro from TB08_SISTEMA WHERE nu_parametro=2");
  if(resultSet1.next()==true){
    deletesRealizados= Integer.parseInt(resultSet1.getString("valor_parametro"));
    System.out.println("Deletes realizados: "+deletesRealizados);
  }
    resultSet1= banco1.executaSQLcomRetorno("SELECT valor_parametro from TB08_SISTEMA WHERE nu_parametro=1");
    if(resultSet1.next()==true){tamanhoArquivoMDB= Integer.parseInt(resultSet1.getString("valor_parametro"));}
  }
  catch(SQLException e){
    JOptionPane.showMessageDialog(null,"Erro na funcao verificaFragmentacaoMDB da classe Sistema : " + e.getMessage(),"Erro",0);
  }
  System.out.println("mdbAtual:"+tamanhoArquivoMDBatual);
  System.out.println("mdbAnterior:"+tamanhoArquivoMDB);
  System.out.println("Percentual de fragmentacao: "+((tamanhoArquivoMDBatual*100)/tamanhoArquivoMDB));

  if(deletesRealizados>12 | ((tamanhoArquivoMDBatual*100)/tamanhoArquivoMDB)> 115){
    if(!sistemaWindows | tamanhoArquivoMDBatual>9000000){
        int reply = JOptionPane.showConfirmDialog(null, "Eh necessario desfragmentar o arquivo de dados. "
                +"Isto pode levar\n alguns minutos, dependendo do tamanho do arquivo atual.\n\n"
                +"Deseja realizar a desfragmentacao do arquivo agora?(Recomendado)", "DESFRAGMENTAR ARQUIVO DE DADOS", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.NO_OPTION) {return;}
        exibeMsgFinal=true;
    }
    desfragmentaMDB();
  }

}



public void desfragmentaMDB(){

   try {
    if(bancoDesfragmentado.exists()){bancoDesfragmentado.delete();}
    
    if(sistemaWindows){
        banco1.desconectaBanco();
        if(!mp.verificaArquivoExiste("DATABASE/JETCOMP.exe")){
            mp.extrairRecursoJar("/Recursos/","JETCOMP.exe","DATABASE/");
            System.out.println("Arquivo 'JETCOMP.exe' criado");
        }

        ProcessBuilder builder = new ProcessBuilder(
          "cmd.exe", "/c", "cd \""+diretorioExecucao+"\\DATABASE\" && JETCOMP.exe -src:BancoJProvas.mdb -dest:BancoVazio.mdb");
        builder.start();
        while(!bancoDesfragmentado.exists()){System.out.println("gerando novo md desfragmentado");Thread.sleep(60);}
    }
    else{ 
        System.out.println("entrou no else mdb");
        if(!mp.verificaArquivoExiste("/DATABASE/BancoVazio.mdb")){mp.extrairRecursoJar("/Recursos/","BancoVazio.mdb","/DATABASE/");}
        replicarBancoMDB(); 
    }
    
    //DELETAR O ARQUIVO MDB ANTERIOR E RENOMEAR O NOVO ARQUIVO MDB DESFRAGMENTADO.
    System.out.println("Novo Arquivo "+bancoDesfragmentado.getPath());
    System.out.println("Novo Arquivo: "+bancoDesfragmentado.exists());
    while(!arquivoMDB.canWrite()){System.out.println("arquivo mdb indisponivel");Thread.sleep(60);}
    while(arquivoMDB.exists()){arquivoMDB.delete();System.out.println("deletando mdb anterior...");Thread.sleep(60);}
    while(!bancoDesfragmentado.renameTo(arquivoMDB)){System.out.println("renomeando mdb desfragmentado...");Thread.sleep(60);}; 
    banco1.conectarBanco();
    banco1.executaSQLsemRetorno("UPDATE TB08_SISTEMA SET valor_parametro="+arquivoMDB.length()+" WHERE nu_parametro=1");
    banco1.executaSQLsemRetorno("UPDATE TB08_SISTEMA SET valor_parametro=0 WHERE nu_parametro=2");
    System.out.println("Desfragmentacao concluida");
    if(exibeMsgFinal==true){JOptionPane.showMessageDialog(null,"O processo de desfragmentacao do arquivo de dados foi concluido.","DESFRAGMENTACAO CONCLUIDA",0);}
  }//fim do try
   
  catch (IOException | InterruptedException e) {
    System.out.println(e.getMessage());
  }

} 
 
public void replicarBancoMDB(){
  
  ResultSet result1;
  ConectaBanco banco2 = new ConectaBanco("BancoVazio.mdb");
  banco2.conectarBanco();
  int campo_id, campo_numerico1, campo_numerico2;
  String campo_texto1,campo_texto2,campo_texto3,campo_texto4,campo_texto5;
     
  //Copiar tabela TB01
  result1=banco1.executaSQLcomRetorno("SELECT * FROM TB01_QUESTOES ORDER BY id_questao");
  
  try {
    while(result1.next()){
      campo_id=result1.getInt("id_questao");
      campo_numerico1=result1.getInt("linhas_resp_dissertativa");
      campo_numerico2=result1.getInt("nu_categoria_tb04");
      campo_texto1=result1.getString("no_questao");
      campo_texto2=result1.getString("texto_questao");
      campo_texto3=result1.getString("tipo_questao");
      campo_texto4=result1.getString("resposta_questao");
      campo_texto5=result1.getString("no_arquivo_img");
      
      banco2.executaSQLsemRetorno("INSERT INTO TB01_QUESTOES (id_questao,no_questao,texto_questao,tipo_questao,resposta_questao,linhas_resp_dissertativa,nu_categoria_tb04,no_arquivo_img) VALUES ("+campo_id+",'"+campo_texto1+
              "','"+campo_texto2+"','"+campo_texto3+"','"+campo_texto4+"',"+campo_numerico1+","+campo_numerico2+",'"+campo_texto5+"')");
    
    }
  } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null,"Erro ao copiar tabela TB01 na função replicarBancoMDB da classe Sistema: " + ex.getMessage(),"Erro",0);
  }
  System.out.println("Tabela TB01_QUESTOES copiada para o novo arquivo mdb desfragmentado");
  
    //Inserir imagens BLO na tabela TB01_QUESTOES
  try{
   result1=banco1.executaSQLcomRetorno("SELECT id_questao, blob_img FROM TB01_QUESTOES WHERE blob_img IS NOT NULL"); 
    while(result1.next()){
      campo_id=result1.getInt("id_questao");
      Blob blob = result1.getBlob("blob_img");
      banco2.executaSQLInsertImagem("UPDATE TB01_QUESTOES SET blob_img=? WHERE id_questao="+campo_id, blob);
    
    }
  }
  catch (SQLException ex) {
   JOptionPane.showMessageDialog(null,"Falha ao incluir imagem BLOB na tabela TB01_QUESTOES :" + ex.getMessage(),"Erro",0);
  }
  System.out.println("Copiado arquivos Blob da TB01_QUESTOES para o novo arquivo mdb desfragmentado");
  
  //Copiar tabela TB02
  result1=banco1.executaSQLcomRetorno("SELECT * FROM TB02_ALTERNATIVAS ORDER BY id_questao_tb01");
  try {
    while(result1.next()){
      campo_id=result1.getInt("id_questao_tb01");
      campo_texto1=result1.getString("letra_alternativa");
      campo_texto2=result1.getString("texto_alternativa");
      
      banco2.executaSQLsemRetorno("INSERT INTO TB02_ALTERNATIVAS (id_questao_tb01,letra_alternativa,texto_alternativa) VALUES ("+campo_id+",'"+campo_texto1+
              "','"+campo_texto2+"')");
    
    }
  } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null,"Erro ao copiar tabela TB02 na função replicarBancoMDB da classe Sistema: " + ex.getMessage(),"Erro",0);
  }  
  System.out.println("Tabela TB02_ALTERNATIVAS copiada para o novo arquivo mdb desfragmentado");
  
  //Copiar tabela TB04
  result1=banco1.executaSQLcomRetorno("SELECT * FROM TB04_CATEGORIAS ORDER BY nu_categoria");

  try {
    while(result1.next()){
      campo_id=result1.getInt("nu_categoria");
      campo_texto1=result1.getString("nu_supracategoria");
      campo_texto2=result1.getString("no_categoria");
      
      banco2.executaSQLsemRetorno("INSERT INTO TB04_CATEGORIAS (nu_categoria,nu_supracategoria,no_categoria) VALUES ("+campo_id+",'"+campo_texto1+
              "','"+campo_texto2+"')");
    
    }
  } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null,"Erro ao copiar tabela TB04 na função replicarBancoMDB da classe Sistema: " + ex.getMessage(),"Erro",0);
  }
  System.out.println("Tabela TB04_CATEGORIAS copiada para o novo arquivo mdb desfragmentado");
    
  //Copiar tabela TB03
  result1=banco1.executaSQLcomRetorno("SELECT * FROM TB03_PROVAS ORDER BY nu_prova");

  try {
    while(result1.next()){
      campo_id=result1.getInt("nu_prova");
      campo_numerico1=result1.getInt("nu_categoria_tb04");
      campo_numerico2=result1.getInt("nu_total_itens_lista");
      campo_texto1=result1.getString("no_prova");
      campo_texto2=result1.getString("no_arquivo_img");
      
      banco2.executaSQLsemRetorno("INSERT INTO TB03_PROVAS (nu_prova,no_prova,nu_categoria_tb04,nu_total_itens_lista,no_arquivo_img,blob_img) VALUES ("+campo_id+",'"+campo_texto1+
              "',"+campo_numerico1+","+campo_numerico2+",'"+campo_texto2+"',null)");
    }
  } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null,"Erro ao copiar tabela TB03 na função replicarBancoMDB da classe Sistema: " + ex.getMessage(),"Erro",0);
  }
  System.out.println("Tabela TB03_PROVAS copiada para o novo arquivo mdb desfragmentado");
  
  //Inserir imagens BLO na tabela TB03_Provas
  try{
   result1=banco1.executaSQLcomRetorno("SELECT nu_prova, blob_img FROM TB03_PROVAS WHERE blob_img IS NOT NULL"); 
    while(result1.next()){
      campo_id=result1.getInt("nu_prova");
      Blob blob = result1.getBlob("blob_img");
      banco2.executaSQLInsertImagem("UPDATE TB03_PROVAS SET blob_img=? WHERE nu_prova="+campo_id, blob);
    
    }
  }
  catch (SQLException ex) {
   JOptionPane.showMessageDialog(null,"Falha ao incluir imagem BLOB na tabela TB03_PROVAS :" + ex.getMessage(),"Erro",0);
  }
  System.out.println("Copiado arquivos Blob da TB03_PROVAS para o novo arquivo mdb desfragmentado");
    
  //Copiar tabela TB05
  result1=banco1.executaSQLcomRetorno("SELECT * FROM TB05_QUESTOES_PROVA ORDER BY nu_prova_tb03");

  try {
    while(result1.next()){
      campo_id=result1.getInt("nu_prova_tb03");
      campo_numerico1=result1.getInt("nu_posicao_questao");
      campo_numerico2=result1.getInt("id_questao_tb01");
      
      banco2.executaSQLsemRetorno("INSERT INTO TB05_QUESTOES_PROVA (nu_prova_tb03,nu_posicao_questao,id_questao_tb01) VALUES ("+campo_id+","+campo_numerico1+","+
              campo_numerico2+")");
    
    }
  } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null,"Erro ao copiar tabela TB05 na função replicarBancoMDB da classe Sistema: " + ex.getMessage(),"Erro",0);
  }
  System.out.println("Tabela TB05_QUESTOES_PROVA copiada para o novo arquivo mdb desfragmentado");
    
   //Copiar tabela TB06
  result1=banco1.executaSQLcomRetorno("SELECT * FROM TB06_TAGS_PROVA ORDER BY nu_prova_tb03");

  try {
    while(result1.next()){
      campo_id=result1.getInt("nu_prova_tb03");
      campo_numerico1=result1.getInt("nu_posicao_tag");
      campo_numerico2=result1.getInt("tipo_tag");
      campo_texto1=result1.getString("descricao_tag");
      
      banco2.executaSQLsemRetorno("INSERT INTO TB06_TAGS_PROVA (nu_prova_tb03,nu_posicao_tag,tipo_tag,descricao_tag) VALUES ("+campo_id+
              ","+campo_numerico1+","+campo_numerico2+",'"+campo_texto1+"')");
    
    }
  } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null,"Erro ao copiar tabela TB06 na função replicarBancoMDB da classe Sistema: " + ex.getMessage(),"Erro",0);
  }
  System.out.println("Tabela TB06_TAGS_PROVA copiada para o novo arquivo mdb desfragmentado");
    
   //Copiar tabela TB07
  result1=banco1.executaSQLcomRetorno("SELECT * FROM TB07_INFORMACOES_PROVA ORDER BY nu_prova_tb03");

  try {
    while(result1.next()){
      campo_id=result1.getInt("nu_prova_tb03");
      campo_numerico1=result1.getInt("tipo_informacao");
      campo_texto1=result1.getString("texto_informacao");
      
      banco2.executaSQLsemRetorno("INSERT INTO TB07_INFORMACOES_PROVA (nu_prova_tb03,tipo_informacao,texto_informacao) VALUES ("+campo_id+","+
              campo_numerico1+",'"+campo_texto1+"')");
    
    }
  } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null,"Erro ao copiar tabela TB07 na função replicarBancoMDB da classe Sistema: " + ex.getMessage(),"Erro",0);
  }
  System.out.println("Tabela TB07_INFORMACOES_PROVA copiada para o novo arquivo mdb desfragmentado");
    
  //Copiar tabela TB08
  result1=banco1.executaSQLcomRetorno("SELECT * FROM TB08_SISTEMA ORDER BY nu_parametro");
  try {
    while(result1.next()){
      campo_id=result1.getInt("nu_parametro");
      campo_texto1=result1.getString("desc_parametro");
      campo_texto2=result1.getString("valor_parametro");
      
      banco2.executaSQLsemRetorno("INSERT INTO TB08_SISTEMA (nu_parametro,desc_parametro,valor_parametro) VALUES ("+campo_id+",'"+
              campo_texto1+"','"+campo_texto2+"')");
    
    }
  } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null,"Erro ao copiar tabela TB08 na função replicarBancoMDB da classe Sistema: " + ex.getMessage(),"Erro",0);
  }
  System.out.println("Tabela TB08_INFORMACOES_PROVA copiada para o novo arquivo mdb desfragmentado");
  
  banco1.desconectaBanco();
  banco2.desconectaBanco();
  
}

}
