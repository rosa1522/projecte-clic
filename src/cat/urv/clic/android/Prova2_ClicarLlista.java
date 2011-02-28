package cat.urv.clic.android;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class Prova2_ClicarLlista  extends Activity {

	 /** Called when the activity is first created. */

	 ListView list;
	 ArrayAdapter<String> adapter;
	 static final String[] names = {"Italo", "Helio", "Marcelo", "Francisco", "Raimundo"};
	 

	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	
		 super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);

		 adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
		 System.out.println("PASSA1");
		 list = (ListView)findViewById(R.xml.llistajocs);
		 System.out.println("PASSA2");
		 list.setAdapter(adapter);
		 System.out.println("PASSA3");
		 list.setItemsCanFocus(true);
		 System.out.println("PASSA4");
		 list.setOnItemClickListener(new OnItemClickListener(){

			 	public void onItemClick(AdapterView<?> arg0, View v, int i,long id) {

			 	String str = list.getItemAtPosition(i).toString();
			 	

			 	}

	 });

	 }

}
