package cat.urv.clic.android;

import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
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
	        // Botó
	        Button bAfegirJoc = (Button) findViewById(R.id.afegirjoc);
	        bAfegirJoc.setOnClickListener( this );  
	        // Llista
	        final ListView list = (ListView) findViewById(R.id.list);
	        
	        SAXBuilder builder=new SAXBuilder(false); 
	        
	        InputStream is = getAssets().open("jocs.xml");
	        Document doc=builder.build(is);
	        /*<jclic nom="Aplicacions jclic">
	        <jocs>
	            <nom>El Nadal</nom>
	            <nom>Els Colors</nom>
	            <nom>Dinosaures</nom>
	            <nom>El Carter</nom>
	            <nom>Pirates</nom>
	            <nom>Peixos</nom>
	            <nom>Tot just comencem</nom>
	        </jocs>
	      </jclic>*/
	        Element raiz=doc.getRootElement();	//s'agafa l'element arrel
	        
	        List<?> joc = raiz.getChildren("joc");   
	        Iterator<?> i = joc.iterator();

	        while (i.hasNext()){		       
	            	Element e= (Element)i.next(); //primer fill que tingui com a nom "jocs"
	            	 
	            	Integer identificador = Integer.valueOf(e.getChild("identificador").getText());
	            	String nom = e.getChild("nom").getText();
	            	System.out.println("APLICACIO" + nom);
	            	Date dataPublicacio = Date.valueOf(e.getChild("dataPublicacio").getText());
	            	System.out.println("DATA" + dataPublicacio);
	            	
	            	// Llengua
	            	List<String> idiomes = new ArrayList<String>();
	            	Iterator<?> iLlengua = e.getChildren("llengua").iterator();
	            	while (iLlengua.hasNext()){
	            		Element ee= (Element)iLlengua.next();
	        	   	 	idiomes.add(ee.getText());
	            	}
	            	System.out.println("PASSA" + nom);
	            	// Nivell
	            	List<String> nivells =  new ArrayList<String>();
	            	Iterator<?> iNivell = e.getChildren("nivellJoc").iterator();
	            	while (iNivell.hasNext()){
	            		Element ee= (Element)iNivell.next();
	        	   	 	nivells.add(ee.getText());
	            	}
	            	System.out.println("PASSA2" + nom);
	            	// Àrea
	            	List<String> arees = new ArrayList<String>();
	            	Iterator<?>iArea = e.getChildren("area").iterator();
	            	while (iArea.hasNext()){
	            		Element ee= (Element)iArea.next();
	        	   	 	arees.add(ee.getText());
	            	}
	            	
	            	String ruta = e.getChild("ruta").getText();
	            	System.out.println("PASSA3" + nom);
	            	Joc dadesJoc = new Joc(identificador, nom, dataPublicacio, idiomes, nivells, arees, ruta);
	            	this.llistaJocs.afegirJoc(identificador, dadesJoc);
	        }
	        

	        
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
   
    
    // Clic del botó
	public void onClick(View v) {
		Intent intent = null;
		intent = new Intent(this, VistaWeb.class);			
		startActivity(intent);		
	}
}

