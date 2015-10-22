package ClassesTeste;
import java.awt.datatransfer.*;
import java.util.*;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.*;


//-----------------------Classe-TransferidorItemsArvore-------------------------------------//
    
class TransferidorItemsArvore extends TransferHandler{ 
       
  DataFlavor nodesFlavor; 
  DataFlavor[] flavors = new DataFlavor[1]; 
  DefaultMutableTreeNode[] nodesToRemove; 
  CriarArvore arvoreCriada;
  
  public TransferidorItemsArvore(){ 
    
    try{ 
      String mimeType = DataFlavor.javaJVMLocalObjectMimeType + 
      ";class=\"" + javax.swing.tree.DefaultMutableTreeNode[].class.getName() + "\""; 
      nodesFlavor = new DataFlavor(mimeType); 
      flavors[0] = nodesFlavor; 
    } 
    catch(ClassNotFoundException e){ 
      System.out.println("Classe não Encontrada: " + e.getMessage()); 
    }
    
  } 
      
  //metodo executado enquanto se arrasta um item da arvore
  @Override
  public boolean canImport(TransferHandler.TransferSupport support) { 
           
    if(!support.isDrop()) { 
      return false;
    }
           
    support.setShowDropLocation(true); 
    if(!support.isDataFlavorSupported(nodesFlavor)) {
      return false; 
    } 
    // Do not allow a drop on the drag source selections. 
    JTree.DropLocation dl = (JTree.DropLocation)support.getDropLocation(); 
    JTree tree = (JTree)support.getComponent();
    int dropRow = tree.getRowForPath(dl.getPath()); 
    int[] selRows = tree.getSelectionRows(); 
    
    for(int i = 0; i < selRows.length; i++) { 
      if(selRows[i] == dropRow){
        return false; 
      } 
    } 

    
    // Do not allow MOVE-action drops if a non-leaf node is selected unless all of its children are also selected. 
    int action = support.getDropAction(); 
    // Do not allow a non-leaf node to be copied to a level which is less than its source level. 
    TreePath dest = dl.getPath(); 
    DefaultMutableTreeNode target = (DefaultMutableTreeNode)dest.getLastPathComponent(); 
    TreePath path = tree.getPathForRow(selRows[0]); 
    DefaultMutableTreeNode firstNode = (DefaultMutableTreeNode)path.getLastPathComponent(); 
    
    System.out.println("Item selecionado no transferidor:"+firstNode.toString());

     if(firstNode.getParent()==target){return false;}
     
    //Os items de pergunta e items de prova não podem receber outros items dentro deles
    if(!target.toString().endsWith("»")){return false;    }
    //Não permitir que itens de prova ou questao sejam incluidos no nível 0 da árvore
    if(target.getLevel()==0 && !firstNode.toString().endsWith("»")){return false;}
        
    return true; 
  } 

      
  @Override
  protected Transferable createTransferable(JComponent c) {
        	
    JTree tree = (JTree)c;
    //CriarArvore tree = (CriarArvore)c;
    TreePath[] paths = tree.getSelectionPaths();
    if(paths != null) {
      // Make up a node array of copies for transfer and another for/of the nodes that will be removed in
      // exportDone after a successful drop.
      List<DefaultMutableTreeNode> copies = new ArrayList<DefaultMutableTreeNode>();
      List<DefaultMutableTreeNode> toRemove = new ArrayList<DefaultMutableTreeNode>();
      DefaultMutableTreeNode node = (DefaultMutableTreeNode)paths[0].getLastPathComponent();
      DefaultMutableTreeNode copiado = copy(node,toRemove);
      copies.add(copiado);
      
      for(int i = 1; i < paths.length; i++) {
        DefaultMutableTreeNode next = (DefaultMutableTreeNode)paths[i].getLastPathComponent();
        
        // Do not allow higher level nodes to be added to list.
        if(node.getLevel()==next.getLevel()){
          if(next.getLevel() < node.getLevel()) {
            break;
          }  
          else if(next.getLevel() > node.getLevel()){ // child node
            copies.add(copy(next,toRemove));
          }	
          // node already contains child
          else {   // sibling
            copies.add(copy(next,toRemove));
          }
	}
      }
              
      DefaultMutableTreeNode[] nodes = copies.toArray(new DefaultMutableTreeNode[copies.size()]);
      System.out.println("-------------Array itens Copiados-------------");
      System.out.println("numero de nodulos da lista copies: "+ copies.size());
      imprimeListaNodulos(copies);
      System.out.println("---------------------------------------------------");
      System.out.println("-------------Array itens a Remover-------------");
      nodesToRemove = toRemove.toArray(new DefaultMutableTreeNode[toRemove.size()]);
      imprimeListaNodulos(toRemove);
      return new NodesTransferable(nodes);
    }
    return null;
      
  }
              
        
  //metodo para testar o que entrou na lista de nodulos a ser movidos
  public void imprimeListaNodulos(List listaX){
    List<DefaultMutableTreeNode> lista =
    new ArrayList<DefaultMutableTreeNode>();
    lista = listaX;
    DefaultMutableTreeNode[] nodulosDalista = lista.toArray(new DefaultMutableTreeNode[lista.size()]);
    //System.out.println("------Lista de nodulos--------");
    for(int i=0; i<nodulosDalista.length; i++){
      System.out.println(nodulosDalista[i].toString()+ "..[selecionado]..Level= "+nodulosDalista[i].getLevel());
    }
    System.out.println(".                             .");
  }
        
      
  /** Defensive copy used in createTransferable. */ 
  private DefaultMutableTreeNode copy(DefaultMutableTreeNode node,List<DefaultMutableTreeNode> listaRemover){ 
    
    DefaultMutableTreeNode nodeCopy = new DefaultMutableTreeNode(node);
    Enumeration<DefaultMutableTreeNode> filhosNodulo = node.children();
    listaRemover.add(node);
    while(filhosNodulo.hasMoreElements()){
      nodeCopy.add(copy((DefaultMutableTreeNode)filhosNodulo.nextElement(),listaRemover));
    }
    return nodeCopy; 
  } 

        
  protected void exportDone(JComponent source, Transferable data, int action){
    
    //System.out.println("metodo exportdone");
    if((action & MOVE) == MOVE) { //Verificar esta linha, aparentemente inútil
      JTree tree = (JTree)source;
      //CriarArvore tree = (CriarArvore)source;
      DefaultTreeModel model = (DefaultTreeModel)tree.getModel(); 
      // Remove nodes saved in nodesToRemove in createTransferable.
      //O array começa a contagem de trás para frente pq obrigatoriamente deve se remover os nodulos filhos antes dos pais
      System.out.println("----------Lista itens Removidos------------");
        for(int i = nodesToRemove.length-1; i >=0; i--) {
          System.out.println("node removido: "+nodesToRemove[i].toString());
          model.removeNodeFromParent(nodesToRemove[i]); 
        } 
    } 
    nodesToRemove=nodesToRemove.clone();
  } 
        
      
  public int getSourceActions(JComponent c) { 
    return COPY_OR_MOVE; 
  } 
      
  public boolean importData(TransferHandler.TransferSupport support){ 
        	
    if(!canImport(support)){ 
      //System.out.println("1 foi executado o metodo Importdata da clasee Transefridor");
      return false; 
    } 
    // Extract transfer data. 
    DefaultMutableTreeNode[] nodes = null; 
      try{ 
        Transferable t = support.getTransferable(); 
        nodes = (DefaultMutableTreeNode[])t.getTransferData(nodesFlavor); 
        //System.out.println("2 foi executado o metodo Importdata da clasee Transefridor");
      } 
      catch(UnsupportedFlavorException ufe) { 
        System.out.println("UnsupportedFlavor: " + ufe.getMessage()); 
      } 
      catch(java.io.IOException ioe) { 
        System.out.println("I/O error: " + ioe.getMessage()); 
      } 
      // Get drop location info. 
      JTree.DropLocation dl = (JTree.DropLocation)support.getDropLocation();
      //CriarArvore.DropLocation dl = (CriarArvore.DropLocation)support.getDropLocation(); 
      int childIndex = dl.getChildIndex(); 
      TreePath dest = dl.getPath(); 
      DefaultMutableTreeNode parent = (DefaultMutableTreeNode)dest.getLastPathComponent(); 
      JTree tree = (JTree)support.getComponent();
      //CriarArvore tree = (CriarArvore)support.getComponent();
      DefaultTreeModel model = (DefaultTreeModel)tree.getModel(); 
      // Configure for drop mode. 
      int index = childIndex;    // DropMode.INSERT 
      //System.out.println("3 foi executado o metodo Importdata da clase Transferidor");
      if(childIndex == -1) {     // DropMode.ON 
        index = parent.getChildCount(); 
        //System.out.println("4 foi executado o metodo Importdata da clasee Transferidor");
      } 
      // Add data to model. 
      for(int i = 0; i < nodes.length; i++) { 
        //mover no banco de dados
        arvoreCriada.moverItens(nodes[i].toString(), parent.toString());
        model.insertNodeInto(nodes[i], parent, index++);
      } 
      return true; 
  } 
    
  
  public String toString() { 
    return getClass().getName(); 
  } 
      
           
 //-----------------Classe-Nodestransferable----------------------------------------//      
public class NodesTransferable implements Transferable { 
  
  DefaultMutableTreeNode[] nodes; 
  public NodesTransferable(DefaultMutableTreeNode[] nodes) { 
  
  this.nodes = nodes; 
  } 
      
  public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException { 
    if(!isDataFlavorSupported(flavor)) 
    throw new UnsupportedFlavorException(flavor); 
    return nodes; 
  } 
      
  public DataFlavor[] getTransferDataFlavors() { 
    return flavors; 
  } 
      
  public boolean isDataFlavorSupported(DataFlavor flavor) { 
    return nodesFlavor.equals(flavor); 
  } 
}
//---------------------------------------------------------------------------------//
 

}  
