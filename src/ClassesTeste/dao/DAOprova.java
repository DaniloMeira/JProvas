package ClassesTeste.dao;

import ClassesTeste.ConectaBanco;
import ClassesTeste.utils.ManipuladorArquivos;
import Telas.SubtelaEditorProva;
import Telas.SubtelaPainelArvore;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author Danilo Meira
 */

public class DAOprova {

  ManipuladorArquivos mpArquivos= new ManipuladorArquivos();
  ConectaBanco bancoConnection;// = new ConectaBanco();
  ResultSet retornoSQL;
  SubtelaPainelArvore sbtPainelArvore;   
  public SubtelaEditorProva editorProva;
  String diretorioImagens=System.getProperty("user.dir")+"\\src\\Imagens\\Imagens_Provas\\";
  
  public DAOprova(){}
  
  public DAOprova(SubtelaPainelArvore sbtParvore, ConectaBanco connection){
    sbtPainelArvore=sbtParvore;
    bancoConnection=connection;
  }
   
  public void abrirTelaProva(String numeroProva) throws FileNotFoundException, IOException{
    editorProva = new SubtelaEditorProva(this);
    editorProva.TelaJProvas_sbtEditorProvas=sbtPainelArvore.jprovas_telasbtPainelArvore; 
    editorProva.daoQuestao=sbtPainelArvore.daoQuest_sbtParvore;
    try{
      String nomeProva,numProva,campo1,campo2,campo3,campo4;
      String imagemQuestao=null;
      // conectTabelProva;// = new ConectaBanco();
      numProva = numeroProva;
      //retornoSQL = conectTabelProva.executaSQLcomRetorno("select * from TB03_PROVAS where nu_prova ="+numProva);
      retornoSQL = bancoConnection.executaSQLcomRetorno("select nu_prova,no_prova,nu_total_itens_lista,no_arquivo_img from TB03_PROVAS where nu_prova ="+numProva);
      retornoSQL.next();
      nomeProva = retornoSQL.getString("no_prova");
      editorProva.nuItensLinha= retornoSQL.getInt("nu_total_itens_lista");
      
      //carregar imagem da questao, se existir
	  retornoSQL= bancoConnection.executaSQLcomRetorno("SELECT no_arquivo_img FROM TB03_PROVAS where nu_prova="+numProva+" AND no_arquivo_img is not null");
      if(retornoSQL.next()==true){
		if(!mpArquivos.verificaDiretorioExiste("HTML")){mpArquivos.criarDiretorio("HTML");System.out.println("Diretorio 'HTML' criado");}
		if(!mpArquivos.verificaDiretorioExiste("HTML/Imagens")){mpArquivos.criarDiretorio("HTML/Imagens");System.out.println("Diretorio 'HTML/Imagens' criado");}
		if(!mpArquivos.verificaDiretorioExiste("HTML/Imagens/Imagens_Provas")){mpArquivos.criarDiretorio("HTML/Imagens/Imagens_Provas");System.out.println("Diretorio 'HTML/Imagens/Imagens_Provas' criado");}
        imagemQuestao = retornoSQL.getString("no_arquivo_img");        
        editorProva.imagemProva=imagemQuestao;
        carregarImagemBlob(numProva);
        editorProva.exibirImagem();
      }
	  else {
		if(!mpArquivos.verificaArquivoExiste("/HTML/Imagens/Imagens_Provas/LogotipoJProvas.gif")){mpArquivos.extrairRecursoJar("/Imagens/IconesSistema/","LogotipoJProvas.gif","/HTML/Imagens/Imagens_Provas/");System.out.println("Arquivo '/HTML/Imagens/Imagens_Provas/LogotipoJProvas.gif' criado");}
	  }
      
      //carregar campos de texto da prova
      campo1 = carregarCamposTexto(numProva,1);
      if(campo1!=null){editorProva.textF_tituloInstituicaoEnsino.setText(campo1);}
      else editorProva.textF_tituloInstituicaoEnsino.setText(consultaParametroSistema(3));
      campo2 = carregarCamposTexto(numProva,2);
      if(campo2!=null){editorProva.textF_IdentificacoAluno.setText(campo2);} 
      else editorProva.textF_IdentificacoAluno.setText(consultaParametroSistema(4));
      campo3 = carregarCamposTexto(numProva,3);
      if(campo3!=null){editorProva.textF_RodapeDescricaoProva.setText(campo3);} 
      else editorProva.textF_RodapeDescricaoProva.setText(consultaParametroSistema(5));
      campo4 = carregarCamposTexto(numProva,4);
      if(campo4!=null){editorProva.textF_RodapeIdentificaoProva.setText(campo4);} 
      else editorProva.textF_RodapeIdentificaoProva.setText(consultaParametroSistema(6));
    
      //Abrir tela de edição da Prova:
      editorProva.arvoreEditorProva=sbtPainelArvore.instanciaClasseArvore.tree;
      editorProva.labelTituloProva.setText(nomeProva);
      editorProva.numeroProva = numProva;
      editorProva.carregarListaItens();
      sbtPainelArvore.sbtEditorProva=editorProva;
      }
	
    catch(SQLException e){
      editorProva.labelTituloProva.setText("ERRO - ITEM DO BANCO DE DADOS ESTÁ CORROMPIDO, Nº Prova= "+numeroProva);
      sbtPainelArvore.sbtEditorProva=editorProva;
      JOptionPane.showMessageDialog(null,"Erro na funcao abrirTelaProva da classe DAOprova : " + e.getMessage(),"Erro",0);
    }

 }
 
 
  public void preencheArrayQuestao(int indiceLinha, int indiceArrayQuestao){
    Object objetoLinha = editorProva.modeloLista.getElementAt(indiceLinha);
    String numQuest="0";
    for(int z=0;z<editorProva.arrayObjetoLinha.length;z++){
      if(objetoLinha== editorProva.arrayObjetoLinha[z]){
        //System.out.println("DAOProva metodo preencheArrayQuestao, achou!");
        numQuest= editorProva.numQuestoesEditProv[z];
        break;
      }
    }

    try{
      retornoSQL= bancoConnection.executaSQLcomRetorno("SELECT* FROM TB01_QUESTOES where id_questao ="+numQuest);
      retornoSQL.next();
      editorProva.questoesEditorProva[indiceArrayQuestao][0] = retornoSQL.getString("texto_questao");
      editorProva.tipoQuestaoEditPrv[indiceArrayQuestao][0]= retornoSQL.getString("tipo_questao");
      retornoSQL.close();
      //System.out.println("O texto da questão é "+editorProva.questoesEditorProva[indiceArrayQuestao][0]);
      //System.out.println("O TIPO DA QUESTAO É: "+editorProva.tipoQuestaoEditPrv[indiceArrayQuestao][0]);
 
      if(editorProva.tipoQuestaoEditPrv[indiceArrayQuestao][0].compareTo("alt")==0){
        retornoSQL= bancoConnection.executaSQLcomRetorno("SELECT * FROM TB02_ALTERNATIVAS where letra_alternativa='a' and id_questao_tb01="+numQuest);
        retornoSQL.next();
        editorProva.questoesEditorProva[indiceArrayQuestao][1]=retornoSQL.getString("texto_alternativa");
        retornoSQL.close();

        retornoSQL= bancoConnection.executaSQLcomRetorno("SELECT * FROM TB02_ALTERNATIVAS where letra_alternativa='b' and id_questao_tb01="+numQuest);
        retornoSQL.next();
        editorProva.questoesEditorProva[indiceArrayQuestao][2]=retornoSQL.getString("texto_alternativa");
        retornoSQL.close();

        retornoSQL= bancoConnection.executaSQLcomRetorno("SELECT * FROM TB02_ALTERNATIVAS where letra_alternativa='c' and id_questao_tb01="+numQuest);
        retornoSQL.next();
        editorProva.questoesEditorProva[indiceArrayQuestao][3]=retornoSQL.getString("texto_alternativa");
        retornoSQL.close();

        retornoSQL= bancoConnection.executaSQLcomRetorno("SELECT * FROM TB02_ALTERNATIVAS where letra_alternativa='d' and id_questao_tb01="+numQuest);
        retornoSQL.next();
        editorProva.questoesEditorProva[indiceArrayQuestao][4]=retornoSQL.getString("texto_alternativa");
        retornoSQL.close();

        retornoSQL= bancoConnection.executaSQLcomRetorno("SELECT * FROM TB02_ALTERNATIVAS where letra_alternativa='e' and id_questao_tb01="+numQuest);
        retornoSQL.next();
        editorProva.questoesEditorProva[indiceArrayQuestao][5]=retornoSQL.getString("texto_alternativa");
        retornoSQL.close();
      }
      else if(editorProva.tipoQuestaoEditPrv[indiceArrayQuestao][0].compareTo("dis")==0){
        retornoSQL= bancoConnection.executaSQLcomRetorno("SELECT linhas_resp_dissertativa FROM TB01_QUESTOES where id_questao ="+numQuest);
        retornoSQL.next();
        editorProva.tipoQuestaoEditPrv[indiceArrayQuestao][1]=retornoSQL.getString("linhas_resp_dissertativa");
        //System.out.println("NUMERO DE LINHAS QUESTAO DISSERTATIVA: "+editorProva.tipoQuestaoEditPrv[indiceArrayQuestao][1]);
        retornoSQL.close();
      }
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,"Erro na funcao preencherArrayQuestao : " + e.getMessage(),"Erro",0);
    }
 
}
 
  public String carregarCamposTexto(String numProva, int tipoInfo){
    try{
      retornoSQL= bancoConnection.executaSQLcomRetorno("SELECT * FROM TB07_INFORMACOES_PROVA WHERE nu_prova_tb03="+numProva+" AND tipo_informacao="+tipoInfo);
      if(retornoSQL.next()==true){
        return retornoSQL.getString("texto_informacao");
      }
      else return null;
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,"Erro na funcao carregarCamposTexto : " + e.getMessage(),"Erro",0);
    }
    return null;
  }
 
 
 
 public void preencheArrayFigura(int indiceLinha, int indiceArrayQuestao) throws IOException{
  
  Object objetoLinha = editorProva.modeloLista.getElementAt(indiceLinha);
  String numQuest="0";
  
  for(int z=0;z<editorProva.arrayObjetoLinha.length;z++){
    if(objetoLinha== editorProva.arrayObjetoLinha[z]){
      //System.out.println("achou a questão no metodo preenche array figura");
      numQuest= editorProva.numQuestoesEditProv[z];
      break;
    }
   }   
  try{   
  retornoSQL= bancoConnection.executaSQLcomRetorno("SELECT no_arquivo_img FROM TB01_QUESTOES where id_questao="+numQuest+" AND no_arquivo_img IS NOT NULL");
  if(retornoSQL.next()==true){
    editorProva.figurasQuestoesEditPrv[indiceArrayQuestao][0]=retornoSQL.getString("no_arquivo_img");
    //System.out.println("figura do array["+indiceArrayQuestao+"][0]="+editorProva.figurasQuestoesEditPrv[indiceArrayQuestao][0]);
    //carregar imagens a partir do arquivo de banco de dados;
    editorProva.daoQuestao.carregarImagemBlob(numQuest);
  }
 }
 catch(SQLException e){
     JOptionPane.showMessageDialog(null,"Erro na funcao preencherArrayFiguras : " + e.getMessage(),"Erro",0);
 } 
}
 
 public void removerImagemBanco(String codProva){
     retornoSQL=bancoConnection.executaSQLcomRetorno("SELECT no_arquivo_img FROM TB03_PROVAS WHERE nu_prova="+codProva+" AND no_arquivo_img is not null");
   try{
     if(retornoSQL.next()==true){
      String imagemProva=retornoSQL.getString("no_arquivo_img");
      bancoConnection.executaSQLsemRetorno("UPDATE TB03_PROVAS SET no_arquivo_img=NULL, blob_img=NULL WHERE nu_prova="+codProva);
      atualizaContagemDeletes();
      try
      {
       Process pro = Runtime.getRuntime().exec("cmd.exe /c del "+diretorioImagens+imagemProva);
       System.out.println("imagem deletada: "+diretorioImagens+imagemProva);
      }
      catch (IOException e){e.printStackTrace();
       // catch (InterruptedException e){e.printStackTrace();}
       System.out.println("imagem a deletar não foi encontrada");
      }
     }
   }
   catch (SQLException e){
     JOptionPane.showMessageDialog(null,"FALHA NO COMANDO SQL removerImagemBanco classe DAOprova: " + e.getMessage(),"Erro",0);
   }
}
 
public void atualizaContagemDeletes(){
  retornoSQL=bancoConnection.executaSQLcomRetorno("SELECT valor_parametro from tb08_sistema WHERE nu_parametro=2");
      try {
          if(retornoSQL.next()==true){
            Integer numDeletes=retornoSQL.getInt("valor_parametro");
            bancoConnection.executaSQLsemRetorno("UPDATE TB08_SISTEMA set valor_parametro="+(numDeletes+1)+" WHERE nu_parametro=2");
          }
      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(null,"FALHA NO COMANDO SQL atualizaContagemDeletes da classe DAOprova: " + ex.getMessage(),"Erro",0);
      }

} 
 
 
 public void inserirImagemBanco(String codProva, String imagemProva){
     bancoConnection.executaSQLsemRetorno("UPDATE TB03_PROVAS SET no_arquivo_img='"+imagemProva+"' WHERE nu_prova="+codProva);
 }
 
 public void inserirImagemBlob(String codProva, File arquivoImg){
   bancoConnection.executaSQLInsertImagem("UPDATE TB03_PROVAS SET blob_img=? WHERE nu_prova="+codProva,arquivoImg);
 }

 public void carregarImagemBlob(String codProva) throws FileNotFoundException, IOException{
 retornoSQL=bancoConnection.executaSQLcomRetorno("SELECT blob_img FROM TB03_PROVAS WHERE nu_prova="+codProva);
 File imagemBlob = new File(editorProva.diretorioImagens+"blob.file" /*editorProva.imagemProva*/);     
  try{
      if (retornoSQL.next()) {
        Blob blob = retornoSQL.getBlob("blob_img");
        byte[] bytearray = blob.getBytes(1L, (int)blob.length());
        FileOutputStream fos = new FileOutputStream(imagemBlob);
        fos.write(bytearray);
        fos.close();
        
       editorProva.mp.descomprimeImagem(imagemBlob, editorProva.diretorioImagens+editorProva.imagemProva );
       mpArquivos.excluirArquivo(imagemBlob);
  }
 }
    catch (SQLException e){
       e.printStackTrace();
       System.out.println("FALHA NO COMANDO SQL removerImagemBanco classe DAOprova");}
 }
 
 
 public void salvarArrayDivisorias(int posicao,String textoDivisoria, String codProva){
     bancoConnection.executaSQLsemRetorno("INSERT INTO TB06_TAGS_PROVA(nu_prova_tb03,nu_posicao_tag, tipo_tag, descricao_tag) VALUES ("+codProva+","+posicao+",',5,"+textoDivisoria+"')");
 }
 
  public void salvarArrayTags(int posicao,String codigoTag, String codProva, String descricaoTag){
    bancoConnection.executaSQLsemRetorno("INSERT INTO TB06_TAGS_PROVA(nu_prova_tb03, nu_posicao_tag, tipo_tag, descricao_tag) VALUES ("+codProva+","+posicao+","+codigoTag+",'"+descricaoTag+"')");
 }
  
  public void salvarArrayQuestoes(int posicaoLinhaLista,int posicaoQuestao,String codProva){
     Object objetoLinha = editorProva.modeloLista.getElementAt(posicaoLinhaLista);
     String numQuest="0";
     for(int z=0;z<editorProva.arrayObjetoLinha.length;z++){
       if(objetoLinha== editorProva.arrayObjetoLinha[z]){
         //System.out.println("Metodo salvarArrayQuestoes: achou!");
         numQuest= editorProva.numQuestoesEditProv[z];
         break;
        }
     }
    bancoConnection.executaSQLsemRetorno("INSERT INTO TB05_QUESTOES_PROVA (nu_prova_tb03, nu_posicao_questao, id_questao_tb01) VALUES ("+codProva+","+posicaoQuestao+","+numQuest+")");
   
  }
  
  public void atualizaNumeroItensProva(int nuLinhas, String codProva){
     bancoConnection.executaSQLsemRetorno("UPDATE TB03_PROVAS SET nu_total_itens_lista="+nuLinhas+" WHERE nu_prova="+codProva);
    
  }
  
  
  public void limparItensDaProva(String codProva){
    bancoConnection.executaSQLsemRetorno("DELETE FROM TB06_TAGS_PROVA WHERE nu_prova_tb03="+codProva);
    bancoConnection.executaSQLsemRetorno("DELETE FROM TB05_QUESTOES_PROVA WHERE nu_prova_tb03="+codProva);
    atualizaContagemDeletes();
  }
  
  public int consultaNumeroItensProva(String codProva){
    try{
   
    retornoSQL = bancoConnection.executaSQLcomRetorno("select * from TB03_PROVAS where nu_prova ="+codProva);
        retornoSQL.next();
        int nuItensLinha= retornoSQL.getInt("nu_total_itens_lista");
        return nuItensLinha;
    }
    catch (SQLException ex) {
     Logger.getLogger(DAOprova.class.getName()).log(Level.SEVERE, null, ex);
     System.out.println("Falha comando SQL do método consultaNumeroItensProva");
   }
    return 0;
  }

 
  public String[] carregarArrayDivisorias(String codProva){
    String [] arrayDivisorias;
    int contagem=0;
    try{
    ResultSet retornoSQL = bancoConnection.executaSQLcomRetorno("SELECT * FROM TB03_PROVAS WHERE nu_prova="+codProva);
    if(retornoSQL.next())contagem = retornoSQL.getInt("nu_total_itens_lista");
    else{return null;}
    retornoSQL = bancoConnection.executaSQLcomRetorno("SELECT nu_posicao_tag, descricao_tag FROM TB06_TAGS_PROVA WHERE tipo_tag=5 AND nu_prova_tb03="+codProva);
    arrayDivisorias= new String[contagem];
    while(retornoSQL.next()){
      int posicao = retornoSQL.getInt("nu_posicao_tag");
      arrayDivisorias[posicao]=retornoSQL.getString("descricao_tag");
    }
    return arrayDivisorias;
    }
    catch (SQLException ex) {
     Logger.getLogger(DAOprova.class.getName()).log(Level.SEVERE, null, ex);
     System.out.println("Falha comando SQL do método carregarArrayDivisorias");
   }
    return null;
  }

  public String[] carregarArrayTags(String codProva, String tipoTag){
    String [] arrayTags;
    int contagem=0;
    try{
    ResultSet retornoSQL = bancoConnection.executaSQLcomRetorno("SELECT nu_total_itens_lista FROM TB03_PROVAS WHERE nu_prova="+codProva);

    if(retornoSQL.next())contagem = retornoSQL.getInt("nu_total_itens_lista");
    else{return null;}
    retornoSQL = bancoConnection.executaSQLcomRetorno("SELECT * FROM TB06_TAGS_PROVA WHERE nu_prova_tb03="+codProva+" AND tipo_tag="+tipoTag);
    arrayTags= new String[contagem];
    while(retornoSQL.next()){
      int posicao = retornoSQL.getInt("nu_posicao_tag");
      arrayTags[posicao]=retornoSQL.getString("tipo_tag");
    }
    return arrayTags;
    }
    catch (SQLException ex) {
     Logger.getLogger(DAOprova.class.getName()).log(Level.SEVERE, null, ex);
     System.out.println("Falha comando SQL do método carregarArrayTags");
   }
    return null;
  }

  public String[][] carregarQuestoesProva(String codProva){
    String [][] arrayQuestoesProva;
    
    try{
      int contagem;
      ResultSet retornoSQL = bancoConnection.executaSQLcomRetorno("SELECT nu_total_itens_lista FROM TB03_PROVAS WHERE nu_prova="+codProva);
      ResultSet retornoSQL2;
      //System.out.println("TENTANDO PEGAR NUMERO DE QUESTOES DA PROVA");
      if(retornoSQL.next()){contagem = retornoSQL.getInt("nu_total_itens_lista");System.out.println("NUMERO DE QUESTOES DA PROVA= "+contagem);}
      else{return null;}
      arrayQuestoesProva = new String[contagem][2];
      retornoSQL = bancoConnection.executaSQLcomRetorno("SELECT * from TB05_QUESTOES_PROVA WHERE nu_prova_tb03="+codProva);
      String numeroQuestao; 
      String nomeQuestao;
      
      int nuPosicaoQuestao;      
      while(retornoSQL.next()){        
        nuPosicaoQuestao=retornoSQL.getInt("nu_posicao_questao");
        numeroQuestao=retornoSQL.getString("id_questao_tb01");
        retornoSQL2=bancoConnection.executaSQLcomRetorno("SELECT * FROM TB01_QUESTOES WHERE id_questao="+numeroQuestao);
        retornoSQL2.next();
        nomeQuestao= retornoSQL2.getString("no_questao");
        
        arrayQuestoesProva[nuPosicaoQuestao][0]= numeroQuestao;
        arrayQuestoesProva[nuPosicaoQuestao][1]= nomeQuestao;
      }
      return arrayQuestoesProva;
    }
    catch (SQLException ex) {
      Logger.getLogger(DAOprova.class.getName()).log(Level.SEVERE, null, ex);
      System.out.println("Falha comando SQL do método carregarQuestoesProva");
   }
    return null;
  }
  
  public int consultaProximaNumeracaoProva(){
     ResultSet comandoSQLadd = bancoConnection.executaSQLcomRetorno("SELECT LAST(nu_prova) from TB03_PROVAS");
             int nuNovaProva=0;
             try{
                 comandoSQLadd.next();
                 nuNovaProva = comandoSQLadd.getInt(1)+1;
                 System.out.println("O NUMERO NOVO É: "+nuNovaProva);
                  }
              catch(SQLException e){
                  JOptionPane.showMessageDialog(null,"Erro no inicio da funcao adicionaItem(prova)" + e.getMessage(),"Erro",0);
              }
             return nuNovaProva;
  }
  
  public void novaProva(String tituloItem, String codItemSupraCateg){
    int nuNovaProva=consultaProximaNumeracaoProva();
    bancoConnection.executaSQLsemRetorno("INSERT INTO TB03_PROVAS (nu_prova, no_prova, nu_categoria_tb04, nu_total_itens_lista, no_arquivo_img) VALUES ("+nuNovaProva+",'"+tituloItem+"',"+codItemSupraCateg+",0,null)");
    bancoConnection.executaSQLsemRetorno("INSERT INTO TB07_INFORMACOES_PROVA (nu_prova_tb03, tipo_informacao, texto_informacao) VALUES ("+nuNovaProva+",1,null)");
    bancoConnection.executaSQLsemRetorno("INSERT INTO TB07_INFORMACOES_PROVA (nu_prova_tb03, tipo_informacao, texto_informacao) VALUES ("+nuNovaProva+",2,null)");
    bancoConnection.executaSQLsemRetorno("INSERT INTO TB07_INFORMACOES_PROVA (nu_prova_tb03, tipo_informacao, texto_informacao) VALUES ("+nuNovaProva+",3,null)");
    bancoConnection.executaSQLsemRetorno("INSERT INTO TB07_INFORMACOES_PROVA (nu_prova_tb03, tipo_informacao, texto_informacao) VALUES ("+nuNovaProva+",4,null)");
  }
  
 public void deletarProva(String codItem){
   removerImagemBanco(codItem); 
   bancoConnection.executaSQLsemRetorno("DELETE FROM TB05_QUESTOES_PROVA WHERE nu_prova_tb03="+codItem);
   bancoConnection.executaSQLsemRetorno("DELETE FROM TB06_TAGS_PROVA WHERE nu_prova_tb03="+codItem);
   bancoConnection.executaSQLsemRetorno("DELETE FROM TB07_INFORMACOES_PROVA WHERE nu_prova_tb03="+codItem);
   bancoConnection.executaSQLsemRetorno("DELETE FROM TB03_PROVAS WHERE nu_prova="+codItem);
   atualizaContagemDeletes();
   System.out.println("TERMINOU REMOVER ITEM PROVA");
   
 }

public void atualizarCampoProva(String codigoProva,String tipoInfo, String texto){
   bancoConnection.executaSQLsemRetorno("UPDATE TB07_INFORMACOES_PROVA SET texto_informacao='"+texto+"' WHERE nu_prova_tb03="+codigoProva+" AND tipo_informacao="+tipoInfo);
} 

 public void atualizaPosicaoListaItensProva(String codigoProva,int posicao){
   try{
       ResultSet comandoSQL = bancoConnection.executaSQLcomRetorno("SELECT * FROM TB05_QUESTOES_PROVA WHERE nu_prova_tb03="+codigoProva+" AND nu_posicao_questao > "+posicao+" ORDER BY nu_posicao_questao ASC");
       while(comandoSQL.next()){
         int linha= comandoSQL.getInt("nu_posicao_questao");
         linha--;
         bancoConnection.executaSQLsemRetorno("UPDATE TB05_QUESTOES_PROVA SET nu_posicao_questao="+linha+" WHERE nu_prova_tb03="+codigoProva+" AND nu_posicao_questao="+(linha+1));
         
       }
       comandoSQL = bancoConnection.executaSQLcomRetorno("SELECT * FROM TB06_TAGS_PROVA WHERE nu_prova_tb03="+codigoProva+" AND nu_posicao_tag > "+posicao);
       
       while(comandoSQL.next()){
         int linha= comandoSQL.getInt("nu_posicao_tag");
         linha--;
         bancoConnection.executaSQLsemRetorno("UPDATE TB06_TAGS_PROVA SET nu_posicao_tag="+linha+" WHERE nu_prova_tb03="+codigoProva+" AND nu_posicao_tag="+(linha+1));
         
       }
       comandoSQL = bancoConnection.executaSQLcomRetorno("SELECT * FROM TB03_PROVAS WHERE nu_prova="+codigoProva);
       
       if(comandoSQL.next()){
         int totalItens= comandoSQL.getInt("nu_total_itens_lista");
         totalItens--;
         bancoConnection.executaSQLsemRetorno("UPDATE TB03_PROVAS SET nu_total_itens_lista="+totalItens+" WHERE nu_prova="+codigoProva);
       }
      }
   catch(SQLException e){
       JOptionPane.showMessageDialog(null,"Erro na funcao atualizaPosicaoListaItensProva()" + e.getMessage(),"Erro",0);
        }  
  
 }
 
 public boolean verificaQuestaoProva(String nuQuestao, String nuProva, int nuPosicao){
   try{
       ResultSet comandoSQL = bancoConnection.executaSQLcomRetorno("SELECT * FROM TB05_QUESTOES_PROVA WHERE nu_prova_tb03="+nuProva+" AND nu_posicao_questao ="+nuPosicao+" AND id_questao_tb01="+nuQuestao);
       if(comandoSQL.next())return true;
       else return false;
   }
   catch(SQLException e){
       JOptionPane.showMessageDialog(null,"Erro na funcao verificaQuestaoProva" + e.getMessage(),"Erro",0);
        }
   return false;
 }
 
 public String consultaParametroSistema(int nuParametro){
  String parametro;
  ResultSet comandoSQL = bancoConnection.executaSQLcomRetorno("SELECT valor_parametro FROM TB08_SISTEMA WHERE nu_parametro="+nuParametro);
  try{
      if(comandoSQL.next()){
        parametro = comandoSQL.getString("valor_parametro");
        return parametro;
      }
      else return null;
  }
  catch(SQLException e){
      JOptionPane.showMessageDialog(null,"Erro na funcao verificaQuestaoProva" + e.getMessage(),"Erro",0);
  }
   return null;
 }
 
}
