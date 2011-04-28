package cat.urv.clic.android;

import java.text.SimpleDateFormat;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;


public class DescripcioJoc extends Activity implements OnClickListener{
	
	//ProgressDialog mDialog1;
	private Bundle bundle;
	private ImageButton bInstalarJoc;
	
	//private static final int DIALOG1_KEY = 0;
	 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descripciojoc);
        
        // Capturem la informacio que conte l'intent
        bundle = getIntent().getExtras();
        Joc joc = ClicApplication.llistaJocs.cercarJoc(bundle.getInt("idJoc"));
        
        TextView text = (TextView) findViewById(R.id.titolJoc);
        text.setText(joc.getNom());  
        
        
        // Area
        Iterator<String>it = joc.getAreaJoc().iterator();
        String str = new String();
        while(it.hasNext()){
        	str = str + ClicApplication.llistaArees.cercarNomArea(it.next()) + " ";
        }
        text = (TextView) findViewById(R.id.areaJoc);
        text.setText(": "+str);  
        
        // Nivell
        it = joc.getNivellJoc().iterator();
        str = new String();
        while(it.hasNext()){
        	str = str + ClicApplication.llistaNivells.cercarNomNivell(it.next()) + " ";
        }
        text = (TextView) findViewById(R.id.nivellJoc);
        text.setText(": "+ str);  
                
        // Data        
        SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yy");    	
        text = (TextView) findViewById(R.id.dataPublicacio);
        text.setText(": "+ formatData.format(joc.getDataPublicacio()));
        
        // Llengua
        it = joc.getLlengua().iterator();
        str = new String();
        while(it.hasNext()){
        	str = str +  ClicApplication.llistaIdiomes.cercarNomIdioma(it.next()) + " ";
        }
        text = (TextView) findViewById(R.id.llengua);
        text.setText(": "+ str);
        
        // Autor
        text = (TextView) findViewById(R.id.autor);
        text.setText(": "+joc.getAutors().toString().trim());
 
        // Centre        
        if (joc.getCentre().toString().trim().compareTo("") == 0) {
        	// Si no hi ha cap centre seleccionat posem invisible el títol del centre
        	text = (TextView) findViewById(R.id.nomcentre);
        	text.setVisibility(View.INVISIBLE);
        	text = (TextView) findViewById(R.id.centre);
        	text.setVisibility(View.INVISIBLE);
        }else{
        	text = (TextView) findViewById(R.id.centre);
        	text.setText(": "+joc.getCentre().toString().trim());
        } 

        
        // Boto
        bInstalarJoc = (ImageButton) findViewById(R.id.instalar);
        if (joc.getDescarregat()) {
        	// Si el joc ja el tenim descarregat li posem al boto la imatge de jugar
        	bInstalarJoc.setImageResource(R.drawable.play);
        };
        bInstalarJoc.setOnClickListener( this );  
                
    
	}	
	
    // Clic del boto
	public void onClick(View v) {
		
		// Si el joc no està instal·lat, l'hem de descarregat i canviar la imatge del botó perquè
		// l'usuario torni a donar al botó per poder jugar
		if (!ClicApplication.llistaJocs.cercarJoc(bundle.getInt("idJoc")).getDescarregat()) {
			//showDialog(DIALOG1_KEY);
			String idJoc =  Integer.toString(bundle.getInt("idJoc"));
			String clicZip =  ClicApplication.llistaJocs.cercarJoc(bundle.getInt("idJoc")).getClic();
			
			Utils.descarregarFitxer(getApplicationContext(), clicZip, idJoc);
			Utils.descomprimirFitxer(getApplicationContext(), idJoc);
	                
	        Utils.creacioActivitat(getApplicationContext(),idJoc);
	        
	        // Marquem el joc com a descarregat a la llista
			ClicApplication.llistaJocs.modificarJocADescarregat(this.bundle.getInt("idJoc"));
			
			// Sobreescribim el fitxer de jocs descarregats
			Utils.exportarJocsDescarregatsXML(getApplicationContext());
			
			// Canviar la imatge del botó
			bInstalarJoc.setImageResource(R.drawable.play);
			
			//removeDialog(DIALOG1_KEY);
		}else{
			Intent intent = null;
			intent = new Intent(this, VistaWeb.class);			
			startActivity(intent);	
		}
	
	}

	/* @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG1_KEY: {
                ProgressDialog dialog = new ProgressDialog(this);
                dialog.setMessage("Siusplau esperi mentre es descarrega...");
                dialog.setIndeterminate(true);
                dialog.setCancelable(true);
                return dialog;
            }
        }
        return null;
    }*/
}
