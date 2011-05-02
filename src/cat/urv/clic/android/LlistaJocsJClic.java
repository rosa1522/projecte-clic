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


public class LlistaJocsJClic extends Activity{
	
	private Intent intent = null;
	private Bundle bundle;
	private ListView list; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	try {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.llistajocsjclic);
	        
	        // Agafem la llista de jocs que ens han passat per parametre
	        bundle = getIntent().getExtras();
	        ArrayList<String> llistaJocs = bundle.getStringArrayList("llistaJocs");
	        
	        // Llista
	        list = (ListView) findViewById(R.id.list_jocs);
	        	                
	        // Demanem la llista de jocs NO DESCARREGATS  
	        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, llistaJocs);
	        list.setAdapter(adp);     
	 
	        // Clic de la llista
	        intent = new Intent(this, DescripcioJoc.class);	
	        list.setOnItemClickListener( new OnItemClickListener(){
	        	public void onItemClick(AdapterView<?> arg0, View v, int i, long id) {
	        		String str = (String) list.getItemAtPosition(i);
	        		int idJoc = ClicApplication.llistaJocs.cercarJoc(str).getIdentificador();
	
        			intent.putExtra("idJoc",idJoc);
        			startActivity(intent);		        					        	
	        	}
	        });
	        
	        // Boto
	        ImageButton bCercarJoc = (ImageButton) findViewById(R.id.cercarjocLlista);
	        // Clic del boto
    		final Intent intentBoto = new Intent(this, CercarJocs.class);			
    		bCercarJoc.setOnClickListener( new OnClickListener(){
		    	public void onClick(View v) {
		    		startActivity(intentBoto);		
		    	}	        	
	        });  
	        
	        
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void onRestart(){
    	super.onRestart();
    	List<String> llistaJocs = ClicApplication.llistaJocs.construirLlistaJocs(false);	   
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, llistaJocs);
        list.setAdapter(adp); 
    }
}

