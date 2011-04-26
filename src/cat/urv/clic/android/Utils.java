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
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.google.gson.Gson;

import android.content.Context;
import cat.urv.clic.android.XMLobjects.Activitat;
import cat.urv.clic.android.XMLobjects.Cell;
import cat.urv.clic.android.XMLobjects.CellList;
import cat.urv.clic.android.XMLobjects.Clic;
import cat.urv.clic.android.XMLobjects.Item;
import cat.urv.clic.android.XMLobjects.RecursiuXML;
import cat.urv.clic.android.XMLobjects.Sequence;
import cat.urv.clic.android.XMLobjects.Settings;


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
            	
            	SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yy");
            	String dataString = e.getChild("dataPublicacio").getText();
            	Date dataPublicacio = dataFormat.parse(dataString);
            	           	
            	//System.out.println("DATA " + dataPublicacio);
            	//Date dataPublicacio = e.getChild("dataPublicacio").getText();
 
            	
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
            	String clic = e.getChild("clic").getText();
            	String img = e.getChild("img").getText();
            	String centre = e.getChild("centre").getText();
            	String autors = e.getChild("autors").getText();
            	            	
				// Afegim el joc
				Joc dadesJoc = new Joc(identificador, nom, dataPublicacio, llengues, nivells, arees, ruta,
									   clic, img, centre, autors, false);
				llistaJocs.afegirJoc(dadesJoc);
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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
	
/*	public static void convertXMLtoJSON(Context c) throws IOException {

        //InputStream is = ConvertXMLtoJSON.class.getResourceAsStream("sample-xml.xml");
        InputStream is = c.getAssets().open("p_nadal.jclic");		
        String xml = IOUtils.toString(is);
        
        XMLSerializer xmlSerializer = new XMLSerializer(); 
        JSON json = xmlSerializer.read( xml );  
        
        System.out.println(json.toString());

	}*/
	
	private static void recursiuXML(RecursiuXML objecteActual, Element nodeActual, String stringActual)
	{
		//Primer mirem els atributs del tag
		List<?> llistaAtributs =  nodeActual.getAttributes(); 	//Llegim els atributs
		Iterator<?> atribut = llistaAtributs.iterator();
		while(atribut.hasNext()){
    		Attribute attrib = (Attribute) atribut.next();
    		if (stringActual.equals("")) {
    			objecteActual.afegirAtribut(attrib.getName(), attrib.getValue());
    			System.out.println(attrib.getName()+" : "+attrib.getValue());
    		} else {
    			objecteActual.afegirAtribut(stringActual+"-"+attrib.getName(), attrib.getValue());
    			System.out.println(stringActual+"-"+attrib.getName()+" : "+attrib.getValue());
    		}
		}
		
		//Desprem mirem el contingut del tag. p, title: ex: <p> XXXXX <p>
		if(nodeActual.getTextTrim().compareTo("")!=0){
			objecteActual.afegirAtribut(nodeActual.getName(), nodeActual.getTextTrim());
		}
		
		//I despres mirem els fills del tag - Aqui entra la recursivitat
		List<?> llistaFills =  nodeActual.getChildren(); 	//Llegim els fills
		Iterator<?> fill = llistaFills.iterator();
		while(fill.hasNext()){
			Element fillActual = (Element) fill.next();
			
			if(fillActual.getName().equals("cells")) {
				CellList listCells = new CellList();
				System.out.println("*** Creada nova cell list");
				recursiuXML(listCells,fillActual, "");
				System.out.println("*** Guardant dades a la nova cell list");
				objecteActual.afegirCells(listCells);
				
			} else if(fillActual.getName().equals("cell")) {
				Cell cell = new Cell();
				System.out.println("*** Creada nova cell");
				recursiuXML(cell,fillActual, "");
				System.out.println("*** Guardant dades a la nova cell");
				objecteActual.afegirCell(cell);
				
			} else {
				//Si no es cap objecte nou, seguim omplint la hashing de l'objecte actual
				if (stringActual.equals("")) {
					recursiuXML(objecteActual, fillActual, fillActual.getName());	
	    		} else {
	    			recursiuXML(objecteActual, fillActual, stringActual+"-"+fillActual.getName());	
	    		}			
			}
		}	
	}
	
	public static void llegirFitxerJClic(Context c, String nomFitxer){
		Document doc = null;
		InputStreamReader isr = null;
		
		Clic clic = new Clic();
		Sequence sequence;
		Settings settings = new Settings();
		try {	
			// Fitxer d'entrada: .jclic
			InputStream is = c.getAssets().open(nomFitxer);									
			isr = new InputStreamReader(is);
			SAXBuilder builder = new SAXBuilder(false);
			doc = builder.build(isr);

			// Fitxer de sortida: data.js
		 	//PrintStream fitxerData = new PrintStream(new FileOutputStream(c.getFilesDir()+"/" + idJoc + "/data.js",false));
			PrintStream fitxerData = new PrintStream(new FileOutputStream(c.getFilesDir()+"/data.js",false));
			
			// Fitxer de sortida: data.js
			//c.getFilesDir()+"/" + idJoc + "/data.js"
			//PrintWriter fitxerData = new PrintWriter(new File(c.getFilesDir()+"/data.js"));
	
			Element raiz = doc.getRootElement();	// S'agafa l'element arrel

			List<?> nodeList = raiz.getChildren();
			Iterator<?> inodeList = nodeList.iterator();
			while ( inodeList.hasNext() ){	
				Element currentNode = (Element) inodeList.next();
				System.out.println( currentNode.getName() );
				
				if (currentNode.getName().equals("activities")) {
					List<?> llistaActivities = currentNode.getChildren();
					Iterator<?> actList = llistaActivities.iterator();
					// Indiquem el numero d'activitats del fitxer
					fitxerData.println("var maxActivitats = " + llistaActivities.size() + ";");
					while ( actList.hasNext() ){
						//Creem una activitat
						Activitat objecteActivitat = new Activitat();
						Element nodeActivitat = (Element) actList.next();;
						System.out.println("-----ACTIVITAT: "+nodeActivitat.getName() );
					
						//Carreguem tot el material a la nova activitat
						recursiuXML(objecteActivitat,nodeActivitat, "");
						//Afegim l'activitat ja plena a la llista d'activitats del clic
						clic.afegirActivitat(objecteActivitat);
					}
				} else if (currentNode.getName().equals("sequence")) {
					List<?> llistaItems = currentNode.getChildren();
					Iterator<?> itList = llistaItems.iterator();
					sequence = new Sequence(llistaItems.size());
					while ( itList.hasNext() ){
						//Creem un Item
						Item item = new Item();
						Element nodeItem = (Element) itList.next();;
						System.out.println("---------SEQUENCE:  " +nodeItem.getName() );
					
						//Carreguem tot els atributs a l'item
						recursiuXML(item,nodeItem, "");
						//Afegim l'item ja ple a la taula de sequence del clic
						sequence.afegirItem(item);
					}
					clic.afegirSequence(sequence);
				}else if (currentNode.getName().equals("settings")) {
					List<?> llistaSettings = currentNode.getChildren();
					Iterator<?> setList = llistaSettings.iterator();
					while ( setList.hasNext() ){
						Element nodeSetting = (Element) setList.next();
						if((nodeSetting.getName().compareTo("revision")!=0)&&(nodeSetting.getName().compareTo("description")!=0)){
							System.out.println("---------SETTINGS:  " + nodeSetting.getName() );
							recursiuXML(settings,nodeSetting, nodeSetting.getName());
						}else if (nodeSetting.getName().compareTo("description")==0){
							//Si es descripcio es guarda en una taula perque sorti en l'ordre correcte.
							List<?> llistaFills =  nodeSetting.getChildren(); 	//Llegim els fills
							Iterator<?> fill = llistaFills.iterator();
							settings.midaDescripcio(llistaFills.size());
							while(fill.hasNext()){
								Element fillActual = (Element) fill.next();
								settings.afegirDescripcioSetting(fillActual.getText());
							}					
						}
					}
					clic.afegirSettings(settings);
				}
			}

			//Creem el fitxer JSON
			Gson gson = new Gson(); 
			String jsonOutput = gson.toJson(clic); 
			
			//System.out.println("txt: "+jsonOutput);
			
			// Escrivim les dades del JSON al fitxer data.js 
			fitxerData.print("var dadesActivitat="+jsonOutput);
									
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
