package cat.urv.clic.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class LlistaJocsJClic extends Activity{
	
	private Intent intent = null;
	private Bundle bundle;
	private ListView list; 
	
	public class MyCustomAdapter extends ArrayAdapter<String> {
		ArrayList<String> objects;
		
		public MyCustomAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
			super(context, textViewResourceId, objects);
			this.objects = objects;
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			//return super.getView(position, convertView, parent);
			
			LayoutInflater inflater=getLayoutInflater();
			View row=inflater.inflate(R.layout.joc_entry, parent, false);
			TextView label=(TextView)row.findViewById(R.id.nomJoc);
			label.setTextSize(25);
			label.setText(objects.get(position));
			
			//Poc optim!!  Estic accdint a la hash per a tots els jocs!!
			Joc joc = ClicApplication.llistaJocs.cercarJoc(objects.get(position));
			
			
			//Posem icones: Ara mateix vermell es si hi ha 1 sol valor i verd si n'hi ha varis
			ImageView icon=(ImageView)row.findViewById(R.id.imatgeIdioma);
			if (joc.getLlengua().size()>1 ) {
				icon.setImageResource(R.drawable.cuadre2);
			} else {
				icon.setImageResource(R.drawable.cuadre);				
			}
			
			icon=(ImageView)row.findViewById(R.id.imatgeNivell);
			if (joc.getNivellJoc().size()>1 ) {
				icon.setImageResource(R.drawable.nivell_no);
			} else {
				if(joc.getNivellJoc().get(0).equals("ba")) icon.setImageResource(R.drawable.nivell_ba);				
				else if(joc.getNivellJoc().get(0).equals("ei")) icon.setImageResource(R.drawable.nivell_ei);				
				else if(joc.getNivellJoc().get(0).equals("ep")) icon.setImageResource(R.drawable.nivell_ep);				
				else if(joc.getNivellJoc().get(0).equals("eso")) icon.setImageResource(R.drawable.nivell_es);				
				else if(joc.getNivellJoc().get(0).equals("tot")) icon.setImageResource(R.drawable.nivell_tot);	
				else icon.setImageResource(R.drawable.nivell_no);
			}
			
			icon=(ImageView)row.findViewById(R.id.imatgeArea);
			if (joc.getAreaJoc().size()>1 ) {
				icon.setImageResource(R.drawable.cuadre2);
			} else {
				icon.setImageResource(R.drawable.cuadre);				
			}			

			return row;
		}
	}
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	try {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.llistajocsjclic);
	        
	        // Agafem la llista de jocs que ens han passat per parametre
	        bundle = getIntent().getExtras();
	        ArrayList<String> llistaJocs = bundle.getStringArrayList("llistaJocs");
	        
	        // Llista
	        list = (ListView) findViewById(R.id.list_jocs);
	        	                
	        // Demanem la llista de jocs NO DESCARREGATS  
	        MyCustomAdapter adp = new MyCustomAdapter(this,android.R.layout.simple_list_item_1, llistaJocs);
	        list.setAdapter(adp);     
	 
	        // Clic de la llista
	        intent = new Intent(this, DescripcioJoc.class);	
	        list.setOnItemClickListener( new OnItemClickListener(){
	        	public void onItemClick(AdapterView<?> arg0, View v, int i, long id) {
	        		String str = (String) list.getItemAtPosition(i);
	        		int idJoc = ClicApplication.llistaJocs.cercarJoc(str).getIdentificador();
	
        			intent.putExtra("idJoc",idJoc);
        			startActivity(intent);		        					        	
	        	}
	        });
	        
	        // Boto
	        ImageButton bCercarJoc = (ImageButton) findViewById(R.id.cercarjocLlista);
	        // Clic del boto
    		final Intent intentBoto = new Intent(this, CercarJocs.class);			
    		bCercarJoc.setOnClickListener( new OnClickListener(){
		    	public void onClick(View v) {
		    		startActivity(intentBoto);		
		    	}	        	
	        });  
	        
	        
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void onRestart(){
    	super.onRestart();
    	List<String> llistaJocs = ClicApplication.llistaJocs.construirLlistaJocs(false);	   
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, llistaJocs);
        list.setAdapter(adp); 
    }
}

