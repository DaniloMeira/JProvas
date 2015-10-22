 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesTeste.dao;

import ClassesTeste.ConectaBanco;
import ClassesTeste.utils.ManipuladorArquivos;
import ClassesTeste.utils.ManipuladorImagem;
import Telas.JProvas;
import Telas.SubTelaEditorQuestao;
import Telas.SubtelaPainelArvore;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Danilo
 */
public class DAOquestao {
    
 ConectaBanco bancoConnection;// = new ConectaBanco();
 ResultSet retornoSQL;
 SubtelaPainelArvore sbtPainelArvore;
 public SubTelaEditorQuestao sbtEditQ;
 //DAOprova daoProva = new DAOprova();
// DAOprova daoProva = JProvas.JProvas_DAOProva;
 char charSeparador = File.separatorChar; 
 String diretorioImagens=System.getProperty("user.dir")+charSeparador+"HTML"+charSeparador+"Imagens"+charSeparador+"Imagens_Questoes"+charSeparador;
 ManipuladorArquivos mpArquivos= new ManipuladorArquivos();
 ManipuladorImagem mpImagens= new ManipuladorImagem();
 
 public DAOquestao(SubtelaPainelArvore sbtParvore,ConectaBanco connection){
     sbtPainelArvore=sbtParvore;
     bancoConnection=connection;
     
 }
 
 public DAOquestao(){
   
 }
 
 
 public void abrirTelaQuestao(String numeroItem) throws FileNotFoundException, IOException{
  sbtEditQ = new SubTelaEditorQuestao(this);
  carregarQuestao(numeroItem);
 }   
 
 public void carregarQuestao(String numeroItem) throws FileNotFoundException, IOException{
     try{
  String noItem,nuItem,textoItem,tipoQuestao, linhasResposta;
  String questA, questB, questC, questD, questE, resposta; 
  String imagemQuestao=null;
  //ConectaBanco bancoConnection= new ConectaBanco();
  nuItem= numeroItem;
  sbtEditQ.codQuestao = Integer.parseInt(numeroItem);
  retornoSQL= bancoConnection.executaSQLcomRetorno("SELECT * FROM TB01_QUESTOES where id_questao ="+nuItem);
  retornoSQL.next();
  noItem = retornoSQL.getString("no_questao");
  textoItem = retornoSQL.getString("texto_questao");
  tipoQuestao = retornoSQL.getString("tipo_questao");
  
  
  if(tipoQuestao.contains("alt")){
    //editor.limpaCampos();
    sbtEditQ.tipoQuestao=0;
    sbtEditQ.modoQuestaoAlternativa();
    resposta = retornoSQL.getString("resposta_questao");
    sbtEditQ.respost=resposta;

    //pegar as alternativas
    retornoSQL= bancoConnection.executaSQLcomRetorno("SELECT * FROM TB02_ALTERNATIVAS where letra_alternativa='a' and id_questao_tb01="+nuItem);
    retornoSQL.next();
    questA = retornoSQL.getString("texto_alternativa");
    sbtEditQ.tQuestaoA.setText(questA);
    sbtEditQ.questA = questA;
  
    retornoSQL= bancoConnection.executaSQLcomRetorno("SELECT * FROM TB02_ALTERNATIVAS where letra_alternativa='b' and id_questao_tb01="+nuItem);
    retornoSQL.next();
    questB = retornoSQL.getString("texto_alternativa");
    sbtEditQ.tQuestaoB.setText(questB);
    sbtEditQ.questB = questB;

    retornoSQL= bancoConnection.executaSQLcomRetorno("SELECT * FROM TB02_ALTERNATIVAS where letra_alternativa='c' and id_questao_tb01="+nuItem);
    retornoSQL.next();
    questC = retornoSQL.getString("texto_alternativa");
    sbtEditQ.tQuestaoC.setText(questC);
    sbtEditQ.questC = questC;
  
    retornoSQL= bancoConnection.executaSQLcomRetorno("SELECT * FROM TB02_ALTERNATIVAS where letra_alternativa='d' and id_questao_tb01="+nuItem);
    retornoSQL.next();
    questD = retornoSQL.getString("texto_alternativa");
    sbtEditQ.tQuestaoD.setText(questD);
    sbtEditQ.questD = questD;

    retornoSQL= bancoConnection.executaSQLcomRetorno("SELECT * FROM TB02_ALTERNATIVAS where letra_alternativa='e' and id_questao_tb01="+nuItem);
    retornoSQL.next();
    questE = retornoSQL.getString("texto_alternativa");
    sbtEditQ.tQuestaoE.setText(questE);
    sbtEditQ.questE = questE;
    
    switch(resposta){
      case "a":{
      sbtEditQ.comboResposta.setSelectedIndex(0);
      sbtEditQ.respost="a";
      break;
      }
      case "b":{
      sbtEditQ.comboResposta.setSelectedIndex(1);
      sbtEditQ.respost="b";
      break;
      }
      case "c":{
      sbtEditQ.comboResposta.setSelectedIndex(2);
      sbtEditQ.respost="c";
      break;
      }
      case "d":{
      sbtEditQ.comboResposta.setSelectedIndex(3);
      sbtEditQ.respost="d";
      break;
      }
      case "e":{
      sbtEditQ.comboResposta.setSelectedIndex(4);
      sbtEditQ.respost = sbtEditQ.comboResposta.getSelectedItem().toString();
      break;
      }
    }
  }
  else{
    sbtEditQ.tipoQuestao=1;
    sbtEditQ.modoQuestaoDissertativa();
    linhasResposta = retornoSQL.getString("linhas_resp_dissertativa");
    sbtEditQ.linhasResp=linhasResposta;
    sbtEditQ.campoTamResp.setText(linhasResposta);
  }

  //abrir o editor de questoes
  sbtEditQ.LbTituloQuest.setText(noItem);
  sbtEditQ.jtCampoQuest.setText(textoItem);
  sbtEditQ.campoQuest=textoItem;
  sbtEditQ.codQuestao= Integer.valueOf(nuItem);
 
  //carregar imagem da questao, se existir
  retornoSQL= bancoConnection.executaSQLcomRetorno("SELECT no_arquivo_img FROM TB01_QUESTOES where id_questao="+nuItem+" AND no_arquivo_img IS NOT NULL");
  //retornoSQL.next();
  if(retornoSQL.next()==true){
	if(!mpArquivos.verificaDiretorioExiste("HTML")){mpArquivos.criarDiretorio("HTML");System.out.println("Diretorio 'HTML' criado");}
	if(!mpArquivos.verificaDiretorioExiste("HTML/Imagens")){mpArquivos.criarDiretorio("HTML/Imagens");System.out.println("Diretorio 'HTML/Imagens' criado");}
	if(!mpArquivos.verificaDiretorioExiste("HTML/Imagens/Imagens_Questoes")){mpArquivos.criarDiretorio("HTML/Imagens/Imagens_Questoes");System.out.println("Diretorio 'HTML/Imagens/Imagens_Questoes' criado");}
	imagemQuestao = retornoSQL.getString("no_arquivo_img");
	sbtEditQ.imagemQuestao=imagemQuestao;
	carregarImagemBlob(nuItem);
	sbtEditQ.exibirImagem();}
  
  sbtEditQ.habilitaCampos();
  
      
  sbtPainelArvore.sbtEditQuest=sbtEditQ;
  //retornoSQL.close();
  //bancoConnection.Desconectar();
  
  sbtEditQ.btSalvarQuest.setEnabled(false);
  sbtEditQ.btCancelar.setEnabled(false);
  /*  Telas.TelaQuestao tela1 = new Telas.TelaQuestao(this,noItem,nuItem,textoItem);
  tela1.setVisible(true);
  */
  }
        catch(SQLException e){
       //  editor.limpaCampos();
        // editor.LbTituloQuest.setText("ERRO: QUESTAO"+editor.numeroQuestao+" INVÁLIDA");
         sbtEditQ.LbTituloQuest.setText("ERRO");
       //  editor.jtCampoQuest.setText("A questão está corrompida no banco de dados, deverá ser excluidaa e recriada");
        // editor.desabilitaCampos();
         JOptionPane.showMessageDialog(null,"Erro na funcao abrirTelaQuestao : " + e.getMessage(),"Erro",0);
         sbtPainelArvore.sbtEditQuest=sbtEditQ;
         sbtEditQ.btSalvarQuest.setEnabled(false);
         System.out.println("catch funçao abrir editor questao");

      }
 }
  
 
    public void inserirImagemBanco(int codQuestao, String imagemQuestao){
     //ConectaBanco bd = new ConectaBanco();
     bancoConnection.executaSQLsemRetorno("UPDATE TB01_QUESTOES SET no_arquivo_img='"+imagemQuestao+"' WHERE id_questao="+codQuestao);
    }
    
    public void removerImagemBanco(int codQuestao/*, String imagemQuestao*/){
     //ConectaBanco bd = new ConectaBanco();
     retornoSQL=bancoConnection.executaSQLcomRetorno("SELECT no_arquivo_img FROM TB01_QUESTOES WHERE id_questao="+codQuestao+" AND no_arquivo_img IS NOT NULL");
     
     try{
      if(retornoSQL.next()==true){
       String arquivoImg= retornoSQL.getString("no_arquivo_img"); 
       bancoConnection.executaSQLsemRetorno("UPDATE TB01_QUESTOES SET no_arquivo_img=null, blob_img=null WHERE id_questao="+codQuestao);
       removerArquivoImagem(arquivoImg);
       atualizaContagemDeletes();
     }
    }
   catch (SQLException e){e.printStackTrace();}
}
 
public void removerArquivoImagem(String imagemQuestao){
 Process pro;
  try
  {
   pro = Runtime.getRuntime().exec("cmd.exe /c del "+diretorioImagens+imagemQuestao);
   System.out.println("imagem deletada: "+diretorioImagens+imagemQuestao);
   
 }
  catch (IOException e){e.printStackTrace();
 // catch (InterruptedException e){e.printStackTrace();}
  System.out.println("imagem a deletar não foi encontrada");
 }
}    
 
public void inserirImagemBlob(Integer codQuestao, File arquivoImg){
   ////ConectaBanco bd = new ConectaBanco();
   bancoConnection.executaSQLInsertImagem("UPDATE TB01_QUESTOES SET blob_img=? WHERE id_questao="+codQuestao,arquivoImg);
 }

public void carregarImagemBlob(String codQuestao) throws FileNotFoundException, IOException{
 ////ConectaBanco bd = new ConectaBanco();
 retornoSQL=bancoConnection.executaSQLcomRetorno("SELECT no_arquivo_img, blob_img FROM TB01_QUESTOES WHERE id_questao="+codQuestao);
 File imagemBlob = new File(diretorioImagens+"blob.file" /*editorProva.imagemProva*/);     
 
 try{
      if (retornoSQL.next()) {
        Blob blob = retornoSQL.getBlob("blob_img");
        String nomeImagem= retornoSQL.getString("no_arquivo_img");
        byte[] bytearray = blob.getBytes(1L, (int)blob.length());
        FileOutputStream fos = new FileOutputStream(imagemBlob);
        fos.write(bytearray);
        fos.close();
        
       mpImagens.descomprimeImagem(imagemBlob, diretorioImagens+nomeImagem);
       
        //excluir arquivo blob.file
       mpArquivos.excluirArquivo(imagemBlob);
  }
 }
    catch (SQLException e){
       e.printStackTrace();
       System.out.println("FALHA NO COMANDO SQL carregarImagemBlob da classe DAOquestao");
    }
 }


 public void excluirAlternativas(int codQuestao){
   bancoConnection.executaSQLsemRetorno("DELETE FROM TB02_ALTERNATIVAS WHERE id_questao_tb01="+codQuestao);
   atualizaContagemDeletes();
 }
 
 public void atualizaContagemDeletes(){
  retornoSQL=bancoConnection.executaSQLcomRetorno("SELECT valor_parametro from tb08_sistema WHERE nu_parametro=2");
      try {
          if(retornoSQL.next()==true){
            Integer numDeletes=retornoSQL.getInt("valor_parametro");
            bancoConnection.executaSQLsemRetorno("UPDATE TB08_SISTEMA set valor_parametro="+(numDeletes+1)+" WHERE nu_parametro=2");
          }
      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(null,"FALHA NO COMANDO SQL atualizaContagemDeletes da classe DAOquestao: " + ex.getMessage(),"Erro",0);
      }

}
 
 public void criarAlternativas(int codQuestao){
   //ConectaBanco bd = new ConectaBanco();
   String nuQuest=Integer.toString(codQuestao);
   String queryA="INSERT INTO TB02_ALTERNATIVAS (id_questao_tb01,letra_alternativa,texto_alternativa) VALUES ("+nuQuest+",'a','------')";
   String queryB="INSERT INTO TB02_ALTERNATIVAS (id_questao_tb01,letra_alternativa,texto_alternativa) VALUES ("+nuQuest+",'b','------')";
   String queryC="INSERT INTO TB02_ALTERNATIVAS (id_questao_tb01,letra_alternativa,texto_alternativa) VALUES ("+nuQuest+",'c','------')";
   String queryD="INSERT INTO TB02_ALTERNATIVAS (id_questao_tb01,letra_alternativa,texto_alternativa) VALUES ("+nuQuest+",'d','------')";
   String queryE="INSERT INTO TB02_ALTERNATIVAS (id_questao_tb01,letra_alternativa,texto_alternativa) VALUES ("+nuQuest+",'e','------')";
   bancoConnection.executaSQLsemRetorno(queryA);
   bancoConnection.executaSQLsemRetorno(queryB);
   bancoConnection.executaSQLsemRetorno(queryC);
   bancoConnection.executaSQLsemRetorno(queryD);
   bancoConnection.executaSQLsemRetorno(queryE);
 }
 
 
 public void gravarAlternativas(int codQuestao){
   //sbtEditQ
   //ConectaBanco bd = new ConectaBanco();
   String queryA="INSERT INTO TB02_ALTERNATIVAS (id_questao_tb01,letra_alternativa,texto_alternativa) VALUES ("+codQuestao+",'"+"a','"+sbtEditQ.questA+"'"+")";
   String queryB="INSERT INTO TB02_ALTERNATIVAS (id_questao_tb01,letra_alternativa,texto_alternativa) VALUES ("+codQuestao+",'"+"b','"+sbtEditQ.questB+"'"+")";
   String queryC="INSERT INTO TB02_ALTERNATIVAS (id_questao_tb01,letra_alternativa,texto_alternativa) VALUES ("+codQuestao+",'"+"c','"+sbtEditQ.questC+"'"+")";
   String queryD="INSERT INTO TB02_ALTERNATIVAS (id_questao_tb01,letra_alternativa,texto_alternativa) VALUES ("+codQuestao+",'"+"d','"+sbtEditQ.questD+"'"+")";
   String queryE="INSERT INTO TB02_ALTERNATIVAS (id_questao_tb01,letra_alternativa,texto_alternativa) VALUES ("+codQuestao+",'"+"e','"+sbtEditQ.questE+"'"+")";
   bancoConnection.executaSQLsemRetorno(queryA);
   bancoConnection.executaSQLsemRetorno(queryB);
   bancoConnection.executaSQLsemRetorno(queryC);
   bancoConnection.executaSQLsemRetorno(queryD);
   bancoConnection.executaSQLsemRetorno(queryE);
  // bancoConnection.Desconectar();
 }
 
 public void alterarModalidade(int codQuestao, String modalidade){
   //ConectaBanco bd = new ConectaBanco();
   bancoConnection.executaSQLsemRetorno("UPDATE TB01_QUESTOES SET tipo_questao='"+modalidade+"' WHERE id_questao="+codQuestao);
   if(modalidade.compareTo("dis")==0){
   bancoConnection.executaSQLsemRetorno("UPDATE TB01_QUESTOES SET resposta_questao=NULL WHERE id_questao="+codQuestao);
   }
   else if(modalidade.compareTo("alt")==0){
   bancoConnection.executaSQLsemRetorno("UPDATE TB01_QUESTOES SET linhas_resp_dissertativa=NULL WHERE id_questao="+codQuestao);
   }
   //bancoConnection.Desconectar();  
 }
 
 public void alterarResposta(int codQuestao, String resposta){
     //ConectaBanco bd = new ConectaBanco();
     bancoConnection.executaSQLsemRetorno("UPDATE TB01_QUESTOES SET resposta_questao='"+resposta+"' WHERE id_questao ="+codQuestao);
     //bancoConnection.Desconectar();  
 }
 
 public void alterarLinhasResposta(String quant_linhas, int codQuestao){
   //ConectaBanco bd = new ConectaBanco();
   bancoConnection.executaSQLsemRetorno("UPDATE TB01_QUESTOES SET linhas_resp_dissertativa=" +quant_linhas+ " WHERE id_questao="+codQuestao);
 }
 
 public void alterarTextoQuestao(int codQuestao, String campoQuest){
     //ConectaBanco bd = new ConectaBanco();
     bancoConnection.executaSQLsemRetorno("UPDATE TB01_QUESTOES SET texto_questao='"+campoQuest+"' WHERE id_questao ="+codQuestao);
     //bancoConnection.Desconectar();    
 }
 
 public void alterarAlternativa(int codQuestao, String letraAlternativa, String textoAlterado){
     //ConectaBanco bd = new ConectaBanco();
     bancoConnection.executaSQLsemRetorno("UPDATE TB02_ALTERNATIVAS SET texto_alternativa='"+textoAlterado+"' where letra_alternativa='"+letraAlternativa+"' AND id_questao_tb01="+codQuestao);
    // bancoConnection.Desconectar();
 }
 
 public void novaQuestao(int tipoQuestao, String tituloItem, String codItemSupraCateg){
   //ConectaBanco bd = new ConectaBanco();
   int nuNovaQuestao= consultaProximaNumeracaoQuestao();
     switch(tipoQuestao){
     case 1:{ //caso 1 = questao alternativa
     //bancoConnection.executaSQLsemRetorno("INSERT INTO TB01_QUESTOES VALUES ("+nuNovaQuestao+",'"+tituloItem+"','....','alt','a',0,"+codItemSupraCateg+")");  
     //bancoConnection.executaSQLsemRetorno("INSERT INTO TB01_QUESTOES (ID,descr,descr,descr,descr,number,number) VALUES ("+nuNovaQuestao+",'"+tituloItem+"','....','alt','a',null,"+Integer.getInteger(codItemSupraCateg)+")");  
     String querysql = "INSERT INTO TB01_QUESTOES (id_questao,no_questao,texto_questao,tipo_questao,resposta_questao,linhas_resp_dissertativa,nu_categoria_tb04,no_arquivo_img) VALUES ("+nuNovaQuestao+",'"+tituloItem+"','.........','alt','a',0,"+codItemSupraCateg+",null)";
     bancoConnection.executaSQLsemRetorno(querysql);  
     criarAlternativas(nuNovaQuestao);
     break;
     }
     case 2:{ //caso 2 = questao dissertativa
       bancoConnection.executaSQLsemRetorno("INSERT INTO TB01_QUESTOES VALUES ("+nuNovaQuestao+",'"+tituloItem+"','....','dis',null,"+codItemSupraCateg+")");  
       break;
     }
   }
   //bancoConnection.Desconectar();
 }
 
 
 
 public int consultaProximaNumeracaoQuestao(){
   //ConectaBanco bd = new ConectaBanco();
   //ResultSet retornoSQL = bancoConnection.executaSQLcomRetorno("SELECT id_questao from TB01_QUESTOES ORDER BY id_questao DESC");
   ResultSet retornoSQL = bancoConnection.executaSQLcomRetorno("SELECT LAST(id_questao) from TB01_QUESTOES");
             int nuNovaQuestao=0;
             try{
                 retornoSQL.next();
                 nuNovaQuestao = retornoSQL.getInt(1)+1;
                 System.out.println("O NUMERO NOVO É: "+nuNovaQuestao);
                  }
              catch(SQLException e){
                  JOptionPane.showMessageDialog(null,"Erro na funcao consultaProximaNumeracaoQuestao()  Classe DAOquestao " + e.getMessage(),"Erro",0);
                 // return nuNovaQuestao;
             }
             //bancoConnection.Desconectar();
   return nuNovaQuestao;
 }
 
 public int consultaProximaNumeracaoCategoria(){
     //ConectaBanco bd = new ConectaBanco();
     ResultSet comandoSQLadd = bancoConnection.executaSQLcomRetorno("SELECT LAST(nu_categoria) FROM TB04_CATEGORIAS");;
     int nuNovaCateg=0;
     try{
       comandoSQLadd.next();
      nuNovaCateg = comandoSQLadd.getInt(1)+1;
      System.out.println("O NUMERO NOVO É: "+nuNovaCateg);
     }
     catch(SQLException e){
       JOptionPane.showMessageDialog(null,"Erro na funcao consultaProximaNumeracaoCategoria()" + e.getMessage(),"Erro",0);
     }
    return nuNovaCateg;
 }
 
 public void novaCategoria(String tituloItem, String codItemSupraCateg){
    //ConectaBanco bd = new ConectaBanco();
    int nuNovaCateg=consultaProximaNumeracaoCategoria();
   
   if(codItemSupraCateg.compareTo("0")==0){
                 /*Devido ao auto-relacionamento na tabela 04, primeiro deve-se criar o registro novo com um numero de uma 
                  supra-categoria já existente(no caso, supracategoria=1)*/
               //  bancoConnection.executaSQLsemRetorno("INSERT INTO TB04_CATEGORIAS VALUES("+nuNovaCateg+","+1+",'"+tituloItem+"')");
               //  bancoConnection.executaSQLsemRetorno("UPDATE TB04_CATEGORIAS SET nu_supracategoria="+nuNovaCateg+" WHERE nu_categoria="+nuNovaCateg);
                 /*Depois se altera o numero da supra-gategoria para igualar com o numero da categoria, assim a categoria fará referencia
                   à ela mesma*/
                 bancoConnection.executaSQLsemRetorno("INSERT INTO TB04_CATEGORIAS (nu_categoria,nu_supracategoria,no_categoria) VALUES("+nuNovaCateg+","+nuNovaCateg+",'"+tituloItem+"')");
   }
           
           else{
               bancoConnection.executaSQLsemRetorno("INSERT INTO TB04_CATEGORIAS (nu_categoria,nu_supracategoria,no_categoria) VALUES("+nuNovaCateg+","+codItemSupraCateg+",'"+tituloItem+"')");
           }
   
 }
 
 public void deletarQuestao(String codItem){
    System.out.println("[METODO DELETAR QUESTAO]"+codItem);
   //ConectaBanco bd = new ConectaBanco();
   removerImagemBanco(Integer.parseInt(codItem));
 /*bancoConnection.executaSQLsemRetorno("DELETE FROM TB02_ALTERNATIVAS WHERE id_questao_tb01="+codItem);
   bancoConnection.executaSQLsemRetorno("DELETE FROM TB05_QUESTOES_PROVA WHERE id_questao_tb01="+codItem);
   bancoConnection.executaSQLsemRetorno("DELETE FROM TB01_QUESTOES WHERE id_questao="+codItem);*/
   try{
     bancoConnection.executaSQLsemRetorno("DELETE FROM TB02_ALTERNATIVAS WHERE id_questao_tb01="+codItem);
     atualizaContagemDeletes();
     ResultSet R1 = bancoConnection.executaSQLcomRetorno("SELECT DISTINCT nu_prova_tb03 FROM TB05_QUESTOES_PROVA WHERE id_questao_tb01="+codItem);
     while(R1.next()){
       String codProva = R1.getString("nu_prova_tb03");
       ResultSet R2 = bancoConnection.executaSQLcomRetorno("SELECT * FROM TB05_QUESTOES_PROVA WHERE nu_prova_tb03="+codProva+" AND id_questao_tb01="+codItem+" ORDER BY nu_posicao_questao ASC");
       int z=0;
       while(R2.next()){
         int nuPosicaoQuest = R2.getInt("nu_posicao_questao");
         nuPosicaoQuest-=z;
         bancoConnection.executaSQLsemRetorno("DELETE FROM TB05_QUESTOES_PROVA WHERE nu_prova_tb03="+codProva+" AND nu_posicao_questao="+nuPosicaoQuest);
         atualizaContagemDeletes();
         //if(nuPosicaoQuest>0){
         JProvas.JProvas_DAOProva.atualizaPosicaoListaItensProva(codProva,nuPosicaoQuest);
         //  }
         z++;
       }
     }
     bancoConnection.executaSQLsemRetorno("DELETE FROM TB01_QUESTOES WHERE id_questao="+codItem);
     atualizaContagemDeletes();
   }
   catch(SQLException e){JOptionPane.showMessageDialog(null,"Erro no TRY da funcao DAOprova.deletarQuestao()" + e.getMessage(),"Erro",0);}
   
   System.out.println("TERMINOU REMOVER ITEM QUESTAO");
 }
 
 public void deletarCategoria(String codItem){
   System.out.println("[METODO DELETA CATEGORIA] - CATEGORIA Nº"+codItem);
   //ConectaBanco bd = new ConectaBanco();
   /*bancoConnection.executaSQLsemRetorno("DELETE FROM TB04_CATEGORIAS WHERE nu_supracategoria="+codItem+" AND nu_categoria <>"+codItem);
   bancoConnection.executaSQLsemRetorno("DELETE FROM TB05_CATEGORIA_QUESTAO WHERE nu_categoria_tb04="+codItem);
   bancoConnection.executaSQLsemRetorno("DELETE FROM TB01_QUESTOES WHERE id_questao not in (SELECT id_questao_tb01 from TB05_CATEGORIA_QUESTAO)");
   bancoConnection.executaSQLsemRetorno("DELETE FROM TB06_CATEGORIA_PROVA WHERE nu_categoria_tb04="+codItem);
   bancoConnection.executaSQLsemRetorno("DELETE FROM TB03_PROVAS WHERE nu_prova not in (SELECT nu_prova_tb03 from TB06_CATEGORIA_PROVA)");
   //se a categoria não tiver nenhum item ou subcategorias, pode ser executado somente esta ultima linha
   bancoConnection.executaSQLsemRetorno("DELETE FROM TB04_CATEGORIAS WHERE nu_categoria="+codItem);*/
   
 ResultSet comandoSQLadd;
   //Exluir questões da categoria
   comandoSQLadd = bancoConnection.executaSQLcomRetorno("SELECT id_questao, no_questao FROM TB01_QUESTOES WHERE nu_categoria_tb04="+codItem);
   try{
   while(comandoSQLadd.next()){
      String questaoX= comandoSQLadd.getString("id_questao");
      String nomeQuestaoX=comandoSQLadd.getString("no_questao");;
      System.out.println("tENtando deLEtar a questao:"+questaoX+" da categoria:"+codItem);
      sbtPainelArvore.verificaQuestaoAberta(questaoX, nomeQuestaoX);
      deletarQuestao(questaoX);
     }
   }
   catch(SQLException e){}
   
   //excluir provas
   comandoSQLadd = bancoConnection.executaSQLcomRetorno("SELECT nu_prova FROM TB03_PROVAS WHERE nu_categoria_tb04="+codItem);
   DAOprova daoProvaX= sbtPainelArvore.daoProva_sbtProva;
   try{
   while(comandoSQLadd.next()){
      String provaX= comandoSQLadd.getString("nu_prova"); 
      System.out.println("tENtando deLEtar a prova:"+provaX+" da categoria:"+codItem);
      sbtPainelArvore.verificaProvaAberta(provaX);
      daoProvaX.deletarProva(provaX);
     }
   }
   catch(SQLException e){}
   
      //Recursão repetindo a chamada do método para excluir os itens das subcategorias
   comandoSQLadd = bancoConnection.executaSQLcomRetorno("SELECT nu_categoria FROM TB04_CATEGORIAS WHERE nu_supracategoria="+codItem+" AND nu_categoria <>"+codItem);
   try{
     while(comandoSQLadd.next()){
      String subcategX= comandoSQLadd.getString("nu_categoria"); 
      deletarCategoria(subcategX);
      atualizaContagemDeletes();
     }
   }
   catch(SQLException e){}
   
   //por fim, excluir a propria categoria
   
   bancoConnection.executaSQLsemRetorno("DELETE FROM TB04_CATEGORIAS WHERE nu_categoria="+codItem);
   System.out.println("[metodo deleta categoria] - excluiu a categoria"+codItem);
}

public String obtemDiretorioImagens(){
  String retorno;
  ResultSet comandoSQL = bancoConnection.executaSQLcomRetorno("SELECT valor_parametro from TB08_SISTEMA where nu_parametro=7");;
  try{
    if(comandoSQL.next()){
      retorno= comandoSQL.getString("valor_parametro");
      return retorno;
    }
    else return null;
  }
  catch(SQLException e){
    JOptionPane.showMessageDialog(null,"Erro na funcao obtemDiretorioImagens()" + e.getMessage(),"Erro",0);
  }
  return null;
}
 
}
 
