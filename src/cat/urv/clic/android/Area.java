package cat.urv.clic.android;

import java.util.Hashtable;

public class Area {

	private Hashtable<String, String> arees;
	private String[] nomsArees;			// Taula que ens servirà per tenir la llista de noms ORDENADA 
	
	public Area() {
		arees = new Hashtable<String, String>();
		nomsArees = new String[10];
	}
	
	public String[] getNomsArees() {
		return nomsArees;
	}

	public void afegirTotesArees(){
		this.arees.put("tot", "Totes les àrees");
		this.arees.put("lleng", "llengües");
		this.arees.put("mat", "matemàtiques");		
		this.arees.put("soc", "ciències socials");
		this.arees.put("exp", "ciències experimentals");		
		this.arees.put("mus", "música");
		this.arees.put("vip", "visual i plàstica");
		this.arees.put("ef", "educació física");		
		this.arees.put("tec", "tecnologies");		
		this.arees.put("div", "diversos");
		
		this.nomsArees[0] = "Totes les àrees";
		this.nomsArees[1] = "llengües";
		this.nomsArees[2] = "matemàtiques";
		this.nomsArees[3] = "ciències socials";
		this.nomsArees[4] = "ciències experimentals";		
		this.nomsArees[5] = "música";		
		this.nomsArees[6] = "visual i plàstica";
		this.nomsArees[7] = "educació física";		
		this.nomsArees[8] = "tecnologies";		
		this.nomsArees[9] = "diversos";	

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