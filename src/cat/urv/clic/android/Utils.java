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
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

//import net.sf.json.JSON;
//import net.sf.json.xml.XMLSerializer;


//import org.apache.commons.io.IOUtils;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.google.gson.Gson;

import android.content.Context;


public class Utils {
	private static Activitat activitat = null;
	private static Cells cells = null;
	private static Style style = null;
	private static Font font = null;
	private static Color color = null;
	private static List<Activitat> llistaActivitats = null;
	
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
            	List<String> llengues =  new ArrayList<String>();
            	Iterator<?> iLlengua = e.getChild("llengua").getChildren().iterator();
            	while (iLlengua.hasNext()){
            		Element ee= (Element)iLlengua.next();
            		llengues.add(ee.getText());
            	}

            	// Nivell
            	List<String> nivells =  new ArrayList<String>();
            	Iterator<?> iNivell = e.getChild("nivellJoc").getChildren().iterator();
            	while (iNivell.hasNext()){
            		Element ee= (Element)iNivell.next();
        	   	 	nivells.add(ee.getText());
            	}

            	// Area
            	List<String> arees = new ArrayList<String>();
            	Iterator<?>iArea = e.getChild("area").getChildren().iterator();
            	while (iArea.hasNext()){
            		Element ee= (Element)iArea.next();
        	   	 	arees.add(ee.getText());
            	}
            	
            	String ruta = e.getChild("ruta").getText();

				// Afegim el joc
				Joc dadesJoc = new Joc(identificador, nom, dataPublicacio, llengues, nivells, arees, ruta, false);
				llistaJocs.afegirJoc(dadesJoc);
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return llistaJocs;
	}
		
	public static void marcarJocsDescarregats(Context c){
		Document doc = null;
		InputStreamReader isr = null;

		try {			
			FileInputStream is = c.openFileInput("descarregats.xml");									
			isr = new InputStreamReader(is);

			SAXBuilder builder = new SAXBuilder(false);
			doc = builder.build(isr);

			Element raiz = doc.getRootElement();	// S'agafa l'element arrel

			List<?> identificadors = raiz.getChildren("identificador");   
			Iterator<?> i = identificadors.iterator();
			
			while (i.hasNext()){
            	Element e= (Element)i.next();    
            	ClicApplication.llistaJocs.modificarJocADescarregat(Integer.parseInt(e.getText()));            	
			}
		} catch (FileNotFoundException e1)  {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}
	}
	
	public static void exportarJocsDescarregatsXML(Context c) {
	    		
		try{			
	    	PrintStream ps = new PrintStream(new FileOutputStream(c.getFilesDir()+"/descarregats.xml",false));
				
	    	List<Integer> llistaId = ClicApplication.llistaJocs.construirLlistaIdJocs(true);
		 
	    	ps.println("<?xml version='1.0' encoding='utf-8'?>");
	    	ps.println("<descarregats>");
		   
	    	Iterator<Integer> it = llistaId.iterator();
	    	while (it.hasNext()){
	    		ps.print("<identificador>");
				ps.print(it.next());
				ps.println("</identificador>");
	    	}
	    	ps.println("</descarregats>");
	    	ps.close();
		   
	   }catch (Exception e){
		   System.out.println(e.getMessage());
	   }	    
	}
		
	private static void copiaFitxer_InputStreamFile(InputStream in, File out) throws IOException   {  
		
		FileOutputStream fos = new FileOutputStream(out);

		// Lectura del fitxer 
		byte[] array = new byte[in.available()]; // Buffer temporal de lectura
		int leido = in.read(array);
		while (leido > 0) {
			fos.write(array, 0, leido);
			leido = in.read(array);
		}		
		
		// Tanquem la connexio i el fitxer
		in.close();
		fos.close();
	}
	
	public static void descarregarFitxer(Context c, String ruta, String idJoc) {
		try {
			// Url del joc
			URL url = new URL(ruta);
			
			// Obrim la connexió
			URLConnection urlCon = url.openConnection();

			// S'obté l'inputStream del joc i s'obre el zip local
			InputStream is =  urlCon.getInputStream();

			File fdesti = new File(c.getFilesDir() + "/" + idJoc + ".zip");
			copiaFitxer_InputStreamFile(is, fdesti);		
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}	
	}
		
	public static void descomprimirFitxer(Context c, String idJoc){
		final int BUFFER = 2048;
		try {
			BufferedOutputStream dest = null;
			File fitxerZip = new File(c.getFilesDir()+ "/" + idJoc + ".zip");
			FileInputStream fis = new FileInputStream(fitxerZip);
			
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
			ZipEntry entry;
						
			// Creem la carpeta perquè es guardi els fitxers del zip
			File directori = new File(c.getFilesDir()+ "/" + idJoc);
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
					FileOutputStream fos = new FileOutputStream(c.getFilesDir()+ "/" + idJoc + "/" +entry.getName());
					dest = new BufferedOutputStream(fos, BUFFER);
					while ((count = zis.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, count);
					}
					dest.flush();
					dest.close();
				}
			}
			zis.close();
			fis.close();
			
			// Quan ja hem descomprimit les dades eliminem el fitxer .zip
			fitxerZip.delete();
			
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
		
	public static void creacioActivitat(Context c, String idJoc) {						
		try {
			// Copiem el index.html
			InputStream forigen =  c.getAssets().open("index_assets.html");							
			File fdesti = new File(c.getFilesDir() + "/" + idJoc +"/index.html");				
			copiaFitxer_InputStreamFile (forigen, fdesti);
			
			// Generem el data1.js, data2.js, data2.js, ...
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Activitat recursiu(List<?> llista, Activitat activitat){	 
		if(llista.size() != 0){	
    		Iterator<?> iLlista = llista.iterator();
    		while(iLlista.hasNext()){
    			Element child = (Element) iLlista.next(); 
    			if (child.getName().compareTo("activity") == 0){	
    				activitat = new Activitat();
    			}else if (child.getName().compareTo("cells") == 0){
    				cells = new Cells();
    			}else if (child.getName().compareTo("style") == 0){
    				style = new Style();
    			}else if (child.getName().compareTo("font") == 0){
    				font = new Font();
    			}else if (child.getName().compareTo("color") == 0){
    				color = new Color();
    			}
    			
    			List<?> llistaAttribChild =  child.getAttributes(); 	//Llegim els atributs
    			if(llistaAttribChild.size()!=0){
	    			Iterator<?> iAttribChild = llistaAttribChild.iterator();
	            	while(iAttribChild.hasNext()){				
	            		Attribute attrib = (Attribute) iAttribChild.next();
	            		if (child.getName().compareTo("activity") == 0){
	            			activitat.afegirAtributActivitat(attrib.getName(),attrib.getValue());
	        			}else if (child.getName().compareTo("cells") == 0){
	        				cells.afegirAtributActivitat(attrib.getName(),attrib.getValue());
	        			}else if (child.getName().compareTo("style") == 0){
	        				style.afegirAtributActivitat(attrib.getName(),attrib.getValue());
	        			}else if (child.getName().compareTo("font") == 0){
	        				font.afegirAtributActivitat(attrib.getName(),attrib.getValue());
	        			}else if (child.getName().compareTo("color") == 0){
	        				color.afegirAtributActivitat(attrib.getName(),attrib.getValue());
	        			}
	            	}
    			}
    			/*else{
    				if(child.getTextTrim().compareTo("") != 0)
    					System.out.println(child.getTextTrim());
    			}*/
    			
            	if(child.getChildren().size() != 0){
            		if (child.getName().compareTo("activity") == 0){	
            			recursiu(child.getChildren(), activitat);			//Seguim llegint si hi ha descendents 		
        			}else if (child.getName().compareTo("cells") == 0){
        				//Style style = (Style) recursiu(child.getChildren(), activitat);			//Seguim llegint si hi ha descendents 
        				recursiu(child.getChildren(), activitat);	
        				System.out.println("Tornooooooo rows: " + cells.retornaRows());
        				activitat.afegirCellsActivitat(cells);
        			}else if (child.getName().compareTo("style") == 0){
        				recursiu(child.getChildren(), activitat);	
        				System.out.println("Styleeeeeee: ");
        				//cells.afegirFillCells(style);
        			}
            	}else{ 
            		if (child.getName().compareTo("font") == 0){
            			style.afegirFillStyle(font);
            		}else if (child.getName().compareTo("color") == 0){
            			//style.afegirFillStyle(color);
            		}
            	}	
            	
            	if (child.getName().compareTo("activity") == 0){	
            		llistaActivitats.add(activitat);	
            		return activitat;
    			}
    		}
		}
		return null;
	}

/*	public static void convertXMLtoJSON(Context c) throws IOException {

        //InputStream is = ConvertXMLtoJSON.class.getResourceAsStream("sample-xml.xml");
        InputStream is = c.getAssets().open("p_nadal.jclic");		
        String xml = IOUtils.toString(is);
        
        XMLSerializer xmlSerializer = new XMLSerializer(); 
        JSON json = xmlSerializer.read( xml );  
        
        System.out.println(json.toString());

	}*/
	
	public static void llegirFitxerJClic(Context c, String nomFitxer, Integer idJoc){
		Document doc = null;
		InputStreamReader isr = null;
		activitat = null;
		llistaActivitats = new ArrayList<Activitat>();
		
		try {	
			// Fitxer d'entrada: .jclic
			InputStream is = c.getAssets().open(nomFitxer);									
			isr = new InputStreamReader(is);
			SAXBuilder builder = new SAXBuilder(false);
			doc = builder.build(isr);

			// Fitxer de sortida: data.js
		 	//PrintStream fitxerData = new PrintStream(new FileOutputStream(c.getFilesDir()+"/" + idJoc + "/data.js",false));
			//PrintStream fitxerData = new PrintStream(new FileOutputStream(c.getFilesDir()+"/data.js",false));
			
			// Fitxer de sortida: data.js
			//c.getFilesDir()+"/" + idJoc + "/data.js"
			PrintWriter fitxerData = new PrintWriter(new File(c.getFilesDir()+"/data.js"));
			
			Element raiz = doc.getRootElement();	// S'agafa l'element arrel
			List<?> llistaActivities = raiz.getChild("activities").getChildren();
			// Principi del fitxer json
			fitxerData.println("var maxActivitats = " + llistaActivities.size() + ";");
			//fitxerData.println("var tipusActivitat = [];");
			//fitxerData.println("var dadesActivitat = [];");
			
			recursiu(llistaActivities, activitat);
			
			Gson gson = new Gson(); 
			String jsonOutput = gson.toJson(llistaActivitats); 
			
			System.out.println("txt: "+jsonOutput);
			
			// Escrivim les dades del JSON al fitxer data.js 
			fitxerData.print(jsonOutput);
									
			is.close();
			fitxerData.close();
			

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}
	}
}

// LLEGIR .JCLIC SENSE RECURSSIU
/*while (iActivities.hasNext()){
Element activity = (Element)iActivities.next(); // Primer fill que tingui com a nom "activity"
List<?> llistaAttrib = activity.getAttributes();
Iterator<?> iAtrib = llistaAttrib.iterator();
while(iAtrib.hasNext()){						//Atributs d'"activity"
	Attribute attrib = (Attribute) iAtrib.next();
	System.out.print(attrib.getName());
	System.out.println("  "+attrib.getValue());

}
//Element cells = activity.getChild("cells");		//Fill d'activity = "cells"
//if(activity.getChildren().size() != 0){	
Iterator<?> illistaChildrenActivity = activity.getChildren().iterator();
while(illistaChildrenActivity.hasNext()){
	Element childActivity = (Element) illistaChildrenActivity.next();
	if((childActivity.getName().compareTo("cells")==0)||(childActivity.getName().compareTo("document")==0)){
		List<?> llistaAttribCells = childActivity.getAttributes();
    	Iterator<?> iAtribCells = llistaAttribCells.iterator();
    	while(iAtribCells.hasNext()){					//Atributs de "cells o document"
    		Attribute attrib = (Attribute) iAtribCells.next();
    		System.out.print(attrib.getName());
    		System.out.println("  "+attrib.getValue());
    	}
 
    	
    //	if(childActivity.getChildren().size() != 0){	
    	List<?> llistaCells = childActivity.getChildren();		//Fills de "cells o document"
    	Iterator<?> iCells = llistaCells.iterator();
    	while(iCells.hasNext()){
    		Element valorCells = (Element)iCells.next();
    		List<?> llistaAttribChildrenCells = valorCells.getAttributes();
        	Iterator<?> iAtribChildrenCells = llistaAttribChildrenCells.iterator();
        	while(iAtribChildrenCells.hasNext()){		//Atributs dels fills de "cells"
        		Attribute attrib = (Attribute) iAtribChildrenCells.next();
        		System.out.print(attrib.getName());
        		System.out.println("  "+attrib.getValue());
        	}
        	
        	
        	
        	if(valorCells.getChildren().size() != 0){		
        		List<?> llistaChildrenCell = valorCells.getChildren();	
        		Iterator<?> iChildrenCell = llistaChildrenCell.iterator();
        		while(iChildrenCell.hasNext()){
        			Element childrenCell = (Element) iChildrenCell.next();
        			List<?> llistaAttribChildrenCell =  childrenCell.getAttributes();
        			Iterator<?> iAtribChildrenCell = llistaAttribChildrenCell.iterator();
                	while(iAtribChildrenCell.hasNext()){					//Atributs dels fills "cell"
                		Attribute attrib = (Attribute) iAtribChildrenCell.next();
                		System.out.print(attrib.getName());
                		System.out.println("  "+attrib.getValue());
                	}
                	
                	
                	
                	if(childrenCell.getChildren().size() != 0){		
                		List<?> llistaNetsCell = childrenCell.getChildren();	
                		Iterator<?> illistaNetsCell = llistaNetsCell.iterator();
                		while(illistaNetsCell.hasNext()){
                			Element netCell = (Element) illistaNetsCell.next();
                			List<?> llistaAttribNetCell =  netCell.getAttributes();
                			Iterator<?> illistaAttribNetCell = llistaAttribNetCell.iterator();
                        	while(illistaAttribNetCell.hasNext()){					//Atributs dels nets "cell"
                        		Attribute attrib = (Attribute) illistaAttribNetCell.next();
                        		System.out.print(attrib.getName());
                        		System.out.println("  "+attrib.getValue());
                        	}
                		}
                	}
        		}
        	}
    	}
	}
}
}*/
