package cat.urv.clic.android;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import android.content.Context;

public class Utils {
	
	

	public static HashJocs llegirFitxerJocsXML(Context c, String nomFitxer){
		Document doc = null;
		InputStreamReader isr = null;

		HashJocs llistaJocs = new HashJocs();

		try {			
			InputStream is = c.getAssets().open(nomFitxer);									
			isr = new InputStreamReader(is);

			SAXBuilder builder = new SAXBuilder(false);
			doc = builder.build(isr);

			Element raiz = doc.getRootElement();	// S'agafa l'element arrel

			List<?> joc = raiz.getChildren("joc");   
			Iterator<?> i = joc.iterator();

			while (i.hasNext()){
				Element e= (Element)i.next(); // Primer fill que tingui com a nom "jocs"

				Integer identificador = Integer.valueOf(e.getChild("identificador").getText());
				String nom = e.getChild("nom").getText();
				Date dataPublicacio = Date.valueOf(e.getChild("dataPublicacio").getText());

				// Llengua
				List<String> idiomes = new ArrayList<String>();
				Iterator<?> iLlengua = e.getChildren("llengua").iterator();
				while (iLlengua.hasNext()){
					Element ee= (Element)iLlengua.next();
					idiomes.add(ee.getText());
				}

				// Nivell
				List<String> nivells =  new ArrayList<String>();
				Iterator<?> iNivell = e.getChildren("nivellJoc").iterator();
				while (iNivell.hasNext()){
					Element ee= (Element)iNivell.next();
					nivells.add(ee.getText());
				}

				// Area
				List<String> arees = new ArrayList<String>();
				Iterator<?>iArea = e.getChildren("area").iterator();
				while (iArea.hasNext()){
					Element ee= (Element)iArea.next();
					arees.add(ee.getText());
				}

				String ruta = e.getChild("ruta").getText();

				// Afegim el joc
				Joc dadesJoc = new Joc(identificador, nom, dataPublicacio, idiomes, nivells, arees, ruta, false);
				llistaJocs.afegirJoc(dadesJoc);
			}
		} catch (FileNotFoundException e1) {
			// MOSTRAR UN MISSATGE PER AVISAR QUE NO S'HA POGUT LLEGIR EL FITXER
			System.out.println("ENTRA A L'EXCEPCIO DE LLEGIR FITXER");
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return llistaJocs;
	}


//	public void exportarJocsDescarregatsXML(HashJocs llistaJocs) {
//		try{
//			PrintStream ps = new PrintStream(new FileOutputStream(getFilesDir()+"/jocs_descarregats.xml",false));
//			System.out.println("MIDA funcio  "+ this.llistaJocs.midaHash());
//			List<Integer> llistaId = llistaJocs.llistaIdentificadorJocs();
//
//			ps.println("<?xml version='1.0' encoding='utf-8'?>");
//			ps.println("<jclic nom='Aplicacions jclic'>");
//
//			Iterator<Integer> it = llistaId.iterator();
//			while (it.hasNext()){
//				Integer id = it.next();   
//				Joc joc = llistaJocs.cercarJoc(id);
//				if(joc.getDescarregat()){
//					ps.print("<identificador>");
//					ps.print(joc.getIdentificador());
//					ps.println("</identificador>");
//					ps.println("</joc>");
//				}
//			}
//			ps.println("</jclic>");
//		}catch (Exception e){
//			System.out.println(e.getMessage());
//		}	    
//	}
	
	
	/*public void exportarJocsDescarregatsXML(HashJocs llistaJocs) {
		   try{
			   PrintStream ps = new PrintStream(new FileOutputStream(getFilesDir()+"/jocs_descarregats.xml",false));
			   
			   List<Integer> llistaId = llistaJocs.llistaIdentificadorJocs();
				 
			   ps.println("<?xml version='1.0' encoding='utf-8'?>");
			   ps.println("<jclic nom='Aplicacions jclic'>");
			   
			   Iterator<Integer> it = llistaId.iterator();
			   while (it.hasNext()){
				    ps.println("<joc>");
				    
				   	Integer id = it.next();   
				   	Joc joc = llistaJocs.cercarJoc(id);
				   	
					ps.print("<identificador>");
					ps.print(joc.getIdentificador());
					ps.println("</identificador>");
					 
					ps.print("<nom>");
					ps.print(joc.getNom());
					ps.println("</nom>");
					
					ps.print("<dataPublicacio>");
					ps.print(joc.getDataPublicacio());
					ps.println("</dataPublicacio>");
					 
					ps.print("<llengua>");
					Iterator<String> itLlengua = joc.getLlengua().iterator();
					while (itLlengua.hasNext()){
						ps.print("<nom>");
						ps.print(itLlengua.next().toString());
						ps.println("</nom>");
					}
					ps.println("</llengua>");
					 
					ps.print("<nivellJoc>");
					Iterator<String> itNivell = joc.getNivellJoc().iterator();
					while (itNivell.hasNext()){
						ps.print("<nom>");
						ps.print(itNivell.next().toString());
						ps.println("</nom>");
					}
					ps.println("</nivellJoc>");
					 
					ps.print("<area>");
					Iterator<String> itArea = joc.getAreaJoc().iterator();
					while (itArea.hasNext()){
						ps.print("<nom>");
						ps.print(itArea.next().toString());
						ps.println("</nom>");
					}
					ps.println("</area>");
					 
					ps.print("<ruta>");
					ps.print(joc.getRuta());
					ps.println("</ruta>");
					 
					ps.println("</joc>");
			   }
			   ps.println("</jclic>");
		   }catch (Exception e){
			   System.out.println(e.getMessage());
		   }	    
		}
	}*/
	
/*	private void descarregarFitxer(String ruta, String nomFitxer) {
		try {
			// Url del joc
			URL url = new URL(ruta);
			
			// Obrim la connexió
			URLConnection urlCon = url.openConnection();

			// S'obté l'inputStream del joc i s'obre el zip local
			InputStream is = urlCon.getInputStream();
			
			File f = new File(getFilesDir() + "/" + nomFitxer + ".zip");
			FileOutputStream fos = new FileOutputStream(f);
		
			// Lectura del fitxer .zip
			byte[] array = new byte[is.available()]; // Buffer temporal de lectura
			int leido = is.read(array);
			while (leido > 0) {
				fos.write(array, 0, leido);
				leido = is.read(array);
			}

			// Tanquem la connexio i el zip
			is.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}*/
	
/*	public void descomprimirFitxer(String nomFitxer){
		final int BUFFER = 2048;
		try {
			BufferedOutputStream dest = null;
			FileInputStream fis = new FileInputStream(getFilesDir()+ "/" + nomFitxer + ".zip");
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
			ZipEntry entry;
			
			
			// Creem la carpeta perquè es guardi els fitxers del zip
			File directori = new File(getFilesDir()+ "/" + nomFitxer);
			directori.mkdir();
			
			while((entry = zis.getNextEntry()) != null) {
				System.out.println("Extracting: " +entry);
				
				int count;
				byte data[] = new byte[BUFFER];
				
				if (entry.isDirectory()) {
					// Si entrem aquí vol dir que tenim una carpeta dintre del zip i llavors l'hem de crear
					// com un directori i no com un fitxer
					File carpeta = new File(directori+ "/" + entry.getName());
					carpeta.mkdir();
				}else{
					// Escrivim els fitxers en local
					FileOutputStream fos = new FileOutputStream(getFilesDir()+ "/" + nomFitxer + "/" +entry.getName());
					dest = new BufferedOutputStream(fos, BUFFER);
					while ((count = zis.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, count);
					}
					dest.flush();
					dest.close();
				}

			}
			zis.close();
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}*/
}
