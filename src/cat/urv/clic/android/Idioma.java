package cat.urv.clic.android;

import java.util.Hashtable;

public class Idioma {

	private Hashtable<String, String> idiomes;
	private String[] nomsIdiomes;			// Taula que ens servir� per tenir-la llista de noms ORDENADA 
	
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
		this.idiomes.put("en", "Angl�s");
		this.idiomes.put("arn", "Arauc�");
		this.idiomes.put("eu", "Basc");
		this.idiomes.put("rmq", "Cal�");
		this.idiomes.put("ca", "Catal�");
		this.idiomes.put("es", "Espanyol");		
		this.idiomes.put("eo", "Esperanto");		
		this.idiomes.put("fr", "Franc�s");
		this.idiomes.put("gl", "Gallec");		
		this.idiomes.put("el", "Grec");
		this.idiomes.put("it", "Itali�");		
		this.idiomes.put("la", "Llat�");
		this.idiomes.put("oc", "Occit�");		
		this.idiomes.put("pt", "Portugu�s");		
		this.idiomes.put("ro", "Roman�s");		
		this.idiomes.put("sv", "Suec");
		this.idiomes.put("zh", "Xin�s");
		
		this.nomsIdiomes[0] = "Tots els idiomes";
		this.nomsIdiomes[1] = "Alemany";
		this.nomsIdiomes[2] = "Angl�s";
		this.nomsIdiomes[3] = "Arauc�";
		this.nomsIdiomes[4] = "Basc";		
		this.nomsIdiomes[5] = "Cal�";		
		this.nomsIdiomes[6] = "Catal�";
		this.nomsIdiomes[7] = "Espanyol";		
		this.nomsIdiomes[8] = "Esperanto";		
		this.nomsIdiomes[9] = "Franc�s";
		this.nomsIdiomes[10] = "Gallec";		
		this.nomsIdiomes[11] = "Grec";
		this.nomsIdiomes[12] = "Itali�";		
		this.nomsIdiomes[13] = "Llat�";
		this.nomsIdiomes[14] = "Occit�";		
		this.nomsIdiomes[15] = "Portugu�s";		
		this.nomsIdiomes[16] = "Roman�s";		
		this.nomsIdiomes[17] = "Suec";
		this.nomsIdiomes[18] = "Xin�s";
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
