package ClassesTeste.moduloProvas;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.BaseFont;
import java.io.File;
/**
 *
 * @author Danilo Meira
 */
public class FontesLetraJProvas {
  
  char charSeparador = File.separatorChar;
  public String diretorioFontes=System.getProperty("user.dir")+charSeparador+"HTML"+charSeparador+"Fontes"+charSeparador;
  
  Font FonteNormal,FonteNegrito,FonteItalico,FonteNegritoItalico;
  
  Font TIMESH1_NORMAL,TIMESH1_NEGRITO,TIMESH1_ITALICO,TIMESH1_NEGRITO_ITALICO;
  Font TIMESH2_NORMAL,TIMESH2_NEGRITO,TIMESH2_ITALICO,TIMESH2_NEGRITO_ITALICO;
  Font TIMESH3_NORMAL,TIMESH3_NEGRITO,TIMESH3_ITALICO,TIMESH3_NEGRITO_ITALICO;
  Font TIMESH4_NORMAL,TIMESH4_NEGRITO,TIMESH4_ITALICO,TIMESH4_NEGRITO_ITALICO;
  Font TIMESH5_NORMAL,TIMESH5_NEGRITO,TIMESH5_ITALICO,TIMESH5_NEGRITO_ITALICO;
  Font TIMESH6_NORMAL,TIMESH6_NEGRITO,TIMESH6_ITALICO,TIMESH6_NEGRITO_ITALICO;
  Font TIMES10_NORMAL,TIMES10_NEGRITO,TIMES10_ITALICO,TIMES10_NEGRITO_ITALICO;
  Font ARIAL13_NORMAL,ARIAL13_NEGRITO,ARIAL13_ITALICO,ARIAL13_NEGRITO_ITALICO;
  
  float tamanhoLinha; 
  
  float TIMESH1_tamanhoLinha =15;
  float TIMESH2_tamanhoLinha =13;  
  float TIMESH3_tamanhoLinha =11.5f;
  float TIMESH4_tamanhoLinha =11.5f;
  float TIMESH5_tamanhoLinha =11.5f;
  float TIMESH6_tamanhoLinha =11.5f;
  float TIMES10_tamanhoLinha =11.5f;
  float ARIAL13_tamanhoLinha=20.5f;
  
  public FontesLetraJProvas(){
  FontFactory.register( diretorioFontes+"times.ttf","times_normal");
  FontFactory.register( diretorioFontes+"timesbd.ttf","times_bold");
  FontFactory.register( diretorioFontes+"timesi.ttf","times_italic");
  FontFactory.register( diretorioFontes+"timesbi.ttf","times_bold_italic");
  
  FontFactory.register( diretorioFontes+"arial.ttf","arial_normal");
  FontFactory.register( diretorioFontes+"arialbd.ttf","arial_bold");
  FontFactory.register( diretorioFontes+"ariali.ttf","arial_italic");
  FontFactory.register( diretorioFontes+"arialbi.ttf","arial_bold_italic");
  
  TIMESH1_NORMAL =  FontFactory.getFont("times_normal", 15, Font.NORMAL);
  TIMESH1_NEGRITO = FontFactory.getFont("times_bold", 15, Font.BOLD);
  TIMESH1_ITALICO = FontFactory.getFont("times_italic", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 15, Font.ITALIC);
  TIMESH1_NEGRITO_ITALICO = FontFactory.getFont("times_bold_italic", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 15, Font.BOLDITALIC);
  
  TIMESH2_NORMAL =  FontFactory.getFont("times_normal", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 13, Font.NORMAL);
  TIMESH2_NEGRITO = FontFactory.getFont("times_bold", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 13, Font.BOLD);
  TIMESH2_ITALICO = FontFactory.getFont("times_italic", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 13, Font.ITALIC);
  TIMESH2_NEGRITO_ITALICO = FontFactory.getFont("times_bold_italic", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 13, Font.BOLDITALIC);
  
  TIMESH3_NORMAL =  FontFactory.getFont("times_normal", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 11, Font.NORMAL);
  TIMESH3_NEGRITO = FontFactory.getFont("times_bold", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 11, Font.BOLD);
  TIMESH3_ITALICO = FontFactory.getFont("times_italic", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 11, Font.ITALIC);
  TIMESH3_NEGRITO_ITALICO = FontFactory.getFont("times_bold_italic", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 11, Font.BOLDITALIC);
  
  TIMESH4_NORMAL =  FontFactory.getFont("times_normal", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 9, Font.NORMAL);
  TIMESH4_NEGRITO = FontFactory.getFont("times_bold", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 9, Font.BOLD);
  TIMESH4_ITALICO = FontFactory.getFont("times_italic", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 9, Font.ITALIC);
  TIMESH4_NEGRITO_ITALICO = FontFactory.getFont("times_bold_italic", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 9, Font.BOLDITALIC);
  
  TIMESH5_NORMAL =  FontFactory.getFont("times_normal", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8, Font.NORMAL);
  TIMESH5_NEGRITO = FontFactory.getFont("times_bold", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8, Font.BOLD);
  TIMESH5_ITALICO = FontFactory.getFont("times_italic", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8, Font.ITALIC);
  TIMESH5_NEGRITO_ITALICO = FontFactory.getFont("times_bold_italic", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8, Font.BOLDITALIC);
  
  TIMESH6_NORMAL =  FontFactory.getFont("times_normal", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 6, Font.NORMAL);
  TIMESH6_NEGRITO = FontFactory.getFont("times_bold", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 6, Font.BOLD);
  TIMESH6_ITALICO = FontFactory.getFont("times_italic", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 6, Font.ITALIC);
  TIMESH6_NEGRITO_ITALICO = FontFactory.getFont("times_bold_italic", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 6, Font.BOLDITALIC);
  
  TIMES10_NORMAL =  FontFactory.getFont("times_normal", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 10, Font.NORMAL);
  TIMES10_NEGRITO = FontFactory.getFont("times_bold", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 10, Font.BOLD);
  TIMES10_ITALICO = FontFactory.getFont("times_italic", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 10, Font.ITALIC);
  TIMES10_NEGRITO_ITALICO = FontFactory.getFont("times_bold_italic", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 10, Font.BOLDITALIC);
  
  ARIAL13_NORMAL = FontFactory.getFont("arial_normal", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 13, Font.NORMAL);
  ARIAL13_NEGRITO = FontFactory.getFont("arial_bold", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 13, Font.BOLD);
  ARIAL13_ITALICO = FontFactory.getFont("arial_italic", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 13, Font.ITALIC);
  ARIAL13_NEGRITO_ITALICO = FontFactory.getFont("arial_bold_italic", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 13, Font.BOLDITALIC);
  
  }
  
float getTamanhoLinha(){
  return this.tamanhoLinha;
}

Font getFonteNormal(){
  return FonteNormal;
}

Font getFonteNegrito(){
  return this.FonteNegrito;
}    

Font getFonteItalico(){
  return this.FonteItalico;
}

Font getFonteNegritoItalico(){
  return this.FonteNegritoItalico;
}

void setFonteAtual(int i){
  FonteNormal=null;FonteNegrito=null;FonteItalico=null;
  switch (i){
            //estranhamente o flyingSaucer transforma a fonte Normal em Negrito 
    case 1: {FonteNormal=TIMESH1_NEGRITO; FonteNegrito=TIMESH1_NEGRITO; FonteItalico=TIMESH1_ITALICO; tamanhoLinha=TIMESH1_tamanhoLinha;
             FonteNegritoItalico=TIMESH1_NEGRITO_ITALICO;
             return;}
    case 2: {FonteNormal=TIMESH2_NEGRITO; FonteNegrito=TIMESH2_NEGRITO; FonteItalico=TIMESH2_ITALICO; tamanhoLinha=TIMESH2_tamanhoLinha;
             FonteNegritoItalico=TIMESH2_NEGRITO_ITALICO;
             return;}
    case 3: {FonteNormal=TIMESH3_NEGRITO; FonteNegrito=TIMESH3_NEGRITO; FonteItalico=TIMESH3_ITALICO; tamanhoLinha=TIMESH3_tamanhoLinha;
             FonteNegritoItalico=TIMESH3_NEGRITO_ITALICO;
             return;}
    case 4: {FonteNormal=TIMESH4_NEGRITO; FonteNegrito=TIMESH4_NEGRITO; FonteItalico=TIMESH4_ITALICO; tamanhoLinha=TIMESH4_tamanhoLinha;
             FonteNegritoItalico=TIMESH4_NEGRITO_ITALICO;
             return;}
    case 5: {FonteNormal=TIMESH5_NEGRITO; FonteNegrito=TIMESH5_NEGRITO; FonteItalico=TIMESH5_ITALICO; tamanhoLinha=TIMESH5_tamanhoLinha;
             FonteNegritoItalico=TIMESH5_NEGRITO_ITALICO;
             return;}
    case 6: {FonteNormal=TIMESH6_NEGRITO; FonteNegrito=TIMESH6_NEGRITO; FonteItalico=TIMESH6_ITALICO; tamanhoLinha=TIMESH6_tamanhoLinha;
             FonteNegritoItalico=TIMESH6_NEGRITO_ITALICO;
             return;}
    case 7: {FonteNormal=ARIAL13_NORMAL; FonteNegrito=ARIAL13_NEGRITO; FonteItalico=ARIAL13_ITALICO; tamanhoLinha=ARIAL13_tamanhoLinha;
             FonteNegritoItalico=ARIAL13_NEGRITO_ITALICO;
             return;}
    case 8: {FonteNormal=TIMES10_NORMAL; FonteNegrito=TIMES10_NEGRITO; FonteItalico=TIMES10_ITALICO; tamanhoLinha=TIMES10_tamanhoLinha;
             FonteNegritoItalico=TIMES10_NEGRITO_ITALICO;
            }
  }

}

}