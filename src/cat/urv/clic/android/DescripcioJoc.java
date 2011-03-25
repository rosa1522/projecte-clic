package cat.urv.clic.android;

import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;


public class DescripcioJoc extends Activity implements OnClickListener{
	
	private Bundle bundle;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descripciojoc);
        
        // Capturem la informacio que conte l'intent
        bundle = getIntent().getExtras();
        Joc joc = ClicApplication.llistaJocs.cercarJoc(bundle.getInt("idJoc"));
        
        TextView text = (TextView) findViewById(R.id.titolJoc);
        text.setText(joc.getNom());  
        
        text = (TextView) findViewById(R.id.dataPublicacio);
        text.setText("Data: "+joc.getDataPublicacio().toString());  

        // Llengua
        Iterator<String> it = joc.getLlengua().iterator();
        String str = new String();
        while(it.hasNext()){
        	str = str + it.next().toString() + " ";
        }
        text = (TextView) findViewById(R.id.llengua);
        text.setText("Idioma: "+str);  
 
        // Nivell
        it = joc.getNivellJoc().iterator();
        str = new String();
        while(it.hasNext()){
        	str = str + it.next().toString() + " ";
        }
        text = (TextView) findViewById(R.id.nivellJoc);
        text.setText("Nivell: "+str);  
        
        // Area
        it = joc.getAreaJoc().iterator();
        str = new String();
        while(it.hasNext()){
        	str = str + it.next().toString() + " ";
        }
        text = (TextView) findViewById(R.id.areaJoc);
        text.setText("Àrea: "+str);  
        
        // Boto
        ImageButton bInstalarJoc = (ImageButton) findViewById(R.id.instalar);
        bInstalarJoc.setOnClickListener( this );  
                
    
	}
	
    // Clic del boto
	public void onClick(View v) {
			
		String idJoc =  Integer.toString(bundle.getInt("idJoc"));
		String ruta =  ClicApplication.llistaJocs.cercarJoc(bundle.getInt("idJoc")).getRuta();
		
		Utils.descarregarFitxer(getApplicationContext(), ruta, idJoc);
        Utils.descomprimirFitxer(getApplicationContext(), idJoc); 
        
        Utils.copiarFitxersJoc(getApplicationContext(),idJoc);
        
        // Marquem el joc com a descarregat a la llista
		ClicApplication.llistaJocs.modificarJocADescarregat(this.bundle.getInt("idJoc"));
		
		// Sobreescribim el fitxer de jocs descarregats
		Utils.exportarJocsDescarregatsXML(getApplicationContext());
		
		//Intent intent = null;
		//intent = new Intent(this, VistaWeb.class);			
		//startActivity(intent);		
	}

}
