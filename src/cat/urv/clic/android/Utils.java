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
		} catch (IOException e) {
		} catch (JDOMException e) {
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
	
	public static boolean descarregarFitxer(Context c, String ruta, String idJoc) {
		boolean hiHaConnexio = false;
		
		try {
			// Url del joc
			URL url = new URL(ruta);
			
			// Obrim la connexió
			URLConnection urlCon = url.openConnection();
			
			// S'obté l'inputStream del joc i s'obre el zip local
			InputStream is =  urlCon.getInputStream();
			File fdesti = new File(c.getFilesDir() + "/" + idJoc + ".zip");
			copiaFitxer_InputStreamFile(is, fdesti);
			hiHaConnexio = true;
			
		} catch (IOException e){
			hiHaConnexio = false;
		}
		return hiHaConnexio;
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
				//**Extracting				
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
	
	public static void descarregarImatgeJoc(Context c, String ruta, String idJoc) {
		// Url del joc
		URL url;
		try {
			url = new URL(ruta);
	
			// Obrim la connexió		
			URLConnection urlCon = url.openConnection();
		
			// S'obté l'inputStream del joc i es descarrega la imatge del joc
			InputStream is =  urlCon.getInputStream();
	
			// A la imatge del joc li posem el nom de l'identificador
			File fdesti = new File(c.getFilesDir() + "/" + idJoc + "/" + idJoc + ".jpg");
			copiaFitxer_InputStreamFile(is, fdesti);	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	        llegirFitxerJClic(c, idJoc);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void recursiuXML(RecursiuXML objecteActual, Element nodeActual, String stringActual)
	{
		//Primer mirem els atributs del tag
		List<?> llistaAtributs =  nodeActual.getAttributes(); 	//Llegim els atributs
		Iterator<?> atribut = llistaAtributs.iterator();
		while(atribut.hasNext()){
    		Attribute attrib = (Attribute) atribut.next();
    		if (stringActual.equals("")) {
    			objecteActual.afegirAtribut(attrib.getName(), attrib.getValue());
    		} else {
    			objecteActual.afegirAtribut(stringActual+"-"+attrib.getName(), attrib.getValue());
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
				//*** Creada nova cell list
				recursiuXML(listCells,fillActual, "");
				//*** Guardant dades a la nova cell list
				objecteActual.afegirCells(listCells);
				
			} else if(fillActual.getName().equals("cell")) {
				Cell cell = new Cell();
				//*** Creada nova cell
				recursiuXML(cell,fillActual, "");
				//*** Guardant dades a la nova cell
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
	
	public static String obtenirNomFitxer(Context c, String idJoc) throws IOException {
		String nomFitxerJclic = null;
		
		File fitxer = new File(c.getFilesDir() + "/" + idJoc + "/.");			
		String[] llista_arxius = fitxer.list();
		int i = 0;
		boolean fitxerTrobat = false;
		while((i < llista_arxius.length) && (!fitxerTrobat))
		{
		    if(llista_arxius[i].endsWith("jclic"))
		    {
		    	nomFitxerJclic = llista_arxius[i];
		    	fitxerTrobat = true;
		    }  
		    i++;
		}
		
		return nomFitxerJclic;
	}
	
	public static void llegirFitxerJClic(Context c, String idJoc){
		Document doc = null;
		InputStreamReader inputStrReader = null;
		Clic clic = new Clic();
		Sequence sequence;
		Settings settings = new Settings();
		
		try {	
			// Obrim el fitxer .jclic	
			String nomFitxerJclic = obtenirNomFitxer(c, idJoc);
			inputStrReader = new InputStreamReader(new FileInputStream(c.getFilesDir()+ "/" + idJoc + "/" + nomFitxerJclic));
			
			SAXBuilder builder = new SAXBuilder(false);
			doc = builder.build(inputStrReader);

			// Fitxer de sortida: data.js
		 	PrintStream fitxerData = new PrintStream(new FileOutputStream(c.getFilesDir()+ "/" + idJoc + "/data.js",false));
			
		 	// Llegim el fitxer .jclic
			Element raiz = doc.getRootElement();	// S'agafa l'element arrel

			List<?> nodeList = raiz.getChildren();
			Iterator<?> inodeList = nodeList.iterator();
			while ( inodeList.hasNext() ){	
				Element currentNode = (Element) inodeList.next();
				
				if (currentNode.getName().equals("activities")) {
					List<?> llistaActivities = currentNode.getChildren();
					Iterator<?> actList = llistaActivities.iterator();
					// Indiquem el numero d'activitats del fitxer
					fitxerData.println("var maxActivitats = " + llistaActivities.size() + ";");
					while ( actList.hasNext() ){
						//Creem una activitat
						Activitat objecteActivitat = new Activitat();
						Element nodeActivitat = (Element) actList.next();;
					
						//Carreguem tot el material a la nova activitat
						recursiuXML(objecteActivitat,nodeActivitat, "");
						//Afegim l'activitat ja plena a la llista d'activitats del clic
						clic.afegirActivitat(nodeActivitat.getAttributeValue("name"),objecteActivitat);
					}
				} else if (currentNode.getName().equals("sequence")) {
					List<?> llistaItems = currentNode.getChildren();
					Iterator<?> itList = llistaItems.iterator();
					sequence = new Sequence(llistaItems.size());
					while ( itList.hasNext() ){
						Element node = (Element) itList.next();;
					
						//Afegim l'item ja ple a la taula de sequence del clic
						sequence.afegirItem(node.getAttributeValue("name"));
					}
					clic.afegirSequence(sequence);
				}else if (currentNode.getName().equals("settings")) {
					List<?> llistaSettings = currentNode.getChildren();
					Iterator<?> setList = llistaSettings.iterator();
					while ( setList.hasNext() ){
						Element nodeSetting = (Element) setList.next();
						if((nodeSetting.getName().compareTo("revision")!=0)&&(nodeSetting.getName().compareTo("description")!=0)){
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
			String jsonOutputSettings = gson.toJson(clic.getSettings()); 
			
			// Escrivim les dades del JSON al fitxer data.js 
			fitxerData.print("var dadesActivitat={\"settings\":"+jsonOutputSettings);
			fitxerData.print(", \"activitats\":[");
			String[] taulaNoms = clic.getSequence().getTaulaNoms();
			Integer index = clic.getSequence().getIndex();
			String jsonOutputActivitat;
			for(int j=0; j < index; j++){
				jsonOutputActivitat = gson.toJson(clic.retornaActivitat(taulaNoms[j]));
				fitxerData.print(jsonOutputActivitat);
				if(j<index-1)
					fitxerData.print(",");
			}
			fitxerData.print("]}");
			fitxerData.close();
			inputStrReader.close();
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}
	}
}