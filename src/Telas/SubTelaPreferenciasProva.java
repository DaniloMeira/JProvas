package Telas;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Danilo
 */
public class SubTelaPreferenciasProva extends javax.swing.JPanel {

  ResultSet rs;
  String textoPadrao_cabecalhoL1 ="JPROVAS - CRIADOR DE PROVAS";
  String textoPadrao_cabecalhoL2 ="Aluno/Candidato:                         Identifica��o:";
  String textoPadrao_rodapeL1 ="Prova de Conhecimentos Gerais";
  String textoPadrao_rodapeL2 ="JProvas - O Criador de Provas";
  
  boolean alterado_cabecalhoL1=false;
  boolean alterado_cabecalhoL2=false;
  boolean alterado_rodapeL1=false;
  boolean alterado_rodapeL2=false;
  
    public SubTelaPreferenciasProva() {
        initComponents();
        carregarCampos();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLabel3 = new javax.swing.JLabel();
    jTextField3 = new javax.swing.JTextField();
    jLabel4 = new javax.swing.JLabel();
    jTextField4 = new javax.swing.JTextField();
    jLabel5 = new javax.swing.JLabel();
    jTextField5 = new javax.swing.JTextField();
    jLabel6 = new javax.swing.JLabel();
    jTextField6 = new javax.swing.JTextField();
    jPanel1 = new javax.swing.JPanel();
    cabecalho_linha1 = new javax.swing.JTextField();
    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    cabecalho_linha2 = new javax.swing.JTextField();
    jPanel2 = new javax.swing.JPanel();
    jLabel7 = new javax.swing.JLabel();
    rodape_linha1 = new javax.swing.JTextField();
    jLabel8 = new javax.swing.JLabel();
    rodape_linha2 = new javax.swing.JTextField();
    botaoSalvar = new javax.swing.JButton();
    botaoCancelar = new javax.swing.JButton();
    labelRetornaPadrao = new javax.swing.JLabel();

    jLabel3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
    jLabel3.setForeground(new java.awt.Color(102, 102, 102));
    jLabel3.setText("Linha1");

    jTextField3.setText("jTextField1");

    jLabel4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
    jLabel4.setForeground(new java.awt.Color(102, 102, 102));
    jLabel4.setText("Linha2");

    jTextField4.setText("jTextField2");

    jLabel5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
    jLabel5.setForeground(new java.awt.Color(102, 102, 102));
    jLabel5.setText("Linha1");

    jTextField5.setText("jTextField1");

    jLabel6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
    jLabel6.setForeground(new java.awt.Color(102, 102, 102));
    jLabel6.setText("Linha2");

    jTextField6.setText("jTextField2");

    jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Texto Padrao do Cabecalho"));

    cabecalho_linha1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
    cabecalho_linha1.setText("jTextField1");
    cabecalho_linha1.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        cabecalho_linha1KeyReleased(evt);
      }
    });

    jLabel1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
    jLabel1.setForeground(new java.awt.Color(102, 102, 102));
    jLabel1.setText("Linha1");

    jLabel2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
    jLabel2.setForeground(new java.awt.Color(102, 102, 102));
    jLabel2.setText("Linha2");

    cabecalho_linha2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
    cabecalho_linha2.setText("jTextField2");
    cabecalho_linha2.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        cabecalho_linha2KeyReleased(evt);
      }
    });

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(cabecalho_linha1)
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel1)
              .addComponent(jLabel2))
            .addGap(0, 0, Short.MAX_VALUE))
          .addComponent(cabecalho_linha2))
        .addContainerGap())
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addGap(5, 5, 5)
        .addComponent(jLabel1)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(cabecalho_linha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(jLabel2)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(cabecalho_linha2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Texto Padrao do Rodape"));

    jLabel7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
    jLabel7.setForeground(new java.awt.Color(102, 102, 102));
    jLabel7.setText("Linha1");

    rodape_linha1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
    rodape_linha1.setText("jTextField1");
    rodape_linha1.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        rodape_linha1KeyReleased(evt);
      }
    });

    jLabel8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
    jLabel8.setForeground(new java.awt.Color(102, 102, 102));
    jLabel8.setText("Linha2");

    rodape_linha2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
    rodape_linha2.setText("jTextField2");
    rodape_linha2.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        rodape_linha2KeyReleased(evt);
      }
    });

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel2Layout.createSequentialGroup()
        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(rodape_linha1)
          .addGroup(jPanel2Layout.createSequentialGroup()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel7)
              .addComponent(jLabel8))
            .addGap(0, 0, Short.MAX_VALUE))
          .addComponent(rodape_linha2))
        .addContainerGap())
    );
    jPanel2Layout.setVerticalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel2Layout.createSequentialGroup()
        .addGap(5, 5, 5)
        .addComponent(jLabel7)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(rodape_linha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(jLabel8)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(rodape_linha2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    botaoSalvar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
    botaoSalvar.setText("Salvar");
    botaoSalvar.setEnabled(false);
    botaoSalvar.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        botaoSalvarMouseClicked(evt);
      }
    });
    botaoSalvar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        botaoSalvarActionPerformed(evt);
      }
    });

    botaoCancelar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
    botaoCancelar.setText("Cancelar");
    botaoCancelar.setEnabled(false);
    botaoCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        botaoCancelarMouseClicked(evt);
      }
    });
    botaoCancelar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        botaoCancelarActionPerformed(evt);
      }
    });

    labelRetornaPadrao.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
    labelRetornaPadrao.setForeground(new java.awt.Color(51, 51, 51));
    labelRetornaPadrao.setText("Redefinir ao valor padrao");
    labelRetornaPadrao.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        labelRetornaPadraoMouseClicked(evt);
      }
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        labelRetornaPadraoMouseEntered(evt);
      }
      public void mouseExited(java.awt.event.MouseEvent evt) {
        labelRetornaPadraoMouseExited(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
            .addComponent(labelRetornaPadrao)
            .addGap(18, 18, 18)
            .addComponent(botaoSalvar)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(botaoCancelar)
            .addGap(22, 22, 22)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(18, 18, 18)
        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(botaoSalvar)
            .addComponent(botaoCancelar))
          .addComponent(labelRetornaPadrao))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents

    private void botaoCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCancelarActionPerformed
      carregarCampos();
      botaoSalvar.setEnabled(false);
      botaoCancelar.setEnabled(false);
      TelaPreferencias.alteracoes_naoSalvas_tela1=false;
    }//GEN-LAST:event_botaoCancelarActionPerformed

  private void labelRetornaPadraoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelRetornaPadraoMouseEntered
    labelRetornaPadrao.setForeground(Color.BLUE);
  }//GEN-LAST:event_labelRetornaPadraoMouseEntered

  private void labelRetornaPadraoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelRetornaPadraoMouseExited
    labelRetornaPadrao.setForeground(Color.BLACK);
  }//GEN-LAST:event_labelRetornaPadraoMouseExited

  private void labelRetornaPadraoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelRetornaPadraoMouseClicked
    // Retornar aos valores padr�o:
    cabecalho_linha1.setText(textoPadrao_cabecalhoL1);
    cabecalho_linha2.setText(textoPadrao_cabecalhoL2);
    rodape_linha1.setText(textoPadrao_rodapeL1);
    rodape_linha2.setText(textoPadrao_rodapeL2);
    alterado_cabecalhoL1=true;
    alterado_cabecalhoL2=true;
    alterado_rodapeL1=true;
    alterado_rodapeL2=true;
    botaoSalvar.setEnabled(true);
    botaoCancelar.setEnabled(true);
    
  }//GEN-LAST:event_labelRetornaPadraoMouseClicked

  private void botaoCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoCancelarMouseClicked
   
  }//GEN-LAST:event_botaoCancelarMouseClicked

  private void botaoSalvarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoSalvarMouseClicked

  }//GEN-LAST:event_botaoSalvarMouseClicked

  private void cabecalho_linha1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cabecalho_linha1KeyReleased
    alterado_cabecalhoL1=true;
    botaoSalvar.setEnabled(true);
    botaoCancelar.setEnabled(true);
    alterado_cabecalhoL1=true;
  }//GEN-LAST:event_cabecalho_linha1KeyReleased

  private void cabecalho_linha2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cabecalho_linha2KeyReleased
    alterado_cabecalhoL2=true;
    botaoSalvar.setEnabled(true);
    botaoCancelar.setEnabled(true);
    alterado_cabecalhoL2=true;
  }//GEN-LAST:event_cabecalho_linha2KeyReleased

  private void rodape_linha1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rodape_linha1KeyReleased
    alterado_rodapeL1=true;
    botaoSalvar.setEnabled(true);
    botaoCancelar.setEnabled(true);

  }//GEN-LAST:event_rodape_linha1KeyReleased

  private void rodape_linha2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rodape_linha2KeyReleased
    alterado_rodapeL2=true;
    botaoSalvar.setEnabled(true);
    botaoCancelar.setEnabled(true);
    alterado_rodapeL1=true;
  }//GEN-LAST:event_rodape_linha2KeyReleased

  private void botaoSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSalvarActionPerformed
    if(alterado_cabecalhoL1==true){atualizaBanco(cabecalho_linha1.getText(),3);}
    if(alterado_cabecalhoL2==true){atualizaBanco(cabecalho_linha2.getText(),4);}
    if(alterado_rodapeL1==true){atualizaBanco(rodape_linha1.getText(),5);}
    if(alterado_rodapeL2==true){atualizaBanco(rodape_linha2.getText(),6);}
    botaoSalvar.setEnabled(false);
    botaoCancelar.setEnabled(false);
    alterado_rodapeL2=true;
  }//GEN-LAST:event_botaoSalvarActionPerformed

  public void carregarCampos(){
    cabecalho_linha1.setText(consultaBanco(3));
    cabecalho_linha2.setText(consultaBanco(4));
    rodape_linha1.setText(consultaBanco(5));
    rodape_linha2.setText(consultaBanco(6));
  }
  
  public String consultaBanco(int nuParametro){
    String retorno = new String();
    rs=JProvas.conexaoJProvas.executaSQLcomRetorno("select valor_parametro from TB08_SISTEMA where nu_parametro="+nuParametro);
    try {
      if(rs.next()==true){retorno=rs.getString("valor_parametro");}
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null,"Falha na fun��o consultaBanco da classe SubTelaPreferenciasProva : " + ex.getMessage(),"Erro",0);
    }
    return retorno;
  }
  
  public void atualizaBanco(String texto, int nuParametro){
    JProvas.conexaoJProvas.executaSQLsemRetorno("update TB08_SISTEMA set valor_parametro='"+texto+"' where nu_parametro="+nuParametro);
  }
  
  /*public void salvaAlteracoes(){
    if(alterado_cabecalhoL1==true){JProvas.conexaoJProvas.executaSQLsemRetorno("");}
    if(alterado_cabecalhoL2==true){}
    if(alterado_rodapeL1==true){}
    if(alterado_rodapeL2==true){}
  }*/
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton botaoCancelar;
  private javax.swing.JButton botaoSalvar;
  private javax.swing.JTextField cabecalho_linha1;
  private javax.swing.JTextField cabecalho_linha2;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JTextField jTextField3;
  private javax.swing.JTextField jTextField4;
  private javax.swing.JTextField jTextField5;
  private javax.swing.JTextField jTextField6;
  private javax.swing.JLabel labelRetornaPadrao;
  private javax.swing.JTextField rodape_linha1;
  private javax.swing.JTextField rodape_linha2;
  // End of variables declaration//GEN-END:variables
}
