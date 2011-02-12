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
    }

    // Captura i gesti— dels events dels botons
	public void onClick(View v) {
		Intent intent = new Intent(this, LlistaClics.class);
		startActivity(intent);		
	}
}
