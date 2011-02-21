package cat.urv.clic.android;

import java.io.*;
import java.util.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

public class Prova extends ListActivity {
	
	private ArrayAdapter<String> adp; 
	private List<String> jocs = new ArrayList<String>();
	  
    public void onCreate(Bundle savedInstanceState) {
    	try {
    		super.onCreate(savedInstanceState);
    		
            SAXBuilder builder=new SAXBuilder(false); 
            
            InputStream is = getAssets().open("jocs.xml");
            Document doc=builder.build(is);

            Element raiz=doc.getRootElement();	//s'agafa l'element arrel
            
            List parcela=raiz.getChildren("jocs");
           
            Iterator i = parcela.iterator();
            while (i.hasNext()){
                	Element e= (Element)i.next(); //primer fill que tingui com a nom "jocs"
                	List noms=e.getChildren("nom");
                	Iterator ii = noms.iterator();
                	while (ii.hasNext()){
                		Element ee= (Element)ii.next();
            	   	 	jocs.add(ee.getText());
                	}
            }
            
            adp = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,jocs); 
        	setListAdapter(adp);
        	
         }catch (Exception e){
            e.printStackTrace();
         }
    }
    
    public void onClick(View v) {
		Intent intent = null;
		if (v.getId() == R.id.next)
		{
			intent = new Intent(this, LlistaClics.class);
		}else if (v.getId() == R.id.vistaweb) {
			//intent = new Intent(this, VistaWeb.class);
			//intent = new Intent(this, Vista.class);
			intent = new Intent(this, Prova.class);
		}
		
		startActivity(intent);		
	}
}