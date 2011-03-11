package cat.urv.clic.android;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class IniciAplicacio extends Activity implements OnClickListener{
	
	private ArrayAdapter<String> adp; 
	private HashJocs llistaJocs = new HashJocs();
	Intent intent = null;
	  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	try {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.inici);
	        // Boto
	        Button bAfegirJoc = (Button) findViewById(R.id.afegirjoc);
	        bAfegirJoc.setOnClickListener( this );  
	        // Llista
	        final ListView list = (ListView) findViewById(R.id.list);
	        	        
	        // Creem el fitxer dels jocs ja descarregats
	        exportarJocsDescarregatsXML();     
	        
	        llegirFitxerJocsXML("jocs.xml",false);
	        //llegirFitxerJocsXML("jocs_descarregats.xml",true);
	        
	        adp = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, this.llistaJocs.construirLlistaJocs()); 
	        list.setAdapter(adp);       
	   	
			intent = new Intent(this, DescripcioJoc.class);	
			
	        // Clic de la llista
	        OnItemClickListener onClic = new OnItemClickListener(){
	        						public void onItemClick(AdapterView<?> arg0, View v, int i, long id) {
	        							String str = list.getItemAtPosition(i).toString();
	        					        							
	        							DescripcioJoc.name = str;
	        							startActivity(intent);	        		
	        						}};
	        list.setOnItemClickListener(onClic);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
       
    // Clic del boto
	public void onClick(View v) {
		Intent intent = null;
		intent = new Intent(this, VistaWeb.class);			
		startActivity(intent);		
	}
		
	public void llegirFitxerJocsXML(String nomFitxer, boolean fitxerIntern){
		
		Document doc = null;
		InputStreamReader isr;
                                
		try {
			SAXBuilder builder = new SAXBuilder(false);
			
			// Obrim el fixer de diferent manera si tenim el tenim guardat al mòbil o no
			if (fitxerIntern) {
				FileInputStream is = openFileInput(nomFitxer);
				isr = new InputStreamReader(is);
			}else{
				InputStream is = getAssets().open(nomFitxer);	
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
	
	            	// Àrea
	            	List<String> arees = new ArrayList<String>();
	            	Iterator<?>iArea = e.getChildren("area").iterator();
	            	while (iArea.hasNext()){
	            		Element ee= (Element)iArea.next();
	        	   	 	arees.add(ee.getText());
	            	}
	            	
	            	String ruta = e.getChild("ruta").getText();
	        
	            	Joc dadesJoc = new Joc(identificador, nom, dataPublicacio, idiomes, nivells, arees, ruta);
	            	this.llistaJocs.afegirJoc(identificador, dadesJoc);
	        }
		} catch (FileNotFoundException e1) {
			// MOSTRAR UN MISSATGE PER AVISAR QUE NO S'HA POGUT LLEGIR EL FITXER
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}


	public void exportarJocsDescarregatsXML() {
	   try{
		   PrintStream ps = new PrintStream(new FileOutputStream("/data/data/cat.urv.clic.android/files/jocs_descarregats.xml",false));
		   
		   List<Integer> llistaId = this.llistaJocs.llistaIdentificadorJocs();
			 
		   ps.println("<?xml version='1.0' encoding='utf-8'?>");
		   ps.println("<jclic nom='Aplicacions jclic'>");
		   
		   Iterator<Integer> it = llistaId.iterator();
		   while (it.hasNext()){
			    ps.println("<joc>");
			    
			   	Integer id = it.next();   
			   	Joc joc = this.llistaJocs.cercarJoc(id);
			   	
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
}

