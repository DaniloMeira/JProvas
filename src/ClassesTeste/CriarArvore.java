package ClassesTeste;

import ClassesTeste.dao.DAOquestao;
import Telas.SubTelaEditorQuestao;
import Telas.SubtelaEditorProva;
import Telas.SubtelaPainelArvore;
import java.awt.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DropMode;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

/**
 * @author Danilo Meira e Silva
 */

public class CriarArvore extends JTree {
   
  public String ultimaSelecao ="";
  ResultSet retornoSQL;
  DefaultMutableTreeNode objetosArvore[][];
  String [][] nomeObjeto;
  String [][] posObjeto;
  String [][] perguntas;
  ImageIcon tutorialIcon;
  public JTree tree;
  DefaultMutableTreeNode categoria;
  int contador = 0;
  int contadorY = 0;
  public boolean telaQuestAberta = false;
  public DefaultMutableTreeNode node;
  DefaultMutableTreeNode top;
   
  public SubTelaEditorQuestao sbt;
  public SubtelaEditorProva sbtProva;
  public SubtelaPainelArvore sbtPainelArvore;
  
  DAOquestao DAOquestao_criarArvore= new DAOquestao();;
  final public ConectaBanco banco= new ConectaBanco();
  
  public CriarArvore (){
  
  }   
  
  
  public JTree TreeDemo(final SubtelaPainelArvore b){
    sbtPainelArvore= b;
    top = new DefaultMutableTreeNode("<html>BANCO DE PERGUNTAS</html>0»");
    arvoreCatPrincipal(top);
    node=top;//ao inicializar o programa, deve ser indicado o node principal como sendo o selecionado para incluir uma nova categoria
    tree = new JTree(top);
    tutorialIcon = createImageIcon("/Imagens/folder.gif");
    if(tutorialIcon != null){
      tree.setCellRenderer(new MyRenderer4());
    }
    tree.setDragEnabled(true);//permitir arrastar itens da arvore
    tree.setDropMode(DropMode.ON_OR_INSERT);
    TransferidorItemsArvore transfArv = new TransferidorItemsArvore();
    tree.setTransferHandler(transfArv); 
    transfArv.arvoreCriada=this;
    tree.getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
    tree.setVisible(true);
    ControleMouseArvore eventoMouse = new ControleMouseArvore(this,b);
    //evento ao clicar duas vezes na arvore      
    tree.addMouseListener(eventoMouse);
    tree.setSelectionRow(0);
    //retorna a arvore pronta  
    return tree;
  }
  
  
  private void arvoreCatPrincipal(DefaultMutableTreeNode top){
    try{
      //Pegar o numero de subcategorias
      ResultSet retornoSQL= banco.executaSQLcomRetorno("SELECT nu_categoria as contagem FROM TB04_CATEGORIAS ORDER BY nu_categoria DESC");
      int contagem=0;
      if(retornoSQL.next()){
        contagem = retornoSQL.getInt("contagem")+1;
        nomeObjeto = new String [contagem][contagem];
        posObjeto = new String [contagem][contagem];
        //selecionar as categorias do ramo principal
        retornoSQL= banco.executaSQLcomRetorno("SELECT * FROM TB04_categorias where nu_categoria = nu_supracategoria order by nu_categoria");
        List capturaArray1 = new ArrayList();
        List capturaArray2 = new ArrayList();
        while(retornoSQL.next()){
          capturaArray1.add(retornoSQL.getString("no_categoria"));
          capturaArray2.add(String.valueOf(retornoSQL.getInt("nu_categoria")));
        }
        String [] nome_categoria = (String[]) capturaArray1.toArray(new String[capturaArray1.size()]);
        String [] numero_categoria = (String[]) capturaArray2.toArray(new String[capturaArray2.size()]);

        //Buscar as questoes de cada categoria e gravar no array perguntas [][]
        retornoSQL= banco.executaSQLcomRetorno("SELECT COUNT(id_questao) as contagem FROM TB01_QUESTOES");
        if(retornoSQL.next()){
          int contaQuestoes = retornoSQL.getInt("contagem")+1;
          retornoSQL= banco.executaSQLcomRetorno("SELECT nu_categoria FROM TB04_CATEGORIAS ORDER BY nu_categoria DESC");
          if(retornoSQL.next()){
            int contacategorias = retornoSQL.getInt("nu_categoria")+1;
            perguntas = new String [contacategorias][contaQuestoes];
            retornoSQL= banco.executaSQLcomRetorno("SELECT * FROM TB01_QUESTOES order by nu_categoria_tb04");
            if(retornoSQL.next()){
            List capturaArray3 = new ArrayList();
            List capturaArray4 = new ArrayList();
            while(retornoSQL.next()){
              capturaArray3.add(retornoSQL.getString("no_questao"));
              capturaArray4.add(retornoSQL.getString("nu_categoria_tb04"));
            }
            String [] perguntas_categoria = (String[]) capturaArray3.toArray(new String[capturaArray3.size()]);
            String [] nucateg_pergunta = (String[]) capturaArray4.toArray(new String[capturaArray4.size()]);
            int b;
            int contar=0;
            while(contar < perguntas_categoria.length){
              b = Integer.valueOf(nucateg_pergunta[contar]);
              perguntas[b][1]= perguntas_categoria[contar];
              contar++;
              int i=2;
              while (contar < perguntas_categoria.length && (b == Integer.valueOf(nucateg_pergunta[contar]))){
                if(nucateg_pergunta[contar] != null){
                  perguntas[b][i]= perguntas_categoria[contar];
                  i++;
                  contar++;
                }
	      }
            }}
            for(int i=0; i< nome_categoria.length; i++){
              if(numero_categoria[i]!= null){
                int numero = Integer.parseInt(numero_categoria[i]);
                nomeObjeto[numero][0]= nome_categoria[i];
                posObjeto[numero][0]= numero_categoria [i];
                contador++;
                //selecionar as categorias filhas
                buscaFilhos(numero,String.valueOf(numero),"");
                contador = 0;
              }
            }
            
          }} } 
            //Criar o objeto arvore ----------------------------------------
            for(int i =1; i<contagem; i++){ 
              if(nomeObjeto[i][0]!=null){
                categoria = funcaoCriaFilhos(i,"");
                top.add(categoria); 
              }
              contadorY=0;
            }
           
        
        
    }//fim do try
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,"Erro na funcao arvoreCatPrincipal :" + e.getMessage(),"Erro",0);
    }
  } //fim da montagem da arvore  
     
  
  //metodo para buscar as categorias filhas do ramo principal
  public void buscaFilhos(int categPai,String categFilho, String espaco){
    try{     
      String x= "select no_categoria, nu_categoria from TB04_CATEGORIAS where nu_supracategoria="+categFilho+ " AND nu_supracategoria != nu_categoria";
      ResultSet consultaSQL = banco.executaSQLcomRetorno(x);
      //System.out.println(x);
      List capturaArray3 = new ArrayList();
      List capturaArray4 = new ArrayList();
      String esp ="*" + espaco;
      while(consultaSQL.next()){
        capturaArray3.add(consultaSQL.getString("no_categoria"));
        //System.out.println(consultaSQL.getString("no_categoria"));
        capturaArray4.add(consultaSQL.getString("nu_categoria"));
      }
      String [] nome_categoria = (String[]) capturaArray3.toArray(new String[capturaArray3.size()]);
      String [] numero_categoria = (String[]) capturaArray4.toArray(new String[capturaArray4.size()]);
      for(int i=0; i< nome_categoria.length; i++){
        nomeObjeto[categPai][contador]=esp + nome_categoria[i];
        posObjeto[categPai][contador]= numero_categoria [i];
        contador++;
        String categPaix = numero_categoria[i];
        buscaFilhos(categPai,categPaix,esp);
      }	
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,"Erro na funcao buscaFilhos - categPai: "+ categPai +" "+ e.getMessage(),"Erro",0);
    }
  }
   
   
  public DefaultMutableTreeNode funcaoCriaFilhos(int i, String indicaNivel){
    String nivel= "*" + indicaNivel;
    int nivelx=indicaNivel.lastIndexOf('*')+1;
    DefaultMutableTreeNode categoriaX = new DefaultMutableTreeNode("<html>"+nomeObjeto[i][contadorY].replace("*","")+"</html>"+posObjeto[i][contadorY]+"»");
    criaProvasCategoria(categoriaX,posObjeto[i][contadorY]);
    criaQuestoesCategoria(categoriaX,posObjeto[i][contadorY]);
    contadorY++;
      for(int w=contadorY;w<nomeObjeto.length;w++){
        if(nomeObjeto[i][w]!=null && nomeObjeto[i][w].lastIndexOf('*')<nivelx ){break;}
        else if(nomeObjeto[i][w]!=null && nomeObjeto[i][w].lastIndexOf('*')==nivelx ){
          DefaultMutableTreeNode subcategoriaX = funcaoCriaFilhos(i,nivel);
          nomeObjeto[i][w]=null;
          categoriaX.add(subcategoriaX);
        }
      }
   return categoriaX;
  }
   
  public void criaProvasCategoria(DefaultMutableTreeNode ramoX, String categoria){
    DefaultMutableTreeNode provas;
    try{
      ResultSet consultaSQL = banco.executaSQLcomRetorno("SELECT * FROM TB03_PROVAS WHERE nu_categoria_tb04 = " + categoria);
      while(consultaSQL.next()){
        provas = new DefaultMutableTreeNode("<html>"+String.valueOf(consultaSQL.getString("no_prova"))+"</html>"+consultaSQL.getInt("nu_prova")+"¶");
        ramoX.add(provas);
      }
    }   
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,"Erro na funcao criaProvasCategoria : " + e.getMessage()," Erro ",0);
    }
  }  
   
   
  
  //Metodo para incluir na árvore todas as questões pertinentes a uma determinada categoria
  public void criaQuestoesCategoria(DefaultMutableTreeNode ramoX, String categoria){
    DefaultMutableTreeNode questoes;
    try{
      ResultSet consultaSQL = banco.executaSQLcomRetorno("SELECT * FROM TB01_QUESTOES WHERE nu_categoria_tb04 = " + categoria);
      while(consultaSQL.next()){
        questoes = new DefaultMutableTreeNode("<html>"+String.valueOf(consultaSQL.getString("no_questao"))+"</html>"+consultaSQL.getInt("id_questao")+"¤");
        ramoX.add(questoes);
      }
    }   
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,"Erro na funcao criaQuestoesCategoria : " + e.getMessage()," Erro ",0);
    }   
  }
  
  //Metodo para incluir na árvore todas as questões pertinentes a uma determinada categoria, usando o array questoes
  public void criaQuestoesCatArray (DefaultMutableTreeNode ramoX, String categoria){
    int nuCateg = Integer.valueOf(categoria);
    int contador=1;
    DefaultMutableTreeNode questoes;
    while(perguntas[nuCateg][contador]!=null){
      questoes = new DefaultMutableTreeNode(perguntas[nuCateg][contador]);
      ramoX.add(questoes);
      contador++;
    }
  }

  //Metodo para renomear a descrição de um item (Questao, categoria, prova, etc...)
  public void renomeiaItem(String tipoItem, String codItem, String novoNome){
     switch (tipoItem){
      case "questao":{
        banco.executaSQLsemRetorno("UPDATE TB01_QUESTOES SET no_questao='"+novoNome+"' where id_questao= " + codItem);
        tree.setEditable(true);
        node.setUserObject("<html>"+novoNome+"</html>"+codItem+"¤");
        tree.updateUI();
        tree.setEditable(false);
        break;
      }
      case "prova":{
        banco.executaSQLsemRetorno("UPDATE TB03_PROVAS SET no_prova='"+novoNome+"' where nu_prova= " + codItem);
        tree.setEditable(true);
        node.setUserObject("<html>"+novoNome+"</html>"+codItem+"¶");
        tree.updateUI();
        tree.setEditable(false);
        break;
      }
      case "categoria":{
        banco.executaSQLsemRetorno("UPDATE TB04_CATEGORIAS SET no_categoria='"+novoNome+"' where nu_categoria= " + codItem);
        tree.setEditable(true);
        node.setUserObject("<html>"+novoNome+"</html>"+codItem+"»");
        tree.updateUI();
        tree.setEditable(false);
        break;
      }
    }
  }
  
  
  public void adicionaItem(String tipoItem, int numeroItem, String tituloItem){
    DefaultTreeModel modeloArvore = (DefaultTreeModel) tree.getModel();
    DefaultMutableTreeNode noduloCriado;
    switch (tipoItem){
      case "questao":{
        noduloCriado = new DefaultMutableTreeNode("<html>"+tituloItem+"</html>"+numeroItem+"¤");
        modeloArvore.insertNodeInto(noduloCriado, node, 0);
        break;
      }
      case "prova":{
        noduloCriado = new DefaultMutableTreeNode("<html>"+tituloItem+"</html>"+numeroItem+"¶");
        modeloArvore.insertNodeInto(noduloCriado, node, 0);
        break;
      }
      case "categoria":{
        noduloCriado = new DefaultMutableTreeNode("<html>"+tituloItem+"</html>"+numeroItem+"»");
        modeloArvore.insertNodeInto(noduloCriado, node, 0);
        if(node.isRoot()){tree.expandRow(0);}
      }
    }
  }
  
  
  public void moverItens(String itemMovido,String itemDestino){
    System.out.println("metodo mover acionada, itemmovido="+itemMovido);
    String codItemDestino=itemDestino.substring(itemDestino.lastIndexOf(">")+1, itemDestino.lastIndexOf("»"));
    if(itemMovido.endsWith("»")){ // item categoria
      String codItemMovido=itemMovido.substring(itemMovido.lastIndexOf(">")+1, itemMovido.lastIndexOf("»"));
      if(Integer.valueOf(codItemDestino)==0){
        banco.executaSQLsemRetorno("UPDATE TB04_CATEGORIAS SET nu_supracategoria="+codItemMovido+" WHERE nu_categoria="+codItemMovido);
      }
      else{
        banco.executaSQLsemRetorno("UPDATE TB04_CATEGORIAS SET nu_supracategoria="+codItemDestino+" WHERE nu_categoria="+codItemMovido);
      }
    }
    else if(itemMovido.endsWith("¤")){ // item questao
      String codItemMovido=itemMovido.substring(itemMovido.lastIndexOf(">")+1,itemMovido.lastIndexOf("¤"));
      banco.executaSQLsemRetorno("UPDATE TB01_QUESTOES SET nu_categoria_tb04="+codItemDestino+" WHERE id_questao="+codItemMovido);
    }
    else if(itemMovido.endsWith("¶")){ // item prova
    String codItemMovido=itemMovido.substring(itemMovido.lastIndexOf(">")+1,itemMovido.lastIndexOf("¶"));
    System.out.println("foi executado o metodo move item categoria prova, destino :"+codItemDestino);
        banco.executaSQLsemRetorno("UPDATE TB03_PROVAS SET nu_categoria_tb04="+codItemDestino+" WHERE nu_prova="+codItemMovido);
    }
}
  
  
  //Método para deletar um item da arvore.
  public void deletaItem(){
    System.out.println("O NODULO removido DA ARVORE é"+node.toString());
    node.removeFromParent();
    tree.updateUI();
    System.out.println("TERMINOU REMOVER O NODULO DA ARVORE");
  }
  
  
  //metodo para mudar o icone-----------------------------------
  protected static ImageIcon createImageIcon(String path){
    java.net.URL imgURL = CriarArvore.class.getResource(path);
    if(imgURL != null){return new ImageIcon(imgURL);}
    else{
      System.err.println("imagem de icone não encontrada: " + path);
      return null;
    }
  }
  
}//-------------------------------FIM DA CLASSE---------------------------------


class MyRenderer4 extends DefaultTreeCellRenderer {
  Icon tutorialIcon = createImageIcon("/Imagens/folder.gif");
  Icon pastaAberta = createImageIcon("/Imagens/pastaAberta.gif");
  Icon pastaFechada = createImageIcon("/Imagens/pastaFechada.gif");
  Icon iconeProvas = createImageIcon("/Imagens/iconeProva.gif");
  Icon iconeQuestoes = createImageIcon("/Imagens/iconeQuestao.gif");
   
  public MyRenderer4(/*Icon icon*/){

  }

  public Component getTreeCellRendererComponent(JTree tree,Object value,
  boolean sel,boolean expanded,boolean leaf,int row,boolean hasFocus){
    super.getTreeCellRendererComponent(tree, value, sel,expanded, 
      leaf,row,hasFocus);
    setClosedIcon(pastaFechada);
    setOpenIcon(pastaAberta);
    if(leaf && isTutorialBook(value)) {
      if(super.getText().endsWith("siva")){setIcon(tutorialIcon);}
      else if(super.getText().contains("¶")){setIcon(iconeProvas);}
      else if(super.getText().contains("¤")){setIcon(iconeQuestoes);}
      else {setIcon(pastaFechada);}
    }
    else{setToolTipText(null);} 
    return this;
  }
   
    
  protected boolean isTutorialBook(Object value) {
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
    return true;
  }
  
  
  protected static ImageIcon createImageIcon(String path){
    java.net.URL imgURL = CriarArvore.class.getResource(path);
    if(imgURL != null){return new ImageIcon(imgURL);}
    else{
      System.err.println("Couldn't find file: " + path);
      return null;
    }
  }
  
}