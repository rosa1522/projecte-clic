package cat.urv.clic.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Inici extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button nextButton = (Button) findViewById(R.id.next);
        nextButton.setOnClickListener( this );  
        
        Button nextButtonView = (Button) findViewById(R.id.vistaweb);
        nextButtonView.setOnClickListener( this );   
    }

    // Captura i gestió dels events dels botons
	public void onClick(View v) {
		Intent intent = null;
		if (v.getId() == R.id.next)
		{
			intent = new Intent(this, LlistaClics.class);
		}else if (v.getId() == R.id.vistaweb) {
			//intent = new Intent(this, VistaWeb.class);
			//intent = new Intent(this, Vista.class);
			intent = new Intent(this, Prova.class);
		}
		
		startActivity(intent);		
	}
}
