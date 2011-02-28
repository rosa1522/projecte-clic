package cat.urv.clic.android;

import java.io.InputStream;
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
	private List<String> jocs = new ArrayList<String>();
	  
	  
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
	
	        Element raiz=doc.getRootElement();	//s'agafa l'element arrel
	        
	        List<?> parcela=raiz.getChildren("jocs");   
	        Iterator<?> i = parcela.iterator();
	        while (i.hasNext()){
	            	Element e= (Element)i.next(); //primer fill que tingui com a nom "jocs"
	            	List<?> noms=e.getChildren("nom");
	            	Iterator<?> ii = noms.iterator();
	            	while (ii.hasNext()){
	            		Element ee= (Element)ii.next();
	        	   	 	jocs.add(ee.getText());
	            	}
	        }
	        adp = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,jocs); 
	        list.setAdapter(adp);       
        
	        
	        // Clic de la llista
	        OnItemClickListener onClic = new OnItemClickListener(){
	        						public void onItemClick(AdapterView<?> arg0, View v, int i,long id) {
	        							String str = list.getItemAtPosition(i).toString();
	        							System.out.println("***********ENTRA");
	        							System.out.println(str);
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

