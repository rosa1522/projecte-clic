package cat.urv.clic.android;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class IniciAplicacio extends Activity {
		
	private ListView list;

	public class MyCustomAdapter extends ArrayAdapter<String> {
		ArrayList<String> objects;
		
		public MyCustomAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
			super(context, textViewResourceId, objects);
			this.objects = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater=getLayoutInflater();
			// joc_entry es on hi ha la vista que hi haur� a cada fila de la llista
			View row = inflater.inflate(R.layout.joc_entry, parent, false);
			
			// Posem el nom del joc a la vista de la fila de la llista
			TextView label = (TextView)row.findViewById(R.id.nomJoc);
			label.setTextSize(17);
			label.setText("  " + objects.get(position));
			
			Joc joc = ClicApplication.llistaJocs.cercarJoc(objects.get(position));
			
			// Posem icones a la llista al costat de cada nom del joc
			ImageView icon;
						
			// Si el joc nom�s t� un idioma posem l'icona amb les inicials de l'idioma
			// sino, posem l'icona sense cap inicial
			icon = (ImageView)row.findViewById(R.id.imatgeIdioma);
			if (joc.getLlengua().size()>1 ) {
				icon.setImageResource(R.drawable.idioma_no);
			} else {
				if(joc.getLlengua().get(0).equals("de")) icon.setImageResource(R.drawable.idioma_al);				
				else if(joc.getLlengua().get(0).equals("en")) icon.setImageResource(R.drawable.idioma_an);				
				else if(joc.getLlengua().get(0).equals("arn")) icon.setImageResource(R.drawable.idioma_ar);				
				else if(joc.getLlengua().get(0).equals("eu")) icon.setImageResource(R.drawable.idioma_ba);				
				else if(joc.getLlengua().get(0).equals("rmq")) icon.setImageResource(R.drawable.idioma_cl);				
				else if(joc.getLlengua().get(0).equals("ca")) icon.setImageResource(R.drawable.idioma_ca);				
				else if(joc.getLlengua().get(0).equals("es")) icon.setImageResource(R.drawable.idioma_es);				
				else if(joc.getLlengua().get(0).equals("eo")) icon.setImageResource(R.drawable.idioma_eo);				
				else if(joc.getLlengua().get(0).equals("fr")) icon.setImageResource(R.drawable.idioma_fr);				
				else if(joc.getLlengua().get(0).equals("gl")) icon.setImageResource(R.drawable.idioma_ga);				
				else if(joc.getLlengua().get(0).equals("el")) icon.setImageResource(R.drawable.idioma_gr);				
				else if(joc.getLlengua().get(0).equals("it")) icon.setImageResource(R.drawable.idioma_it);				
				else if(joc.getLlengua().get(0).equals("la")) icon.setImageResource(R.drawable.idioma_ll);				
				else if(joc.getLlengua().get(0).equals("oc")) icon.setImageResource(R.drawable.idioma_oc);				
				else if(joc.getLlengua().get(0).equals("pt")) icon.setImageResource(R.drawable.idioma_po);				
				else if(joc.getLlengua().get(0).equals("ro")) icon.setImageResource(R.drawable.idioma_ro);				
				else if(joc.getLlengua().get(0).equals("sv")) icon.setImageResource(R.drawable.idioma_su);				
				else if(joc.getLlengua().get(0).equals("zh")) icon.setImageResource(R.drawable.idioma_xi);				
				else if(joc.getLlengua().get(0).equals("tot")) icon.setImageResource(R.drawable.idioma_tot);	
				else icon.setImageResource(R.drawable.idioma_no);
			}
			
			// Si el joc nom�s t� un nivell posem l'icona amb les inicials del nivell
			// sino, posem l'icona sense cap inicial
			icon = (ImageView)row.findViewById(R.id.imatgeNivell);
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
			
			// Si el joc nom�s t� una �rea posem l'icona amb les inicials de l'�rea
			// sino, posem l'icona sense cap inicial
			icon = (ImageView)row.findViewById(R.id.imatgeArea);
			if (joc.getAreaJoc().size()>1 ) {
				icon.setImageResource(R.drawable.area_no);
			} else {
				if(joc.getAreaJoc().get(0).equals("lleng")) icon.setImageResource(R.drawable.area_ll);				
				else if(joc.getAreaJoc().get(0).equals("mat")) icon.setImageResource(R.drawable.area_ma);				
				else if(joc.getAreaJoc().get(0).equals("soc")) icon.setImageResource(R.drawable.area_so);				
				else if(joc.getAreaJoc().get(0).equals("exp")) icon.setImageResource(R.drawable.area_ex);				
				else if(joc.getAreaJoc().get(0).equals("mus")) icon.setImageResource(R.drawable.area_mu);	
				else if(joc.getAreaJoc().get(0).equals("vip")) icon.setImageResource(R.drawable.area_vi);	
				else if(joc.getAreaJoc().get(0).equals("ef")) icon.setImageResource(R.drawable.area_ef);	
				else if(joc.getAreaJoc().get(0).equals("tec")) icon.setImageResource(R.drawable.area_te);	
				else if(joc.getAreaJoc().get(0).equals("div")) icon.setImageResource(R.drawable.area_di);	
				else if(joc.getAreaJoc().get(0).equals("tot")) icon.setImageResource(R.drawable.area_tot);	
				else icon.setImageResource(R.drawable.area_no);
			}			

			return row;
		}
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	try {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.inici);
	        
	        //////////////////////////////////////////
	        // Boto
	        ImageButton bAfegirJoc = (ImageButton) findViewById(R.id.afegirjoc);
	        // Clic del boto
    		final Intent intentBoto = new Intent(this, LlistaJocsJClic.class);			
	        bAfegirJoc.setOnClickListener( new OnClickListener(){
		    	public void onClick(View v) {
		    		// Li passem la llista de tots els jocs
		    		ArrayList<String> llistaJocs = (ArrayList<String>) ClicApplication.llistaJocs.construirLlistaJocs(false);
		    		intentBoto.putStringArrayListExtra("llistaJocs",llistaJocs);

		    		startActivity(intentBoto);		
		    	}	        	
	        });  
	    
	        //////////////////////////////////////////
	        // Llista
	        list = (ListView) findViewById(R.id.list_descarregats);         
	        // Demanem la llista de jocs DESCARREGATS
	        ArrayList<String> llistaJocs = ClicApplication.llistaJocs.construirLlistaJocs(true);	   
	        MyCustomAdapter adp = new MyCustomAdapter(this,android.R.layout.simple_list_item_1, llistaJocs);
	        list.setAdapter(adp);       	   	
						
	        // Clic de la llista
	        final Intent intent = new Intent(this, DescripcioJoc.class);	
	        list.setOnItemClickListener( new OnItemClickListener(){
	        	public void onItemClick(AdapterView<?> arg0, View v, int i, long id) {
	        		String str = (String) list.getItemAtPosition(i);
	        		
	        		int idJoc = ClicApplication.llistaJocs.cercarJoc(str).getIdentificador();
	        		
	        		// Passem l'identificador a l'activity DescripcioJoc
        			intent.putExtra("idJoc",idJoc);
        			startActivity(intent);		        					        	
	        	}
	        });
	        
        }catch (Exception e){
            e.printStackTrace();
        }
    }
	
    @Override
    public void onRestart(){
    	super.onRestart();
    	
    	// S'acualitza la llista de jocs descarregats
    	ArrayList<String> llistaJocs = ClicApplication.llistaJocs.construirLlistaJocs(true);	   
        MyCustomAdapter adp = new MyCustomAdapter(this,android.R.layout.simple_list_item_1, llistaJocs);
        list.setAdapter(adp); 
    }
    
}

