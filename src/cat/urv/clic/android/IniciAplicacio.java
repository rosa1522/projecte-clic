package cat.urv.clic.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;


public class IniciAplicacio extends Activity {
		
	private ListView list;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	try {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.inici);
	        
	        //////////////////////////////////////////
	        // Boto
	        ImageButton bAfegirJoc = (ImageButton) findViewById(R.id.afegirjoc);
	        // Clic del boto
    		final Intent intentBoto = new Intent(this, LlistaJocsJClic.class);			
	        bAfegirJoc.setOnClickListener( new OnClickListener(){
		    	public void onClick(View v) {
		    		// Li passem la llista de tots els jocs
		    		ArrayList<String> llistaJocs = (ArrayList<String>) ClicApplication.llistaJocs.construirLlistaJocs(false);
		    		intentBoto.putStringArrayListExtra("llistaJocs",llistaJocs);

		    		startActivity(intentBoto);		
		    	}	        	
	        });  
	    
	        //////////////////////////////////////////
	        // Llista
	        list = (ListView) findViewById(R.id.list_descarregats);         
	        // Demanem la llista de jocs DESCARREGATS
	        List<String> llistaJocs = ClicApplication.llistaJocs.construirLlistaJocs(true);	   
	        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, llistaJocs);
	        list.setAdapter(adp);       	   	
						
	        // Clic de la llista
	        final Intent intent = new Intent(this, DescripcioJoc.class);	
	        list.setOnItemClickListener( new OnItemClickListener(){
	        	public void onItemClick(AdapterView<?> arg0, View v, int i, long id) {
	        		String str = (String) list.getItemAtPosition(i);
	        		
	        		int idJoc = ClicApplication.llistaJocs.cercarJoc(str).getIdentificador();
	        		
	        		// Passem l'identificador a l'activity DescripcioJoc
        			intent.putExtra("idJoc",idJoc);
        			startActivity(intent);		        					        	
	        	}
	        });
	        
        }catch (Exception e){
            e.printStackTrace();
        }
    }
	
    @Override
    public void onRestart(){
    	super.onRestart();
    	List<String> llistaJocs = ClicApplication.llistaJocs.construirLlistaJocs(true);	   
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, llistaJocs);
        list.setAdapter(adp); 
    }
    
}

