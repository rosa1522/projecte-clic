package cat.urv.clic.android;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class DescripcioJoc extends Activity implements OnClickListener{
	
	// ARREGLAR LA VARIABLE
	public static String name;
	private Intent intent = null;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descripciojoc);
        
        TextView text = (TextView) findViewById(R.id.titolJoc);
        text.setText(name);  
        
        // Boto
        ImageButton bInstalarJoc = (ImageButton) findViewById(R.id.instalar);
        bInstalarJoc.setOnClickListener( this );  
    
        
        descarregarFitxer("http://clic.xtec.cat/projects/dinosaur/jclic/dinosaur.jclic.zip","dinosaures");
        descomprimirFitxer("dinosaures");
	
	}
	
    // Clic del boto
	public void onClick(View v) {
		Intent intent = null;
		intent = new Intent(this, VistaWeb.class);			
		startActivity(intent);		
	}
	
	private void descarregarFitxer(String ruta, String nomFitxer) {
		try {
			// Url del joc
			URL url = new URL(ruta);
			
			// Obrim la connexió
			URLConnection urlCon = url.openConnection();

			// S'obté l'inputStream del joc i s'obre el zip local
			InputStream is = urlCon.getInputStream();
			
			File f = new File(getFilesDir() + "/" + nomFitxer + ".zip");
			FileOutputStream fos = new FileOutputStream(f);
		
			// Lectura del fitxer .zip
			byte[] array = new byte[is.available()]; // Buffer temporal de lectura
			int leido = is.read(array);
			while (leido > 0) {
				fos.write(array, 0, leido);
				leido = is.read(array);
			}

			// Tanquem la connexio i el zip
			is.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void descomprimirFitxer(String nomFitxer){
		final int BUFFER = 2048;
		try {
			BufferedOutputStream dest = null;
			FileInputStream fis = new FileInputStream(getFilesDir()+ "/" + nomFitxer + ".zip");
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
			ZipEntry entry;
			
			
			// Creem la carpeta perquè es guardi els fitxers del zip
			File directori = new File(getFilesDir()+ "/" + nomFitxer);
			directori.mkdir();
			
			while((entry = zis.getNextEntry()) != null) {
				System.out.println("Extracting: " +entry);
				
				int count;
				byte data[] = new byte[BUFFER];
				
				if (entry.isDirectory()) {
					// Si entrem aquí vol dir que tenim una carpeta dintre del zip i llavors l'hem de crear
					// com un directori i no com un fitxer
					File carpeta = new File(directori+ "/" + entry.getName());
					carpeta.mkdir();
				}else{
					// Escrivim els fitxers en local
					FileOutputStream fos = new FileOutputStream(getFilesDir()+ "/" + nomFitxer + "/" +entry.getName());
					dest = new BufferedOutputStream(fos, BUFFER);
					while ((count = zis.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, count);
					}
					dest.flush();
					dest.close();
				}

			}
			zis.close();
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
}
