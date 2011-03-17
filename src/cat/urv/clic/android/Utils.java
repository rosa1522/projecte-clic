package cat.urv.clic.android;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import android.content.Context;

public class Utils {
	
	//TOT AQUEST TIPUS DE CODI QUE LLEGEIX / ESCRIU FITXERS NO HAURIA DE SER DINS ELS ACTIVITIES, POSEU-HO MILLOR AQUI O EN ALTRES FITXERS

	public static HashJocs llegirFitxerJocsXML(Context c, String nomFitxer){
		Document doc = null;
		InputStreamReader isr = null;

		HashJocs llistaJocs = new HashJocs();

		try {			
			// Obrim el fixer de diferent manera si tenim el tenim guardat al mòbil o no

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
}
