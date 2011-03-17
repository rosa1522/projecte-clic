package cat.urv.clic.android;

import android.app.Activity;
import android.os.Bundle;

public class GestioFitxers extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    }
    
    // TOT AIXO ESTA A UTILS ARA!!!
    // NO HA D'ESTAR EN UN ACTIVITY!!
/*
	public void llegirFitxerJocsXML(String nomFitxer, boolean fitxerIntern, HashJocs llistaJocs){
		
		Document doc = null;
		InputStreamReader isr = null;
                                
		try {
			SAXBuilder builder = new SAXBuilder(false);
			
			// Obrim el fixer de diferent manera si tenim el tenim guardat al mòbil o no
			if (fitxerIntern) {
				// Comprovem que existeixi el fitxer amb els jocs descarregats
				if (new File(getFilesDir()+nomFitxer).exists()){
					FileInputStream is = openFileInput(nomFitxer);
					isr = new InputStreamReader(is);
				}
			}else{
				System.out.println("NOM FITXER "+nomFitxer);
				InputStream is = getAssets().open(nomFitxer);									
				System.out.println("PASSA");
				isr = new InputStreamReader(is);
			}
			 	
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
	            	
	            	Boolean descarregat;
	            	if (fitxerIntern) {
	            		descarregat = true;
	            	}else{
	            		descarregat = true;
	    			}
	        
	            	Joc dadesJoc = new Joc(identificador, nom, dataPublicacio, idiomes, nivells, arees, ruta, descarregat);
	            	llistaJocs.afegirJoc(identificador, dadesJoc);
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
        
	}

	public void exportarJocsDescarregatsXML(HashJocs llistaJocs) {
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
	}*/
}
