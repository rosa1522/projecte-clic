package cat.urv.clic.android;

import java.util.Hashtable;

public class Nivell {

	private Hashtable<String, String> nivells;
	private String[] nomsNivells;			// Taula que ens servirà per tenir la llista de noms ORDENADA
	
	public Nivell() {
		nivells = new Hashtable<String, String>();
		nomsNivells = new String[5];
	}
	
	public String[] getNomsNivells() {
		return nomsNivells;
	}

	public void afegirTotsNivells(){
		this.nivells.put("tot", "Tots els nivells");
		this.nivells.put("ei", "infantil (3-6)");
		this.nivells.put("ep", "primària (6-12)");
		this.nivells.put("eso", "secundària (12-16)");
		this.nivells.put("ba", "batxillerat (16-18)");

		this.nomsNivells[0] = "Tots els nivells";
		this.nomsNivells[1] = "infantil (3-6)";
		this.nomsNivells[2] = "primària (6-12)";
		this.nomsNivells[3] = "secundària (12-16)";
		this.nomsNivells[4] = "batxillerat (16-18)";
	}
	
	public String cercarIdNivell(String nomNivell){
		String idNivell = null;        		
		for(String id: nivells.keySet()){			
			if (nivells.get(id).compareTo(nomNivell) == 0)
				idNivell = id;
		}		
		return idNivell;
	}
	
	public String cercarNomNivell(String id){
		return nivells.get(id);
	}
	
}
