package cat.urv.clic.android;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


   
public class DescripcioJoc extends Activity implements OnClickListener{
	
	private static final int DIALOG1_KEY = 0;
	private static final int DIALOG_CONNEXIO = 0;
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
        Integer idJoc = (bundle.getInt("idJoc"));
        Joc joc = ClicApplication.llistaJocs.cercarJoc(idJoc);
        
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

        // La imatge del joc només la mostrem si està el joc descarregat
        if (joc.getDescarregat()) {    
        	File file = new File (getFilesDir() + "/" + idJoc + "/" + idJoc + ".jpg");
        	if (file.exists()) {
    	        ImageView imageView = (ImageView) findViewById(R.id.imatgeJoc);
    	        BitmapDrawable bitmap = new BitmapDrawable(getFilesDir() + "/" + idJoc + "/" + idJoc + ".jpg");
    	        imageView.setImageDrawable(bitmap);
        	}
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
			String rutaImatge =  ClicApplication.llistaJocs.cercarJoc(bundle.getInt("idJoc")).getImatge();
			
			boolean hiHaConnexio = Utils.descarregarFitxer(getApplicationContext(), clicZip, idJoc);
			if (hiHaConnexio) {
				Utils.descomprimirFitxer(getApplicationContext(), idJoc);
				
				// Descarreguem la imatge i la mostrem
				Utils.descarregarImatgeJoc(getApplicationContext(), rutaImatge, idJoc);	        
		        ImageView imageView = (ImageView) findViewById(R.id.imatgeJoc);
		        BitmapDrawable bitmap = new BitmapDrawable(getFilesDir() + "/" + idJoc + "/" + idJoc + ".jpg");
		        imageView.setImageDrawable(bitmap);
		       		        
		        Utils.creacioActivitat(getApplicationContext(),idJoc);
		        
		        // Marquem el joc com a descarregat a la llista
				ClicApplication.llistaJocs.modificarJocADescarregat(this.bundle.getInt("idJoc"));
				
				// Sobreescribim el fitxer de jocs descarregats
				Utils.exportarJocsDescarregatsXML(getApplicationContext());
				
				// Canviar la imatge del botó
				bInstalarJoc.setImageResource(R.drawable.play);
			}else{
				showDialog(DIALOG_CONNEXIO);
			}
			
			//removeDialog(DIALOG1_KEY);
		}else{
    		// Passem l'identificador del joc que volem jugar	
			Intent intent = null;
			intent = new Intent(this, VistaWeb.class);		
			intent.putExtra("idJoc",bundle.getInt("idJoc"));
			startActivity(intent);	
		}
	
	}

	 @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
	    	/*AlertDialog dialog = new AlertDialog(getApplicationContext());
	        dialog.setMessage("Siusplau esperi mentre es descarrega...");
	   
	        dialog.setCancelable(true);
	        return dialog;*/
        
            case DIALOG_CONNEXIO: {                               
                AlertDialog.Builder alert = new AlertDialog.Builder(this); 
                alert.setMessage("No hi ha connexió a Internet per poder descarregar el joc.");  
                alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {  
                      public void onClick(DialogInterface dialog, int whichButton) {}
                });  
                alert.show();
            }
        }
        return null;
    }
}




