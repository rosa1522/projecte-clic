package cat.urv.clic.android;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class LlistaClics extends ListActivity {
    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.llista);
//    }
    
    
    private Object[] fruits = {"Orange", "Apple", "Pear"}; 
    private ArrayAdapter<TextView> adp = null; 
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	
    	adp = new ArrayAdapter(this,R.id.listItem,fruits); 
    	setListAdapter(adp); 
    	setContentView(R.layout.llista); 
    }
    
}
