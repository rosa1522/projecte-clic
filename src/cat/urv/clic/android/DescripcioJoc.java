package cat.urv.clic.android;

import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class DescripcioJoc extends Activity implements OnClickListener{
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descripciojoc);
        
        // Capturem la informacio que conte l'intent
        Bundle bundle = getIntent().getExtras();
        Joc joc = ClicApplication.llistaJocs.cercarJoc(bundle.getInt("idJoc"));
        
        TextView text = (TextView) findViewById(R.id.titolJoc);
        text.setText(joc.getNom());  
        
        text = (TextView) findViewById(R.id.dataPublicacio);
        text.setText(joc.getDataPublicacio().toString());  
        
        Iterator<String> it = joc.getLlengua().iterator();
        String str = new String();
        while(it.hasNext()){
        	//str = str + it.next().toString();
        	System.out.println("11111LLENGUA: " + it.next().toString());
        }
        text = (TextView) findViewById(R.id.llengua);
        text.setText(str);  
 
        text = (TextView) findViewById(R.id.nivellJoc);
       // text.setText();  
        
        text = (TextView) findViewById(R.id.areaJoc);
        //text.setText(joc.getAreaJoc());  
        
        // Boto
        ImageButton bInstalarJoc = (ImageButton) findViewById(R.id.instalar);
        bInstalarJoc.setOnClickListener( this );  
    
	}
	
    // Clic del boto
	public void onClick(View v) {
       // descarregarFitxer("http://clic.xtec.cat/projects/dinosaur/jclic/dinosaur.jclic.zip","dinosaures");
       // descomprimirFitxer("dinosaures"); 
        
		Intent intent = null;
		intent = new Intent(this, VistaWeb.class);			
		startActivity(intent);		
	}

}
