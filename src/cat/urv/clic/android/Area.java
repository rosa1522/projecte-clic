package cat.urv.clic.android;

import java.util.Hashtable;

public class Area {

	private Hashtable<String, String> arees;
	private String[] nomsArees;			// Taula que ens servirà per tenir-la llista de noms ORDENADA 
	
	public Area() {
		arees = new Hashtable<String, String>();
		nomsArees = new String[10];
	}
	
	public String[] getNomsArees() {
		return nomsArees;
	}

	public void afegirTotesArees(){
		this.arees.put("tot", "Totes les àrees");
		this.arees.put("lleng", "Llengües");
		this.arees.put("mat", "Matemàtiques");		
		this.arees.put("soc", "Ciències socials");
		this.arees.put("exp", "Ciències experimentals");		
		this.arees.put("mus", "Música");
		this.arees.put("vip", "Visual i plàstica");
		this.arees.put("ef", "Educació física");		
		this.arees.put("tec", "Tecnologies");		
		this.arees.put("div", "Diversos");
		
		this.nomsArees[0] = "Totes les àrees";
		this.nomsArees[1] = "Llengües";
		this.nomsArees[2] = "Matemàtiques";
		this.nomsArees[3] = "Ciències socials";
		this.nomsArees[4] = "Ciències experimentals";		
		this.nomsArees[5] = "Música";		
		this.nomsArees[6] = "Visual i plàstica";
		this.nomsArees[7] = "Educació física";		
		this.nomsArees[8] = "Tecnologies";		
		this.nomsArees[9] = "Diversos";	

	}

	public String cercarIdArea(String nomArea){
		String idArea = null;        		
		for(String id: arees.keySet()){			
			if (arees.get(id).compareTo(nomArea) == 0)
				idArea = id;
		}		
		return idArea;
	}
	
	public String cercarNomArea(String id){
		return arees.get(id);
	}
}