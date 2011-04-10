package cat.urv.clic.android;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Nivells {

	private Hashtable<String, String> nivells;
	private Array nomsNivells[];
	
	public Nivells() {
		nivells = new Hashtable<String, String>();
	}
	
	public void afegirNivell(String clau, String valor){
		this.nivells.put(clau, valor);
	}	
	
	public String cercarNivellAmbDescrip(String str){
		return nivells.get(str);
	}
	
	public void afegirTotsNivells(){
		this.nivells.put("tot", "Tots els nivells");
		this.nivells.put("ei", "Infantil (3-6)");
		this.nivells.put("ep", "Primària (6-12)");
		this.nivells.put("eso", "Secundària (12-16)");
		this.nivells.put("ba", "Batxillerat (16-18)");
	}
	
	public List<String> nomsNivells(){
		List<String> llistaNomsNivells = new ArrayList<String>();
		for(String nom: nivells.values()){
			llistaNomsNivells.add(nom);
		}		
		return llistaNomsNivells;
	}
	
}
