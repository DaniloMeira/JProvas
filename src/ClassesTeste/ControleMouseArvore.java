package ClassesTeste;

import java.awt.dnd.*;
import java.awt.dnd.DragGestureRecognizer;
//import ClassesTeste.CriarArvore;
import Telas.SubtelaPainelArvore;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

//Classe para controlar açoes de mouse 
	public class ControleMouseArvore implements MouseListener, DragGestureListener{
	
          
        CriarArvore arvore;
        JTree tree;
	DragSource dataSource;
        DropTarget dt;
        boolean Mousesaiu=false;
        SubtelaPainelArvore b;
	//TelaPrincipal telaP1;	
		
		public ControleMouseArvore(CriarArvore arvoreX, final SubtelaPainelArvore b){
			arvore = arvoreX;
                        tree=arvoreX.tree;
                        this.b= b;
                       // this.b= telaP1.SubTpArvore;
                        
                        dataSource = new DragSource();
                        DragGestureRecognizer dgr = dataSource.createDefaultDragGestureRecognizer(tree,DnDConstants.ACTION_COPY, this);
                        DropTarget dtOriginal = tree.getDropTarget();
		}
	
	
	    public void dragGestureRecognized(DragGestureEvent dge) {
         
	    	DefaultMutableTreeNode nozinho= (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
  
            if(nozinho.isLeaf()!=true && tree.isExpanded(new TreePath(nozinho.getPath()))==true){   
				int [] somma;
				somma = selecionaNodulos(nozinho);
				tree.setSelectionInterval(somma[0], somma[somma.length-1]);
				tree.updateUI();
            }
        }

		
		public int [] selecionaNodulos(DefaultMutableTreeNode noduloX){

			int []array = new int [noduloX.getChildCount()+1];
			array[0]= tree.getRowForPath(new TreePath(noduloX.getPath()));
			int i=1;
			Enumeration<DefaultMutableTreeNode> filhosNo= noduloX.children();
			DefaultMutableTreeNode filhinho = new DefaultMutableTreeNode();
			
			while(filhosNo.hasMoreElements()){
				filhinho = (DefaultMutableTreeNode)filhosNo.nextElement();
				array[i]=tree.getRowForPath(new TreePath(filhinho.getPath()));
				// System.out.println(array[i]);
				i++;
			}

			boolean x = tree.isExpanded(new TreePath(filhinho.getPath()));
			
			if(filhinho.isLeaf()!=true && x== true){
				array = somaArrays(array,selecionaNodulos(filhinho) );
            }

			return array;

		}

    
		public int[] somaArrays(int[]array1, int[]array2){
			int[]arraySomado = new int [array1.length + array2.length];
			int i;
			for(i=0; i <array1.length; i++){
				arraySomado[i]= array1[i];
			}
			i++;
			for(int x=1; x <array2.length; x++){
				arraySomado[i]= array2[x];
				i++;
			}
			return arraySomado;
		}

     
   
		//eventos para implementar classe mouseListener
		
		@Override
		public void mouseClicked(MouseEvent evt) {
           
	  DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
           
           arvore.node = node; //indica para a Classe "CriarArvore" qual foi o nó da Jtree selecionado, para renomear o nó no metodo renomear.
           b.trataTipoItem(node.toString());// indica para a classe "SubTelaPainelArvore" o tipo do item selecionado e o texto de titulo do item.

         // TreePath tp=arvore.getClosestPathForLocation(evt.getX(), evt.getY());
           //desabilitar botoes do SubtelaPainelArvore 
           if(node.isLeaf()&& !node.toString().endsWith("»")){
             b.btAdicionarCat.setEnabled(false);
             b.btNovaProva.setEnabled(false);
             b.btNovaQuestao.setEnabled(false);
             b.btRenomearItem.setEnabled(true);
             if(node.getLevel()!=0){
             b.btRemoveItem.setEnabled(true);}
           }
           else if(node.isRoot() || node.toString().endsWith("»") ){
             b.btAdicionarCat.setEnabled(true);
             b.btRenomearItem.setEnabled(true);
             b.btNovaProva.setEnabled(true);
             b.btNovaQuestao.setEnabled(true);
           //  arvore.node = node; //indica para a Classe "CriarArvore" qual foi o nó da Jtree selecionado, para renomear o nó no metodo renomear.
          //   b.trataTipoItem(node.toString());// indica para a classe "SubTelaPainelArvore" o tipo do item selecionado e o texto de titulo do item.
             if(node.getLevel()!=0){
             b.btRemoveItem.setEnabled(true);}
           }
           //------------------------------------------
           //abrir aba para editar uma questão/prova
           if(evt.getClickCount()==2 && node.isLeaf()){
             if(b.tipoItem=="questao"){
                 try {
                     //arvore.abrirTelaQuestao(node.toString());
                     b.criarAba();
                 } catch (IOException ex) {
                     Logger.getLogger(ControleMouseArvore.class.getName()).log(Level.SEVERE, null, ex);
                 }
}
             else if(b.tipoItem=="prova"){
                 try {
                     //arvore.abrirTelaProva(node.toString());
                     b.criarAba();
                 } catch (IOException ex) {
                     Logger.getLogger(ControleMouseArvore.class.getName()).log(Level.SEVERE, null, ex);
                 }
 }
             System.out.println(".."+node.toString());
            
             }
                      
           else {System.out.println(tree.getClosestPathForLocation(evt.getX(), evt.getY()).toString());
             System.out.println("Item Selecionado" + node.toString());
             b.trataTipoItem(node.toString());
             if(node.getLevel()==0){b.btRemoveItem.setEnabled(false);
             b.btRenomearItem.setEnabled(false);
             b.btNovaProva.setEnabled(false);
             b.btNovaQuestao.setEnabled(false);
             }
             
           }
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub
        if(Mousesaiu==false){/*tree.clearSelection();*/}
	//	  System.out.println("Mouse entrou");
                 
        Mousesaiu=false;
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
        Mousesaiu=true;
	//	  System.out.println("Mouse saiu");
        }

		@Override
		public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub
		//  System.out.println("Mouse pressionado");
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
		//  System.out.println("Mouse soltado");
		}
      
    }
	
	
