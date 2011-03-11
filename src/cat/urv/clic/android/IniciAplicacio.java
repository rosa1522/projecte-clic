package cat.urv.clic.android;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
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
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;

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
	        
	        SAXBuilder builder=new SAXBuilder(false); 
	        
	        // Creem el fitxer dels jocs ja descarregats
	        exportarJocsDescarregatsXML();     
	        
	        //InputStream is = getAssets().open("jocs.xml");
	        FileInputStream is = openFileInput("jocs_descarregats.xml");
	        
	        InputStreamReader isr = new InputStreamReader(is);
	        
	        Document doc=builder.build(isr);
	        Element raiz=doc.getRootElement();	//s'agafa l'element arrel
	        
	        List<?> joc = raiz.getChildren("joc");   
	        Iterator<?> i = joc.iterator();

	        while (i.hasNext()){
	            	Element e= (Element)i.next(); //primer fill que tingui com a nom "jocs"
	            	 
	            	Integer identificador = Integer.valueOf(e.getChild("identificador").getText());
	            	String nom = e.getChild("nom").getText();
	            	System.out.println("APLICACIO" + nom);
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
	
	public void exportarJocsDescarregats(){
		try { 

		//Creamos una cadena para guardarla en el archivo 
		String Cadena_Guardar = new String("Hello Android"); 

		//Iniciamos un objeto tipo FileOutputStream con el nombre que le vamos //a dar al archivo y los permisos que va a tener 
		FileOutputStream fOut = openFileOutput("jocs_descarregats.xml", MODE_WORLD_READABLE); 

		//Iniciamos un objeto tipo OutputStreamWrite que es el que nos va a //permitir escribir la cadena en el archivo antes creado
		OutputStreamWriter osw = new OutputStreamWriter(fOut); 

		//Escribimos la cadena 
		osw.write(Cadena_Guardar); 

		//Nos aseguramos que todo quedo guardado y luego cerramos 
		osw.flush(); 
		osw.close();
		} 
		catch (IOException ioe) { }
	}

	public void exportarJocsDescarregatsXML() {
	   try{
		 PrintStream ps=new PrintStream( new FileOutputStream("/data/data/cat.urv.clic.android/files/jocs_descarregats.xml",false));
	     ps.println("<?xml version='1.0' encoding='utf-8'?>");
	     ps.println("<jclic nom='Aplicacions jclic'>");
	     ps.println("<joc>");
	     
	     ps.print("<identificador>");
	     ps.print(4343);
	     ps.println("</identificador>");
	     
	     ps.print("<nom>");
	     ps.print("aina");
	     ps.println("</nom>");
	    
	     ps.print("<dataPublicacio>");
	     ps.print("2011-08-08");
	     ps.println("</dataPublicacio>");
	     
	     ps.print("<llengua>");
	     ps.print("aina");
	     ps.println("</llengua>");
	     
	     ps.print("<nivellJoc>");
	     ps.print("aina");
	     ps.println("</nivellJoc>");
	     
	     ps.print("<area>");
	     ps.print("aina");
	     ps.println("</area>");
	     
	     ps.print("<ruta>");
	     ps.print("aina");
	     ps.println("</ruta>");
	     
	     ps.println("</joc>");
	     ps.println("</jclic>");
	     
	     }catch (Exception e){
	      System.out.println(e.getMessage());
	     }	     
		
	}
}

