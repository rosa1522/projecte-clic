package cat.urv.clic.android;

import java.util.ArrayList;
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
        String[] nomsArees = ClicApplication.llistaArees.getNomsArees();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nomsArees);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sArea.setAdapter(adapter);
        
        // Idioma
        final Spinner sIdioma = (Spinner) findViewById(R.id.spinnerIdioma);
        String[] nomsIdiomes = ClicApplication.llistaIdiomes.getNomsIdiomes();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nomsIdiomes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sIdioma.setAdapter(adapter);
        
        // Nivell
        final Spinner sNivell = (Spinner) findViewById(R.id.spinnerNivell);
        String[] nomsNivells = ClicApplication.llistaNivells.getNomsNivells();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nomsNivells);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sNivell.setAdapter(adapter);
        
        
        // Boto per la cerca dels jocs amb els criteris introduïts
        ImageButton bCercarJoc = (ImageButton) findViewById(R.id.cercarjocsCercar);
        // Clic del boto
		final Intent intentBoto = new Intent(this, LlistaJocsJClic.class);			
		bCercarJoc.setOnClickListener( new OnClickListener(){
	    	public void onClick(View v) {	    
	    		String idNivell = ClicApplication.llistaNivells.cercarIdNivell(sNivell.getSelectedItem().toString());
	    		String idIdioma = ClicApplication.llistaIdiomes.cercarIdIdioma(sIdioma.getSelectedItem().toString());
	    		String idArea = ClicApplication.llistaArees.cercarIdArea(sArea.getSelectedItem().toString());
	    		
	    		// Li passem la llista dels jocs filtrada amb els filtres introduits
				ArrayList<String> llistaJocs = (ArrayList<String>) ClicApplication.llistaJocs.construirLlistaJocsCondicions(idNivell, idIdioma, idArea);
				intentBoto.putStringArrayListExtra("llistaJocs",llistaJocs);
	    		
				startActivity(intentBoto);		
	    	}	        	
        }); 
    }
	
}


