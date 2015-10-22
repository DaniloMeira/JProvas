package ClassesTeste.moduloProvas;

import com.lowagie.text.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

public class CriarHTML{

float tamPagina=691f;//medida em PT
int espaço1 =15;
int espaço2 =6;
int espaço3 =5;
float espaço4 =3.5f;

int altura_Pagina =762;
float pagina_largF1 = 260.66003f;
int pagina_largF2 = 531;


float NumQuestao_altura = 9.5f;
float Divisoria_altura = 20.5f;

int QuadroQuestao_largF1 = 349;
int QuadroQuestao_largF2 = 712;

float textoNumerado_largF1 = 252.151f;
float recuoParagrafo_textoNuF1=18.29f;
float textoNumerado_largF2 = 486.1f;

float quadroAlternativas_largF1 = 236.17f;
float quadroAlternativas_largF2 = 491.11f;

float textoPoesia_largF1 = 235.80f;
int textoPoesia_largF2 = 510;

int divAfirmativas_largF1 =240;
float divAfirmativas_largF2 = 486.1f;

FontesLetraJProvas enumFontes = new FontesLetraJProvas();
Font fonteLetraEmUso;

  
char charSeparador = File.separatorChar;
public String rodapeLadoEsq/*="JProvas - Prova de Conhecimentos Gerais"*/; 
public String rodapeLadoDir/*="São Paulo 19/06/2014"*/;
public String tituloCabecalho;
public String dadosCabecalho;
String diretorioAplicacao=System.getProperty("user.dir");
String diretorioImagens=/*System.getProperty("user.dir")+*/charSeparador+"HTML"+charSeparador+"Imagens"+charSeparador+"Imagens_Questoes"+charSeparador;
String diretorioLogotipos=System.getProperty("user.dir")+charSeparador+"HTML"+charSeparador+"Imagens"+charSeparador+"Imagens_Provas"+charSeparador;
String fonteQuestao="Arial";
int tamanhoFonte=10;
float alturaLinha=10.5f;
float alturaBloco;// variavel q armazena tamanho do bloco montado

//int larguraDivAtual;// = 279;
float espaçamento=2.5f; //espaço de linha em branco usado para formatar a pagina

public String [][] questoes;

//Primeiro eixo=nome do arquivo da figura; segundo eixo=texto descrição da figura
public String [][] figuraQuestao;

/*O primeiro eixo do array indica o tipo de questão, o segundo eixo indica: 
o espaço em linhas para a resposta(se for questao dissertativa), 
ou a letra correspondente à alternativa correta(se for questão alternativa)*/
public String [][] tipoQuestao; 


PrintWriter ProvaHtml[];
public int numPag;


Graphics grafico;
public String logotipoProva="LogotipoJProvas.gif";
String htmlLogotipo="<div class=\"logoCabecalho\"><div class=\"imgLogo\"><img src=\"HTML"+charSeparador+"Imagens"+charSeparador+"Imagens_Provas"+charSeparador+logotipoProva+"\"></img></div></div>";
public String divisoria;
public String questao;
String alternativaA;
String alternativaB;
String alternativaC;
String alternativaD;
String alternativaE;
String figura;

public String[] indicaFormatoPg2;
public String[] divisorias;
public String[] quebraDePagina;
PrintWriter[] ProvasHtml = new PrintWriter[50];
int numQuestao;
boolean formatoPg2=false;
boolean indicaQuebraDePagina=false;

public CriarHTML(){
  enumFontes.setFonteAtual(8);
  fonteLetraEmUso = enumFontes.getFonteNormal();

}


public void gerarHTML() throws FileNotFoundException, UnsupportedEncodingException{

numPag=1;	

if(logotipoProva.compareTo("logotipoPadrao.jpg")!=0){
  dimensionaImagemLogotipo();
  //System.out.println("logotipo não é o padrão");
}

if(indicaFormatoPg2[0]!=null && indicaFormatoPg2[0].compareTo("sim")==0){formatoPg2=true;}


for(numQuestao=0; numQuestao<=questoes.length;numQuestao++){

	if(numQuestao>0){numQuestao--;}//Para evitar que uma questao seja pulada após criar nova página
        //System.out.println("numero questoes:"+questoes.length);
	//System.out.println("numero de folhas é:"+numPag);
        //System.out.println("numero da questao é:"+numQuestao);
	ProvasHtml[numPag]= new PrintWriter("HTML"+charSeparador+"prova"+numPag+".xhtml","UTF-8");
	criarCabecalhoHTML();
	if(formatoPg2==false){montarCorpoHtmlFormatoPg1();} //Formato1=layout pagina dividida, Formato2=layout pagina inteira
	else if(formatoPg2==true){montarCorpoHtmlFormatoPg2();}
	numPag++;
}
criarRodapePaginasHtml();
}


public void criarCabecalhoHTML(){
	ProvasHtml[numPag].println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	ProvasHtml[numPag].println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//BR\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
	ProvasHtml[numPag].println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
	ProvasHtml[numPag].println("<head><title></title>");
	ProvasHtml[numPag].println("<link rel=\"stylesheet\" type=\"text/css\" href=\"HTML/CSS/DivTeste.css\" media=\"screen, print\" charset=\"UTF-8\"/>");
	ProvasHtml[numPag].println("</head>");
	ProvasHtml[numPag].println("<body>");
	ProvasHtml[numPag].println("");
	ProvasHtml[numPag].println("<div class=\"divBordaPagina\">");
	ProvasHtml[numPag].println("<div class=\"cabecalho\" align=\"left\">");
	ProvasHtml[numPag].println(htmlLogotipo);
	ProvasHtml[numPag].println("<div class=\"cabecalho_txt1\"><center>"+tituloCabecalho+"</center></div>");
        if(numPag==1){ProvasHtml[numPag].println("<div class=\"cabecalho_txt2\"><div class=\"txtBranco\">.</div>"+dadosCabecalho+"</div>");}
	ProvasHtml[numPag].println("</div>");
}




public void montarCorpoHtmlFormatoPg1(){
	
	float divEsquerda=altura_Pagina;//1060;//pixels
	float divDireita=altura_Pagina;//1060;//pixels
        alturaBloco=0;
        calculaTamanhoBloco(numQuestao);
	ProvasHtml[numPag].println("<div class=\"divEsquerda\">");
	
	while(divEsquerda>=alturaBloco && numQuestao<questoes.length){
	
		if(formatoPg2==true || indicaQuebraDePagina==true){
                  ProvasHtml[numPag].println("</div>");//fechar divEsquerda
                  criarDivisoriaHtmlPagFormato1();
                  indicaQuebraDePagina=false;return;}

		//System.out.println("Questao:"+numQuestao+", TamanhoBloco= "+alturaBloco);
		
		if(alturaBloco<=divEsquerda){
                  //montarBloco(numQuestao,1);
                  ProvasHtml[numPag].println(blocoQuestao);
                  divEsquerda-= alturaBloco;
                  numQuestao++;
                  if(numQuestao<questoes.length){
                    //calculaTamanhoBloco(numQuestao);
                    if(quebraDePagina[numQuestao]!=null && quebraDePagina[numQuestao].compareTo("sim")==0){indicaQuebraDePagina=true;}
                    if(indicaFormatoPg2[numQuestao]!=null && indicaFormatoPg2[numQuestao].compareTo("sim")==0){formatoPg2=true;}
                  }
		}
		
		else if(alturaBloco>altura_Pagina){
                  //System.out.println("entrou no else if linha 193");
                  ProvasHtml[numPag].println("<p>Erro: Tamanho da questão é maior que tamanho da pagina, verifique.</p>");
                  numQuestao++;
                  if(numQuestao<questoes.length){
                    if(quebraDePagina[numQuestao]!=null && quebraDePagina[numQuestao].compareTo("sim")==0){indicaQuebraDePagina=true;};
                    if(indicaFormatoPg2[numQuestao]!=null && indicaFormatoPg2[numQuestao].compareTo("sim")==0){formatoPg2=true;};
                  }
		}
                if(numQuestao<questoes.length){calculaTamanhoBloco(numQuestao);}
		//System.out.println("tamanho atual da DIvEsquerda:"+divEsquerda);
	}
	
	ProvasHtml[numPag].println("</div>"); //verificar, fechando divesquerda
	criarDivisoriaHtmlPagFormato1();
	
	if(numQuestao<questoes.length){
		ProvasHtml[numPag].println("<div class=\"divDireita\">");
                alturaBloco=0;
                calculaTamanhoBloco(numQuestao);
		while(divDireita>=alturaBloco && numQuestao<questoes.length){
			//System.out.println("FORMATO DA PAGINA= "+ indicaFormatoPg2[numQuestao]);
			if(formatoPg2==true || indicaQuebraDePagina==true){
                          ProvasHtml[numPag].println("</div>");
                          indicaQuebraDePagina=false;
                          return;
			}
			
			if(alturaBloco<=divDireita){
				ProvasHtml[numPag].println(blocoQuestao);
				divDireita-=alturaBloco;
				numQuestao++;
				if(numQuestao<questoes.length){
                    if(quebraDePagina[numQuestao]!=null && quebraDePagina[numQuestao].compareTo("sim")==0){indicaQuebraDePagina=true;};
					if(indicaFormatoPg2[numQuestao]!=null && indicaFormatoPg2[numQuestao].compareTo("sim")==0){formatoPg2=true;};
				}
			}
			
			else if(alturaBloco>altura_Pagina){
				ProvasHtml[numPag].println("<p>Erro: Tamanho da questão é maior que tamanho da pagina, verifique.</p>");
				numQuestao++;
				if(numQuestao<questoes.length){
					calculaTamanhoBloco(numQuestao);
                    if(quebraDePagina[numQuestao]!=null && quebraDePagina[numQuestao].compareTo("sim")==0){indicaQuebraDePagina=true;};
					if(indicaFormatoPg2[numQuestao]!=null && indicaFormatoPg2[numQuestao].compareTo("sim")==0){formatoPg2=true;};
				}
			}
                    if(numQuestao<questoes.length){calculaTamanhoBloco(numQuestao);}
		}
		
		ProvasHtml[numPag].println("</div>");
	}
}



public void montarCorpoHtmlFormatoPg2(){
	alturaBloco=0;
	float espacoDisponivel=altura_Pagina;
	ProvasHtml[numPag].println("<div class=\"divFormato2\">");
	calculaTamanhoBloco(numQuestao);
	while(espacoDisponivel>=alturaBloco && numQuestao<questoes.length){
	    
		//System.out.println("FORMATO DA PAGINA= "+ indicaFormatoPg2[numQuestao]);
		if(formatoPg2==false || indicaQuebraDePagina==true){ProvasHtml[numPag].println("</div>");indicaQuebraDePagina=false;return;}
            
		if(alturaBloco<=espacoDisponivel){
			ProvasHtml[numPag].println(blocoQuestao);
			espacoDisponivel-=alturaBloco;
			numQuestao++;
			if(numQuestao<questoes.length){
				if(quebraDePagina[numQuestao]!=null && quebraDePagina[numQuestao].compareTo("sim")==0){indicaQuebraDePagina=true;};
				if(indicaFormatoPg2[numQuestao]!=null && indicaFormatoPg2[numQuestao].compareTo("nao")==0){formatoPg2=false;};
			}
		}
	
		else if(alturaBloco>altura_Pagina){
			ProvasHtml[numPag].println("<p>Erro: Tamanho da questão é maior que tamanho da pagina, verifique.</p>");
			numQuestao++;
                        alturaBloco=0;
			if(numQuestao<questoes.length){
                if(quebraDePagina[numQuestao]!=null && quebraDePagina[numQuestao].compareTo("sim")==0){indicaQuebraDePagina=true;};
				if(indicaFormatoPg2[numQuestao]!=null && indicaFormatoPg2[numQuestao].compareTo("nao")==0){formatoPg2=false;};
			}
		}
         //System.out.println("Espaço disponivel:"+espacoDisponivel);
	 //System.out.println("Tamanho Bloco Questao:"+alturaBloco);
         if(numQuestao<questoes.length){calculaTamanhoBloco(numQuestao);}
	}
        
	ProvasHtml[numPag].println("</div>");
}


public void criarDivisoriaHtmlPagFormato1(){
	ProvasHtml[numPag].println("<center>");
	ProvasHtml[numPag].println("<div class=\"barraCentro\">");
	ProvasHtml[numPag].println("</div>");
	ProvasHtml[numPag].println("</center>");
}

public void criarRodapePaginasHtml(){
	for(int i=1;i<numPag;i++){
	ProvasHtml[i].println("<div class=\"rodape\">");
        ProvasHtml[i].println("<div class=\"rodape_txt1\">"+rodapeLadoEsq+"</div>");
        ProvasHtml[i].println("<div class=\"rodape_txt2\">"+rodapeLadoDir+" | Pag."+(i)+"/"+(numPag-1)+"</div>");
	ProvasHtml[i].println("</div>");
        ProvasHtml[i].println("</div>");
	ProvasHtml[i].println("</body>");
	ProvasHtml[i].println("</html>");
	ProvasHtml[i].close();}
}

String blocoQuestao;

public void calculaTamanhoBloco(int numQuestao){
	
	blocoQuestao= "";
	alturaBloco=0; 

	if(formatoPg2==true){blocoQuestao+= "<div class=\"subDivFormato2\">";}// pagina Formato2: dividida ao meio
        else{blocoQuestao+= "<div class=\"subDiv\">";}//pagina Formato1: sem divisão
	
        //verificar se foi incluído uma divisória na prova
	if(divisorias[numQuestao]!=null){
		blocoQuestao+= dimensionaString("<divisoria>"+divisorias[numQuestao]+"</divisoria>");
		blocoQuestao+= dimensionaString("<espacamento4/>");
	}
	
	//Numerador da questão
	blocoQuestao+= "<div class=\"numeradorQuestao\">"+(numQuestao+1)+"</div>";
	alturaBloco+=NumQuestao_altura;
	blocoQuestao+= dimensionaString("<espacamento4/>");
 
	//Incluir texto da questão
	blocoQuestao+= dimensionaString("<p>"+questoes[numQuestao][0]+"</p>");
	blocoQuestao+= dimensionaString("<espacamento2/>");
	
	//incluir imagem
	if(figuraQuestao[numQuestao][0]!=null){
		dimensionaFigura(numQuestao);
	}
 
	//Incluir alternativas da questão:
	if(tipoQuestao[numQuestao][0].compareTo("alt")==0){

		alternativaA= questoes[numQuestao][1];
		alternativaB= questoes[numQuestao][2];
		alternativaC= questoes[numQuestao][3];
		alternativaD= questoes[numQuestao][4];
		alternativaE= questoes[numQuestao][5];
		blocoQuestao+= dimensionaString("<alternativas>"+
			"<li><p>" +alternativaA +"</p></li>"+ "<espacamento3/>"+ 
			"<li><p>" +alternativaB + "</p></li>"+ "<espacamento3/>"+ 
			"<li><p>" +alternativaC + "</p></li>"+ "<espacamento3/>"+ 
			"<li><p>" +alternativaD + "</p></li>"+ "<espacamento3/>"+ 
			"<li><p>" +alternativaE + "</p></li>"+ "</alternativas>");
		blocoQuestao+= dimensionaString("<espacamento1/>");
	}
 
	else if(tipoQuestao[numQuestao][0].compareTo("dis")==0){
		int linhasResposta=1;
		linhasResposta=Integer.parseInt(tipoQuestao[numQuestao][1]);
		for(int i=1; i<=linhasResposta;i++){alturaBloco+=alturaLinha;dimensionaString("<br/>");}
	}
 
	blocoQuestao+= "</div>";
	//System.out.println("Questao"+numQuestao+", TAMANHO TOTAL EM PT DO BLOCO:"+alturaBloco);
}



public void montarBloco(int numQuestao, int formatoPg){

}

int p2=0;
int p1=0;
boolean tagTexto_ativa;
boolean tagPoesia_ativa;
boolean tagAfirmativas_ativa;
boolean inclui_tagParagrafoAdicional;
boolean posicionadoNoInicioParagrafo; //indica se está posicionado no primeiro paragrafo da Div.
boolean tagBR_ativa;
boolean tagAlternativas_ativa;
boolean tagDivisoria_ativa;
boolean tagNegritoAtiva;
boolean tagItalicoAtiva;
boolean tagNegritoItalicoAtiva;
int linhas_tagTexto;
int posicaoInicial_tag;
float larguraDiv;
float larguraDivAtual;
String trecho;
float espaçoParagrafo;
float tamanhoRestante;
int numeroLinhas;
String armazena_CaractereEspaço;
String armazena_CaractereEspaço2;



@SuppressWarnings("unused")
public String dimensionaString(String X){
  /*retirar tabulaçoes e quebras de linha digitadas no texto da questão, formatações do texto
  devem ser feitas usando somente as tags <p> e <br/>
  */
  X=X.replaceAll("\\r\\n|\\r|\\n", " ");
  posicaoInicial_tag=0;
  espaçoParagrafo=0;
  armazena_CaractereEspaço="";
  armazena_CaractereEspaço2="";
  linhas_tagTexto=0; 
  tagTexto_ativa=false;
  tagPoesia_ativa=false;
  tagAfirmativas_ativa=false;
  inclui_tagParagrafoAdicional=false;
  posicionadoNoInicioParagrafo=true;
  tagBR_ativa=false;
  tagAlternativas_ativa=false;
  tagDivisoria_ativa=false;
  tagNegritoAtiva=false;
  tagItalicoAtiva=false;
  tagNegritoItalicoAtiva=false;
  numeroLinhas=0;
  tamanhoRestante=0;

	if(formatoPg2==true){larguraDiv = pagina_largF2; }
	else{larguraDiv = pagina_largF1;}

	float quebraDeString = medidorFonte("-");
	p1=0;
	float tamanho;
	if(X.charAt(0)=='<'){p2=X.indexOf(">");}
    else p2=X.indexOf(" ");
	if (p2<0){p2 = X.length();}
	else p2+=1;
	trecho = "";
	String subTrecho;

	
 do{ 
      subTrecho = X.substring(p1,p2);
      subTrecho=verificaTagsTamanho(subTrecho);
      tamanho=medidorFonte(subTrecho);
      larguraDivAtual=larguraDiv-espaçoParagrafo;
      //System.out.println("while/ subtrecho: "+subTrecho+" /tamanho2:"+tamanho2+" /restante:"+(tamanhoRestante-tamanho));	
	  //1º verificar se tamanho da string é maior q a largura da DIV. Se for, a string será dividida para a linha abaixo
	  if(tamanho > larguraDivAtual){
            //System.out.println("entrou no if 1 [tamanho > larguraDivAtual]");
            if(tagBR_ativa==true){tamanhoRestante=larguraDiv-espaçoParagrafo;
              tagBR_ativa=false;numeroLinhas++;alturaBloco+=alturaLinha;
            }
	    subTrecho=quebrarString(subTrecho,tamanhoRestante-quebraDeString);
	    trecho += subTrecho + "-";
	    numeroLinhas++;
            alturaBloco+=alturaLinha;
            //System.out.println("nova linha!!!");
            trecho+= System.getProperty("line.separator");//quebra de linha para facilitar visualização do log
            if(tagTexto_ativa==true){++linhas_tagTexto;}
	    p1+=subTrecho.length();
	    tamanhoRestante=larguraDivAtual+espaçoParagrafo;
            espaçoParagrafo=0;
          }
	  else if(tamanho> tamanhoRestante){
            //System.out.println("entrou no if 1 [tamanho> tamanhoRestante]");
            //preenche o restante da linha com espaços e mantem o mesmo trecho de string da iteração anterior
	    trecho+=insereEspacoBranco(tamanhoRestante);
	    numeroLinhas++;
            alturaBloco+=alturaLinha;
            trecho +=System.getProperty("line.separator");
            //System.out.println("nova linha!!!");
            if(tagTexto_ativa==true){++linhas_tagTexto;}
            if(inclui_tagParagrafoAdicional==true){trecho+="</p><p>";inclui_tagParagrafoAdicional=false;}
	    tamanhoRestante=larguraDivAtual;
            espaçoParagrafo=0;
	  }
          else {
            //System.out.println("entrou no if 3[else]");
	    trecho+=subTrecho;
	    p1=p2;
            armazena_CaractereEspaço=armazena_CaractereEspaço2;
            armazena_CaractereEspaço2="";
            if(tamanho>0){
              if(tagBR_ativa==true){
                if(tamanhoRestante<larguraDivAtual){
                  tamanhoRestante=larguraDiv-espaçoParagrafo;numeroLinhas++;alturaBloco+=alturaLinha;
                  if(tagTexto_ativa==true){++linhas_tagTexto;}
                }
                else if(tamanhoRestante==larguraDivAtual & posicionadoNoInicioParagrafo==true){
                  if(tagTexto_ativa==true){++linhas_tagTexto;}
                  numeroLinhas++;alturaBloco+=alturaLinha;
                }

                //System.out.println("ENTROU BR");

                tagBR_ativa=false;
              }
              posicionadoNoInicioParagrafo=false;//inseriu texto na linha, não está mais no inicio do paragrafo.
            }
            tamanhoRestante-=tamanho;
	  }
    
	 if(p1<X.length()){
 

      if(X.charAt(p1)=='<'){p2 = X.indexOf(">",p1)+1;}    
      else p2 = X.indexOf(" ",p1)+1;
      if(p2<=0){p2=X.length();};
      /*System.out.println("Saida da iteração de trecho | espaçoParagrafo="+espaçoParagrafo+"tamanhoTrecho="+tamanho+"; tamanhoRestante="+tamanhoRestante+"; p1="+p1+"; p2="+p2
            +" Número de linhas="+numeroLinhas);*/		 
	 }
	}
	while(p2<=X.length() && p1!=p2);
	
	//System.out.println(System.getProperty("line.separator")+"FINAL: "+trecho+System.getProperty("line.separator")+" , numero de linhas ="+numeroLinhas);
	return trecho;
}

/*@metodo para verificar modificaçoes no tamanho da fonte e largura da Div devido a 
utilização de uma tag de formataçao*/
public String verificaTagsTamanho(String x){
 //System.out.println("ENTROU NA VERIFICAÇAO DE TAGS: subtrecho="+x+";");
  String w="";

int posicao2= x.indexOf('>');  
if(x.charAt(0)=='<' & posicao2>0){
  
  String y= x.substring(0, posicao2+1);
  //System.out.println("ENTROU No charAt(0) da VERIFICAÇOA DE TAGS!");
  switch(y){
    
    case "<b>":{
      trecho+="<b>";
      if(tagItalicoAtiva==true){fonteLetraEmUso= enumFontes.getFonteNegritoItalico();tagNegritoItalicoAtiva=true;}
      else {fonteLetraEmUso= enumFontes.getFonteNegrito();tagNegritoAtiva=true;}      
      if(tamanhoRestante< larguraDivAtual & medidorFonte(" ")<=tamanhoRestante){return armazena_CaractereEspaço;}
	  break; }
    
    case "</b>":{
      trecho+="</b>"; 
      if(tagNegritoItalicoAtiva==true){fonteLetraEmUso= enumFontes.getFonteItalico();tagNegritoItalicoAtiva=false;}
      else{fonteLetraEmUso= enumFontes.getFonteNormal();tagNegritoAtiva=false;}
      break; }
    
    case "<i>":{
      trecho+="<i>"; 
      if(tagNegritoAtiva==true){fonteLetraEmUso= enumFontes.getFonteNegritoItalico();tagNegritoItalicoAtiva=true;}
      else {fonteLetraEmUso= enumFontes.getFonteItalico();tagItalicoAtiva=true;}
      if(tamanhoRestante< larguraDivAtual & medidorFonte(" ")<=tamanhoRestante){return armazena_CaractereEspaço;}
	  break; }
    
    case "</i>":{
      trecho+="</i>"; 
      if(tagNegritoItalicoAtiva==true){fonteLetraEmUso= enumFontes.getFonteNegrito();tagNegritoItalicoAtiva=false;}
      else{fonteLetraEmUso= enumFontes.getFonteNormal();tagItalicoAtiva=false;}
      break; }
    
    case "<u>":{
      trecho+="<u>";
	  if(tamanhoRestante< larguraDivAtual & medidorFonte(" ")<=tamanhoRestante){return armazena_CaractereEspaço;}
      break;
    }
    
    case "</u>":{
      trecho+="</u>";
      break;
    }
    
    case "<s>":{
      trecho+="<s>";
	  if(tamanhoRestante< larguraDivAtual & medidorFonte(" ")<=tamanhoRestante){return armazena_CaractereEspaço;}
      break;
    }
    
    case "</s>":{
      trecho+="</s>";
      break;
    }
    
    case "<br/>":{
                  if(tagBR_ativa==true) {
                    numeroLinhas++;alturaBloco+=alturaLinha;
                    if(tagTexto_ativa==true){++linhas_tagTexto;}
                  }
                  trecho+="<br/>";
                  tagBR_ativa=true;
                  espaçoParagrafo=0;
                  break;
                  
                 }
    
    case "<center>":{trecho+="<center>";if(tamanhoRestante< larguraDivAtual & medidorFonte(" ")<=tamanhoRestante){return armazena_CaractereEspaço;}break;}
    
    case "</center>":{trecho+="</center>";break;}
    
    case "<p>":{
                  trecho+=insereEspacoBranco(tamanhoRestante);
                  tamanhoRestante=0;
                  if(tagTexto_ativa==true){espaçoParagrafo=18.31f;}
                  if(tagPoesia_ativa==true | tagAfirmativas_ativa==true){
                    if(posicionadoNoInicioParagrafo==false){
                    trecho+="</p></li><li><p>";
                    }
                  }
                  else trecho+="<p>";
 
                  if(tagBR_ativa==true){
                    tagBR_ativa=false;
                    if(tagTexto_ativa==true & posicionadoNoInicioParagrafo==true){
                      ++linhas_tagTexto;
                      numeroLinhas++;alturaBloco+=alturaLinha;
                    }
                    if(tagAfirmativas_ativa==true & posicionadoNoInicioParagrafo==true){
                      numeroLinhas++;alturaBloco+=alturaLinha;
                    }
                     
                    else if(tagTexto_ativa==false & tagAfirmativas_ativa==false & tagPoesia_ativa==false){
                      numeroLinhas++;alturaBloco+=alturaLinha;}
                  }
                posicionadoNoInicioParagrafo=true;  
				if(tamanhoRestante< larguraDivAtual & medidorFonte(" ")<=tamanhoRestante){return armazena_CaractereEspaço;}
                break;
               }
    
    
    
    case "</p>":{
 
 trecho+=insereEspacoBranco(tamanhoRestante);

                 if(tagPoesia_ativa==true | tagAfirmativas_ativa==true){
                   if(posicionadoNoInicioParagrafo==false){
                     trecho+="</p></li><li><p>";
                   }
                 }
                 else trecho+="</p>";
                 posicionadoNoInicioParagrafo=true;
                 if(tagTexto_ativa==true){espaçoParagrafo=18.31f;}
                 if(tagBR_ativa==true){
                    tagBR_ativa=false;
                    if(tagTexto_ativa==true){
                      espaçoParagrafo=0;
                    } 
                    if(tamanhoRestante==0){numeroLinhas++;alturaBloco+=alturaLinha;}
                 }
                 tamanhoRestante=0; //inserido tag alternativa
                 break;}
    
    case "<h1>":{trecho+="<h1>";alteraFonte(1);if(tamanhoRestante< larguraDivAtual & medidorFonte(" ")<=tamanhoRestante){return armazena_CaractereEspaço;}break; }
    case "</h1>":{trecho+="</h1>";if(tagDivisoria_ativa==true){alteraFonte(7);}else alteraFonte(8);break;}
    case "<h2>":{trecho+="<h2>";if(tagDivisoria_ativa==true){alteraFonte(7);}else alteraFonte(2);if(tamanhoRestante< larguraDivAtual & medidorFonte(" ")<=tamanhoRestante){return armazena_CaractereEspaço;};break;  }
    case "</h2>":{trecho+="</h2>";if(tagDivisoria_ativa==true){alteraFonte(7);}else alteraFonte(8);break;  }
    case "<h3>":{trecho+="<h3>";if(tagDivisoria_ativa==true){alteraFonte(7);}else alteraFonte(3);if(tamanhoRestante< larguraDivAtual & medidorFonte(" ")<=tamanhoRestante){return armazena_CaractereEspaço;}break; }
    case "</h3>":{trecho+="</h3>";if(tagDivisoria_ativa==true){alteraFonte(7);}else alteraFonte(8);break; }
    case "<h4>":{trecho+="<h4>";if(tagDivisoria_ativa==true){alteraFonte(7);}else alteraFonte(4);if(tamanhoRestante< larguraDivAtual & medidorFonte(" ")<=tamanhoRestante){return armazena_CaractereEspaço;}break; }
    case "</h4>":{trecho+="</h4>";if(tagDivisoria_ativa==true){alteraFonte(7);}else alteraFonte(8); break; }
    case "<h5>":{trecho+="<h5>";if(tagDivisoria_ativa==true){alteraFonte(7);}else alteraFonte(5);if(tamanhoRestante< larguraDivAtual & medidorFonte(" ")<=tamanhoRestante){return armazena_CaractereEspaço;}break; }
    case "</h5>":{trecho+="</h5>";if(tagDivisoria_ativa==true){alteraFonte(7);}else alteraFonte(8);break; }
    case "<h6>":{trecho+="<h6>";if(tagDivisoria_ativa==true){alteraFonte(7);}else alteraFonte(6);if(tamanhoRestante< larguraDivAtual & medidorFonte(" ")<=tamanhoRestante){return armazena_CaractereEspaço;}break; }
    case "</h6>":{trecho+="</h6>";if(tagDivisoria_ativa==true){alteraFonte(7);}else alteraFonte(8);break; }	
	
    case "<poesia>":{
                     tagPoesia_ativa=true;
                     posicionadoNoInicioParagrafo=true;
                     trecho+=insereEspacoBranco(tamanhoRestante);
                     if(formatoPg2==true){
                      larguraDiv=textoPoesia_largF2;
                      trecho+="</p><p><div class=\"divPoesia_f2\"><ul><li><p>";
                     }
                     else{
                      larguraDiv=textoPoesia_largF1;
                      trecho+="</p><p><div class=\"divPoesia_f1\"><ul><li><p>";
                     }
                     tamanhoRestante=0;
                     break;
                    }
    
    case "</poesia>":{
                      tagPoesia_ativa=false;
                      if(formatoPg2==true){larguraDiv=pagina_largF2;} 
                      else {larguraDiv=pagina_largF1;}
                      trecho+=insereEspacoBranco(tamanhoRestante);
                      trecho+="</p></li></ul></div>";
                      tamanhoRestante=0;
                      inclui_tagParagrafoAdicional=true;//para incluir uma tag <p> caso ainda haja texto a ser incluido após a div </poesia>
                      
                      if(tagBR_ativa==true){
                        tagBR_ativa=false;
                        if(posicionadoNoInicioParagrafo==true){numeroLinhas++;alturaBloco+=alturaLinha;}
                      }
                      posicionadoNoInicioParagrafo=true;
                      break;
                     }
    
    case "<texto>":{if(formatoPg2==true){larguraDiv=textoNumerado_largF2;} 
                    else {larguraDiv=textoNumerado_largF1;}
                    tagTexto_ativa=true;
                    trecho+=insereEspacoBranco(tamanhoRestante);
                    posicaoInicial_tag=trecho.length();
                    espaçoParagrafo=18.31f;
                    tamanhoRestante=0;
                    if(tagBR_ativa==true){
                      if(posicionadoNoInicioParagrafo==true){numeroLinhas++;alturaBloco+=alturaLinha;}
                      tagBR_ativa=false;
                    }
                    posicionadoNoInicioParagrafo=true;
                    break;
                   }
    
    case "</texto>":{if(formatoPg2==true){larguraDiv=pagina_largF2;} 
                     else {larguraDiv=pagina_largF1;}
                     tagTexto_ativa=false;
                     trecho+=insereEspacoBranco(tamanhoRestante);
                     montarTagTexto();
                     tamanhoRestante=0;
                    tagBR_ativa=false;
                     posicionadoNoInicioParagrafo=true;
                     linhas_tagTexto=0;
                     break;}
    
    case "<afirmativas>":{
                          posicionadoNoInicioParagrafo=true;
                          tagAfirmativas_ativa=true;
                          if(formatoPg2==true){
                            larguraDiv=divAfirmativas_largF2;
                            trecho+="</p><p><div class=\"divAfirmativas_f2\"><ul><li><p>";
                          }
                          else{
                            larguraDiv=divAfirmativas_largF1;
                            trecho+="</p><p><div class=\"divAfirmativas_f1\"><ul><li><p>";
                          }
                          tamanhoRestante=0;
                          break;
                         }
    
    case "</afirmativas>":{
                      tagAfirmativas_ativa=false;
                      if(formatoPg2==true){larguraDiv=pagina_largF2;} 
                      else {larguraDiv=pagina_largF1;}
                      trecho+=insereEspacoBranco(tamanhoRestante);
                      trecho+="</p></li></ul></div>";
                      tamanhoRestante=0;
                      inclui_tagParagrafoAdicional=true;
                      posicionadoNoInicioParagrafo=true;
                      break;
                     }
					 
	case "<alternativas>":{
			if(formatoPg2==true){trecho+="<div class=\"divAltern_f2\"><div class=\"alternativas\">"; larguraDiv= quadroAlternativas_largF2;}// pagina Formato1: dividida ao meio
			else{trecho+="<div class=\"divAltern_f1\"><div class=\"alternativas\">"; larguraDiv= quadroAlternativas_largF1; }//pagina Formato2: sem divisão
			trecho+="<ul>";
			tagAlternativas_ativa= true;
			posicionadoNoInicioParagrafo=true;
			break;	
		}
	
	case "</alternativas>":{
			trecho+="</ul>";
			trecho+="</div></div>";
			tagAlternativas_ativa= false;
                        tamanhoRestante=0;
			posicionadoNoInicioParagrafo=true;
            break;
		}
        
        case "<divisoria>":{
          if(formatoPg2==true){trecho+="<div class=\"TituloDivisoriaFormato2\">";}
          else trecho+="<div class=\"TituloDivisoria\">";
          alteraFonte(7);
          alturaBloco+=0.3;//tamanho da borda
          tagDivisoria_ativa=true;
          break;
        }
    
        case "</divisoria>":{
          trecho+="</div>";
          alteraFonte(8);
          alturaBloco+=0.3;
          tagDivisoria_ativa=false;
          break;
        }
        
	case "<espacamento1/>":{
			trecho+="<div class=\"esp1\"></div>";
			alturaBloco+=espaço1;
                        
                     tamanhoRestante=0;
                     tagBR_ativa=false;
                     posicionadoNoInicioParagrafo=true;
                     linhas_tagTexto=0;
            break;
		}
	
	case "<espacamento2/>":{
			trecho+="<div class=\"esp2\"></div>";
			alturaBloco+=espaço2;
                        
                     tamanhoRestante=0;
                     tagBR_ativa=false;
                     posicionadoNoInicioParagrafo=true;
                     linhas_tagTexto=0;
                        
			break;
		}
		
	case "<espacamento3/>":{
			trecho+="<div class=\"esp3\"></div>";
			alturaBloco+=espaço3;
                        
                        tamanhoRestante=0;
                     tagBR_ativa=false;
                     posicionadoNoInicioParagrafo=true;
                     linhas_tagTexto=0;
                        
			break;
	}
	
	case "<espacamento4/>":{
			trecho+="<div class=\"esp4\"></div>";
			alturaBloco+=espaço4;
                        
                        tamanhoRestante=0;
                        tagBR_ativa=false;
                        posicionadoNoInicioParagrafo=true;
                        linhas_tagTexto=0;
                        
			break;
	}
	
	case "<li>":{
			if(tagAlternativas_ativa==true){trecho+="<li>";}
			break;	
	}
	
	case "</li>":{
			if(tagAlternativas_ativa==true)trecho+="</li>";
			break;	
	}
	
        
  }
  
 if(!y.startsWith("</")){
   if(tamanhoRestante< larguraDivAtual & medidorFonte(" ")<=tamanhoRestante){return armazena_CaractereEspaço;};   
 }
 
  p2=p1+y.length();
  return "";
}

else{
  
  for(int i=0; i<x.length(); i++){
    if (x.charAt(i)=='<'){
      p2=p1+w.length(); 
      break;
    }
    else w+=x.charAt(i);
  }
  
}
 
 
	if(w.compareTo(" ")==0){armazena_CaractereEspaço2=" ";
          //System.out.println("armazenou espaco, linha 847");
          return "";
        }
 
	//insere caractere de espaço armazenado no trecho anterior, caso o trecho atual não esteja no início de uma linha nova
	//verifica se trecho está no inicio de uma linha nova
	if(tamanhoRestante>= larguraDivAtual){armazena_CaractereEspaço="";}//se sim, retira caractere armazenado
	else{w=armazena_CaractereEspaço+w;
          //System.out.println("inseriu caractere armazenado");
        }//se não, insere o caractere de espaço no início do trecho atual
 
  
  //se na ultima posição da string houver um caractere de espaço, ele ñ pode ser considerado quando for verificar se o trecho cabe ou não
  // no espaço restante de Div; portanto o caractere é armazenado em uma variavel e jogado no inicio do trecho de string seguinte,
  //caso este trecho não esteja no início de uma nova linha.
 if(w.charAt(w.length()-1)==' '){armazena_CaractereEspaço2=" ";w= w.substring(0,w.length()-1);}
 return w;
}


public void alteraFonte(int nuFonte){

  switch (nuFonte){
    case 1:{enumFontes.setFonteAtual(1);break;}
    case 2:{enumFontes.setFonteAtual(2);break;}
    case 3:{enumFontes.setFonteAtual(3);break;}
    case 4:{enumFontes.setFonteAtual(4);break;}
    case 5:{enumFontes.setFonteAtual(5);break;}
    case 6:{enumFontes.setFonteAtual(6);break;}
    case 7:{enumFontes.setFonteAtual(7);break;}
    case 8:{enumFontes.setFonteAtual(8);break;}
  }
  fonteLetraEmUso = enumFontes.getFonteNormal();
  alturaLinha=enumFontes.getTamanhoLinha();
}


public void montarTagTexto(){
  String saidaString;
  saidaString="</p><table>";
  for(int i=1; i<=linhas_tagTexto; i++){
    saidaString+="<tr><td>"+i+"</td></tr>";
  }
  saidaString+="</table>";
  if(formatoPg2==true){saidaString+="<div class=\"texto_tipo2\">";} 
  else {saidaString+="<div class=\"texto_tipo1\">";}
  String parte1=trecho.substring(0,posicaoInicial_tag);
  parte1+=saidaString;
  String parte2=trecho.substring(posicaoInicial_tag);
  parte2+="</div><p>";
  saidaString=parte1+parte2;
  trecho=saidaString;
}


public void dimensionaFigura(int numQuestao){
    figura = figuraQuestao[numQuestao][0];
    int largura=0;
    int altura=0;
	BufferedImage ArquivoImagem;
	try {
		//http://stackoverflow.com/questions/672916/how-to-get-image-height-and-width-using-java
		ArquivoImagem = ImageIO.read(new File(diretorioAplicacao+diretorioImagens+figura));
		largura = ArquivoImagem.getWidth();
		altura = ArquivoImagem.getHeight();
		//System.out.println("largura da imagem:"+largura+" altura:"+altura);
                       //verifica o formato atual da pagina e dimensiona a figura'
                       if(formatoPg2==false){
                         if(largura>345){
                            altura=(altura*345)/largura; 
                            largura=345;}
                       }
	} catch (IOException e) {
          blocoQuestao+= "<center><p>Arquivo da imagem("+figura+") não existe/foi excluído</p></center>";
          //System.out.println("Imagem não encontrada");
          return;
      }
	
    //http://stackoverflow.com/questions/139655/convert-pixels-to-points
    alturaBloco+=(altura*72)/96;
    //System.out.println("Altura da imagem:" + (altura*72)/96 );
    blocoQuestao+= "<p><center><img src=\"HTML"+charSeparador+/*diretorioImagens+*/"Imagens"+charSeparador+"Imagens_Questoes"+charSeparador+figura+"\" height=\""+altura+"\" width=\""+largura+"\"></img></center></p>";

}

public void dimensionaImagemLogotipo(){
  int largura=0;
  int altura=0;
		BufferedImage ArquivoImagem;
		try {
			//http://stackoverflow.com/questions/672916/how-to-get-image-height-and-width-using-java
			ArquivoImagem = ImageIO.read(new File(diretorioLogotipos+logotipoProva));
			largura = ArquivoImagem.getWidth();
			altura = ArquivoImagem.getHeight();
			//System.out.println("largura do logotipo:"+largura+" altura:"+altura);
                        //verifica o formato atual da pagina e dimensiona a figura'
                        if(largura>130){
                             //altura=(altura*135)/largura; 
                             largura=130;}
                        if(altura>38){
                            //largura=(largura*38)/altura; 
                            altura=38;
                            }
                  htmlLogotipo="<div class=\"logoCabecalho\"><div class=\"imgLogo\"><img src=\"HTML"+charSeparador+"Imagens"+charSeparador+"Imagens_Provas"+charSeparador+logotipoProva+"\" height=\""+altura+"\" width=\""+largura+"\"></img></div></div>";

		} catch (IOException e) {
           
           //System.out.println("imagem de logotipo nao localizada");
           return;
          }
  
}


public float calculalinhasFigura(){
 float linhas=1;
 int posicao1=figura.indexOf("height="); 
 int posicao2=figura.indexOf(" width");
 if(posicao1>0 && posicao2>0){
  String x = figura.substring(posicao1+8, posicao2-1);
  float altura = Integer.parseInt(x);
  //http://stackoverflow.com/questions/139655/convert-pixels-to-points
  linhas = (altura*72)/96;
  //System.out.println("O numero de pixels(altura) "+altura+" /Conversao em PT="+(altura*72)/96 +"/ linhas da figura é: "+linhas);
 }
 alturaBloco+=alturaLinha;
 return linhas;
}



@SuppressWarnings("serial")
public float medidorFonte(String x){
    //System.out.println("trecho:"+x+" , tamanho da string é:"+tamanho);
    float tamanho= fonteLetraEmUso.getCalculatedBaseFont(true).getWidthPoint(x, fonteLetraEmUso.getCalculatedSize());     
    return tamanho;
}


public String insereEspacoBranco(float i){
	String espaço=" ";
	while(medidorFonte(espaço)<= i && medidorFonte(espaço+" ")<=i){
		espaço+=" ";
	}
	return espaço;
}

public String quebrarString(String X, float i){
  //System.out.println("Entrou na quebra de string");
  int p2 = X.length();
  String trechoQuebrado = X;
  while(medidorFonte(trechoQuebrado)>i & p2>0){
    p2--;
    trechoQuebrado=X.substring(0,p2);
    //System.out.println("tamanhoTrecho:"+medidorFonte(trechoQuebrado)+", P2:"+p2);
  }
    //System.out.println("Saiu quebra de string");
    return trechoQuebrado;
}



}