package cat.urv.clic.android;

import java.util.Hashtable;

public class Area {

	private Hashtable<String, String> arees;
	private String[] nomsArees;			// Taula que ens servir� per tenir-la llista de noms ORDENADA 
	
	public Area() {
		arees = new Hashtable<String, String>();
		nomsArees = new String[10];
	}
	
	public String[] getNomsArees() {
		return nomsArees;
	}

	public void afegirTotesArees(){
		this.arees.put("tot", "Totes les �rees");
		this.arees.put("lleng", "Lleng�es");
		this.arees.put("mat", "Matem�tiques");		
		this.arees.put("soc", "Ci�ncies socials");
		this.arees.put("exp", "Ci�ncies experimentals");		
		this.arees.put("mus", "M�sica");
		this.arees.put("vip", "Visual i pl�stica");
		this.arees.put("ef", "Educaci� f�sica");		
		this.arees.put("tec", "Tecnologies");		
		this.arees.put("div", "Diversos");
		
		this.nomsArees[0] = "Totes les �rees";
		this.nomsArees[1] = "Lleng�es";
		this.nomsArees[2] = "Matem�tiques";
		this.nomsArees[3] = "Ci�ncies socials";
		this.nomsArees[4] = "Ci�ncies experimentals";		
		this.nomsArees[5] = "M�sica";		
		this.nomsArees[6] = "Visual i pl�stica";
		this.nomsArees[7] = "Educaci� f�sica";		
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