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
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;



public class DescripcioJoc extends Activity implements OnClickListener{
	private static final int DIALOG_CONNEXIO = 1;
	private static final int DIALOG_JOC_NO_DESCARREGAT = 2;

	private Bundle bundle;
	private ImageButton bInstalarJoc;

	ProgressDialog progressDialog;
	String clicZip;
	String rutaImatge;
	String idJoc;

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
			if (str.equals("")){
				str = ClicApplication.llistaArees.cercarNomArea(it.next());
			}else{
				str = str + ", " + ClicApplication.llistaArees.cercarNomArea(it.next());
			}
		}
		text = (TextView) findViewById(R.id.areaJoc);
		text.setText(": "+str);  

		// Nivell
		it = joc.getNivellJoc().iterator();
		str = new String();
		while(it.hasNext()){
			if (str.equals("")){
				str = ClicApplication.llistaNivells.cercarNomNivell(it.next());
			}else{
				str = str + ", " + ClicApplication.llistaNivells.cercarNomNivell(it.next());
			}
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
			if (str.equals("")){
				str = ClicApplication.llistaIdiomes.cercarNomIdioma(it.next());
			}else{
				str = str + ", " + ClicApplication.llistaIdiomes.cercarNomIdioma(it.next());
			}
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
		// l'usuari no torni a donar al botó per poder jugar
		System.out.println("DESCARREGAT" + ClicApplication.llistaJocs.cercarJoc(bundle.getInt("idJoc")).getDescarregat());
		if (!ClicApplication.llistaJocs.cercarJoc(bundle.getInt("idJoc")).getDescarregat()) {
			idJoc =  Integer.toString(bundle.getInt("idJoc"));
			clicZip =  ClicApplication.llistaJocs.cercarJoc(bundle.getInt("idJoc")).getClic();
			rutaImatge =  ClicApplication.llistaJocs.cercarJoc(bundle.getInt("idJoc")).getImatge();
			
			if ( Utils.testConnexio(getApplicationContext(), clicZip, idJoc) ) {
				progressDialog = ProgressDialog.show(this, "", "Descarregant...", false, false);
				DownloadThread dt = new DownloadThread(handler);
				dt.start();		
				
			}else{
				showDialog(DIALOG_CONNEXIO);
			}			
			//removeDialog(DIALOG1_KEY);
		}else{
			// Primer ens assegurem que la carpeta del joc seleccionat existeixi
			// sinó existeix mostrem un missatge perquè se'l tornin a descarregar
			File file = new File (getFilesDir() + "/" + bundle.getInt("idJoc"));
			if (!file.exists()) {
				// Marquem el joc com a NO descarregat
				ClicApplication.llistaJocs.modificarJocANODescarregat(this.bundle.getInt("idJoc"));
				// Canviar la imatge del botó perquè tornin a descarregar-lo
				bInstalarJoc.setImageResource(R.drawable.download);
				showDialog(DIALOG_JOC_NO_DESCARREGAT);
			}else{
				// Passem l'identificador del joc que volem jugar	
				Intent intent = null;
				intent = new Intent(this, VistaWeb.class);		
				intent.putExtra("idJoc", bundle.getInt("idJoc"));
				startActivity(intent);	
			}

		}

	}

	
	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
	        case DIALOG_CONNEXIO: {                               
                AlertDialog.Builder alert = new AlertDialog.Builder(this); 
                alert.setMessage("No hi ha connexió a Internet per poder descarregar el joc.");  
                alert.setPositiveButton("Acceptar", new DialogInterface.OnClickListener() {  
                      public void onClick(DialogInterface dialog, int whichButton) {}
                });  
                alert.show();
                break;
            }
	         case DIALOG_JOC_NO_DESCARREGAT: {                               
	             AlertDialog.Builder alert = new AlertDialog.Builder(this); 
	             alert.setMessage("El joc no està disponible. Sisplau torni a descarregar-lo.");  
	             alert.setPositiveButton("Acceptar", new DialogInterface.OnClickListener() {  
	                   public void onClick(DialogInterface dialog, int whichButton) {}
	             });  
	             alert.show();
	             break;
	         }
        }
        return null;
    }

	
	// Define the Handler that receives messages from the thread and update the progress
	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			//Si ens arriba un missatge amb valor 100 es que ha acabat la descarrega
			if (msg.arg1 >= 100){
				//Desactivar el dialeg
				progressDialog.dismiss();

				// Canviar la imatge del botó
				bInstalarJoc.setImageResource(R.drawable.play);

				// Posar la imatge de Jugar
				ImageView imageView = (ImageView) findViewById(R.id.imatgeJoc);
				BitmapDrawable bitmap = new BitmapDrawable(getFilesDir() + "/" + idJoc + "/" + idJoc + ".jpg");
				imageView.setImageDrawable(bitmap);
			}
		}
	};

	// Nested class that performs Data Downloading 
	private class DownloadThread extends Thread {
		Handler mHandler;

		DownloadThread(Handler mHandler) { this.mHandler=mHandler; }

		public void run() {
			Utils.descarregarFitxer(getApplicationContext(), clicZip, idJoc);
			Utils.descomprimirFitxer(getApplicationContext(), idJoc);

			// Descarreguem la imatge i la mostrem
			Utils.descarregarImatgeJoc(getApplicationContext(), rutaImatge, idJoc);	        
			Utils.creacioActivitat(getApplicationContext(),idJoc);

			// Marquem el joc com a descarregat a la llista
			ClicApplication.llistaJocs.modificarJocADescarregat(bundle.getInt("idJoc"));

			// Sobreescribim el fitxer de jocs descarregats
			Utils.exportarJocsDescarregatsXML(getApplicationContext());

			// Enviem un missatge per avisar que la tasca s'ha acabat
			// La resta de coses (Desactivar dialog, canviar botons, etc... ho fara el handler).
			Message msg = mHandler.obtainMessage();
			msg.arg1 = 100;
			mHandler.sendMessage(msg);
		}
	}
}




