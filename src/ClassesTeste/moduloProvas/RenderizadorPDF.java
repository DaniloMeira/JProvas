package ClassesTeste.moduloProvas;

import Telas.JProvas;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

//import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.FSEntityResolver;
import org.xml.sax.SAXException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Document;
import java.awt.Color;
import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextUserAgent;



public class RenderizadorPDF{ 
 
 char charSeparador = File.separatorChar; 
 String diretorioProjeto= System.getProperty("user.dir");
 public String tituloPDF = "ProvaCriada_";
 public JProvas TelaJProvas_RenderizadorPDF; 
 
public RenderizadorPDF(String tituloDocumento) throws IOException,DocumentException, ParserConfigurationException, SAXException {
 tituloPDF= tituloDocumento+"_"+System.currentTimeMillis()+".pdf";
}

public void gerarPDF(int numeroPaginas) throws IOException, DocumentException, ParserConfigurationException, SAXException {

	 List<InputStream> pdfs = new ArrayList<InputStream>();
	 
	 long start = System.currentTimeMillis();
	 for(int i=1; i<numeroPaginas; i++){

		    String outputFile= "HTML"+charSeparador+"PDF_paginas"+charSeparador+"saidaProva"+i+".pdf";
		    OutputStream os = new FileOutputStream(outputFile);
		    
		    //Solução encontrada em: http://stackoverflow.com/questions/5431646/is-there-any-way-improve-the-performance-of-flyingsaucer
		    InputStream is = new FileInputStream(new File("HTML"+charSeparador+"prova"+i+".xhtml")); 
		    DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
		    fac.setNamespaceAware(false);
		    fac.setValidating(false);
		    fac.setFeature("http://xml.org/sax/features/namespaces", false);
		    fac.setFeature("http://xml.org/sax/features/validation", false);
		    fac.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		    fac.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		    
		    DocumentBuilder builder = fac.newDocumentBuilder();
		    builder.setEntityResolver(FSEntityResolver.instance());
		    org.w3c.dom.Document doc = builder.parse(is);
		    ITextRenderer renderer = new ITextRenderer(); 
                    //renderer.getSharedContext().getUserAgentCallback().setBaseURL(diretorioProjeto+"\\HTML");
//		    renderer.getFontResolver().addFont("HTML"+charSeparador+"fontes"+charSeparador+"arial.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
   		    renderer.getFontResolver().addFont("HTML/Fontes/arialbi.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
                    renderer.getFontResolver().addFont("HTML/Fontes/arial.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
                    renderer.getFontResolver().addFont("HTML/Fontes/arialbd.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
                    renderer.getFontResolver().addFont("HTML/Fontes/times.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
                    renderer.getFontResolver().addFont("HTML/Fontes/timesbd.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
                    renderer.getFontResolver().addFont("HTML/Fontes/timesi.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
                    renderer.getFontResolver().addFont("HTML/Fontes/timesbi.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
                    renderer.setDocument((org.w3c.dom.Document) doc,null);
                    renderer.layout();
		    renderer.createPDF(os);
		    renderer.finishPDF();
                    os.flush();
		    os.close();

		     
		        long end = System.currentTimeMillis();
		        System.out.println("Criou pagina "+i+" em: "+ (end-start) + "ms");
		        
		        //juntas as paginas em um unico pdf
                        pdfs.add(new FileInputStream("HTML"+charSeparador+"PDF_paginas"+charSeparador+"saidaProva"+i+".pdf"));
                              
                        is.close();
}
   
 
	//juntar as paginas em um unico pdf
   	 try {
	      OutputStream output = new FileOutputStream(tituloPDF);
	      concatenaPDFs(pdfs, output, false);
              TelaJProvas_RenderizadorPDF.jlabInformativaRodape.setText("Última prova gerada: "+diretorioProjeto+"\\"+tituloPDF);
              abrirPDF();
              //fechar lista de InputStreamFile para permitir q as paginas pdf sejam deletadas
              for(int i=0;i<pdfs.size();i++){
                pdfs.get(i).close();
              }

	    } catch (Exception e) {
	      e.printStackTrace();
              TelaJProvas_RenderizadorPDF.jlabInformativaRodape.setBackground(Color.red);
              TelaJProvas_RenderizadorPDF.jlabInformativaRodape.setText("Falha na geração do arquivo PDF");
	    }

   }
 //-------------------
 

 public static void concatenaPDFs(List<InputStream> streamOfPDFFiles, OutputStream outputStream, boolean paginar) {

	    Document document = new Document();
	    try {
	      List<InputStream> pdfs = streamOfPDFFiles;
	      List<PdfReader> readers = new ArrayList<PdfReader>();
	      int totalPages = 0;
	      Iterator<InputStream> iteratorPDFs = pdfs.iterator();

	      // Create Readers for the pdfs.
	      while (iteratorPDFs.hasNext()) {
	        InputStream pdf = iteratorPDFs.next();
	        PdfReader pdfReader = new PdfReader(pdf);
	        readers.add(pdfReader);
	        totalPages += pdfReader.getNumberOfPages();
	      }
	      // Create a writer for the outputstream
	      PdfWriter writer = PdfWriter.getInstance(document, outputStream);

	      document.open();
	      BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
	      PdfContentByte cb = writer.getDirectContent(); // Holds the PDF data
	      //cb.setFontAndSize(bf,9);

	      PdfImportedPage page;
	      int currentPageNumber = 0;
	      int pageOfCurrentReaderPDF = 0;
	      Iterator<PdfReader> iteratorPDFReader = readers.iterator();

	      // Loop through the PDF files and add to the output.
	      while (iteratorPDFReader.hasNext()) {
	        PdfReader pdfReader = iteratorPDFReader.next();

	        // Create a new page in the target for each source page.
	        while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
	          document.newPage();
	          pageOfCurrentReaderPDF++;
	          currentPageNumber++;
	          page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
	          cb.addTemplate(page, 0, 0);

	          // Code for pagination.
	          if (paginar) {
	            cb.beginText();
	            cb.setFontAndSize(bf, 9);
	            cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "" + currentPageNumber + " of " + totalPages, 520, 5, 0);
	            cb.endText();
	          }
	        }
	        pageOfCurrentReaderPDF = 0;
	      }
	      outputStream.flush();
	      document.close();
	      outputStream.close();
              
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      if (document.isOpen())
	        document.close();
	      try {
	        if (outputStream != null)
	          outputStream.close();
	      } catch (IOException ioe) {
	        ioe.printStackTrace();
	      }
	    }

 }
 
 //===----------------------
 
public void abrirPDF(){
   Process pro;
   ProcessBuilder p = new ProcessBuilder();
   File arquivo = new File(diretorioProjeto+"/"+tituloPDF);
   try
  {
      Desktop.getDesktop().open(arquivo);
   /*p.command("AcroRd32.exe",diretorioProjeto+"\\"+tituloPDF);
   p.start();*/
   /*pro = Runtime.getRuntime().exec("cmd.exe /c  "+diretorioProjeto+"\\"+tituloPDF);
System.out.println("Abrindo em : "+"cmd.exe /c  "+diretorioProjeto+"\\"+tituloPDF);*/
 }
  catch (IOException e){e.printStackTrace();}
 // catch (InterruptedException e){e.printStackTrace();}

 }
 
}


class ResourceLoaderUserAgent extends ITextUserAgent {
    public ResourceLoaderUserAgent(ITextOutputDevice outputDevice) {
      super(outputDevice);
    }

    protected InputStream resolveAndOpenStream(String uri) {
      System.out.println("IN resolveAndOpenStream() " + uri);
      InputStream is = super.resolveAndOpenStream(uri);
      if(is == null) {
	//Assumes that the last part is the file name and that the image is on the classpath
        String[] split = uri.split("/");
        String lastPart = split[split.length - 1];
        try {
          //Could extend this to look in more places
          //is = ResourceLoaderUserAgent.class.getResourceAsStream(lastPart);
          is = new FileInputStream(new File(lastPart));
        } catch (FileNotFoundException ex) {
          Logger.getLogger(ResourceLoaderUserAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      return is;
    }
  

  public static String readFileAsString(String filePath) throws java.io.IOException {
    byte[] buffer = new byte[(int)new File(filePath).length()];
    BufferedInputStream f = null;
    try {
      f = new BufferedInputStream(new FileInputStream(filePath));
      f.read(buffer);
    } finally {
      if(f != null) {
        try {
          f.close();
        } catch(IOException ignored) {
        }
      }
    }
    return new String(buffer);
  }
}