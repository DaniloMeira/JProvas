package Telas;
import ClassesTeste.utils.ManipuladorArquivos;
import com.sun.awt.AWTUtilities;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import java.io.File;
import java.net.URL;

/**
 * @author Danilo Meira e Silva
 */

public class TelaLoading extends javax.swing.JFrame implements Runnable{

  
  ManipuladorArquivos mp = new ManipuladorArquivos();
  public String diretorioExecucao=System.getProperty("user.dir")+File.separator;
  public Thread instancia1;
  private JProvas telaJProvas;
  
  URL url = this.getClass().getResource("/Imagens/IconesSistema/JP32.png");  
  Image imagemTitulo = Toolkit.getDefaultToolkit().getImage(url);
  
  public TelaLoading() {
    this.setTitle("JProvas Loader");
    setIconImage(imagemTitulo);
    
        // Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(JProvas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(JProvas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(JProvas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(JProvas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
        //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
    public void run() {
      
      System.out.println("INICIANDO JPROVAS.");
      System.out.println("Verificando diretórios e arquivos");
      System.out.println("DIRETÓRIO DE EXECUÇÃO: "+diretorioExecucao);
      }
    });
       
    new Transparencia().TransCompFrame(this); 
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    initComponents();

  }

  public void run(){
    try{
      this.setLocationRelativeTo(null);
      this.setVisible(true);

      int percentualLoad=0;
    
      if (!mp.verificaDiretorioExiste("DATABASE")){mp.criarDiretorio("DATABASE");}
      if (!mp.verificaArquivoExiste("DATABASE/BancoJProvas.mdb")){mp.extrairRecursoJar("/Recursos/","BancoJProvas.mdb","DATABASE/");System.out.println("Arquivo '/Recursos/BancoJProvas.mdb' criado");}
      
      percentualLoad+=23;
      barraLoad.setValue(percentualLoad);
      instancia1.sleep(180);
      if(!mp.verificaDiretorioExiste("HTML")){mp.criarDiretorio("HTML");System.out.println("Diretorio 'HTML' criado");}
      if(!mp.verificaDiretorioExiste("HTML/CSS")){mp.criarDiretorio("HTML/CSS");System.out.println("Diretorio 'HTML/CSS' criado");}
      percentualLoad+=23;
      barraLoad.setValue(percentualLoad);
      instancia1.sleep(180);
      if(!mp.verificaDiretorioExiste("HTML/PDF_paginas")){mp.criarDiretorio("HTML/PDF_paginas");System.out.println("Diretorio 'HTML/PDF_paginas' criado");}
      percentualLoad+=23;
      barraLoad.setValue(percentualLoad);
      instancia1.sleep(180);
      if(!mp.verificaDiretorioExiste("HTML/Fontes")){mp.criarDiretorio("HTML/Fontes");System.out.println("Diretorio 'HTML/Fontes' criado");}
      if(!mp.verificaDiretorioExiste("HTML/Imagens")){mp.criarDiretorio("HTML/Imagens");System.out.println("Diretorio 'HTML/Imagens' criado");}
      if(!mp.verificaDiretorioExiste("HTML/Imagens/Imagens_Questoes")){mp.criarDiretorio("HTML/Imagens/Imagens_Questoes");System.out.println("Diretorio 'HTML/Imagens/Imagens_Questoes' criado");}
      if(!mp.verificaDiretorioExiste("HTML/Imagens/Imagens_Provas")){mp.criarDiretorio("HTML/Imagens/Imagens_Provas");System.out.println("Diretorio 'HTML/Imagens/Imagens_Provas' criado");}
      percentualLoad+=13;
      barraLoad.setValue(percentualLoad);
      instancia1.sleep(50);
      if(!mp.verificaArquivoExiste("/HTML/CSS/DivTeste.css")){mp.extrairRecursoJar("/HTML/CSS/","DivTeste.css","HTML/CSS/");System.out.println("Arquivo '/HTML/CSS/DivTeste.css' criado");}
      if(!mp.verificaArquivoExiste("/HTML/Fontes/arial.ttf")){mp.extrairRecursoJar("/HTML/Fontes/","arial.ttf","HTML/Fontes/");System.out.println("Arquivo '/HTML/Fontes/arial.ttf' criado");}
      if(!mp.verificaArquivoExiste("/HTML/Fontes/arialbd.ttf")){mp.extrairRecursoJar("/HTML/Fontes/","arialbd.ttf","HTML/Fontes/");System.out.println("Arquivo '/HTML/Fontes/arial.ttf' criado");}
      if(!mp.verificaArquivoExiste("/HTML/Fontes/arialbi.ttf")){mp.extrairRecursoJar("/HTML/Fontes/","arialbi.ttf","HTML/Fontes/");System.out.println("Arquivo '/HTML/Fontes/arialbi.ttf' criado");}
      if(!mp.verificaArquivoExiste("/HTML/Fontes/ariali.ttf")){mp.extrairRecursoJar("/HTML/Fontes/","ariali.ttf","HTML/Fontes/");System.out.println("Arquivo '/HTML/Fontes/ariali.ttf' criado");}
      if(!mp.verificaArquivoExiste("/HTML/Fontes/times.ttf")){mp.extrairRecursoJar("/HTML/Fontes/","times.ttf","HTML/Fontes/");System.out.println("Arquivo '/HTML/Fontes/times.ttf' criado");}
      if(!mp.verificaArquivoExiste("/HTML/Fontes/timesbd.ttf")){mp.extrairRecursoJar("/HTML/Fontes/","timesbd.ttf","HTML/Fontes/");System.out.println("Arquivo '/HTML/Fontes/timesbd.ttf' criado");}
      if(!mp.verificaArquivoExiste("/HTML/Fontes/timesbi.ttf")){mp.extrairRecursoJar("/HTML/Fontes/","timesbi.ttf","HTML/Fontes/");System.out.println("Arquivo '/HTML/Fontes/timesbi.ttf' criado");}
      if(!mp.verificaArquivoExiste("/HTML/Fontes/timesi.ttf")){mp.extrairRecursoJar("/HTML/Fontes/","timesi.ttf","HTML/Fontes/");System.out.println("Arquivo '/HTML/Fontes/timesi.ttf' criado");}
      
      telaJProvas= new JProvas();
      telaJProvas.setExtendedState(JFrame.MAXIMIZED_BOTH); 
      barraLoad.setValue(100);
      instancia1.sleep(100);
      telaJProvas.setVisible(true);
      this.dispose();  

  }

  catch (Exception e){
    System.out.println(""+e);
  }

} 
  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLabel1 = new javax.swing.JLabel();
    barraLoad = new javax.swing.JProgressBar();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/IconesSistema/LogoTipoJProvas_loading.png"))); // NOI18N

    barraLoad.setStringPainted(true);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(barraLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(65, 65, 65))
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jLabel1)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jLabel1)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(barraLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

 
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JProgressBar barraLoad;
  private javax.swing.JLabel jLabel1;
  // End of variables declaration//GEN-END:variables
}


class Transparencia {

  public void TransCompFrame(JFrame frm) {
    frm.setUndecorated(true);
    AWTUtilities.setWindowOpaque(frm, false);
  }
  
}