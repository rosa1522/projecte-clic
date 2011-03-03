package cat.urv.clic.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class DescripcioJoc extends Activity{
	
	// ARREGLAR LA VARIABLE
	public static String name;
	

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descripciojoc);
        
        TextView text = (TextView) findViewById(R.id.titolJoc);
        text.setText(name);  
    }

}
