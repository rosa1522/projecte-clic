package cat.urv.clic.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;


public class CercarJocs extends Activity {
	
	private ArrayAdapter<String> adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cercarjocs);
        
        
        // Area
        final Spinner sArea = (Spinner) findViewById(R.id.spinnerArea);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arees);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sArea.setAdapter(adapter);
        
        // Idioma
        final Spinner sIdioma = (Spinner) findViewById(R.id.spinnerIdioma);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, idiomes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sIdioma.setAdapter(adapter);
        
        // Nivell
        final Spinner sNivell = (Spinner) findViewById(R.id.spinnerNivell);
        List<String> nomsNivells = ClicApplication.llistaNivells.nomsNivells();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nomsNivells);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sNivell.setAdapter(adapter);
        
        
        // Boto per la cerca dels jocs amb els criteris introduïts
        ImageButton bCercarJoc = (ImageButton) findViewById(R.id.cercarjocsCercar);
        // Clic del boto
		final Intent intentBoto = new Intent(this, LlistaJocsJClic.class);			
		bCercarJoc.setOnClickListener( new OnClickListener(){
	    	public void onClick(View v) {	    		
	    		System.out.println("NIVELL!!" + sNivell.toString());
	    		//idNivell = nomsNivells.
	    		
	    		// Li passem la llista dels jocs filtrada amb els filtres introduits
				//ArrayList<String> llistaJocs = (ArrayList<String>) ClicApplication.llistaJocs.construirLlistaJocsCondicions(false);
				//intentBoto.putStringArrayListExtra("llistaJocs",llistaJocs);
	    		
				startActivity(intentBoto);		
	    	}	        	
        }); 
    }
	
    
    private static final String[] idiomes = {
    	"Tots els idiomes", "alemany", "anglès", "araucà", "basc", "caló", "català", "espanyol", "esperanto", "francès", 
    	"gallec", "grec", "italià", "llatí", "occità", "portuguès", "rumanès", "suec", "xins" 
	};
    
    /*private static final String[] nivells = {
    	"Tots els nivell", "Infantil (3-6)", "Primària (6-12)", "Secundària (12-16)", "Batxillerat (16-18)"
    };*/
    
    private static final String[] arees = {
    	"Totes les àrees", "Llengües", "Matemàtiques", "Ciències socials", "Ciències experimentals", "Música", "Visual i plàstica", 
    	"Educació física", "Tecnologies", "Diversos" 
    };
	
}


