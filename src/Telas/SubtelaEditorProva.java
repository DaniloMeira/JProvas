package Telas;

import ClassesTeste.dao.DAOprova;
import ClassesTeste.dao.DAOquestao;
import ClassesTeste.moduloProvas.CriarHTML;
import ClassesTeste.moduloProvas.RenderizadorPDF;
import ClassesTeste.utils.FiltroExtensoesImagem;
import ClassesTeste.utils.ManipuladorArquivos;
import ClassesTeste.utils.ManipuladorImagem;
import ClassesTeste.utils.PreviewImagem;
import com.lowagie.text.DocumentException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * @author Danilo Meira e Silva
 */

public class SubtelaEditorProva extends javax.swing.JPanel {

  public JProvas TelaJProvas_sbtEditorProvas;
  //private SubtelaPainelArvore sbtParvore;
  Border bordaErro = BorderFactory.createLineBorder(Color.RED, 2);
  JFileChooser jf;
  char charSeparador = File.separatorChar;
  public String diretorioImagens=System.getProperty("user.dir")+"/HTML/Imagens/Imagens_Provas/";
  //ImageIcon logotipoPadrao;
  Icon logotipoPadrao=new javax.swing.ImageIcon(getClass().getResource("/Imagens/IconesSistema/LogotipoJProvas_editorProva.png"));
  Icon icon_removeImg_released = new javax.swing.ImageIcon(getClass().getResource("/Imagens/IconesSistema/iconeFechar_24.png"));
  Icon icon_removeImg_pressed = new javax.swing.ImageIcon(getClass().getResource("/Imagens/IconesSistema/iconeFechar2_24.png"));
  String nomeArquivoLogotipoPadrao= charSeparador+"Imagens"+charSeparador+"IconesSistema"+charSeparador+"LogotipoJProvas.gif";
	
  TelaRenomearItem telaNomearDivisoriaSbtEditorProva;
  public JTree arvoreEditorProva;
  public String numeroProva;
  public int indiceArvoreDaProva;
  public int nuItensLinha;
  public DAOprova daoProva;
  public DAOquestao daoQuestao;

  public Boolean labelLogotipoProva_Alterado=false;
  public Boolean textF_tituloInstituicaoEnsino_Alterado=false;
  public Boolean textF_IdentificacoAluno_Alterado=false;
  public Boolean textF_RodapeDescricaoProva_Alterado=false;
  public Boolean textF_RodapeIdentificaoProva_Alterado=false;
  public Boolean listaQuestoes_Alterado=false;
       
  File arquivoImagemProva;
    
  public String imagemProva=nomeArquivoLogotipoPadrao;
  public String nomeQuestaoSelecionada;
  public String numeroQuestaoSelecionada;
   
  public String tituloInstituicaoEnsino; 
  public String identificacaoAluno;
  public String rodapeDescricaoProva;
  public String rodapeIdentificaoProva;
    
  public String campo1_textoPadrao = "<center>JPROVAS - CRIADOR DE PROVAS</center>";
  public String campo2_textoPadrao = "Aluno/Candidato:                                                         | Identificação:";
  public String campo3_textoPadrao = "JProvas - Prova de Conhecimentos Gerais";
  public String campo4_textoPadrao = "São Paulo, 17 de setembro de 2014";
    
  public static final String stringAbreTagFormatoPagInteira="[FORMATO DE PÁGINA INTEIRA]:>";
  public static final String stringFechaTagFormatoPagInteira="[FORMATO DE PÁGINA DUPLA]:>";
  public static final String stringTagQuebraDepagina="[QUEBRA DE PÁGINA]----------";
  public static final String  stringTagDivisoria="DIVISÓRIA:>";
    
  public final DefaultListModel modeloLista;
  private JLabel labelDivisoria = new JLabel();
  int maximoNumeroQuestoes=300;
  public int numeroQuestoesLista=0;
    
  public String diretorioImagensEditPrv="src"+charSeparador+"Imagens"+charSeparador;
  public String [] tagFormatoPaginaDividida;
  public String []tagQuebraDePagina;
  public String []tagFormatoPaginaInteira;
  public String []divisoriasEditPrv;
  public String [][]questoesEditorProva;
  public String [][]figurasQuestoesEditPrv;
  public String [][]tipoQuestaoEditPrv;
  public String []numQuestoesEditProv;
  public Object []arrayObjetoLinha;
  private boolean labelLogotipoProva_Removido=false;
  public ManipuladorImagem mp=null;
  public File fileImagemlogo;
    
    
  public SubtelaEditorProva(DAOprova daoProvaX) {
    //carregar o objeto EditorProva com os valores padrão
    mp = new ManipuladorImagem();
    fileImagemlogo = new File(nomeArquivoLogotipoPadrao);
    mp.imagem=fileImagemlogo;
    mp.dimensionaQuadroEditor(121,67);
    //logotipoPadrao=mp.thumbnail;
    //logotipoPadrao=new javax.swing.ImageIcon(getClass().getResource(nomeArquivoLogotipoPadrao));
    daoProva=daoProvaX;

    initComponents();

    numQuestoesEditProv= new String[maximoNumeroQuestoes];
    arrayObjetoLinha= new Object [maximoNumeroQuestoes];
     
    RenderizadorLista renderizador= new RenderizadorLista();
    listaQuestoes.setCellRenderer(renderizador);
    modeloLista= new DefaultListModel();
    listaQuestoes.setModel(modeloLista);
      
    tituloInstituicaoEnsino= textF_tituloInstituicaoEnsino.getText();
    identificacaoAluno=textF_IdentificacoAluno.getText();
    rodapeDescricaoProva=textF_RodapeDescricaoProva.getText();
    rodapeIdentificaoProva=textF_RodapeIdentificaoProva.getText();
  }

  
  public void adicionaQuestao(/*String tituloQuestao*/){
    int i=listaQuestoes.getMinSelectionIndex();
    int z=listaQuestoes.getMaxSelectionIndex();
    int numUltimaLinha = modeloLista.getSize()-1;
    //System.out.println("selecao minima- "+i);
    //System.out.println("selecao maxima- "+z);
    if(i>-1){
      modeloLista.add(z+1,nomeQuestaoSelecionada);
      numQuestoesEditProv[numeroQuestoesLista]=numeroQuestaoSelecionada;
      arrayObjetoLinha[numeroQuestoesLista]=modeloLista.get(z+1);
      numeroQuestoesLista++;
      listaQuestoes.setSelectionInterval(i,z+1);
    }
    else{
      modeloLista.addElement(nomeQuestaoSelecionada);  
      numQuestoesEditProv[numeroQuestoesLista]=numeroQuestaoSelecionada;
      arrayObjetoLinha[numeroQuestoesLista]=modeloLista.get(numUltimaLinha+1);
      numeroQuestoesLista++;
      //i=0;
      listaQuestoes.setSelectionInterval(-1,0);
    }
    //listaQuestoes.setSelectionInterval(i,z+1);
  }
  
  
  public void adicionaDivisoria(String tituloDivisoria){
    labelDivisoria.setText(tituloDivisoria);
    modeloLista.addElement(labelDivisoria);
  }
  
  public void adicionaFormatoPagInteira(){
    int i=listaQuestoes.getMinSelectionIndex();
    int z=listaQuestoes.getMaxSelectionIndex();
    int numLinhas = modeloLista.getSize();
    if(numLinhas>0){
      if(i==(-1)){i=0;z=0;}
      String textoLinhaLista = modeloLista.getElementAt(i).toString();
      if(textoLinhaLista.compareTo(stringTagQuebraDepagina)==0){return;}
      if(textoLinhaLista.compareTo(stringAbreTagFormatoPagInteira)==0){return;}
      if(textoLinhaLista.compareTo(stringFechaTagFormatoPagInteira)==0){return;}
      if(i>=1){textoLinhaLista = modeloLista.getElementAt(i-1).toString();if(textoLinhaLista.compareTo(stringAbreTagFormatoPagInteira)==0){return;}}
      modeloLista.add(i,stringAbreTagFormatoPagInteira);
      listaQuestoes.setSelectionInterval(i+1,z+1);
      jbSalvar.setEnabled(true);
      jbCancelar.setEnabled(true);
      listaQuestoes_Alterado=true;
    } 
    else{
      JOptionPane.showMessageDialog(null,"Adicione ao menos uma questão para poder inserir uma tag de layout de Pagina Inteira" ,"Não há questões adicionadas",0);
    }
  }
  
  
@SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    buttonGroup1 = new javax.swing.ButtonGroup();
    jRadioButton3 = new javax.swing.JRadioButton();
    jRadioButton4 = new javax.swing.JRadioButton();
    jbSalvar = new javax.swing.JButton();
    jbCancelar = new javax.swing.JButton();
    btAdTagAbrirPagInteira = new javax.swing.JButton();
    jScrollPane1 = new javax.swing.JScrollPane();
    listaQuestoes = new javax.swing.JList();
    jPanel5 = new javax.swing.JPanel();
    textF_tituloInstituicaoEnsino = new javax.swing.JTextField();
    jPanel7 = new javax.swing.JPanel();
    labelLogotipoProva = new javax.swing.JLabel();
    textF_IdentificacoAluno = new javax.swing.JTextField();
    btAlterarImgProva = new javax.swing.JButton();
    jLabel2 = new javax.swing.JLabel();
    btRemoveImg = new javax.swing.JButton();
    jLabel1 = new javax.swing.JLabel();
    jPanel3 = new javax.swing.JPanel();
    textF_RodapeDescricaoProva = new javax.swing.JTextField();
    textF_RodapeIdentificaoProva = new javax.swing.JTextField();
    btAddQuest = new javax.swing.JButton();
    btRetiraQuestao = new javax.swing.JButton();
    btAdTagFecharPagInteira = new javax.swing.JButton();
    btAdQuebraPag = new javax.swing.JButton();
    btMoveItemListaAcima = new javax.swing.JButton();
    btMoveItemListaAbaixo = new javax.swing.JButton();
    btGerarProva = new javax.swing.JButton();
    btInserirDivisoria = new javax.swing.JButton();

    jRadioButton3.setText("Nao");

    jRadioButton4.setText("Sim");

    jbSalvar.setText("Salvar");
    jbSalvar.setEnabled(false);
    jbSalvar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jbSalvarActionPerformed(evt);
      }
    });

    jbCancelar.setText("Cancelar");
    jbCancelar.setEnabled(false);
    jbCancelar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jbCancelarActionPerformed(evt);
      }
    });

    btAdTagAbrirPagInteira.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/IconesSistema/iconeAbrePagInteira_24.png"))); // NOI18N
    btAdTagAbrirPagInteira.setToolTipText("Exibir as questões em formato de página sem divisão.");
    btAdTagAbrirPagInteira.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btAdTagAbrirPagInteiraActionPerformed(evt);
      }
    });

    listaQuestoes.setModel(new javax.swing.AbstractListModel() {
      String[] strings = { "Item 1", " " };
      public int getSize() { return strings.length; }
      public Object getElementAt(int i) { return strings[i]; }
    });
    listaQuestoes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    jScrollPane1.setViewportView(listaQuestoes);

    jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cabeçalho"));

    textF_tituloInstituicaoEnsino.setHorizontalAlignment(javax.swing.JTextField.LEFT);
    textF_tituloInstituicaoEnsino.setText(campo1_textoPadrao);
    textF_tituloInstituicaoEnsino.setPreferredSize(new java.awt.Dimension(59, 22));
    textF_tituloInstituicaoEnsino.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        textF_tituloInstituicaoEnsinoActionPerformed(evt);
      }
    });
    textF_tituloInstituicaoEnsino.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        textF_tituloInstituicaoEnsinoKeyReleased(evt);
      }
    });

    jPanel7.setBackground(new java.awt.Color(255, 255, 255));
    jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    jPanel7.setPreferredSize(new java.awt.Dimension(126, 72));

    labelLogotipoProva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/IconesSistema/LogotipoJProvas_editorProva.png"))); // NOI18N
    labelLogotipoProva.setMinimumSize(new java.awt.Dimension(126, 64));
    labelLogotipoProva.setName(""); // NOI18N

    javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
    jPanel7.setLayout(jPanel7Layout);
    jPanel7Layout.setHorizontalGroup(
      jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(labelLogotipoProva, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
    );
    jPanel7Layout.setVerticalGroup(
      jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel7Layout.createSequentialGroup()
        .addComponent(labelLogotipoProva, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );

    textF_IdentificacoAluno.setHorizontalAlignment(javax.swing.JTextField.LEFT);
    textF_IdentificacoAluno.setPreferredSize(new java.awt.Dimension(59, 22));
    textF_IdentificacoAluno.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        textF_IdentificacoAlunoActionPerformed(evt);
      }
    });
    textF_IdentificacoAluno.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        textF_IdentificacoAlunoKeyReleased(evt);
      }
    });

    btAlterarImgProva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/IconesSistema/add_imag-24.png"))); // NOI18N
    btAlterarImgProva.setToolTipText("Alterar a imagem para incluir a marca da Instituição nos cabeçalhos da prova");
    btAlterarImgProva.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btAlterarImgProvaActionPerformed(evt);
      }
    });

    jLabel2.setText("Alterar Imagem");
    jLabel2.setToolTipText("");

    btRemoveImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/IconesSistema/iconeFechar_24.png"))); // NOI18N
    btRemoveImg.setToolTipText("retirar imagem");
    btRemoveImg.setContentAreaFilled(false);
    btRemoveImg.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(java.awt.event.MouseEvent evt) {
        btRemoveImgMousePressed(evt);
      }
      public void mouseReleased(java.awt.event.MouseEvent evt) {
        btRemoveImgMouseReleased(evt);
      }
    });
    btRemoveImg.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btRemoveImgActionPerformed(evt);
      }
    });

    jLabel1.setText("Remover Imagem");

    javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
    jPanel5.setLayout(jPanel5Layout);
    jPanel5Layout.setHorizontalGroup(
      jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel5Layout.createSequentialGroup()
        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(jPanel5Layout.createSequentialGroup()
            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(textF_IdentificacoAluno, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
              .addComponent(textF_tituloInstituicaoEnsino, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
          .addGroup(jPanel5Layout.createSequentialGroup()
            .addComponent(btAlterarImgProva, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel2)
            .addGap(18, 18, 18)
            .addComponent(btRemoveImg, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel1)
            .addGap(0, 0, Short.MAX_VALUE)))
        .addContainerGap())
    );
    jPanel5Layout.setVerticalGroup(
      jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel5Layout.createSequentialGroup()
        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGroup(jPanel5Layout.createSequentialGroup()
            .addComponent(textF_tituloInstituicaoEnsino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(textF_IdentificacoAluno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(btRemoveImg, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
          .addGroup(jPanel5Layout.createSequentialGroup()
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel1)
              .addComponent(btAlterarImgProva, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(jLabel2))
            .addGap(0, 0, Short.MAX_VALUE)))
        .addContainerGap())
    );

    jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Rodapé"));

    textF_RodapeDescricaoProva.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        textF_RodapeDescricaoProvaKeyReleased(evt);
      }
    });

    textF_RodapeIdentificaoProva.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        textF_RodapeIdentificaoProvaKeyReleased(evt);
      }
    });

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
      jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel3Layout.createSequentialGroup()
        .addComponent(textF_RodapeDescricaoProva, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(textF_RodapeIdentificaoProva, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
        .addContainerGap())
    );
    jPanel3Layout.setVerticalGroup(
      jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel3Layout.createSequentialGroup()
        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(textF_RodapeDescricaoProva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(textF_RodapeIdentificaoProva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(0, 8, Short.MAX_VALUE))
    );

    btAddQuest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/IconesSistema/iconAddQuestao_24.png"))); // NOI18N
    btAddQuest.setToolTipText("Selecione uma questão no Banco de Perguntas e clique nesta seta para incluí-la na prova");
    btAddQuest.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btAddQuestActionPerformed(evt);
      }
    });

    btRetiraQuestao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/IconesSistema/lixeira_24.png"))); // NOI18N
    btRetiraQuestao.setToolTipText("Clique aqui para deletar questões da prova ou tags de formatação da página");
    btRetiraQuestao.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btRetiraQuestaoActionPerformed(evt);
      }
    });

    btAdTagFecharPagInteira.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/IconesSistema/iconeFechaPagInteira_24.png"))); // NOI18N
    btAdTagFecharPagInteira.setToolTipText("Exibir as questoes em formato de pagina dividida em dois blocos.");
    btAdTagFecharPagInteira.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btAdTagFecharPagInteiraActionPerformed(evt);
      }
    });

    btAdQuebraPag.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/IconesSistema/iconeQuebraPagina_32.png"))); // NOI18N
    btAdQuebraPag.setToolTipText("Inserir quebra de página.");
    btAdQuebraPag.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btAdQuebraPagActionPerformed(evt);
      }
    });

    btMoveItemListaAcima.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/IconesSistema/iconMoveItemListaAcima_24.png"))); // NOI18N
    btMoveItemListaAcima.setToolTipText("Move item selecionados para uma posição acima");
    btMoveItemListaAcima.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btMoveItemListaAcimaActionPerformed(evt);
      }
    });

    btMoveItemListaAbaixo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/IconesSistema/iconMoveItemListaAbaixo_24.png"))); // NOI18N
    btMoveItemListaAbaixo.setToolTipText("Move item selecionados para uma posição abaixo");
    btMoveItemListaAbaixo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btMoveItemListaAbaixoActionPerformed(evt);
      }
    });

    labelTituloProva.setAlignment(java.awt.Label.CENTER);
    labelTituloProva.setBackground(new java.awt.Color(255, 102, 102));
    labelTituloProva.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
    labelTituloProva.setForeground(new java.awt.Color(255, 255, 255));
    labelTituloProva.setText("TITULO DA PROVA");

    btGerarProva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/IconesSistema/iconeGerarProva_48.png"))); // NOI18N
    btGerarProva.setText("GERAR PROVA");
    btGerarProva.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btGerarProvaActionPerformed(evt);
      }
    });

    btInserirDivisoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/IconesSistema/iconeDivisoriaPagina_32.png"))); // NOI18N
    btInserirDivisoria.setToolTipText("Inserir divisória entre as questões.");
    btInserirDivisoria.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btInserirDivisoriaActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(layout.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE)
            .addComponent(btAdTagFecharPagInteira, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(btRetiraQuestao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(btAddQuest, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(jScrollPane1)
              .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jbSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btGerarProva))
              .addComponent(labelTituloProva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(btMoveItemListaAbaixo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(btMoveItemListaAcima, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
              .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btAdTagAbrirPagInteira, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(btAdQuebraPag, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(btInserirDivisoria, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))))
        .addGap(45, 45, 45))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(labelTituloProva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
            .addGap(15, 15, 15))
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                .addComponent(btAdTagAbrirPagInteira)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btAdTagFecharPagInteira)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btAdQuebraPag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btInserirDivisoria)
                .addGap(16, 16, 16)
                .addComponent(btMoveItemListaAcima, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addGroup(layout.createSequentialGroup()
                .addComponent(btAddQuest, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btRetiraQuestao)))
            .addGap(30, 30, 30)
            .addComponent(btMoveItemListaAbaixo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addGap(12, 12, 12)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jbSalvar)
              .addComponent(jbCancelar)))
          .addGroup(layout.createSequentialGroup()
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(btGerarProva, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addContainerGap())
    );
  }// </editor-fold>//GEN-END:initComponents

  private void btAdTagAbrirPagInteiraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdTagAbrirPagInteiraActionPerformed
    adicionaFormatoPagInteira();
  }//GEN-LAST:event_btAdTagAbrirPagInteiraActionPerformed

  private void btAddQuestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddQuestActionPerformed
    String caminhoItemArvore= arvoreEditorProva.getLastSelectedPathComponent().toString();
    if(caminhoItemArvore.endsWith("¤")) {
      numeroQuestaoSelecionada= caminhoItemArvore.substring(caminhoItemArvore.lastIndexOf(">")+1,caminhoItemArvore.lastIndexOf("¤"));
      nomeQuestaoSelecionada= caminhoItemArvore.substring(caminhoItemArvore.lastIndexOf("<html>")+6,caminhoItemArvore.lastIndexOf("</"));
      adicionaQuestao();
      jbSalvar.setEnabled(true);
      jbCancelar.setEnabled(true);
      listaQuestoes_Alterado=true;
      System.out.println("Botao adicionar Questao, nome item selecionado é: "+nomeQuestaoSelecionada);
      System.out.println("Botao adicionar Questao, numero item selecionado é: "+numeroQuestaoSelecionada);
      nuItensLinha++;
    }
  }//GEN-LAST:event_btAddQuestActionPerformed

  private void btAdTagFecharPagInteiraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdTagFecharPagInteiraActionPerformed
    int i=listaQuestoes.getMinSelectionIndex();
    int z=listaQuestoes.getMaxSelectionIndex();
    int numLinhas = modeloLista.getSize();

    if(numLinhas>0){
      if(z==-1){i=0;z=numLinhas-1;}
      
      String textoLinhaLista = modeloLista.getElementAt(z).toString();
     
      if(textoLinhaLista.compareTo(stringFechaTagFormatoPagInteira)==0){return;}
      if(textoLinhaLista.compareTo(stringAbreTagFormatoPagInteira)==0){return;}
      if(textoLinhaLista.compareTo(stringTagQuebraDepagina)==0){return;}
      if(z>0){
       String textoLinhaAnteriorLista = modeloLista.getElementAt(z-1).toString();
       if(textoLinhaAnteriorLista.compareTo(stringFechaTagFormatoPagInteira)==0){return;}
      }
      if(z<numLinhas-1){
       String textoLinhaPosteriorLista = modeloLista.getElementAt(z+1).toString();
       if(textoLinhaPosteriorLista.compareTo(stringFechaTagFormatoPagInteira)==0){return;}
      }
      modeloLista.add(z+1,stringFechaTagFormatoPagInteira);
      listaQuestoes.setSelectionInterval(i,z);
      jbSalvar.setEnabled(true);
      jbCancelar.setEnabled(true);
      listaQuestoes_Alterado=true;
      nuItensLinha++;
    } 
   else {
    JOptionPane.showMessageDialog(null,"Adicione ao menos uma questão para poder inserir uma tag de layout de Pagina Inteira" ,"Não há questões adicionadas",0);}
  }//GEN-LAST:event_btAdTagFecharPagInteiraActionPerformed

  private void btRetiraQuestaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRetiraQuestaoActionPerformed
    int i=listaQuestoes.getMinSelectionIndex();
    int z=listaQuestoes.getMaxSelectionIndex();
   
   if(i!=-1){
    for(int x=i;x<=z;x++){
      for(int y=0;y<maximoNumeroQuestoes;y++){
        if(modeloLista.get(i)==arrayObjetoLinha[y]){arrayObjetoLinha[y]=null;numQuestoesEditProv[y]=null;numeroQuestoesLista--;break;}
      }
      modeloLista.removeElementAt(i);
      nuItensLinha--;
      listaQuestoes_Alterado=true;
      jbSalvar.setEnabled(true);
      jbCancelar.setEnabled(true);
    }  
   }  
  }//GEN-LAST:event_btRetiraQuestaoActionPerformed

  private void btAdQuebraPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdQuebraPagActionPerformed
    int i=listaQuestoes.getMinSelectionIndex();
    int z=listaQuestoes.getMaxSelectionIndex();
    int numUltimaLinha = modeloLista.getSize()-1;
        
  if(numUltimaLinha>=0){
    if(i!=-1){
     String textoLinhaLista = modeloLista.getElementAt(i).toString();
     if(textoLinhaLista.compareTo(stringAbreTagFormatoPagInteira)==0){return;}
     if(textoLinhaLista.compareTo(stringFechaTagFormatoPagInteira)==0){return;}
     if(textoLinhaLista.compareTo(stringTagQuebraDepagina)==0){return;}
     if(i>=1){textoLinhaLista = modeloLista.getElementAt(i-1).toString();if(textoLinhaLista.compareTo(stringTagQuebraDepagina)==0 || textoLinhaLista.compareTo(stringAbreTagFormatoPagInteira)==0 || textoLinhaLista.compareTo(stringFechaTagFormatoPagInteira)==0){return;}}
     modeloLista.add(i,stringTagQuebraDepagina);
     listaQuestoes.setSelectionInterval(i+1,z+1);
     jbSalvar.setEnabled(true);
     jbCancelar.setEnabled(true);
     listaQuestoes_Alterado=true;
     nuItensLinha++;
    }
    else if(modeloLista.getElementAt(numUltimaLinha).toString()!=stringAbreTagFormatoPagInteira && modeloLista.getElementAt(numUltimaLinha).toString()!=stringFechaTagFormatoPagInteira && modeloLista.getElementAt(numUltimaLinha).toString()!=stringTagQuebraDepagina ){
      modeloLista.add(numUltimaLinha+1,stringTagQuebraDepagina);
      jbSalvar.setEnabled(true);
      jbCancelar.setEnabled(true);
      listaQuestoes_Alterado=true;}
      nuItensLinha++;
  } 
  else {
    JOptionPane.showMessageDialog(null,"Adicione ao menos uma questão para poder inserir uma tag de quebra de pagina" ,"Não há questões adicionadas",0);}

  }//GEN-LAST:event_btAdQuebraPagActionPerformed

  //Metodo para corrigir tags de formatação da pagina que foram incluídas erroneamente
  public boolean validarFormatacaoProva(){
    int numUltimaLinha = modeloLista.getSize()-1;
    
    String textoLinhaLista;
    String textoProximaLinha;
      
    if(numUltimaLinha>=1){
        
      //se o elemento for uma abertura de tag, apagar todas as ocorrencias de tags abertas até encontrar uma tag fechada
      for(int i=0;i<=(numUltimaLinha-1);i++){
        textoLinhaLista = modeloLista.getElementAt(i).toString();
        if(textoLinhaLista.compareTo(stringAbreTagFormatoPagInteira)==0){
          for(int z=i+1;z<=numUltimaLinha;z++){
            textoLinhaLista = modeloLista.getElementAt(z).toString();
            if(textoLinhaLista.compareTo(stringFechaTagFormatoPagInteira)==0){i=(z+1);break;}
            if(textoLinhaLista.compareTo(stringAbreTagFormatoPagInteira)==0){modeloLista.removeElementAt(z);numUltimaLinha--;}
           // if(z==numUltimaLinha){break;}//for não está quebrando o laço quando z fica maior q o numUltimaLInha, ñ consegui identificar a causa, incluida esta linha como paliativo
          }
        }
      }
      numUltimaLinha = modeloLista.getSize()-1;
      
      //partindo do ultimo item da lista para cima, apagar todas as ocorrencias de tags pagina dividida até encontrar uma tag pagina inteira
      for(int i=numUltimaLinha;i>=1;i--){
        textoLinhaLista = modeloLista.getElementAt(i).toString();
        if(textoLinhaLista.compareTo(stringFechaTagFormatoPagInteira)==0){
          for(int z=i-1;z>=0;z--){
            textoLinhaLista = modeloLista.getElementAt(z).toString();
            if(textoLinhaLista.compareTo(stringAbreTagFormatoPagInteira)==0){i=(z);break;}
            if(textoLinhaLista.compareTo(stringFechaTagFormatoPagInteira)==0){modeloLista.removeElementAt(z);i--;numUltimaLinha--;}
            if(z==0){modeloLista.removeElementAt(i);}//se chegou até o inicio da lista e não encontrou uma tag de pagina inteira, remova esta tag de pagina dividida.
          }
        }
      }
      numUltimaLinha = modeloLista.getSize()-1;
      
      //Não pode haver uma tag de abertura imediatamente abaixo de uma tag fechada, ou vice-versa
	  for(int i=0;i<=(numUltimaLinha-1);i++){
         textoLinhaLista = modeloLista.getElementAt(i).toString();
         textoProximaLinha = modeloLista.getElementAt(i+1).toString();
         if(textoLinhaLista.compareTo(stringFechaTagFormatoPagInteira)==0 && textoProximaLinha.compareTo(stringAbreTagFormatoPagInteira)==0){
            String X = modeloLista.getElementAt(i).toString();
            String X2 = X + "¨";
            modeloLista.setElementAt(X2,i);
            String Y = modeloLista.getElementAt(i+1).toString();
            String Y2 = Y + "¨";
            modeloLista.setElementAt(Y2,i+1);
            JOptionPane.showMessageDialog(null,"Você está incluindo uma formatação de pagina inteira \n e logo em seguida uma formatação para pagina \n dividida e sem nenhuma questão incluída entre elas.\n Corrija a posição das formatações ou exclua.","LISTA DE QUESTÕES NÃO FOI SALVA",0);
            modeloLista.setElementAt(X,i);
            modeloLista.setElementAt(Y,i+1);
            return false;

         }
         if(textoLinhaLista.compareTo(stringAbreTagFormatoPagInteira)==0 && textoProximaLinha.compareTo(stringFechaTagFormatoPagInteira)==0){
            String X = modeloLista.getElementAt(i).toString();
            String X2 = X + "¨";
            modeloLista.setElementAt(X2,i);
            String Y = modeloLista.getElementAt(i+1).toString();
            String Y2 = Y + "¨";
            modeloLista.setElementAt(Y2,i+1);
            JOptionPane.showMessageDialog(null,"Você está incluindo uma formatação de pagina inteira \n e logo em seguida uma formatação para pagina \n dividida e sem nenhuma questão incluída entre elas.\n Corrija a posição das formatações ou exclua.","LISTA DE QUESTÕES NÃO FOI SALVA",0);
            modeloLista.setElementAt(X,i);
            modeloLista.setElementAt(Y,i+1);
            return false;
         }
        }
    }
      numUltimaLinha = modeloLista.getSize()-1;
      if(numUltimaLinha>=0){
      //o primeiro item da lista nunca poderá ser um tag de quebra de pagina ou uma tag de pagina dividida
      textoLinhaLista= modeloLista.getElementAt(0).toString();
      if(textoLinhaLista.compareTo(stringTagQuebraDepagina)==0){
        String X = modeloLista.getElementAt(0).toString();
        String X2 = X + "¨";
        modeloLista.setElementAt(X2,0);
        JOptionPane.showMessageDialog(null,"Você está incluindo uma quebra de página sem\n haver nenhuma questão anteriormente. Exclua a\n quebra de página ou mude a posição de\n maneira que exista ao menos uma questão\n antecedendo a quebra.","ERRO DE FORMATAÇÃO",0);
        modeloLista.setElementAt(X,0);
        return false;
      } 
      if(textoLinhaLista.compareTo(stringFechaTagFormatoPagInteira)==0){
        String X = modeloLista.getElementAt(0).toString();
        String X2 = X + "¨";
        modeloLista.setElementAt(X2,0);
        JOptionPane.showMessageDialog(null,"Não há necessidade de incluir a formatação de página dividida no \n inicio da lista de questões, por padrão a prova já é criada com as \n primeiras questões neste formato.Exclua a formatação ou \n verifique se ela deve ser movida para outra posição da lista","ERRO DE FORMATAÇÃO",0);
        modeloLista.setElementAt(X,0);
        return false;
      }
      
      
      //o ultimo item da lista nunca poderá ser: uma tag de divisoria, uma tag de quebra de pagina ou uma tag de formatação de página.
      textoLinhaLista= modeloLista.getElementAt(numUltimaLinha).toString();
      if(textoLinhaLista.startsWith(stringTagDivisoria)){
        String X = modeloLista.getElementAt(numUltimaLinha).toString();
        String X2 = X + "¨";
        modeloLista.setElementAt(X2,numUltimaLinha);
        JOptionPane.showMessageDialog(null,"Voce esta incluindo uma divisoria sem nunhuma questao na lista ou \n incluindo apos a ultima questao da lista.\n Exclua a divisoria ou mude a posição de maneira que exista\n ao menos uma questão posterior.","ERRO DE FORMATAÇÃO",0);
        modeloLista.setElementAt(X,numUltimaLinha);
        return false;
      }
      if(textoLinhaLista.compareTo(stringTagQuebraDepagina)==0){
        String X = modeloLista.getElementAt(numUltimaLinha).toString();
        String X2 = X + "¨";
        modeloLista.setElementAt(X2,numUltimaLinha);
        JOptionPane.showMessageDialog(null,"Não há necessidade de incluir uma quebra de página\n após a última questão da lista. Exclua a quebra de\n página ou mude a posição de maneira que\n exista ao menos uma questão após a quebra.","ERRO DE FORMATAÇÃO",0);
        modeloLista.setElementAt(X,numUltimaLinha);
        return false;
      }
      if(textoLinhaLista.compareTo(stringAbreTagFormatoPagInteira)==0 || textoLinhaLista.compareTo(stringFechaTagFormatoPagInteira)==0){
        String X = modeloLista.getElementAt(numUltimaLinha).toString();
        String X2 = X + "¨";
        modeloLista.setElementAt(X2,numUltimaLinha);
        JOptionPane.showMessageDialog(null,"Não há necessidade de incluir uma formatação de página após a última questão da lista.\n Exclua a formatação ou mude a posição de maneira que exista ao menos uma questão após.","ERRO DE FORMATAÇÃO",0);
        modeloLista.setElementAt(X,numUltimaLinha);
        return false;
      }

     }
      
      //nao pode haver duas divisorias ou duas quebras de pagina juntas
      for(int i=0;i<(numUltimaLinha);i++){
        textoLinhaLista = modeloLista.getElementAt(i).toString();
        if(textoLinhaLista.compareTo(stringTagQuebraDepagina)==0){
          
            textoLinhaLista = modeloLista.getElementAt(i+1).toString();
            if(textoLinhaLista.compareTo(stringTagQuebraDepagina)==0){
                String X = modeloLista.getElementAt(i).toString();
                String X2 = X + "¨";
                String Y = modeloLista.getElementAt(i+1).toString();
                String Y2 = X + "¨";
                modeloLista.setElementAt(X2,i);
                modeloLista.setElementAt(Y2,i+1);
                JOptionPane.showMessageDialog(null,"Voce incluiu uma quebra de pagina apos a outra sem nenhuma questao entre elas.\n Exclua as quebras de pagina ou mude a posição de maneira que haja ao menos uma questão entre elas.","ERRO DE FORMATAÇÃO",0);
                modeloLista.setElementAt(X,i);
                modeloLista.setElementAt(Y,i+1);
                return false;
            }
            else{textoLinhaLista = modeloLista.getElementAt(i).toString();}
          }  
            if(textoLinhaLista.startsWith(stringTagDivisoria)){
               textoLinhaLista = modeloLista.getElementAt(i+1).toString();
               if(textoLinhaLista.startsWith(stringTagDivisoria)){
                  String X = modeloLista.getElementAt(i).toString();
                  String X2 = X + "¨";
                  String Y = modeloLista.getElementAt(i+1).toString();
                  String Y2 = Y + "¨";
                  modeloLista.setElementAt(X2,i);
                  modeloLista.setElementAt(Y2,i+1); 
                  JOptionPane.showMessageDialog(null,"Voce incluiu uma divisoria apos a outra sem nenhuma questao entre elas.\n Exclua as divisorias ou mude a posição de maneira que haja ao menos uma questão entre elas.","ERRO DE FORMATAÇÃO",0);
                  modeloLista.setElementAt(X,i);
                  modeloLista.setElementAt(Y,i+1);
                return false;
               }
            }
      }    
        //nao pode haver uma divisoria antecedendo uma quebra de pagina ou uma tag de formatacao
      for(int i=0;i<numUltimaLinha;i++){
         textoLinhaLista = modeloLista.getElementAt(i).toString();
         textoProximaLinha = modeloLista.getElementAt(i+1).toString();
         if(textoLinhaLista.startsWith(stringTagDivisoria)){
            if(textoProximaLinha.compareTo(stringTagQuebraDepagina)==0 | textoProximaLinha.compareTo(stringAbreTagFormatoPagInteira)==0 | textoProximaLinha.compareTo(stringFechaTagFormatoPagInteira)==0){
             String X = modeloLista.getElementAt(i).toString();
             String X2 = X + "¨";
             modeloLista.setElementAt(X2,i);
            JOptionPane.showMessageDialog(null,".Você está incluindo uma divisória sem nenhuma questão abaixo dela. \n Corrija a posição da divisória ou exclua.","ERRO DE FORMATAÇÃO",0);
            modeloLista.setElementAt(X,i);
            return false;}
         }
      }

      //nao pode haver divisoria ou quebra de pagina sem nenhuma questao na lista
      return true;
  }

  
  private void jbSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarActionPerformed

    //validar tags digitadas
    String []tagsCampo = new String[]{"<u>","</u>","<i>","</i>","<b>","</b>","<s>","</s>"};
    if(!SubtelaPainelArvore.validTag.validaTagDigitada(textF_tituloInstituicaoEnsino.getText(),tagsCampo,0)){textF_tituloInstituicaoEnsino.setBorder(bordaErro);JOptionPane.showMessageDialog(null,"PROVA NÃO FOI SALVA, REALIZE AS CORREÇÕES E TENTE NOVAMENTE.","PROVA INVÁLIDA",0);return;}  else textF_tituloInstituicaoEnsino.setBorder(null);
    if(!SubtelaPainelArvore.validTag.validaTagDigitada(textF_IdentificacoAluno.getText(),tagsCampo,0)){textF_IdentificacoAluno.setBorder(bordaErro);JOptionPane.showMessageDialog(null,"PROVA NÃO FOI SALVA, REALIZE AS CORREÇÕES E TENTE NOVAMENTE.","PROVA INVÁLIDA",0);return;}  else textF_IdentificacoAluno.setBorder(null);
    if(!SubtelaPainelArvore.validTag.validaTagDigitada(textF_RodapeDescricaoProva.getText(),tagsCampo,0)){textF_RodapeDescricaoProva.setBorder(bordaErro);JOptionPane.showMessageDialog(null,"PROVA NÃO FOI SALVA, REALIZE AS CORREÇÕES E TENTE NOVAMENTE.","PROVA INVÁLIDA",0);return;}  else textF_RodapeDescricaoProva.setBorder(null);
    if(!SubtelaPainelArvore.validTag.validaTagDigitada(textF_RodapeIdentificaoProva.getText(),tagsCampo,0)){textF_RodapeIdentificaoProva.setBorder(bordaErro);JOptionPane.showMessageDialog(null,"PROVA NÃO FOI SALVA, REALIZE AS CORREÇÕES E TENTE NOVAMENTE.","PROVA INVÁLIDA",0);return;}  else textF_RodapeIdentificaoProva.setBorder(null);
    
    //preparar mensagem informativa de alteraçoes gravadas
    String mensagemAlterações="";
       
    if(labelLogotipoProva_Removido==true){
           daoProva.removerImagemBanco(numeroProva);
           imagemProva=nomeArquivoLogotipoPadrao;
           labelLogotipoProva_Removido=false;
           mensagemAlterações+="Remoção da imagem de logotipo da prova. \n";
    }
    
    if(labelLogotipoProva_Alterado==true){
	    //remover o arquivo de imagem anterior e remover registro no banco de dados
		if(imagemProva!=nomeArquivoLogotipoPadrao){
			daoProva.removerImagemBanco(numeroProva);
                }
		//criar copia do arquivo de imagem adaptada ao tamanho da pagina pdf
		imagemProva=arquivoImagemProva.getName();
                imagemProva=imagemProva.replace(" ","_");
                imagemProva=imagemProva.replace("&","e");
		String extensao=imagemProva.substring(imagemProva.indexOf("."));
		imagemProva=imagemProva.substring(0,imagemProva.indexOf("."))+"_prova"+numeroProva+extensao;
		ManipuladorImagem gp = new ManipuladorImagem(arquivoImagemProva,200,200);
		gp.gravarImagem(imagemProva,diretorioImagens);
		//inserir informação no banco de dados
		daoProva.inserirImagemBanco(numeroProva,imagemProva);
                gp.comprimeImagem();
		daoProva.inserirImagemBlob(numeroProva,gp.imagem);
		labelLogotipoProva_Alterado=false;
		mensagemAlterações+="Imagem de logotipo da prova \n";
		
	}
        

        if(textF_tituloInstituicaoEnsino_Alterado==true){
            daoProva.atualizarCampoProva(numeroProva,"1",textF_tituloInstituicaoEnsino.getText());
            textF_tituloInstituicaoEnsino_Alterado=false;
            tituloInstituicaoEnsino=textF_tituloInstituicaoEnsino.getText();
            mensagemAlterações+="Cabeçalho: Titulo da Instituicao \n";
        }
        if(textF_IdentificacoAluno_Alterado==true){
            daoProva.atualizarCampoProva(numeroProva,"2",textF_IdentificacoAluno.getText());
            textF_IdentificacoAluno_Alterado=false;
            mensagemAlterações+="Cabeçalho: Texto de Identificação do Aluno \n";
            identificacaoAluno=textF_IdentificacoAluno.getText();
        }
        if(textF_RodapeDescricaoProva_Alterado==true){
            daoProva.atualizarCampoProva(numeroProva,"3",textF_RodapeDescricaoProva.getText());
            textF_RodapeDescricaoProva_Alterado=false;
            mensagemAlterações+="Rodapé: Descrição da Prova \n";
            rodapeDescricaoProva=textF_RodapeDescricaoProva.getText();
        }
        if(textF_RodapeIdentificaoProva_Alterado==true){
            daoProva.atualizarCampoProva(numeroProva,"4",textF_RodapeIdentificaoProva.getText());
            textF_RodapeIdentificaoProva_Alterado=false;
            mensagemAlterações+="Rodapé: Identificação da Prova \n";
            rodapeIdentificaoProva=textF_RodapeIdentificaoProva.getText();
        }
 
      

      
          //tentar salvar lista se tiver sido alterada
    if(listaQuestoes_Alterado==true){
      if(validarFormatacaoProva()){
      salvarItensLista();
      listaQuestoes_Alterado=false;
      mensagemAlterações+="Lista de questoes da prova \n";
      }
      else{JOptionPane.showMessageDialog(null,"A Lista de questões não foi salva, \n realize as correçoes e tente novamente.","LISTA DE QUESTOES NÃO FOI SALVA",0);}
      jbCancelar.setEnabled(true);
      jbSalvar.setEnabled(true);
    } 
      
      jbCancelar.setEnabled(false);
      jbSalvar.setEnabled(false);
    
    //exibe mensagem informando quais modificações foram gravadas
    if(mensagemAlterações.compareTo("")!=0){
    JOptionPane.showMessageDialog(null,"Foram gravadas as seguintes alterações:\n \n"+mensagemAlterações); 
    }
    else{JOptionPane.showMessageDialog(null,"Não houve alterações a serem salvas."); }
  }//GEN-LAST:event_jbSalvarActionPerformed

  private void btMoveItemListaAcimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btMoveItemListaAcimaActionPerformed
     int i=listaQuestoes.getMinSelectionIndex();
     int z=listaQuestoes.getMaxSelectionIndex();
     int numLinhas = modeloLista.getSize();
     
     if(i>=1){
      modeloLista.add(z+1,modeloLista.elementAt(i-1));
      modeloLista.removeElementAt(i-1);
      jbSalvar.setEnabled(true);
      jbCancelar.setEnabled(true);
      listaQuestoes_Alterado=true;
     }
     
  }//GEN-LAST:event_btMoveItemListaAcimaActionPerformed

  private void btMoveItemListaAbaixoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btMoveItemListaAbaixoActionPerformed
     int i=listaQuestoes.getMinSelectionIndex();
     int z=listaQuestoes.getMaxSelectionIndex();
     int numLinhas = modeloLista.getSize();
     
     if(z<=(numLinhas-2)){
      modeloLista.add(i,modeloLista.elementAt(z+1));
      modeloLista.removeElementAt(z+2);
      listaQuestoes.setSelectionInterval(i+1,z+1);
      jbSalvar.setEnabled(true);
      jbCancelar.setEnabled(true);
      listaQuestoes_Alterado=true;
    }  
  }//GEN-LAST:event_btMoveItemListaAbaixoActionPerformed

  private void btInserirDivisoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInserirDivisoriaActionPerformed
    int numUltimaLinha = modeloLista.getSize()-1; 
    int i=listaQuestoes.getMinSelectionIndex();
    String textoLinhaLista;
   // String descricao=texto;
    if(numUltimaLinha>=0){
      if(i>=0){
        textoLinhaLista = modeloLista.getElementAt(i).toString();
        if(textoLinhaLista.compareTo(stringTagQuebraDepagina)==0 || textoLinhaLista.compareTo(stringFechaTagFormatoPagInteira)==0 || textoLinhaLista.compareTo(stringAbreTagFormatoPagInteira)==0){
          JOptionPane.showMessageDialog(null,"Não é possivel incluir uma divisória antecedendo uma tag de formatação de página","ERRO AO INCLUIR DIVISÓRIA",0);
          return;}
        if(textoLinhaLista.startsWith(stringTagDivisoria)){
          JOptionPane.showMessageDialog(null,"Não é válido incluir uma divisória antecedendo outra","ERRO AO INCLUIR DIVISÓRIA",0);  
          return;}
        /*if(i<numUltimaLinha){
          textoLinhaLista = modeloLista.getElementAt(i+1).toString();
          if(textoLinhaLista.compareTo(stringTagQuebraDepagina)==0 || textoLinhaLista.compareTo(stringFechaTagFormatoPagInteira)==0 || textoLinhaLista.compareTo(stringAbreTagFormatoPagInteira)==0){
            JOptionPane.showMessageDialog(null,"2Não é possivel incluir uma divisória antecedendo uma tag de formatação de página","ERRO AO INCLUIR DIVISÓRIA",0);
            return;}
          if(textoLinhaLista.startsWith(stringTagDivisoria)){
            JOptionPane.showMessageDialog(null,"Não é válido incluir uma divisória antecedendo outra","ERRO AO INCLUIR DIVISÓRIA",0);
            return;}
        }*/
        if(i>=1){
          textoLinhaLista = modeloLista.getElementAt(i-1).toString();
          if(textoLinhaLista.compareTo(stringTagQuebraDepagina)==0 || textoLinhaLista.compareTo(stringFechaTagFormatoPagInteira)==0 || textoLinhaLista.compareTo(stringAbreTagFormatoPagInteira)==0){
            JOptionPane.showMessageDialog(null,"Não é possivel incluir uma divisória após uma tag de formatação de página","ERRO AO INCLUIR DIVISÓRIA",0);
            return;
          }
          if(textoLinhaLista.startsWith(stringTagDivisoria)){
            JOptionPane.showMessageDialog(null,"Não é válido incluir uma divisória após outra","ERRO AO INCLUIR DIVISÓRIA",0);
            return;
          }
        }
    }

    if(i==-1){
      System.out.println("Adicionando divisoria linha 1009"); 
      textoLinhaLista = modeloLista.getElementAt(numUltimaLinha).toString();
      if(textoLinhaLista.compareTo(stringTagQuebraDepagina)==0 || textoLinhaLista.compareTo(stringFechaTagFormatoPagInteira)==0 || textoLinhaLista.compareTo(stringAbreTagFormatoPagInteira)==0){
        JOptionPane.showMessageDialog(null,"Não é possivel incluir uma divisória após uma tag de formatação de página","ERRO AO INCLUIR DIVISÓRIA",0);
        return;
      }
      if(textoLinhaLista.startsWith(stringTagDivisoria)){
        JOptionPane.showMessageDialog(null,"Não é válido incluir uma divisória abaixo de outra","ERRO AO INCLUIR DIVISÓRIA",0);return;
      }
    }  
    telaNomearDivisoriaSbtEditorProva = new TelaRenomearItem(null,true,this);
  }
    
  else {JOptionPane.showMessageDialog(null,"Insira ao menos uma questão na lista para poder incluir uma divisória","ERRO AO INCLUIR DIVISÓRIA",0);}
  }//GEN-LAST:event_btInserirDivisoriaActionPerformed
  
  
  public void adicionarDivisoria(String texto){
    int i=listaQuestoes.getMinSelectionIndex();
    int numUltimaLinha = modeloLista.getSize()-1;
    String descricao=texto;
    
    if(i==-1){
      modeloLista.add(numUltimaLinha+1,stringTagDivisoria+" "+descricao);
    }
    else if(i>=0){
      modeloLista.add(i,stringTagDivisoria+" "+descricao);
    }
    
    listaQuestoes.setSelectionInterval(i,i);
    jbSalvar.setEnabled(true);
    jbCancelar.setEnabled(true);
    listaQuestoes_Alterado=true;
    nuItensLinha++;
  }
  
    private void btGerarProvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGerarProvaActionPerformed
     
     //validar tags digitadas
    String []tagsCampo = new String[]{"<u>","</u>","<i>","</i>","<b>","</b>","<s>","</s>"};
    if(!SubtelaPainelArvore.validTag.validaTagDigitada(textF_tituloInstituicaoEnsino.getText(),tagsCampo,0)){textF_tituloInstituicaoEnsino.setBorder(bordaErro);JOptionPane.showMessageDialog(null,"PROVA NÃO PODE SER GERADA, REALIZE AS CORREÇÕES E TENTE NOVAMENTE.","PROVA INVÁLIDA",0);return;}  else textF_tituloInstituicaoEnsino.setBorder(null);
    if(!SubtelaPainelArvore.validTag.validaTagDigitada(textF_IdentificacoAluno.getText(),tagsCampo,0)){textF_IdentificacoAluno.setBorder(bordaErro);JOptionPane.showMessageDialog(null,"PROVA NÃO PODE SER GERADA, REALIZE AS CORREÇÕES E TENTE NOVAMENTE.","PROVA INVÁLIDA",0);return;}  else textF_IdentificacoAluno.setBorder(null);
    if(!SubtelaPainelArvore.validTag.validaTagDigitada(textF_RodapeDescricaoProva.getText(),tagsCampo,0)){textF_RodapeDescricaoProva.setBorder(bordaErro);JOptionPane.showMessageDialog(null,"PROVA NÃO PODE SER GERADA, REALIZE AS CORREÇÕES E TENTE NOVAMENTE.","PROVA INVÁLIDA",0);return;}  else textF_RodapeDescricaoProva.setBorder(null);
    if(!SubtelaPainelArvore.validTag.validaTagDigitada(textF_RodapeIdentificaoProva.getText(),tagsCampo,0)){textF_RodapeIdentificaoProva.setBorder(bordaErro);JOptionPane.showMessageDialog(null,"PROVA NÃO PODE SER GERADA, REALIZE AS CORREÇÕES E TENTE NOVAMENTE.","PROVA INVÁLIDA",0);return;}  else textF_RodapeIdentificaoProva.setBorder(null);
    
    if(modeloLista.getSize()<=0){JOptionPane.showMessageDialog(null,"Adicione ao menos uma questão para poder gerar a prova." ,"Não há questões adicionadas",0);return;}
        
     if (validarFormatacaoProva()){
        try {
            System.out.println("Iniciando a geração da Prova.");
            gerarPDF();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SubtelaEditorProva.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SubtelaEditorProva.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SubtelaEditorProva.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(SubtelaEditorProva.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(SubtelaEditorProva.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(SubtelaEditorProva.class.getName()).log(Level.SEVERE, null, ex);
        }
      } 
     else{JOptionPane.showMessageDialog(null,"A prova não foi gerada.\n Realize as correções e tente novamente","PROVA NÃO GERADA",0);
           }
    }//GEN-LAST:event_btGerarProvaActionPerformed

  private void btAlterarImgProvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarImgProvaActionPerformed
//     telaAbrirArqSbtEditorProva = new TelaAbrirArquivo();
//     telaAbrirArqSbtEditorProva.setVisible(true);
    daoProva.editorProva=this;
    jf = new JFileChooser();
    if(JProvas.JProvas_DAOProva.consultaParametroSistema(8)!=null){
    File diretorioImagens = new File(JProvas.JProvas_DAOProva.consultaParametroSistema(8));
    jf.setCurrentDirectory(diretorioImagens);}
    jf.setAccessory(new PreviewImagem(jf,210,50));
    jf.setAcceptAllFileFilterUsed(false);
    jf.addChoosableFileFilter(new FiltroExtensoesImagem());
    int returnVal=jf.showOpenDialog(this);
    
    File arquivo = jf.getSelectedFile();
   
        if (returnVal == jf.APPROVE_OPTION) {
            arquivoImagemProva = jf.getSelectedFile();
			
            //This is where a real application would open the file.
            System.out.println("Opening: " + arquivoImagemProva.getName() + ".");

            //Atualizar o label da tela com a nova Imagem
            ManipuladorImagem mp = new ManipuladorImagem();
            mp.imagem=arquivoImagemProva;
            mp.dimensionaQuadroEditor(121,67);
	       
            labelLogotipoProva.setIcon(mp.thumbnail);
            labelLogotipoProva_Alterado=true;
            jbSalvar.setEnabled(true);
            jbCancelar.setEnabled(true);
        }
        else {
            System.out.println("A escolha de alteração da imagem do logotipo da Prova foi cancelada pelo usuário.");
        }
   
  }//GEN-LAST:event_btAlterarImgProvaActionPerformed

  private void textF_tituloInstituicaoEnsinoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF_tituloInstituicaoEnsinoKeyReleased

    textF_tituloInstituicaoEnsino.setBorder(null);
    if(tituloInstituicaoEnsino.compareTo(textF_tituloInstituicaoEnsino.getText())!=0){
      textF_tituloInstituicaoEnsino_Alterado=true;
      jbSalvar.setEnabled(true);
      jbCancelar.setEnabled(true);
      System.out.println("Alterou tituloInstitEnsino");
    }
    else{textF_tituloInstituicaoEnsino_Alterado=false;
      if(labelLogotipoProva_Alterado==false&textF_tituloInstituicaoEnsino_Alterado==false&textF_IdentificacoAluno_Alterado==false&textF_RodapeDescricaoProva_Alterado==false&textF_RodapeIdentificaoProva_Alterado==false&listaQuestoes_Alterado==false&labelLogotipoProva_Removido==false){
        jbSalvar.setEnabled(false);
        jbCancelar.setEnabled(false);
       }
    }
  }//GEN-LAST:event_textF_tituloInstituicaoEnsinoKeyReleased

  private void textF_IdentificacoAlunoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF_IdentificacoAlunoKeyReleased
   
    textF_IdentificacoAluno.setBorder(null);  
    if(identificacaoAluno.compareTo(textF_IdentificacoAluno.getText())!=0){
      textF_IdentificacoAluno_Alterado=true;
      jbSalvar.setEnabled(true);
      jbCancelar.setEnabled(true);
      System.out.println("Alterou identificacaoAluno");
    }
    else{textF_IdentificacoAluno_Alterado=false;
       if(labelLogotipoProva_Alterado==false&textF_tituloInstituicaoEnsino_Alterado==false&textF_IdentificacoAluno_Alterado==false&textF_RodapeDescricaoProva_Alterado==false&textF_RodapeIdentificaoProva_Alterado==false&listaQuestoes_Alterado==false&labelLogotipoProva_Removido==false){
        jbSalvar.setEnabled(false);
        jbCancelar.setEnabled(false);
       }
    }
  }//GEN-LAST:event_textF_IdentificacoAlunoKeyReleased

  private void textF_RodapeDescricaoProvaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF_RodapeDescricaoProvaKeyReleased
    textF_RodapeDescricaoProva.setBorder(null);
     if(rodapeDescricaoProva.compareTo(textF_RodapeDescricaoProva.getText())!=0){
      textF_RodapeDescricaoProva_Alterado=true;
      jbSalvar.setEnabled(true);
      jbCancelar.setEnabled(true);
      System.out.println("Alterou rodapéDescricaoProva");
    }
    else{
      textF_RodapeDescricaoProva_Alterado=false;
      if(labelLogotipoProva_Alterado==false&textF_tituloInstituicaoEnsino_Alterado==false&textF_IdentificacoAluno_Alterado==false&textF_RodapeDescricaoProva_Alterado==false&textF_RodapeIdentificaoProva_Alterado==false&listaQuestoes_Alterado==false&labelLogotipoProva_Removido==false){
        jbSalvar.setEnabled(false);
        jbCancelar.setEnabled(false);
      }
    }
  }//GEN-LAST:event_textF_RodapeDescricaoProvaKeyReleased

  private void textF_RodapeIdentificaoProvaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF_RodapeIdentificaoProvaKeyReleased

    textF_RodapeIdentificaoProva.setBorder(null);
    if(rodapeIdentificaoProva.compareTo(textF_RodapeIdentificaoProva.getText())!=0){
      textF_RodapeIdentificaoProva_Alterado=true;
      jbSalvar.setEnabled(true);
      jbCancelar.setEnabled(true);
      System.out.println("Alterou rodapéIdentificacaoProva");
    }
    else{textF_RodapeIdentificaoProva_Alterado=false;
        if(labelLogotipoProva_Alterado==false&textF_tituloInstituicaoEnsino_Alterado==false&textF_IdentificacoAluno_Alterado==false&textF_RodapeDescricaoProva_Alterado==false&textF_RodapeIdentificaoProva_Alterado==false&listaQuestoes_Alterado==false&labelLogotipoProva_Removido==false){
        jbSalvar.setEnabled(false);
        jbCancelar.setEnabled(false);
        }
    }
  }//GEN-LAST:event_textF_RodapeIdentificaoProvaKeyReleased

  private void btRemoveImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveImgActionPerformed
     //falta exibir mensagem de confirmacao
    if(imagemProva!=nomeArquivoLogotipoPadrao){
      labelLogotipoProva.setIcon(logotipoPadrao);
      labelLogotipoProva_Removido=true;
      removerArqImagem();
      jbSalvar.setEnabled(true);
      jbCancelar.setEnabled(true);
    }
    //retirar uma imagem que foi incluida mas ainda nao foi salva no banco da de dados 
    else if(labelLogotipoProva_Alterado==true){
      labelLogotipoProva.setIcon(logotipoPadrao);
      labelLogotipoProva_Removido=false;
      if(textF_tituloInstituicaoEnsino_Alterado==false & textF_IdentificacoAluno_Alterado==false &
        textF_IdentificacoAluno_Alterado==false & textF_RodapeDescricaoProva_Alterado==false & textF_RodapeIdentificaoProva_Alterado==false
        & textF_RodapeIdentificaoProva_Alterado==false & listaQuestoes_Alterado==false){

        jbSalvar.setEnabled(false);
        jbCancelar.setEnabled(false);
      }
    }
  }//GEN-LAST:event_btRemoveImgActionPerformed

  private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
    
      textF_RodapeIdentificaoProva.setBorder(null);
      textF_RodapeDescricaoProva.setBorder(null);
      textF_IdentificacoAluno.setBorder(null);
      textF_tituloInstituicaoEnsino.setBorder(null);
      
      if(listaQuestoes_Alterado==true){
      apagarListaItens(); //linha1161
      nuItensLinha=daoProva.consultaNumeroItensProva(numeroProva);
      carregarListaItens();//linha1139
      listaQuestoes_Alterado=false;
    }
    if(labelLogotipoProva_Alterado==true |labelLogotipoProva_Removido==true){
      try {
        daoProva.carregarImagemBlob(numeroProva);
      } catch (IOException ex) {
        Logger.getLogger(SubtelaEditorProva.class.getName()).log(Level.SEVERE, null, ex);
      }
      exibirImagem();
      labelLogotipoProva_Alterado=false;
      labelLogotipoProva_Removido=false;
    }
    if(textF_tituloInstituicaoEnsino_Alterado==true){
      String campo1=daoProva.carregarCamposTexto(numeroProva,1);
      if(campo1!=null){
        textF_tituloInstituicaoEnsino.setText(daoProva.carregarCamposTexto(numeroProva,1));
        textF_tituloInstituicaoEnsino_Alterado=false;
      }
      else{ 
        textF_tituloInstituicaoEnsino.setText(daoProva.consultaParametroSistema(3));
        textF_tituloInstituicaoEnsino_Alterado=false;
      }
    }
    if(textF_IdentificacoAluno_Alterado==true){
      String campo2=daoProva.carregarCamposTexto(numeroProva,2);
      if(campo2!=null){
        textF_IdentificacoAluno.setText(campo2);
        textF_IdentificacoAluno_Alterado=false;
      }
      else{ 
        textF_IdentificacoAluno.setText(daoProva.consultaParametroSistema(4));
        textF_IdentificacoAluno_Alterado=false;
      }
    }
    if(textF_RodapeDescricaoProva_Alterado==true){
      String campo3=daoProva.carregarCamposTexto(numeroProva,3);
      if(campo3!=null){
        textF_RodapeDescricaoProva.setText(campo3);
        textF_RodapeDescricaoProva_Alterado=false;}
      else {
        textF_RodapeDescricaoProva.setText(daoProva.consultaParametroSistema(5));
        textF_RodapeDescricaoProva_Alterado=false;}
    }
    if(textF_RodapeIdentificaoProva_Alterado==true){
      String campo4=daoProva.carregarCamposTexto(numeroProva,4);
      if(campo4!=null){
        textF_RodapeIdentificaoProva.setText(campo4);
        textF_RodapeIdentificaoProva_Alterado=false;
      }
      else {
        textF_RodapeIdentificaoProva.setText(daoProva.consultaParametroSistema(5));
        textF_RodapeIdentificaoProva_Alterado=false;}
    } 
    
    jbCancelar.setEnabled(false);
    jbSalvar.setEnabled(false);
  }//GEN-LAST:event_jbCancelarActionPerformed

  private void textF_IdentificacoAlunoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textF_IdentificacoAlunoActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_textF_IdentificacoAlunoActionPerformed

  private void btRemoveImgMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btRemoveImgMousePressed
    btRemoveImg.setIcon(icon_removeImg_pressed);
    
  }//GEN-LAST:event_btRemoveImgMousePressed

  private void btRemoveImgMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btRemoveImgMouseReleased
    btRemoveImg.setIcon(icon_removeImg_released);
  }//GEN-LAST:event_btRemoveImgMouseReleased

    private void textF_tituloInstituicaoEnsinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textF_tituloInstituicaoEnsinoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textF_tituloInstituicaoEnsinoActionPerformed

  public void exibirImagem() {
      
      if(imagemProva!=nomeArquivoLogotipoPadrao){
       // daoProva.carregarImagemBlob(numeroProva);
        File arquivoImg = new File(diretorioImagens+imagemProva);
        ManipuladorImagem mp = new ManipuladorImagem();
        mp.imagem=arquivoImg;
        mp.dimensionaQuadroEditor(121, 67);
        labelLogotipoProva.setIcon(mp.thumbnail);
        labelLogotipoProva.setText(null);
      }
  }
  
public void removerArqImagem(){

if(imagemProva!=nomeArquivoLogotipoPadrao){
  File arquivoImg = new File(diretorioImagens+imagemProva);
  ManipuladorArquivos mpArq= new ManipuladorArquivos();
  mpArq.excluirArquivo(arquivoImg);
 }

}
    
public void carregarListaItens(){
  daoProva.editorProva=this;
  divisoriasEditPrv = daoProva.carregarArrayDivisorias(numeroProva);
  tagFormatoPaginaInteira =daoProva.carregarArrayTags(numeroProva,"1");
  tagFormatoPaginaDividida = daoProva.carregarArrayTags(numeroProva,"2");
  tagQuebraDePagina =daoProva.carregarArrayTags(numeroProva,"3");
  String [][]arrayListaQuestoes=daoProva.carregarQuestoesProva(numeroProva);
  //if(arrayListaQuestoes!=null){
  int contadorLinhaLista=0;
   for(int i=0;i<nuItensLinha;i++){
    if(tagFormatoPaginaInteira[i]!=null){modeloLista.add(i, stringAbreTagFormatoPagInteira);contadorLinhaLista++;}
    else if(tagFormatoPaginaDividida[i]!=null){modeloLista.add(i, stringFechaTagFormatoPagInteira);contadorLinhaLista++;}
    else if(tagQuebraDePagina[i]!=null){modeloLista.add(i, stringTagQuebraDepagina);contadorLinhaLista++;}
    else if(divisoriasEditPrv[i]!=null){modeloLista.add(i,stringTagDivisoria+divisoriasEditPrv[i]);contadorLinhaLista++;}
    else if(arrayListaQuestoes[i]!=null){numeroQuestaoSelecionada=arrayListaQuestoes[i][0];
     nomeQuestaoSelecionada=arrayListaQuestoes[i][1];
     adicionaQuestao();
     listaQuestoes.setSelectedIndex(-1);
     contadorLinhaLista++;}
   }
 // }
}

public void apagarListaItens(){
  System.out.println("METODO APARGAR LISTA, nuitensLinha="+nuItensLinha);
  numeroQuestoesLista=0;
  if(nuItensLinha>0){
    for(int i=0;i<nuItensLinha;i++){
      modeloLista.removeElementAt(0);
      System.out.println("apagarItensLista nº"+i+" nuitenslinha="+nuItensLinha);
    }
  nuItensLinha=0;
  System.out.println("terminou apagar lista");
  }
}


public void salvarItensLista(){
  daoProva.editorProva=this;
  daoProva.limparItensDaProva(numeroProva);
  int posicaoQuestao=0;
  int posicaoLista=0;
  String textoLinhaLista;
  for(int i=0;i<modeloLista.getSize();i++){
    textoLinhaLista = modeloLista.getElementAt(i).toString();
    if(textoLinhaLista.compareTo(stringAbreTagFormatoPagInteira)==0){daoProva.salvarArrayTags(i,"1",numeroProva,"");posicaoLista++;}
    else if(textoLinhaLista.compareTo(stringFechaTagFormatoPagInteira)==0){daoProva.salvarArrayTags(i,"2",numeroProva,"");posicaoLista++;}
    else if(textoLinhaLista.compareTo(stringTagQuebraDepagina)==0){daoProva.salvarArrayTags(i,"3",numeroProva,"");posicaoLista++;}
    else if(textoLinhaLista.startsWith(stringTagDivisoria)){daoProva.salvarArrayTags(i,"5",numeroProva,textoLinhaLista.substring(stringTagDivisoria.length()));posicaoLista++;}
    else{daoProva.salvarArrayQuestoes(i,i,numeroProva);posicaoQuestao++;posicaoLista++;}
  }
  daoProva.atualizaNumeroItensProva(modeloLista.getSize(),numeroProva);
  
}

 
public void gerarPDF() throws FileNotFoundException, UnsupportedEncodingException, IOException, DocumentException, ParserConfigurationException, SAXException{
 
 //mudar o cursor para estado de espera
 this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
 
 verificaArquivosGeracaoProva();
 
 daoProva.editorProva=this;
 tagQuebraDePagina = new String[numeroQuestoesLista];
 tagFormatoPaginaInteira = new String[numeroQuestoesLista];
 divisoriasEditPrv = new String[numeroQuestoesLista];
 questoesEditorProva = new String[numeroQuestoesLista][6];
 figurasQuestoesEditPrv = new String[numeroQuestoesLista][2];
 tipoQuestaoEditPrv= new String[numeroQuestoesLista][2]; 
 
 int posicaoQuestao=0;
 String textoLinhaLista;
 if(numeroQuestoesLista>=1){
	for(int y=0;y<modeloLista.getSize();y++){
            textoLinhaLista = modeloLista.getElementAt(y).toString();
	    if(textoLinhaLista.compareTo(stringAbreTagFormatoPagInteira)==0){tagFormatoPaginaInteira[posicaoQuestao]="sim";}
            else if(textoLinhaLista.compareTo(stringFechaTagFormatoPagInteira)==0){tagFormatoPaginaInteira[posicaoQuestao]="nao";}
            else if(textoLinhaLista.compareTo(stringTagQuebraDepagina)==0){tagQuebraDePagina[posicaoQuestao]="sim";}
            else if(textoLinhaLista.startsWith(stringTagDivisoria)){divisoriasEditPrv[posicaoQuestao]= textoLinhaLista.substring(stringTagDivisoria.length());}
            else{daoProva.preencheArrayQuestao(y,posicaoQuestao);daoProva.preencheArrayFigura(y,posicaoQuestao);/*daoProva.;*/posicaoQuestao++;}
                
	}
 }
CriarHTML provaX= new CriarHTML();
if(imagemProva!=null){provaX.logotipoProva=imagemProva;}
provaX.rodapeLadoEsq=textF_RodapeDescricaoProva.getText();
provaX.rodapeLadoDir=textF_RodapeIdentificaoProva.getText();
provaX.tituloCabecalho=textF_tituloInstituicaoEnsino.getText();
provaX.dadosCabecalho=textF_IdentificacoAluno.getText();
provaX.questoes=questoesEditorProva;
provaX.figuraQuestao=figurasQuestoesEditPrv;
provaX.tipoQuestao=tipoQuestaoEditPrv;
provaX.indicaFormatoPg2= tagFormatoPaginaInteira;
provaX.divisorias= divisoriasEditPrv;
provaX.quebraDePagina=tagQuebraDePagina;
provaX.gerarHTML();
RenderizadorPDF renderizadorProvaX = new RenderizadorPDF(labelTituloProva.getText());
renderizadorProvaX.TelaJProvas_RenderizadorPDF=TelaJProvas_sbtEditorProvas;
renderizadorProvaX.gerarPDF(provaX.numPag);

deletaArquivosGeracaoPDF();

//mudar cursor para estado normal:
this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
}

ManipuladorArquivos mpArquivos = new ManipuladorArquivos();

void verificaArquivosGeracaoProva() throws IOException{
  //verifica se diretorios existem
  if(!mpArquivos.verificaDiretorioExiste("HTML")){mpArquivos.criarDiretorio("HTML");}
  if(!mpArquivos.verificaDiretorioExiste("HTML/CSS")){mpArquivos.criarDiretorio("HTML/CSS");}
  if(!mpArquivos.verificaDiretorioExiste("HTML/PDF_paginas")){mpArquivos.criarDiretorio("HTML/PDF_paginas");}
  if(!mpArquivos.verificaDiretorioExiste("HTML/Fontes")){mpArquivos.criarDiretorio("HTML/Fontes");}
  if(!mpArquivos.verificaDiretorioExiste("HTML/Imagens")){mpArquivos.criarDiretorio("HTML/Imagens");}
  if(!mpArquivos.verificaDiretorioExiste("HTML/Imagens/Imagens_Questoes")){mpArquivos.criarDiretorio("HTML/Imagens/Imagens_Questoes");}
  if(!mpArquivos.verificaDiretorioExiste("HTML/Imagens/Imagens_Provas")){mpArquivos.criarDiretorio("HTML/Imagens/Imagens_Provas");}
  if(imagemProva!=nomeArquivoLogotipoPadrao){if(!mpArquivos.verificaArquivoExiste("/HTML/Imagens/Imagens_Provas/"+imagemProva)){daoProva.carregarImagemBlob(numeroProva);}}
  else if(!mpArquivos.verificaArquivoExiste("/HTML/Imagens/Imagens_Provas/LogotipoJProvas.gif")){mpArquivos.extrairRecursoJar("/Imagens/IconesSistema/","LogotipoJProvas.gif","/HTML/Imagens/Imagens_Provas/");System.out.println("Arquivo '/HTML/Imagens/Imagens_Provas/LogotipoJProvas.gif' criado");};
  if(!mpArquivos.verificaArquivoExiste("/HTML/CSS/DivTeste.css")){mpArquivos.extrairRecursoJar("/HTML/CSS/","DivTeste.css","HTML/CSS/");}
  if(!mpArquivos.verificaArquivoExiste("/HTML/Fontes/arial.ttf")){mpArquivos.extrairRecursoJar("/HTML/Fontes/","arial.ttf","HTML/Fontes/");}
  if(!mpArquivos.verificaArquivoExiste("/HTML/Fontes/arialbd.ttf")){mpArquivos.extrairRecursoJar("/HTML/Fontes/","arialbd.ttf","HTML/Fontes/");}
  if(!mpArquivos.verificaArquivoExiste("/HTML/Fontes/arialbi.ttf")){mpArquivos.extrairRecursoJar("/HTML/Fontes/","arialbi.ttf","HTML/Fontes/");}
  if(!mpArquivos.verificaArquivoExiste("/HTML/Fontes/ariali.ttf")){mpArquivos.extrairRecursoJar("/HTML/Fontes/","ariali.ttf","HTML/Fontes/");}
  if(!mpArquivos.verificaArquivoExiste("/HTML/Fontes/times.ttf")){mpArquivos.extrairRecursoJar("/HTML/Fontes/","times.ttf","HTML/Fontes/");}
  if(!mpArquivos.verificaArquivoExiste("/HTML/Fontes/timesbd.ttf")){mpArquivos.extrairRecursoJar("/HTML/Fontes/","timesbd.ttf","HTML/Fontes/");}
  if(!mpArquivos.verificaArquivoExiste("/HTML/Fontes/timesbi.ttf")){mpArquivos.extrairRecursoJar("/HTML/Fontes/","timesbi.ttf","HTML/Fontes/");}
  if(!mpArquivos.verificaArquivoExiste("/HTML/Fontes/timesi.ttf")){mpArquivos.extrairRecursoJar("/HTML/Fontes/","timesi.ttf","HTML/Fontes/");}
}


public void deletaArquivosGeracaoPDF() throws IOException{

 //Deletar arquivos .xhtml criados durante a geração do pdf
  for(int i=1;mpArquivos.verificarEexcluirArquivo("/HTML/prova"+i+".xhtml");i++){
    System.out.println("Excluindo: "+"/HTML/prova"+i+".xhtml");
  }

  //Deletar arquivos com as paginas do pdf criados durante a geração do pdf final
  for(int i=1;mpArquivos.verificarEexcluirArquivo("/HTML/PDF_Paginas/saidaProva"+i+".pdf");i++){
    System.out.println("Excluindo: "+"/HTML/PDF_Paginas/saidaProva"+i+".pdf");
  }
  
  //Deletar arquivos de imagens das questões utilizadas durante a geraçao do pdf
  for(int i=0;i<figurasQuestoesEditPrv.length;i++){
    if(figurasQuestoesEditPrv[i][0]!=null){
      System.out.println("Excluindo: "+"/HTML/Imagens/Imagens_Questoes/"+figurasQuestoesEditPrv[i][0]);
      mpArquivos.verificarEexcluirArquivo("HTML/Imagens/Imagens_Questoes/"+figurasQuestoesEditPrv[i][0]);}
  }
  
  //Deletar arquivo de imagem do logotipo da prova utilizado durante a geração do pdf
  mpArquivos.verificarEexcluirArquivo("/HTML/Imagens/Imagens_Provas/"+imagemProva);
  
}

public void destacaQuestaoDeletada(String nomeItem, String nuQuestao, String nuProva, int nuPosicao){
	int z = modeloLista.getSize();
	for(int i=0;i<z;i++){
	    String X = modeloLista.getElementAt(i).toString();
            System.out.println("destacaQuestao "+X+",  parametro nomeItem="+ nomeItem+ ", linha:"+i);
		if(nomeItem.compareTo(X)==0){
			X= X+"- [QUESTÃO EXCLUÍDA DO SISTEMA]§";
                        System.out.println("achado na linha:"+i);
			modeloLista.setElementAt(X,i);
                        /*if (daoProva.verificaQuestaoProva(nuQuestao,nuProva,nuPosicao)& nuPosicao>0){
                          daoProva.atualizaPosicaoListaItensProva(numeroProva,i);
                        }*/
		}
	}
}

public void renomeiaQuestaoDaLista (String nomeItem, String novoNome){
  int z = modeloLista.getSize();
	for(int i=0;i<z;i++){
	    String X = modeloLista.getElementAt(i).toString();
            System.out.println("renomeia questao "+X+",  parametro novoNome="+ novoNome+ ", linha:"+i);
		if(nomeItem.compareTo(X)==0){
			X= novoNome;
                        System.out.println("achado na linha:"+i);
			modeloLista.setElementAt(X,i);
                        arrayObjetoLinha[i]=modeloLista.getElementAt(i);
		}
	}
}

  
  
  
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton btAdQuebraPag;
  private javax.swing.JButton btAdTagAbrirPagInteira;
  private javax.swing.JButton btAdTagFecharPagInteira;
  private javax.swing.JButton btAddQuest;
  private javax.swing.JButton btAlterarImgProva;
  private javax.swing.JButton btGerarProva;
  private javax.swing.JButton btInserirDivisoria;
  private javax.swing.JButton btMoveItemListaAbaixo;
  private javax.swing.JButton btMoveItemListaAcima;
  private javax.swing.JButton btRemoveImg;
  private javax.swing.JButton btRetiraQuestao;
  private javax.swing.ButtonGroup buttonGroup1;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JPanel jPanel5;
  private javax.swing.JPanel jPanel7;
  private javax.swing.JRadioButton jRadioButton3;
  private javax.swing.JRadioButton jRadioButton4;
  private javax.swing.JScrollPane jScrollPane1;
  public javax.swing.JButton jbCancelar;
  private javax.swing.JButton jbSalvar;
  private javax.swing.JLabel labelLogotipoProva;
  public final java.awt.Label labelTituloProva = new java.awt.Label();
  private javax.swing.JList listaQuestoes;
  public javax.swing.JTextField textF_IdentificacoAluno;
  public javax.swing.JTextField textF_RodapeDescricaoProva;
  public javax.swing.JTextField textF_RodapeIdentificaoProva;
  public javax.swing.JTextField textF_tituloInstituicaoEnsino;
  // End of variables declaration//GEN-END:variables

  
}

  //personaliza a aparencia dos componentes da JList:
  class RenderizadorLista extends JLabel
                           implements ListCellRenderer {
   // java.net.URL imgURL1 = RenderizadorLista.class.getResource("src/Imagens/IconesSistema/iconeDivisoria.gif");
    //getClass().getResource("/Imagens/IconesSistema/iconeAbrePagInteira_24.png")
    Icon iconeTagsLayout = createImageIcon("/Imagens/IconesSistema/seta_tag.png");
    Icon iconeTagQuebraPagina = createImageIcon("/Imagens/IconesSistema/quebrapagina_tag.png");
    
    public RenderizadorLista() {
        super();
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                     int index, boolean isSelected, boolean cellHasFocus) {
      setText(value.toString());
      //String texto= getComponent(index).toString();
     
      if(value.toString().endsWith("¨")){
      setBackground(Color.red);
      // setIcon(iconeDivisoria);
      setBorder(BorderFactory.createDashedBorder(Color.BLACK));
      return this;
      }
	  if(value.toString().startsWith(SubtelaEditorProva.stringTagDivisoria)){
            setIcon(null);
	    setBackground(Color.decode("#d5e0c9"));
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            if(isSelected) {setBackground(Color.decode("#d4d0c5"));
              setBorder(null);
              setIcon(null);
            }
	  }
	  else if(value.toString().startsWith(SubtelaEditorProva.stringAbreTagFormatoPagInteira)){
            setIcon(iconeTagsLayout);
	    setBackground(Color.decode("#b5e0c9"));
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            if(isSelected) {setBackground(Color.decode("#d4d0c5"));
              setBorder(null);
              setIcon(iconeTagsLayout);
            }
	  }
	  else if(value.toString().startsWith(SubtelaEditorProva.stringFechaTagFormatoPagInteira)){
            setIcon(iconeTagsLayout);
	    setBackground(Color.decode("#b5e0c9"));
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            if(isSelected) {setBackground(Color.decode("#d4d0c5"));
              setBorder(null);
              setIcon(iconeTagsLayout);
            }
	  }
	  else if(value.toString().startsWith(SubtelaEditorProva.stringTagQuebraDepagina)){
            setIcon(iconeTagQuebraPagina);
	    setBackground(Color.decode("#b5e0c9"));
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            if(isSelected) {setBackground(Color.decode("#d4d0c5"));
              setBorder(null);
              setIcon(iconeTagQuebraPagina);
            }
	  }
      else if (isSelected) {
       setBackground(Color.decode("#d4d0c5"));
       setIcon(null);
      
      }

      else {
         setBackground(Color.white);
         setBorder(null);
         setIcon(null);
      }
      return this;
    }
    
  protected static ImageIcon createImageIcon(String path){
     java.net.URL imgURL = RenderizadorLista.class.getResource(path);
     if(imgURL != null){return new ImageIcon(imgURL);}
     else{
        System.err.println("[Classe SubTelaEditorProva] - Imagem do ícone não foi encontrada: " + path);
        return null;
     }}
    
}
