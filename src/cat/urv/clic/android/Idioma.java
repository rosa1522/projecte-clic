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
		this.idiomes.put("de", "alemany");
		this.idiomes.put("en", "anglès");
		this.idiomes.put("arn", "araucà");
		this.idiomes.put("eu", "basc");
		this.idiomes.put("rmq", "caló");
		this.idiomes.put("ca", "català");
		this.idiomes.put("es", "espanyol");		
		this.idiomes.put("eo", "esperanto");		
		this.idiomes.put("fr", "francès");
		this.idiomes.put("gl", "gallec");		
		this.idiomes.put("el", "grec");
		this.idiomes.put("it", "italià");		
		this.idiomes.put("la", "llatí");
		this.idiomes.put("oc", "occità");		
		this.idiomes.put("pt", "portuguès");		
		this.idiomes.put("ro", "romanès");		
		this.idiomes.put("sv", "suec");
		this.idiomes.put("zh", "xinès");
		
		this.nomsIdiomes[0] = "Tots els idiomes";
		this.nomsIdiomes[1] = "alemany";
		this.nomsIdiomes[2] = "anglès";
		this.nomsIdiomes[3] = "araucà";
		this.nomsIdiomes[4] = "basc";		
		this.nomsIdiomes[5] = "caló";		
		this.nomsIdiomes[6] = "català";
		this.nomsIdiomes[7] =  "espanyol";		
		this.nomsIdiomes[8] =  "esperanto";		
		this.nomsIdiomes[9] =  "francès";
		this.nomsIdiomes[10] =  "gallec";		
		this.nomsIdiomes[11] =  "grec";
		this.nomsIdiomes[12] =  "italià";		
		this.nomsIdiomes[13] = "llatí";
		this.nomsIdiomes[14] =  "occità";		
		this.nomsIdiomes[15] =  "portuguès";		
		this.nomsIdiomes[16] =  "romanès";		
		this.nomsIdiomes[17] =  "suec";
		this.nomsIdiomes[18] =  "xinès";
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
