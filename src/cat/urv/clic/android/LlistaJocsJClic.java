package cat.urv.clic.android;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//import cat.urv.clic.android.GestioFitxers;

public class LlistaJocsJClic extends Activity{
	
	private Intent intent = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	try {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.llistajocsjclic);
	        
	        // Llista
	        final ListView list = (ListView) findViewById(R.id.list_jocs);
	        	                
	        //Demanem la llista de jocs NO DESCARREGATS
	        List<String> llistaJocs = ClicApplication.llistaJocs.construirLlistaJocs(false);	   
	        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, llistaJocs);
	        list.setAdapter(adp);     
	 
	        
	        // Clic de la llista
	        intent = new Intent(this, DescripcioJoc.class);	
	        list.setOnItemClickListener( new OnItemClickListener(){
	        	public void onItemClick(AdapterView<?> arg0, View v, int i, long id) {
	        		String str = (String) list.getItemAtPosition(i);
	        		
	        		int idJoc = ClicApplication.llistaJocs.cercarJoc(str).getIdentificador();
	        		
	        		//Per passar l'identificador a un altre activity ho fem aixi:
        			intent.putExtra("idJoc",idJoc);
        			startActivity(intent);		        					        	
	        	}
	        });
	        
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

