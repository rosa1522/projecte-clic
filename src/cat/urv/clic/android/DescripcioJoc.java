package cat.urv.clic.android;

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
    }
	
    // Clic del boto
	public void onClick(View v) {
		Intent intent = null;
		intent = new Intent(this, VistaWeb.class);			
		startActivity(intent);		
	}
}
