package cat.urv.clic.android;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class IniciAplicacio extends Activity {
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	try {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.inici);
	        
	        //////////////////////////////////////////
	        // Boto
	        Button bAfegirJoc = (Button) findViewById(R.id.afegirjoc);
	        // Clic del boto
    		final Intent intentBoto = new Intent(this, LlistaJocsJClic.class);			
	        bAfegirJoc.setOnClickListener( new OnClickListener(){
		    	public void onClick(View v) {
		    		startActivity(intentBoto);		
		    	}	        	
	        });  
	        Utils.llegirFitxerJClic(getApplicationContext(), "p_nadal.jclic",123);
	        //////////////////////////////////////////
	        // Llista
	        final ListView list = (ListView) findViewById(R.id.list_descarregats);         
	        //Demanem la llista de jocs DESCARREGATS
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
	
}

