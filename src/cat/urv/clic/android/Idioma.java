package cat.urv.clic.android;

import java.util.Hashtable;

public class Idioma {

	private Hashtable<String, String> idiomes;
	private String[] nomsIdiomes;			// Taula que ens servirà per tenir la llista de noms ORDENADA 
	
	public Idioma() {
		idiomes = new Hashtable<String, String>();
		nomsIdiomes = new String[19];
	}
	
	public String[] getNomsIdiomes() {
		return nomsIdiomes;
	}

	public void afegirTotsIdiomes(){
		this.idiomes.put("tot", "Tots els idiomes");
		this.idiomes.put("de", "Alemany");
		this.idiomes.put("en", "Anglès");
		this.idiomes.put("arn", "Araucà");
		this.idiomes.put("eu", "Basc");
		this.idiomes.put("rmq", "Caló");
		this.idiomes.put("ca", "Català");
		this.idiomes.put("es", "Espanyol");		
		this.idiomes.put("eo", "Esperanto");		
		this.idiomes.put("fr", "Francès");
		this.idiomes.put("gl", "Gallec");		
		this.idiomes.put("el", "Grec");
		this.idiomes.put("it", "Italià");		
		this.idiomes.put("la", "Llatí");
		this.idiomes.put("oc", "Occità");		
		this.idiomes.put("pt", "Portuguès");		
		this.idiomes.put("ro", "Romanès");		
		this.idiomes.put("sv", "Suec");
		this.idiomes.put("zh", "Xinès");
		
		this.nomsIdiomes[0] = "Tots els idiomes";
		this.nomsIdiomes[1] = "Alemany";
		this.nomsIdiomes[2] = "Anglès";
		this.nomsIdiomes[3] = "Araucà";
		this.nomsIdiomes[4] = "Basc";		
		this.nomsIdiomes[5] = "Caló";		
		this.nomsIdiomes[6] = "Català";
		this.nomsIdiomes[7] = "Espanyol";		
		this.nomsIdiomes[8] = "Esperanto";		
		this.nomsIdiomes[9] = "Francès";
		this.nomsIdiomes[10] = "Gallec";		
		this.nomsIdiomes[11] = "Grec";
		this.nomsIdiomes[12] = "Italià";		
		this.nomsIdiomes[13] = "Llatí";
		this.nomsIdiomes[14] = "Occità";		
		this.nomsIdiomes[15] = "Portuguès";		
		this.nomsIdiomes[16] = "Romanès";		
		this.nomsIdiomes[17] = "Suec";
		this.nomsIdiomes[18] = "Xinès";
	}

	public String cercarIdIdioma(String nomIdioma){
		String idIdioma = null;        		
		for(String id: idiomes.keySet()){			
			if (idiomes.get(id).compareTo(nomIdioma) == 0)
				idIdioma = id;
		}		
		return idIdioma;
	}
	
	public String cercarNomIdioma(String id){
		return idiomes.get(id);
	}
}
