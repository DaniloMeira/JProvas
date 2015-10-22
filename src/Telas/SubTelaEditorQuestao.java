/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Telas;

import ClassesTeste.dao.DAOquestao;
import ClassesTeste.utils.FiltroExtensoesImagem;
import ClassesTeste.utils.ManipuladorArquivos;
import ClassesTeste.utils.ManipuladorImagem;
import ClassesTeste.utils.PreviewImagem;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

/**
 *
 * @author Danilo
 */
public class SubTelaEditorQuestao extends javax.swing.JPanel {

SubtelaPainelArvore sbtPnArvore;  
DAOquestao daoQuest_sbtEditQuest;   
char charSeparador = File.separatorChar;    
JFileChooser jf;  
public int codQuestao;
public int tipoQuestao;
Border bordaErro = BorderFactory.createLineBorder(Color.RED, 2);

//--armazena o conteudo original para verificar se houve alteração
public String questA, questB, questC, questD, questE;
public String linhasResp;
public String campoQuest, respost, imagemQuestao, novaImagemQuestao;
File arquivoImagem;

Icon icon_removeImg_released = new javax.swing.ImageIcon(getClass().getResource("/Imagens/IconesSistema/iconeFechar_24.png"));
Icon icon_removeImg_pressed = new javax.swing.ImageIcon(getClass().getResource("/Imagens/IconesSistema/iconeFechar2_24.png"));

//public String diretorioImagens=System.getProperty("user.dir")+"\\src\\Imagens\\Imagens_Questoes\\";
public String diretorioImagens=System.getProperty("user.dir")+charSeparador+"HTML"+charSeparador+"Imagens"+charSeparador+"Imagens_Questoes"+charSeparador;

//-indicadores de alteração nos campos
public boolean questA_alterado=false, questB_alterado=false, questC_alterado=false, 
       questD_alterado=false, questE_alterado=false;
public boolean tipoQuest_alterado=false, respost_alterado=false, campoQuest_alterado=false, linhasResp_alterado=false;
private boolean imagemQuestao_removido=false, imagemQuestao_alterado=false;
  
public ManipuladorImagem mp = new ManipuladorImagem();
    /**
     * Creates new form SubTelaEditorQuestao
     */
    public SubTelaEditorQuestao(DAOquestao daoQuestao) {
        initComponents();
        daoQuest_sbtEditQuest=daoQuestao;
        campoTamResp.setVisible(false);
        //btCancelarx.setEnabled(false);
         
    }

    //caso haja erro na abertura de uma questao, todos os campos aparecem desabilitados
    public void desabilitaCampos(){
         jtCampoQuest.setEnabled(false);
         tQuestaoA.setEnabled(false);
         tQuestaoB.setEnabled(false);
         tQuestaoC.setEnabled(false);
         tQuestaoD.setEnabled(false);
         comboTpQuestao.setEnabled(false);
         comboResposta.setEnabled(false);
         LbTituloQuest.setBackground(Color.gray);
    }
    
   public void habilitaCampos(){
         jtCampoQuest.setEnabled(true);
         tQuestaoA.setEnabled(true);
         tQuestaoB.setEnabled(true);
         tQuestaoC.setEnabled(true);
         tQuestaoD.setEnabled(true);
         comboTpQuestao.setEnabled(true);
         comboResposta.setEnabled(true);
    }
    
   public void modoQuestaoDissertativa(){
         habilitaCampos();
         comboTpQuestao.setSelectedIndex(1);
         comboTpQuestao.setVisible(true);
         lbTamanCampoResp.setVisible(true);
         campoTamResp.setVisible(true);
         tQuestaoA.setVisible(false);
         tQuestaoB.setVisible(false);
         tQuestaoC.setVisible(false);
         tQuestaoD.setVisible(false);
         tQuestaoE.setVisible(false);
         lbA.setVisible(false);
         lbB.setVisible(false);
         lbC.setVisible(false);
         lbD.setVisible(false);
         lbE.setVisible(false);
         jPanelAlternA.setVisible(false);
         jPanelAlternB.setVisible(false);
         jPanelAlternC.setVisible(false);
         jPanelAlternD.setVisible(false);
         jPanelAlternE.setVisible(false);
         labelResposta.setVisible(false);
         comboResposta.setVisible(false);
         
   }
   
      public void modoQuestaoAlternativa(){
         comboTpQuestao.setSelectedIndex(0);
         comboTpQuestao.setVisible(true);
         campoTamResp.setVisible(false);
         lbTamanCampoResp.setVisible(false);
         tQuestaoA.setVisible(true);
         tQuestaoB.setVisible(true);
         tQuestaoC.setVisible(true);
         tQuestaoD.setVisible(true);
         tQuestaoE.setVisible(true);
         lbA.setVisible(true);
         lbB.setVisible(true);
         lbC.setVisible(true);
         lbD.setVisible(true);
         lbE.setVisible(true);
         jPanelAlternA.setVisible(true);
         jPanelAlternB.setVisible(true);
         jPanelAlternC.setVisible(true);
         jPanelAlternD.setVisible(true);
         jPanelAlternE.setVisible(true);
         labelResposta.setVisible(true);
         comboResposta.setVisible(true);
         respost=comboResposta.getSelectedItem().toString();
         btCancelar.setEnabled(false);
   }
   
   public void limpaCampos(){
     String campoLimpo = "----------";
     jtCampoQuest.setText(campoLimpo);
     tQuestaoA.setText(campoLimpo);
     tQuestaoB.setText(campoLimpo);
     tQuestaoC.setText(campoLimpo);
     tQuestaoD.setText(campoLimpo);
     tQuestaoE.setText(campoLimpo);
     comboResposta.setSelectedIndex(0);
     questA=campoLimpo;
     questB=campoLimpo;
     questC=campoLimpo;
     questD=campoLimpo;
     questE=campoLimpo;
     campoQuest=campoLimpo;
     respost=comboResposta.getSelectedItem().toString();
     
   jtCampoQuest.setBorder(null);
   tQuestaoA.setBorder(null);
   tQuestaoB.setBorder(null);
   tQuestaoC.setBorder(null);
   tQuestaoD.setBorder(null);
   tQuestaoE.setBorder(null);
   }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        textField3 = new java.awt.TextField();
        LbTituloQuest = new java.awt.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtCampoQuest = new javax.swing.JTextArea();
        labelResposta = new java.awt.Label();
        lbTpQuestao = new javax.swing.JLabel();
        comboTpQuestao = new javax.swing.JComboBox();
        comboResposta = new javax.swing.JComboBox();
        lbTamanCampoResp = new javax.swing.JLabel();
        jPanelAlternA = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lbA = new javax.swing.JLabel();
        jPanelAlternC = new javax.swing.JPanel();
        lbC = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanelAlternD = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        lbD = new javax.swing.JLabel();
        btAlterarImgQuestao = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        labelImgQuestao = new javax.swing.JLabel();
        jPanelAlternB = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        lbB = new javax.swing.JLabel();
        jPanelAlternE = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        lbE = new javax.swing.JLabel();
        campoTamResp = new javax.swing.JFormattedTextField();
        btRemoverImgQuestao = new javax.swing.JButton();
        btSalvarQuest = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("B)");

        textField3.setText("textField2");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textField3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(textField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addContainerGap())
        );

        LbTituloQuest.setAlignment(java.awt.Label.CENTER);
        LbTituloQuest.setBackground(new java.awt.Color(51, 153, 255));
        LbTituloQuest.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        LbTituloQuest.setForeground(new java.awt.Color(255, 255, 255));
        LbTituloQuest.setText("TITULO DA QUESTAO");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jtCampoQuest.setColumns(20);
        jtCampoQuest.setLineWrap(true);
        jtCampoQuest.setRows(5);
        jtCampoQuest.setMinimumSize(new java.awt.Dimension(169, 94));
        jtCampoQuest.setName(""); // NOI18N
        jtCampoQuest.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtCampoQuestFocusLost(evt);
            }
        });
        jtCampoQuest.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtCampoQuestKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtCampoQuest);

        labelResposta.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N
        labelResposta.setText("Resposta:");

        lbTpQuestao.setText("Tipo questão:");

        comboTpQuestao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "alternativa", "dissertativa", " " }));
        comboTpQuestao.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboTpQuestaoItemStateChanged(evt);
            }
        });
        comboTpQuestao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTpQuestaoActionPerformed(evt);
            }
        });

        comboResposta.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "a", "b", "c", "d", "e" }));
        comboResposta.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboRespostaItemStateChanged(evt);
            }
        });
        comboResposta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                comboRespostaFocusLost(evt);
            }
        });
        comboResposta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboRespostaMouseClicked(evt);
            }
        });

        lbTamanCampoResp.setText("Quantidade de linhas para a resposta:");
        lbTamanCampoResp.setToolTipText("");

        jPanelAlternA.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelAlternA.setMinimumSize(new java.awt.Dimension(203, 50));

        tQuestaoA.setColumns(20);
        tQuestaoA.setRows(1);
        tQuestaoA.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tQuestaoAFocusLost(evt);
            }
        });
        tQuestaoA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tQuestaoAKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tQuestaoA);

        lbA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbA.setText("A)");

        javax.swing.GroupLayout jPanelAlternALayout = new javax.swing.GroupLayout(jPanelAlternA);
        jPanelAlternA.setLayout(jPanelAlternALayout);
        jPanelAlternALayout.setHorizontalGroup(
            jPanelAlternALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAlternALayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbA)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2))
        );
        jPanelAlternALayout.setVerticalGroup(
            jPanelAlternALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(lbA, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
        );

        jPanelAlternC.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelAlternC.setMinimumSize(new java.awt.Dimension(203, 50));
        jPanelAlternC.setPreferredSize(new java.awt.Dimension(203, 50));

        lbC.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbC.setText("C)");

        tQuestaoC.setColumns(20);
        tQuestaoC.setRows(1);
        tQuestaoC.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tQuestaoCFocusLost(evt);
            }
        });
        tQuestaoC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tQuestaoCKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tQuestaoC);

        javax.swing.GroupLayout jPanelAlternCLayout = new javax.swing.GroupLayout(jPanelAlternC);
        jPanelAlternC.setLayout(jPanelAlternCLayout);
        jPanelAlternCLayout.setHorizontalGroup(
            jPanelAlternCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAlternCLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbC)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4))
        );
        jPanelAlternCLayout.setVerticalGroup(
            jPanelAlternCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanelAlternCLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbC, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanelAlternD.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelAlternD.setMinimumSize(new java.awt.Dimension(203, 50));
        jPanelAlternD.setPreferredSize(new java.awt.Dimension(203, 50));

        tQuestaoD.setColumns(20);
        tQuestaoD.setRows(1);
        tQuestaoD.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tQuestaoDFocusLost(evt);
            }
        });
        tQuestaoD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tQuestaoDKeyReleased(evt);
            }
        });
        jScrollPane5.setViewportView(tQuestaoD);

        lbD.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbD.setText("D)");

        javax.swing.GroupLayout jPanelAlternDLayout = new javax.swing.GroupLayout(jPanelAlternD);
        jPanelAlternD.setLayout(jPanelAlternDLayout);
        jPanelAlternDLayout.setHorizontalGroup(
            jPanelAlternDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAlternDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5))
        );
        jPanelAlternDLayout.setVerticalGroup(
            jPanelAlternDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAlternDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbD, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        btAlterarImgQuestao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/IconesSistema/add_imag-24.png"))); // NOI18N
        btAlterarImgQuestao.setToolTipText("Alterar a imagem para incluir a marca da Instituição nos cabeçalhos da prova");
        btAlterarImgQuestao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterarImgQuestaoActionPerformed(evt);
            }
        });

        jLabel2.setText("Alterar Imagem");
        jLabel2.setToolTipText("");

        labelImgQuestao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelImgQuestao.setText("IMAGEM DA QUESTÃO");
        labelImgQuestao.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        labelImgQuestao.setPreferredSize(new java.awt.Dimension(323, 191));

        jPanelAlternB.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelAlternB.setMinimumSize(new java.awt.Dimension(203, 50));

        tQuestaoB.setColumns(20);
        tQuestaoB.setRows(1);
        tQuestaoB.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tQuestaoBFocusLost(evt);
            }
        });
        tQuestaoB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tQuestaoBKeyReleased(evt);
            }
        });
        jScrollPane7.setViewportView(tQuestaoB);

        lbB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbB.setText("B)");

        javax.swing.GroupLayout jPanelAlternBLayout = new javax.swing.GroupLayout(jPanelAlternB);
        jPanelAlternB.setLayout(jPanelAlternBLayout);
        jPanelAlternBLayout.setHorizontalGroup(
            jPanelAlternBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAlternBLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7))
        );
        jPanelAlternBLayout.setVerticalGroup(
            jPanelAlternBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(lbB, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
        );

        jPanelAlternE.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelAlternE.setMinimumSize(new java.awt.Dimension(203, 50));

        tQuestaoE.setColumns(20);
        tQuestaoE.setRows(1);
        tQuestaoE.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tQuestaoEFocusLost(evt);
            }
        });
        tQuestaoE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tQuestaoEKeyReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tQuestaoE);

        lbE.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbE.setText("E)");

        javax.swing.GroupLayout jPanelAlternELayout = new javax.swing.GroupLayout(jPanelAlternE);
        jPanelAlternE.setLayout(jPanelAlternELayout);
        jPanelAlternELayout.setHorizontalGroup(
            jPanelAlternELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAlternELayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6))
        );
        jPanelAlternELayout.setVerticalGroup(
            jPanelAlternELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAlternELayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbE, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        campoTamResp.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        campoTamResp.setText("1");
        campoTamResp.setToolTipText("Informe até 60 linhas para o espaço de resposta");
        campoTamResp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoTamRespFocusLost(evt);
            }
        });
        campoTamResp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                campoTamRespKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoTamRespKeyTyped(evt);
            }
        });

        btRemoverImgQuestao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/IconesSistema/iconeFechar_24.png"))); // NOI18N
        btRemoverImgQuestao.setToolTipText("deletar imagem");
        btRemoverImgQuestao.setBorderPainted(false);
        btRemoverImgQuestao.setContentAreaFilled(false);
        btRemoverImgQuestao.setPreferredSize(new java.awt.Dimension(51, 20));
        btRemoverImgQuestao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btRemoverImgQuestaoMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btRemoverImgQuestaoMouseReleased(evt);
            }
        });
        btRemoverImgQuestao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoverImgQuestaoActionPerformed(evt);
            }
        });

        btSalvarQuest.setText("SALVAR");
        btSalvarQuest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarQuestActionPerformed(evt);
            }
        });

        btCancelar.setText("CANCELAR");
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
            }
        });

        jLabel1.setText("Remover Imagem");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanelAlternE, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelAlternA, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LbTituloQuest, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(lbTpQuestao)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(comboTpQuestao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(lbTamanCampoResp)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(campoTamResp, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(6, 6, 6))
                            .addComponent(jPanelAlternC, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                            .addComponent(jPanelAlternD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                            .addComponent(jPanelAlternB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(21, 21, 21))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelResposta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(comboResposta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(236, 301, Short.MAX_VALUE)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelImgQuestao, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btAlterarImgQuestao, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(21, 21, 21)
                        .addComponent(btRemoverImgQuestao, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btSalvarQuest)
                        .addGap(25, 25, 25)
                        .addComponent(btCancelar)))
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(labelImgQuestao, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btAlterarImgQuestao, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(btRemoverImgQuestao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1))
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btCancelar)
                            .addComponent(btSalvarQuest))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(LbTituloQuest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbTpQuestao)
                            .addComponent(comboTpQuestao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbTamanCampoResp, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoTamResp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addComponent(jPanelAlternA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanelAlternB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanelAlternC, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanelAlternD, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelAlternE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelResposta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboResposta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(55, 55, 55))))
        );
    }// </editor-fold>//GEN-END:initComponents

    
    
  private void comboTpQuestaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTpQuestaoActionPerformed

    if(comboTpQuestao.getSelectedIndex()==0){
      modoQuestaoAlternativa();
    }
    else{
         modoQuestaoDissertativa();
    }
    
    if(comboTpQuestao.getSelectedIndex()!=tipoQuestao){
      tipoQuest_alterado=true;
      btSalvarQuest.setEnabled(true);
      btCancelar.setEnabled(true);
    }
    else {
      tipoQuest_alterado=false;
      if(!verificaAlteracoes()){
        btSalvarQuest.setEnabled(false);
        btCancelar.setEnabled(false);
      }
    }
    
    
  }//GEN-LAST:event_comboTpQuestaoActionPerformed

  
  private void jtCampoQuestFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtCampoQuestFocusLost
  }//GEN-LAST:event_jtCampoQuestFocusLost

  private void comboRespostaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_comboRespostaFocusLost
    
  }//GEN-LAST:event_comboRespostaFocusLost

  private void btAlterarImgQuestaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarImgQuestaoActionPerformed
    //     telaAbrirArqSbtEditorProva = new TelaAbrirArquivo();
    //     telaAbrirArqSbtEditorProva.setVisible(true);
    jf = new JFileChooser();
    if(JProvas.JProvas_DAOquestao.obtemDiretorioImagens()!=null){
    File diretorioImagens = new File(JProvas.JProvas_DAOquestao.obtemDiretorioImagens());
    jf.setCurrentDirectory(diretorioImagens);}
    jf.setAccessory(new PreviewImagem(jf,210,50));
    jf.setAcceptAllFileFilterUsed(false);
    jf.addChoosableFileFilter(new FiltroExtensoesImagem());
    int returnVal=jf.showOpenDialog(this);

    File arquivo = jf.getSelectedFile();

    if (returnVal == jf.APPROVE_OPTION) {
      arquivoImagem = jf.getSelectedFile();
      //This is where a real application would open the file.
      System.out.println("Opening: " + arquivoImagem.getName());
      //ManipuladorImagem mp = new ManipuladorImagem(file,323,191);
      
      mp.imagem=arquivoImagem;
      mp.dimensionaQuadroEditor(323,191);
      labelImgQuestao.setIcon(mp.thumbnail);
      labelImgQuestao.setText(null);
      
     
      novaImagemQuestao=arquivoImagem.getName();
      novaImagemQuestao=novaImagemQuestao.replace(" ","_");
      novaImagemQuestao=novaImagemQuestao.replace("&","e");
      String extensao=novaImagemQuestao.substring(novaImagemQuestao.indexOf("."));
      novaImagemQuestao=novaImagemQuestao.substring(0,novaImagemQuestao.indexOf("."))+"_quest"+codQuestao+extensao;
      
      imagemQuestao_alterado=true;
      btSalvarQuest.setEnabled(true);
      btCancelar.setEnabled(true);

    }
    else {
      System.out.println("Operação de seleção da imagem foi cancelada pelo usuario.");
    }
    
  }//GEN-LAST:event_btAlterarImgQuestaoActionPerformed

    private void tQuestaoAFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tQuestaoAFocusLost

    }//GEN-LAST:event_tQuestaoAFocusLost

    private void tQuestaoBFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tQuestaoBFocusLost

    }//GEN-LAST:event_tQuestaoBFocusLost

    private void tQuestaoCFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tQuestaoCFocusLost

    }//GEN-LAST:event_tQuestaoCFocusLost

    private void tQuestaoDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tQuestaoDFocusLost
        
    }//GEN-LAST:event_tQuestaoDFocusLost

    private void tQuestaoEFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tQuestaoEFocusLost
       
    }//GEN-LAST:event_tQuestaoEFocusLost

  private void campoTamRespKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoTamRespKeyTyped

  }//GEN-LAST:event_campoTamRespKeyTyped

  private void campoTamRespKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoTamRespKeyReleased
    String data=campoTamResp.getText();  
        try{  
            int val=Integer.parseInt(data);  
            if(val>60){  
              campoTamResp.setText("60");      
            }  
        }  
        catch(Exception e){}      // TODO add your handling code here:
    if(campoTamResp.getText()!=linhasResp){
      linhasResp_alterado=true;
      btSalvarQuest.setEnabled(true);
      btCancelar.setEnabled(true);
    }
    else{
      linhasResp_alterado=false;
      if(!verificaAlteracoes()){
        btSalvarQuest.setEnabled(false);
        btCancelar.setEnabled(false);     
      }
    }
    
  }//GEN-LAST:event_campoTamRespKeyReleased

  private void campoTamRespFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoTamRespFocusLost
    
  }//GEN-LAST:event_campoTamRespFocusLost

  private void jtCampoQuestKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtCampoQuestKeyReleased
      jtCampoQuest.setBorder(null);
      if(campoQuest.compareTo(jtCampoQuest.getText())!=0){
         System.out.println("CampoQuestAltaerado = "+campoQuest+" ,jtCampoQuest= "+jtCampoQuest.getText());
         //JOptionPane.showMessageDialog(null,"teste :alterou o texto da questao" );
         campoQuest_alterado=true;
         btSalvarQuest.setEnabled(true);
         btCancelar.setEnabled(true);
       }
       else{
         campoQuest_alterado=false;
         if(!verificaAlteracoes()){
           btSalvarQuest.setEnabled(false);
           btCancelar.setEnabled(false);
         }
       }
  }//GEN-LAST:event_jtCampoQuestKeyReleased

    private void tQuestaoEKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tQuestaoEKeyReleased
      if(questE.compareTo(tQuestaoE.getText())!=0){
         System.out.println("CampoQuestAlterado = "+questE+" ,jtCampoQuest= "+tQuestaoE.getText());
         //JOptionPane.showMessageDialog(null,"alterou o texto da questao E" );
         questE_alterado=true;
         btSalvarQuest.setEnabled(true);
         btCancelar.setEnabled(true);
      }
      else{
         questE_alterado=false;
         if(!verificaAlteracoes()){
          btSalvarQuest.setEnabled(false);
          btCancelar.setEnabled(false);
         }
      }
    }//GEN-LAST:event_tQuestaoEKeyReleased

    private void tQuestaoDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tQuestaoDKeyReleased
       if(questD.compareTo(tQuestaoD.getText())!=0){
         System.out.println("CampoQuestAlterado = "+questD+" ,jtCampoQuest= "+tQuestaoD.getText());
         //JOptionPane.showMessageDialog(null,"alterou o texto da questao D" );
         questD_alterado=true;
         btSalvarQuest.setEnabled(true);
         btCancelar.setEnabled(true);
        }
      else{
         questD_alterado=false;
        if(!verificaAlteracoes()){
          btSalvarQuest.setEnabled(false);
          btCancelar.setEnabled(false);
        }
      }
    }//GEN-LAST:event_tQuestaoDKeyReleased

    private void tQuestaoCKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tQuestaoCKeyReleased
      if(questC.compareTo(tQuestaoC.getText())!=0){
         System.out.println("CampoQuestAlterado = "+questC+" ,jtCampoQuest= "+tQuestaoC.getText());
        // JOptionPane.showMessageDialog(null,"alterou o texto da questao C" );
         questC_alterado=true;
         btSalvarQuest.setEnabled(true);
         btCancelar.setEnabled(true);
      }
      else{
         questC_alterado=false;
        if(!verificaAlteracoes()){
          btSalvarQuest.setEnabled(false);
          btCancelar.setEnabled(false);
        }
      }
    }//GEN-LAST:event_tQuestaoCKeyReleased

    private void tQuestaoBKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tQuestaoBKeyReleased
      if(questB.compareTo(tQuestaoB.getText())!=0){
         System.out.println("CampoQuestAlterado = "+questB+" ,jtCampoQuest= "+tQuestaoB.getText());
        // JOptionPane.showMessageDialog(null,"alterou o texto da questao B" );
         questB_alterado=true;
         btSalvarQuest.setEnabled(true);
         btCancelar.setEnabled(true);
      }
      else{
         questB_alterado=false;
        if(!verificaAlteracoes()){
          btSalvarQuest.setEnabled(false);
          btCancelar.setEnabled(false);
        }
      }
    }//GEN-LAST:event_tQuestaoBKeyReleased

    private void tQuestaoAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tQuestaoAKeyReleased
      if(questA.compareTo(tQuestaoA.getText())!=0){
         System.out.println("CampoQuestAlterado = "+questA+" ,jtCampoQuest= "+tQuestaoA.getText());
       //  JOptionPane.showMessageDialog(null,"alterou o texto da questao A" );
         questA_alterado=true;
         btSalvarQuest.setEnabled(true);
         btCancelar.setEnabled(true);
      }
      else{
         questA_alterado=false;
        if(!verificaAlteracoes()){
          btSalvarQuest.setEnabled(false);
          btCancelar.setEnabled(false);
        }
      }        
    }//GEN-LAST:event_tQuestaoAKeyReleased

    private void comboRespostaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboRespostaItemStateChanged
      if(comboResposta.getSelectedItem().toString().compareTo(respost)!=0){
      //System.out.println("resposta alterada = "+respost+" ,combo resposta= "+comboResposta.getSelectedItem().toString());
      //JOptionPane.showMessageDialog(null,"alterou a resposta da questao" );
      respost_alterado=true;
      btSalvarQuest.setEnabled(true);
      btCancelar.setEnabled(true);
      }
      else{
        respost_alterado=false;
        if(!verificaAlteracoes()){
          btSalvarQuest.setEnabled(false);
          btCancelar.setEnabled(false);
        }
      }
    }//GEN-LAST:event_comboRespostaItemStateChanged

    private void comboRespostaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboRespostaMouseClicked

    }//GEN-LAST:event_comboRespostaMouseClicked

    private void btRemoverImgQuestaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoverImgQuestaoActionPerformed
        if(imagemQuestao!=null){
        labelImgQuestao.setIcon(null);
        labelImgQuestao.setText("IMAGEM DA QUESTÃO");
        removerArqImagem();
        imagemQuestao_removido=true;
        btSalvarQuest.setEnabled(true);
        btCancelar.setEnabled(true);
      } 
        else if(labelImgQuestao.getIcon()!=null){
          labelImgQuestao.setIcon(null);
          labelImgQuestao.setText("IMAGEM DA QUESTÃO");
          imagemQuestao_alterado=false;
          if(questA_alterado==false & questB_alterado==false & questC_alterado==false & questD_alterado==false & 
             questE_alterado==false & tipoQuest_alterado==false & respost_alterado==false &
             campoQuest_alterado==false & linhasResp_alterado==false /*& imagemQuestao_removido==false*/){
                    
               btSalvarQuest.setEnabled(false);
               btCancelar.setEnabled(false);
          }
        }
    }//GEN-LAST:event_btRemoverImgQuestaoActionPerformed

  private void comboTpQuestaoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboTpQuestaoItemStateChanged
    
  }//GEN-LAST:event_comboTpQuestaoItemStateChanged

  private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
    limpaCampos();
   // desabilitaCampos();

    daoQuest_sbtEditQuest.sbtEditQ = this;
    try {
        daoQuest_sbtEditQuest.carregarQuestao(Integer.toString(codQuestao));
    } catch (FileNotFoundException ex) {
        Logger.getLogger(SubTelaEditorQuestao.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(SubTelaEditorQuestao.class.getName()).log(Level.SEVERE, null, ex);
    }
    retirarImagem();
   // daoQuest_sbtEditQuest.carregarImagemBlob(codQuestao);
    exibirImagem();
    btCancelar.setEnabled(false);
    btSalvarQuest.setEnabled(false);
    //chamar metodo para desabilitar tudo    // TODO add your handling code here:
  }//GEN-LAST:event_btCancelarActionPerformed

  private void btSalvarQuestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarQuestActionPerformed
      //verificar tags digitadas no texto da questao
      if(!SubtelaPainelArvore.validTag.validaTagDigitada(jtCampoQuest.getText())){jtCampoQuest.setBorder(bordaErro);JOptionPane.showMessageDialog(null,"QUESTÃO NÃO FOI SALVA, REALIZE AS CORREÇÕES E TENTE NOVAMENTE.","QUESTÃO INVÁLIDA",0);return;}else jtCampoQuest.setBorder(null);
               
      //validar digitação das tags antes de salvar:
      if(comboTpQuestao.getSelectedIndex()==0){
      if(!SubtelaPainelArvore.validTag.validaTagDigitada(tQuestaoA.getText())){tQuestaoA.setBorder(bordaErro);JOptionPane.showMessageDialog(null,"QUESTÃO NÃO FOI SALVA, REALIZE AS CORREÇÕES E TENTE NOVAMENTE.","QUESTÃO INVÁLIDA",0);return;} else tQuestaoA.setBorder(null);
      if(!SubtelaPainelArvore.validTag.validaTagDigitada(tQuestaoB.getText())){tQuestaoB.setBorder(bordaErro);JOptionPane.showMessageDialog(null,"QUESTÃO NÃO FOI SALVA, REALIZE AS CORREÇÕES E TENTE NOVAMENTE.","QUESTÃO INVÁLIDA",0);return;} else tQuestaoB.setBorder(null);
      if(!SubtelaPainelArvore.validTag.validaTagDigitada(tQuestaoC.getText())){tQuestaoC.setBorder(bordaErro);JOptionPane.showMessageDialog(null,"QUESTÃO NÃO FOI SALVA, REALIZE AS CORREÇÕES E TENTE NOVAMENTE.","QUESTÃO INVÁLIDA",0);return;} else tQuestaoC.setBorder(null);
      if(!SubtelaPainelArvore.validTag.validaTagDigitada(tQuestaoD.getText())){tQuestaoD.setBorder(bordaErro);JOptionPane.showMessageDialog(null,"QUESTÃO NÃO FOI SALVA, REALIZE AS CORREÇÕES E TENTE NOVAMENTE.","QUESTÃO INVÁLIDA",0);return;} else tQuestaoD.setBorder(null);
      if(!SubtelaPainelArvore.validTag.validaTagDigitada(tQuestaoE.getText())){tQuestaoE.setBorder(bordaErro);JOptionPane.showMessageDialog(null,"QUESTÃO NÃO FOI SALVA, REALIZE AS CORREÇÕES E TENTE NOVAMENTE.","QUESTÃO INVÁLIDA",0);return;} else tQuestaoE.setBorder(null);
      }
      
      
      //mudou de pergunta de alternativas para pergunta dissertativa 
       if(comboTpQuestao.getSelectedIndex()==1 && tipoQuestao ==0){
          //remover do banco de dados todas as alternativas relacionadas a esta questao
           daoQuest_sbtEditQuest.excluirAlternativas(codQuestao);
         //alterar para dissertativa
           daoQuest_sbtEditQuest.alterarModalidade(codQuestao,"dis");
         //salvar numero de linhas da resposta
           daoQuest_sbtEditQuest.alterarLinhasResposta(campoTamResp.getText(),codQuestao);
           
         //salvar alteração na descrição da questão
          if(campoQuest_alterado==true){
             campoQuest=jtCampoQuest.getText();
             campoQuest_alterado=false;
             daoQuest_sbtEditQuest.alterarTextoQuestao(codQuestao,campoQuest);  
          }
          tipoQuestao=1;
        }
       
       //pergunta já era do tipo dissertativa mas foi modificado alguma coisa nela
       else if(comboTpQuestao.getSelectedIndex()==1 && tipoQuestao ==1){
         //salvar alteração na descrição da questão 
         if(campoQuest_alterado==true){
            campoQuest=jtCampoQuest.getText();
            campoQuest_alterado=false;
            daoQuest_sbtEditQuest.alterarTextoQuestao(codQuestao, campoQuest);
         }
          if(linhasResp_alterado=true){
            daoQuest_sbtEditQuest.alterarLinhasResposta(campoTamResp.getText(),codQuestao);
            linhasResp_alterado=false;
          }
          //salvar numero de linhas if numlinhas=alterado 
         
       }
       
    //salvar modificações em uma questao de alternativas
       //se era dissertativa e mudou para alternativa
       else if(comboTpQuestao.getSelectedIndex()==0 && tipoQuestao ==1){
         //criar no banco todas as alternativas relacionadas a esta questao
           daoQuest_sbtEditQuest.gravarAlternativas(codQuestao);
         //alterar para alternativa e incluir a resposta
         daoQuest_sbtEditQuest.alterarModalidade(codQuestao,"alt");
         daoQuest_sbtEditQuest.alterarResposta(codQuestao,comboResposta.getSelectedItem().toString());
           
         //salvar alteração na descrição da questão
         if(campoQuest_alterado==true){campoQuest=jtCampoQuest.getText();campoQuest_alterado=false;}
         tipoQuestao=0;
         daoQuest_sbtEditQuest.alterarTextoQuestao(codQuestao,campoQuest);
       }
       
       //questao já era de alternativa mas foi modificada
       else if(comboTpQuestao.getSelectedIndex()==0 && tipoQuestao==0){
         
         //salvar alteração na descrição da questão 
         if(campoQuest_alterado==true){campoQuest=jtCampoQuest.getText();campoQuest_alterado=false; daoQuest_sbtEditQuest.alterarTextoQuestao(codQuestao,campoQuest);}
         if(questA_alterado==true){questA=tQuestaoA.getText();questA_alterado=false; daoQuest_sbtEditQuest.alterarAlternativa(codQuestao,"a",questA);}
         if(questB_alterado==true){questB=tQuestaoB.getText();questB_alterado=false; daoQuest_sbtEditQuest.alterarAlternativa(codQuestao,"b",questB);}
         if(questC_alterado==true){questC=tQuestaoC.getText();questC_alterado=false; daoQuest_sbtEditQuest.alterarAlternativa(codQuestao,"c",questC);}
         if(questD_alterado==true){questD=tQuestaoD.getText();questD_alterado=false; daoQuest_sbtEditQuest.alterarAlternativa(codQuestao,"d",questD);}
         if(questE_alterado==true){questE=tQuestaoE.getText();questE_alterado=false; daoQuest_sbtEditQuest.alterarAlternativa(codQuestao,"e",questE);}
         if(respost_alterado==true){respost=comboResposta.getSelectedItem().toString();respost_alterado=false;daoQuest_sbtEditQuest.alterarResposta(codQuestao,respost);}
       }
       
       //salvar imagem da questão
       if(imagemQuestao_alterado==true){
          //remover arquivo de imagem e remover registro no banco de dados
          daoQuest_sbtEditQuest.removerImagemBanco(codQuestao/*,imagemQuestao*/);
          //criar imagem adaptada ao tamanho de uma pagina pdf inteira
          ManipuladorImagem gp = new ManipuladorImagem(arquivoImagem,724,738);
          //gerar novo arquivo de imagem
          gp.gravarImagem(novaImagemQuestao,diretorioImagens);
          //inserir registro da imagem no banco de dados
          imagemQuestao=novaImagemQuestao;
          daoQuest_sbtEditQuest.inserirImagemBanco(codQuestao,imagemQuestao);
          gp.comprimeImagem();
          daoQuest_sbtEditQuest.inserirImagemBlob(codQuestao,gp.imagem);
          imagemQuestao_alterado=false;
       }
       
       else if(imagemQuestao_removido==true){
          daoQuest_sbtEditQuest.removerImagemBanco(codQuestao);
          imagemQuestao=null;
          imagemQuestao_removido=false;
       }
       
       btSalvarQuest.setEnabled(false);
       btCancelar.setEnabled(false);
  }//GEN-LAST:event_btSalvarQuestActionPerformed

public void removerArqImagem(){

if(imagemQuestao!=null){
  File arquivoImg = new File(diretorioImagens+imagemQuestao);
  ManipuladorArquivos mpArq= new ManipuladorArquivos();
  mpArq.excluirArquivo(arquivoImg);
}
}  
  
  private void btRemoverImgQuestaoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btRemoverImgQuestaoMousePressed
    btRemoverImgQuestao.setIcon(icon_removeImg_pressed);
  }//GEN-LAST:event_btRemoverImgQuestaoMousePressed

  private void btRemoverImgQuestaoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btRemoverImgQuestaoMouseReleased
    btRemoverImgQuestao.setIcon(icon_removeImg_released);
  }//GEN-LAST:event_btRemoverImgQuestaoMouseReleased


    public void exibirImagem(){
      if(imagemQuestao!=null){
        File arquivoImg = new File(diretorioImagens+imagemQuestao);
        ManipuladorImagem mp = new ManipuladorImagem();
        mp.imagem=arquivoImg;
        mp.dimensionaQuadroEditor(323, 191);
        labelImgQuestao.setIcon(mp.thumbnail);
        labelImgQuestao.setText(null);
      }
    }
    
   public void retirarImagem(){
     labelImgQuestao.setIcon(null);
     labelImgQuestao.setText("IMAGEM DA QUESTÃO");
   }
  
//metodo auxiliar para verificar se há alterações não gravadas no banco dedados
public boolean verificaAlteracoes(){
  if(questA_alterado==false & questB_alterado==false & questC_alterado==false & questD_alterado==false & 
    questE_alterado==false & tipoQuest_alterado==false & respost_alterado==false &
    campoQuest_alterado==false & linhasResp_alterado==false & imagemQuestao_removido==false){
    return false;
  }
  return true;
}
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public java.awt.Label LbTituloQuest;
    private javax.swing.JButton btAlterarImgQuestao;
    public javax.swing.JButton btCancelar;
    private javax.swing.JButton btRemoverImgQuestao;
    public javax.swing.JButton btSalvarQuest;
    public javax.swing.JFormattedTextField campoTamResp;
    public javax.swing.JComboBox comboResposta;
    public javax.swing.JComboBox comboTpQuestao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelAlternA;
    private javax.swing.JPanel jPanelAlternB;
    private javax.swing.JPanel jPanelAlternC;
    private javax.swing.JPanel jPanelAlternD;
    private javax.swing.JPanel jPanelAlternE;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    public javax.swing.JTextArea jtCampoQuest;
    private javax.swing.JLabel labelImgQuestao;
    private java.awt.Label labelResposta;
    private javax.swing.JLabel lbA;
    private javax.swing.JLabel lbB;
    private javax.swing.JLabel lbC;
    private javax.swing.JLabel lbD;
    private javax.swing.JLabel lbE;
    private javax.swing.JLabel lbTamanCampoResp;
    private javax.swing.JLabel lbTpQuestao;
    public final javax.swing.JTextArea tQuestaoA = new javax.swing.JTextArea();
    public final javax.swing.JTextArea tQuestaoB = new javax.swing.JTextArea();
    public final javax.swing.JTextArea tQuestaoC = new javax.swing.JTextArea();
    public final javax.swing.JTextArea tQuestaoD = new javax.swing.JTextArea();
    public final javax.swing.JTextArea tQuestaoE = new javax.swing.JTextArea();
    private java.awt.TextField textField3;
    // End of variables declaration//GEN-END:variables
}