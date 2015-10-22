package ClassesTeste.utils;
import javax.swing.JOptionPane;
/**
 *
 * @author Danilo
 */
public class ValidadorTags {
  
  static String []arrayTags = {
	    "<p>","</p>",
	    "<b>","</b>",
	    "<i>","</i>",
	    "<u>","</u>",
	    "<s>","</s>",
	    "<li>","</li>",
	    "<br/>",
	    "<center>","</center>",
	    "<poesia>","</poesia>",
	    "<texto>","</texto>",
	    "<afirmativas>","</afirmativas>",
	    "<alternativas>","</alternativas>",
	    "<h1>","</h1>",
	    "<h2>","</h2>",
	    "<h3>","</h3>",
	    "<h4>","</h4>",
	    "<h5>","</h5>",
	    "<h6>","</h6>",
	    "<espacamento1/>",
	    "<espacamento2/>",
	    "<espacamento3/>",
	    "<espacamento4/>"
	  };
	  
	    
	  public boolean validaTextoDigitado(String texto){
	    char caractere;
	    boolean tagAberturaAtiva=false;
	    int posicaoY;
	    for(int i=0;i<texto.length();i++){
	      caractere=texto.charAt(i);
	      if(caractere=='<'){
	        tagAberturaAtiva=true;
	        posicaoY=texto.indexOf('>', i);
	        //System.out.println(texto.substring(i+1, posicaoY));
	        if(posicaoY<0){JOptionPane.showMessageDialog(null,"Foi incluído caractere de abertura de tag '<' sem o seu respectivo caractere de "
	        		+ "encerramento '>'\n Consulte o manual de utilização. \n\nTrecho inválido:\n"+destacaTrechoInvalido(texto,i,i+1),"TAG INVÁLIDA",0);return false;}
	        else{ 
	        	tagAberturaAtiva=false;
	           if(texto.substring(i+1, posicaoY).indexOf('<')>=0){JOptionPane.showMessageDialog(null,"Foi incluído caractere de abertura de tag '<' sem o seu respectivo caractere de encerramento '>'\n "
	           		+ "Consulte o manual de utilização. \n \nTrecho inválido:\n"+destacaTrechoInvalido(texto,i,i+1),"TAG INVÁLIDA",0);return false;}
	        }
	        i=posicaoY;
	      }
	      else if(caractere=='>' && tagAberturaAtiva==false){JOptionPane.showMessageDialog(null,"Foi incluído caractere de encerramento de tag '>' sem o seu respectivo caractere de abertura '<'"
	      		+ "\nConsulte o manual de utilização. \n \nTrecho inválido:\n"+destacaTrechoInvalido(texto,i,i+1)+"\n\n","TAG INVÁLIDA",0);return false;}
	    }
	    return true;
	  }
	  
	    
	  public boolean validaTagDigitada(String texto, String []tagsDoCampo,int propriedadeArrayTags){
	    if(!validaTextoDigitado(texto)){return false;}
	    char caractere;
	    int posicaoX;
	    int posicaoY;
	    int posicaoZ;
	    String textoTag1="";
	    boolean tagExiste;
	    for(int i=0;i<texto.length();i++){
	        caractere=texto.charAt(i);
	        if(caractere=='<'){
	            posicaoY=texto.indexOf('>',i);
	            if(posicaoY >0 && posicaoY <= texto.length()){
	            	textoTag1= texto.substring(i, posicaoY+1);
	               //verifica se tag existe;
	                  tagExiste=buscaTag(textoTag1,tagsDoCampo,propriedadeArrayTags);
	                 //-se ñ existir, return false; mensagem de erro.
	                  if(!tagExiste){return false;}                 
	                 //-se tag existir, verificar se existe a tag de fechamento:
	                    caractere=texto.charAt(i+1);
	                    //procurar a tag de encerramento se a mesma nao for uma e se não for uma tag do tipo <br/>:
	                    if(caractere!='/' && texto.charAt(posicaoY-1)!='/'){
	                        String textoTag2="</"+textoTag1.substring(1,textoTag1.length());
	                        posicaoZ= texto.indexOf(textoTag2, posicaoY);
	                        //-caso n exista tag de fechamento correspondente, mensagem de erro:
	                        if(posicaoZ<0){JOptionPane.showMessageDialog(null,"Não foi encontrado uma tag de encerramento correspondente a esta tag."+"\nConsulte o manual de utilização, para melhor entendimento. \n \nTrecho inválido:\n"+destacaTrechoInvalido(texto,i,i+textoTag1.length())+"\n\n","TAG NÃO ENCERRADA",0);return false;}
	                        else{
	                       	  String subtexto=texto.substring(posicaoY+1,posicaoZ);
	                       	  int posicaoW = subtexto.indexOf(textoTag1);
	                       	  //posicaoZ+=textoTag2.length()-1;
	                       	  while (posicaoW <=subtexto.length() && posicaoZ <=texto.length()){
	                              if(posicaoW>=0){
	                                  posicaoZ=texto.indexOf(textoTag2, posicaoZ+textoTag2.length()-1);
	                                  if(posicaoZ<0){JOptionPane.showMessageDialog(null,"Não foi encontrado uma tag de encerramento correspondente a esta tag."+"\nConsulte o manual de utilização, para melhor entendimento. \n \nTrecho inválido:\n"+destacaTrechoInvalido(texto,i, posicaoY+1)+"\n\n","TAG NÃO ENCERRADA",0);return false;}
	                                    posicaoW+=textoTag1.length()-1;
	                                    if(posicaoW<subtexto.length()){posicaoW=subtexto.indexOf(textoTag1,posicaoW);};
		                      }
		                      else {break;}
		                  }
		                }
		            }

		            //se for tag de fechamento,verificar se existe a tag de abertura
	                    else if(caractere=='/'){
	                    	 System.out.println("entrou= "+i);
		                      String tagAbertura=textoTag1.replace("</","<");
			              posicaoX=0;
			              posicaoZ=i;
			              String subtexto=texto.substring(0,i);
			              while(posicaoZ>0){
			                posicaoX=subtexto.lastIndexOf(tagAbertura);
			                if(posicaoX<0){JOptionPane.showMessageDialog(null,"Não foi encontrado uma tag de abertura correspondente a esta tag."+"\nConsulte o manual de utilização, para melhor entendimento. \n \nTrecho inválido:\n"+destacaTrechoInvalido(texto,i, posicaoY+1)+"\n\n","TAG NÃO ABERTA",0);return false;}
			                else{
		                      posicaoX+=tagAbertura.length()-1;
			                  subtexto=texto.substring(posicaoX, posicaoZ);
			                  posicaoZ=subtexto.indexOf(textoTag1);
			                  if(posicaoZ>=0){JOptionPane.showMessageDialog(null,"Não existe uma tag de abertura correspondente a esta tag"+"\nConsulte o manual de utilização para melhor entendimento. \n \nTrecho inválido:\n"+destacaTrechoInvalido(texto,i,i+tagAbertura.length()-1)+"\n\n","TAG NÃO ABERTA",0);return false;}
			                }
			              }
			            }
		            //verifica restante do texto;
		            i+=textoTag1.length()-1;
			    System.out.println("i= "+i);
		    }
		    else{/*Mensagem:  caractere invalido, consulte o manual*/JOptionPane.showMessageDialog(null,"Não foi encontrado uma tag de encerramento correspondente a esta tag."+"\nConsulte o manual de utilização, para melhor entendimento. \n \nTrecho inválido:\n"+destacaTrechoInvalido(texto,i,i+textoTag1.length()-1)+"\n\n","TAG NÃO ENCERRADA",0);return false;}
		        
		}
	    }
	    //ñ tem digitaçao de tag
	    return true;
	  }
		
	  public boolean validaTagDigitada(String texto){

		    if(!validaTextoDigitado(texto)){return false;};
		    char caractere;
		    int posicaoX;
		    int posicaoY;
		    int posicaoZ;
		    String textoTag1="";
		    boolean tagExiste;
		    for(int i=0;i<texto.length();i++){
		        caractere=texto.charAt(i);
		        if(caractere=='<'){
		            posicaoY=texto.indexOf('>',i);
		            if(posicaoY >0 && posicaoY <= texto.length()){
		            	textoTag1= texto.substring(i, posicaoY+1);
		               //verifica se tag existe;
		                  tagExiste=buscaTag(textoTag1);
		                 //-se ñ existir, return false; mensagem de erro.
		                  if(!tagExiste){return false;}                 
		                 //-se tag existir, verificar se existe a tag de fechamento:
		                    caractere=texto.charAt(i+1);
		                    //procurar a tag de encerramento se a mesma nao for uma e se não for uma tag do tipo <br/>:
		                    if(caractere!='/' && texto.charAt(posicaoY-1)!='/'){
		                        String textoTag2="</"+textoTag1.substring(1,textoTag1.length());
		                        posicaoZ= texto.indexOf(textoTag2, posicaoY);
		                        //-caso n exista tag de fechamento correspondente, mensagem de erro:
		                        if(posicaoZ<0){JOptionPane.showMessageDialog(null,"Não foi encontrado uma tag de encerramento correspondente a esta tag."+"\nConsulte o manual de utilização, para melhor entendimento. \n \nTrecho inválido:\n"+destacaTrechoInvalido(texto,i,i+textoTag1.length())+"\n\n","TAG NÃO ENCERRADA",0);return false;}
		                        else{
		                       	  String subtexto=texto.substring(posicaoY+1,posicaoZ);
		                       	  int posicaoW = subtexto.indexOf(textoTag1);
		                       	  //posicaoZ+=textoTag2.length()-1;
		                       	  while (posicaoW <=subtexto.length() && posicaoZ <=texto.length()){
		                              if(posicaoW>=0){
		                                  posicaoZ=texto.indexOf(textoTag2, posicaoZ+textoTag2.length()-1);
		                                  if(posicaoZ<0){JOptionPane.showMessageDialog(null,"Não foi encontrado uma tag de encerramento correspondente a esta tag."+"\nConsulte o manual de utilização, para melhor entendimento. \n \nTrecho inválido:\n"+destacaTrechoInvalido(texto,i, posicaoY+1)+"\n\n","TAG NÃO ENCERRADA",0);return false;}
		                                    posicaoW+=textoTag1.length()-1;
		                                    if(posicaoW<subtexto.length()){posicaoW=subtexto.indexOf(textoTag1,posicaoW);};
			                      }
			                      else {break;}
			                  }
			                }
			            }

			            //se for tag de fechamento,verificar se existe a tag de abertura
		                    else if(caractere=='/'){
		                    	 System.out.println("entrou= "+i);
			                      String tagAbertura=textoTag1.replace("</","<");
				              posicaoX=0;
				              posicaoZ=i;
				              String subtexto=texto.substring(0,i);
				              while(posicaoZ>0){
				                posicaoX=subtexto.lastIndexOf(tagAbertura);
				                if(posicaoX<0){JOptionPane.showMessageDialog(null,"Não foi encontrado uma tag de abertura correspondente a esta tag."+"\nConsulte o manual de utilização, para melhor entendimento. \n \nTrecho inválido:\n"+destacaTrechoInvalido(texto,i, posicaoY+1)+"\n\n","TAG NÃO ABERTA",0);return false;}
				                else{
			                      posicaoX+=tagAbertura.length()-1;
				                  subtexto=texto.substring(posicaoX, posicaoZ);
				                  posicaoZ=subtexto.indexOf(textoTag1);
				                  if(posicaoZ>=0){JOptionPane.showMessageDialog(null,"Não existe uma tag de abertura correspondente a esta tag"+"\nConsulte o manual de utilização para melhor entendimento. \n \nTrecho inválido:\n"+destacaTrechoInvalido(texto,i,i+tagAbertura.length()-1)+"\n\n","TAG NÃO ABERTA",0);return false;}
				                }
				              }
				            }
			            //verifica restante do texto;
			            i+=textoTag1.length()-1;
				    System.out.println("i= "+i);
			    }
			    else{/*Mensagem:  caractere invalido, consulte o manual*/JOptionPane.showMessageDialog(null,"Não foi encontrado uma tag de encerramento correspondente a esta tag."+"\nConsulte o manual de utilização, para melhor entendimento. \n \nTrecho inválido:\n"+destacaTrechoInvalido(texto,i,i+textoTag1.length()-1)+"\n\n","TAG NÃO ENCERRADA",0);return false;}
			        
			}
		    }
		    //ñ tem digitaçao de tag
		    return true;
		  }  
	  
	  
	  
	  public static boolean buscaTag(String tag,String []tagsDoCampo,int propriedade){
              switch (propriedade){
                  //considerar as tags do array tagsDocampo como sendo as unicas permitidas
                  case 0:{
                      for(int x=0; x<tagsDoCampo.length;x++){
                        if(tag.toUpperCase().compareTo(tagsDoCampo[x].toUpperCase())==0){
	        	  return true;
                        }
                      }
                      for(int x=0; x<arrayTags.length;x++){
                        if(tag.toUpperCase().compareTo(arrayTags[x].toUpperCase())==0){
	        	  JOptionPane.showMessageDialog(null,"A tag "+tag+" não é válida para utilização neste campo.\n Consulte o manual de ajuda, para melhor entendimento.","TAG INVÁLIDA",0);
	        	  return false;
                        }
                      }
                  }
                  
                  //considerar as tags do array tagsDoCampo como sendo nao permitidas    
                  case 1:{
                      for(int x=0; x<tagsDoCampo.length;x++){
                        if(tag.toUpperCase().compareTo(tagsDoCampo[x].toUpperCase())==0){
	        	  JOptionPane.showMessageDialog(null,"A tag "+tag+" não é válida para utilização neste campo.\n Consulte o manual de ajuda, para melhor entendimento.","TAG INVÁLIDA",0);
	        	  return false;
                        }
                      }
                      for(int i=0; i<arrayTags.length;i++){
			  if(tag.toUpperCase().compareTo(arrayTags[i].toUpperCase())==0){
				  //System.out.println("tag encontrada");
				  return true;
			  }
                      }
                  }
              }
              JOptionPane.showMessageDialog(null,"A tag "+tag+" não está configurada para uso no JPROVAS.\n Consulte o manual de ajuda, para melhor entendimento.","TAG INVÁLIDA",0);
              return false;
	  }   
	  
	  
	  public static boolean buscaTag(String tag){
		  for(int i=0; i<arrayTags.length;i++){
			  if(tag.toUpperCase().compareTo(arrayTags[i].toUpperCase())==0){
				  //System.out.println("tag encontrada");
				  return true;
			  }
		  }
		  JOptionPane.showMessageDialog(null,"A tag "+tag+" não está configurada para uso no JPROVAS.\n Consulte o manual de ajuda, para melhor entendimento.","TAG INVÁLIDA",0);
		  return false;
	  } 
	  
	  
	  
	  
	  
	  public static String destacaTrechoInvalido(String texto, int posicaoX, int posicaoY){
	    String trechoDestacado="";
	    
	    if(posicaoX>3){trechoDestacado+="..."+texto.substring(posicaoX-3, posicaoX);}
	    else{trechoDestacado+=texto.substring(0, posicaoX);}
	    if(posicaoX>0){trechoDestacado+="-";}
	    trechoDestacado+="''"+texto.substring(posicaoX, posicaoY)+"''-";
	    if(texto.length()-posicaoY >3){trechoDestacado+=texto.substring(posicaoY,posicaoY+3)+"...";}
	    else{trechoDestacado+=texto.substring(posicaoY);}
	    return trechoDestacado;
	  }
  
}
